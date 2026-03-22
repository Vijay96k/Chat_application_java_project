import java.io.*;
import java.net.*;

public class ChatClient {

    public static void main(String[] args) {

        try {
            Socket socket = new Socket("localhost", 8191);
            System.out.println("Connected to server");

            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

            // 👉 Ask username
            System.out.print("Enter your name: ");
            String username = console.readLine();

            // 👉 Send username first
            output.println(username);

            // 👉 Thread to receive messages
            Thread receiveThread = new Thread(() -> {
                try {
                    String msg;
                    while ((msg = input.readLine()) != null) {
                        System.out.println(msg);
                    }
                } catch (IOException e) {
                    System.out.println("Disconnected from server");
                }
            });

            receiveThread.start();

            // 👉 Send messages
            String userInput;
            while ((userInput = console.readLine()) != null) {
               output.println(userInput);
            }

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}