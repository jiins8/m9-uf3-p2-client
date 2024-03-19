package ex2.endivina_num;

import java.io.*;
import java.net.Socket;

public class WelcomeClient {
    private Socket socket;
    private OutputStream out;
    private InputStream in;

    public void connect(String ipAddress, int port) {
        try {
            socket = new Socket(ipAddress, port);
            System.out.println("Connected to the server");
            in = socket.getInputStream();
            out = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
        writer.write(message);
        writer.newLine();
        writer.flush();
    }

    public String readMessage() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        return reader.readLine();
    }

    public void close() {
        try {
            if (out != null)
                out.close();
            if (in != null)
                in.close();
            if (socket != null)
                socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
