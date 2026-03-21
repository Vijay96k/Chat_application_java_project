import java.io.*;
import java.net.*;
import java.util.*;

public class MessageManager {

    private static List<ClientHandler> clients = new ArrayList<>();

    public static synchronized void addClient(ClientHandler client) {
        clients.add(client);
        broadcast("User joined. Total users: " + clients.size());
    }

    public static synchronized void removeClient(ClientHandler client) {
        clients.remove(client);
        broadcast("User left. Total users: " + clients.size());
    }

    public static synchronized void broadcast(String message) {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }
}
class ClientHandler extends Thread {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public ClientHandler(Socket socket) {
        this.socket = socket;

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (Exception e) {
            System.out.println("Initialization error: " + e.getMessage());
        }
    }

    public void run() {
        try {
            String msg;

            while ((msg = in.readLine()) != null) {
                MessageManager.broadcast(msg);
            }

        } catch (Exception e) {
            System.out.println("Client disconnected");
        } finally {
            MessageManager.removeClient(this);

            try {
                socket.close();
            } catch (Exception e) {}
        }
    }
    public void sendMessage(String message) {
        out.println(message);
    }
}