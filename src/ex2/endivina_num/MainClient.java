package ex2.endivina_num;

import java.io.IOException;
import java.util.Scanner;

public class MainClient {
    public static void main(String[] args) {
        WelcomeClient client = new WelcomeClient();
        client.connect("127.0.0.1", 6000);

        Scanner scanner = new Scanner(System.in);
        try {
            while (true) {
                String response = client.readMessage();
                System.out.println("Server says: " + response);

                if (response.startsWith("Welcome, you are")) {
                    while (true) {
                        System.out.println("Introduce a number (or type 'exit' to quit): ");
                        String input = scanner.nextLine();
                        client.sendMessage(input);
                        if (input.equalsIgnoreCase("exit")) {
                            break;
                        }
                        response = client.readMessage();
                        System.out.println("Server says: " + response);
                    }
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            client.close();
            scanner.close();
        }
    }
}
