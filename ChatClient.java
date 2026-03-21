import java.net.*;
import java.io.*;

public class ChatClient {

    public static void main(String[] args) {

        try {
            // 1. Connect to server
            Socket socket = new Socket("localhost", 8191);
            System.out.println("Connected to server");

            // 2. Input & Output streams
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            // 3. Read user input
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

            // 4. Thread to receive messages
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

            // 5. Send messages
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