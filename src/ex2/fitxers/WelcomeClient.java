package ex2.fitxers;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

public class WelcomeClient {

    private Socket socket;
    private OutputStream out;
    private InputStream in;

    private static final String DOWNLOAD_DIRECTORY = "C:\\Users\\jiins8\\OneDrive\\Escritorio\\prueba";

    public WelcomeClient(String serverAddress, int port) {
        try {
            socket = new Socket(serverAddress, port);
            System.out.println("Connected to server");
            in = socket.getInputStream();
            out = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getFileList() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String fileName;
            while ((fileName = reader.readLine()) != null) {
                if ("END_OF_LIST".equals(fileName)) {
                    break;
                }
                System.out.println("File available: " + fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void requestAndReceiveFile() {
        try {
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedInputStream in = new BufferedInputStream(socket.getInputStream());

            System.out.print("Enter the name of the file you want to download: ");
            String fileName = userInput.readLine();
            out.println(fileName);

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            int byteRead;
            while ((byteRead = in.read()) != -1) {
                buffer.write(byteRead);
                if (byteRead == '\n') {
                    break; // Reached end of line
                }
            }

            int fileSize = Integer.parseInt(buffer.toString().trim());
            if (fileSize > 0) {
                byte[] fileBytes = new byte[fileSize];
                int totalBytesRead = 0;
                while (totalBytesRead < fileSize) {
                    int bytesRead = in.read(fileBytes, totalBytesRead, fileSize - totalBytesRead);
                    if (bytesRead == -1) {
                        break; // Reached end of stream
                    }
                    totalBytesRead += bytesRead;
                }

                String downloadPath = DOWNLOAD_DIRECTORY + File.separator + fileName;
                File downloadFile = new File(downloadPath);

                // Ensure download directory exists
                File downloadDir = new File(DOWNLOAD_DIRECTORY);
                if (!downloadDir.exists()) {
                    downloadDir.mkdirs(); // Create the directory and any missing parent directories
                }

                if (totalBytesRead == fileSize) {
                    Files.write(downloadFile.toPath(), fileBytes);
                    System.out.println("File downloaded successfully.");
                } else {
                    System.out.println("Error downloading file.");
                }
            } else {
                System.out.println("File not found on server.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            in.close();
            out.close();
            socket.close();
            System.out.println("Connection closed.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
