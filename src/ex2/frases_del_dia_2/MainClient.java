package ex2.frases_del_dia_2;

public class MainClient {
    public static void main(String[] args) {
        var client = new WelcomeClient();
        client.connecta("127.0.0.1", 8000);
        System.out.println("El servidor diu: " + client.llegeixMissatge());
    }
}