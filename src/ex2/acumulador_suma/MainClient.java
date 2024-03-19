package ex2.acumulador_suma;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class MainClient {
    public static void main(String[] args) {
        var client = new WelcomeClient();
        client.connecta("127.0.0.1", 6000);

        Scanner scanner = new Scanner(System.in);
        try {
            OutputStream out = client.out;
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
            while (true) {
                System.out.println("Introdueix un número (o escriu 'exit' per sortir): ");
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("exit")) {
                    break;
                }
                writer.write(input);
                writer.newLine();
                writer.flush();

                String response = client.llegeixMissatge();
                System.out.println("Servidor diu: " + response);
            }

            out.close(); // Close the OutputStream directly
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
