import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.JOptionPane;

public class ChatGUI extends Frame implements ActionListener {

    TextArea chatArea;
    TextField messageField;
    Button sendButton;

    Socket socket;
    BufferedReader in;
    PrintWriter out;

    String name;

    public ChatGUI() {

        name = JOptionPane.showInputDialog(this, "Enter your name:");
        if (name == null || name.trim().isEmpty()) {
            name = "User";
        }

        setTitle("Chat - " + name);
        setSize(400, 500);
        setLayout(new BorderLayout());

        // Chat area
        chatArea = new TextArea();
        chatArea.setEditable(false);
        add(chatArea, BorderLayout.CENTER);

        // Bottom panel
        Panel p = new Panel();
        p.setLayout(new BorderLayout());

        // ✅ FIX: Bigger input box
        messageField = new TextField();
        messageField.setPreferredSize(new Dimension(0, 40)); // 👈 height increased
        p.add(messageField, BorderLayout.CENTER);

        sendButton = new Button("Send");
        sendButton.setPreferredSize(new Dimension(70, 40)); // 👈 match height
        p.add(sendButton, BorderLayout.EAST);

        add(p, BorderLayout.SOUTH);

        sendButton.addActionListener(this);
        messageField.addActionListener(this);

        messageField.requestFocus();

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                try {
                    if (out != null) out.println("exit");
                    if (socket != null) socket.close();
                } catch (Exception e) {}
                System.exit(0);
            }
        });

        setVisible(true);
        messageField.requestFocus();

        new Thread(() -> startClient()).start();
    }

    void startClient() {
        try {
            socket = new Socket("127.0.0.1", 8191);

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            chatArea.append("Connected to server\n");

            out.println(name);

            new Thread(() -> {
                try {
                    String msg;
                    while ((msg = in.readLine()) != null) {
                        chatArea.append(msg + "\n");
                    }
                } catch (Exception e) {
                    chatArea.append("Disconnected\n");
                }
            }).start();

        } catch (Exception e) {
            chatArea.append("Connection error\n");
        }
    }

    public void actionPerformed(ActionEvent e) {
        String msg = messageField.getText().trim();

        if (out != null && !msg.isEmpty()) {
            out.println(msg);
            messageField.setText("");
            messageField.requestFocus();
        }
    }

    public static void main(String[] args) {
        new ChatGUI();
    }
}