package ex2.fitxers;

public class MainClient {
    public static void main(String[] args) {
        WelcomeClient client = new WelcomeClient("127.0.0.1", 8000);
        client.getFileList();
        client.requestAndReceiveFile();
        client.closeConnection();
    }
}
