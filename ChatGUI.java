import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ChatGUI extends Frame implements ActionListener {

    TextArea chatArea;
    TextField messageField;
    Button sendButton;

    Socket socket;
    BufferedReader in;
    PrintWriter out;

    String name;

    public ChatGUI() {

        // simple username (no popup)
        name = javax.swing.JOptionPane.showInputDialog("Enter your name:");
        if (name == null || name.trim().isEmpty()) {
        name = "User";
        }

        setTitle("Chat - " + name);
        setSize(400, 500);
        setLayout(new BorderLayout());

        // chat display
        chatArea = new TextArea();
        chatArea.setEditable(false);
        add(chatArea, BorderLayout.CENTER);

        // input panel
        Panel p = new Panel(new BorderLayout());

        messageField = new TextField();
        messageField.setPreferredSize(new Dimension(0, 35)); // bigger box
        p.add(messageField, BorderLayout.CENTER);

        sendButton = new Button("Send");
        p.add(sendButton, BorderLayout.EAST);

        add(p, BorderLayout.SOUTH);

        sendButton.addActionListener(this);
        messageField.addActionListener(this);

        setVisible(true);
        messageField.requestFocus();

        // start client
        new Thread(this::connect).start();
    }

    void connect() {
        try {
            socket = new Socket("127.0.0.1", 8191);

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            chatArea.append("Connected\n");

            // send username
            out.println(name);

            // receive messages
            while (true) {
                String msg = in.readLine();
                if (msg != null) {
                    chatArea.append(msg + "\n");
                }
            }

        } catch (Exception e) {
            chatArea.append("Error\n");
        }
    }

    public void actionPerformed(ActionEvent e) {
        String msg = messageField.getText();

        if (!msg.isEmpty()) {
            out.println(msg); // server adds username
            messageField.setText("");
        }
    }

    public static void main(String[] args) {
        new ChatGUI();
    }
}