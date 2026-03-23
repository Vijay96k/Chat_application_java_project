import javax.swing.JOptionPane;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ChatGUI extends Frame implements ActionListener {

    private TextArea chatArea;
    private TextField messageField;
    private Button sendButton;
    private List userList;

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    private String name;

    public ChatGUI() {
        name = DialogInput("Enter your name:");

        setTitle("💬 Chat - " + name);
        setSize(500, 600);
        setLayout(new BorderLayout());
        setBackground(new Color(30, 30, 30));

        chatArea = new TextArea();
        chatArea.setEditable(false);
        chatArea.setBackground(new Color(40, 40, 40));
        chatArea.setForeground(Color.WHITE);
        chatArea.setFont(new Font("Arial", Font.PLAIN, 14));
        add(chatArea, BorderLayout.CENTER);
        userList = new List();
        userList.setBackground(new Color(50, 50, 50));
        userList.setForeground(Color.WHITE);
        userList.setFont(new Font("Arial", Font.PLAIN, 13));

        userList.setPreferredSize(new Dimension(120, 0));

        add(userList, BorderLayout.EAST);

        Panel bottomPanel = new Panel(new BorderLayout());
        bottomPanel.setBackground(new Color(30, 30, 30));

        messageField = new TextField();
        messageField.setFont(new Font("Arial", Font.PLAIN, 14));
        messageField.setBackground(new Color(60, 60, 60));
        messageField.setForeground(Color.WHITE);
        bottomPanel.add(messageField, BorderLayout.CENTER);

        sendButton = new Button("Send");
        sendButton.setBackground(new Color(0, 120, 215));
        sendButton.setForeground(Color.WHITE);
        sendButton.addActionListener(this);
        bottomPanel.add(sendButton, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);

        messageField.addActionListener(this);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                try {
                    socket.close();
                } catch (Exception e) {}
                dispose();
                System.exit(0);
            }
        });

        setVisible(true);

        connectToServer();

        startReading();
    }

    private String DialogInput(String message) {
        return (String) JOptionPane.showInputDialog(
                null,
                message,
                "User Name",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                ""
        );
    }

    private void connectToServer() {
        try {
            socket = new Socket("localhost", 1234); 
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            out.println(name);

            chatArea.append("Connected to server...\n");

        } catch (Exception e) {
            chatArea.append("❌ Cannot connect to server. Check if server is running.\n");            
         e.printStackTrace();
        }
    }

    private void startReading() {
        new Thread(() -> {
            try {
                String msg;
                while ((msg = in.readLine()) != null) {

    if (msg.startsWith("USERS:")) {
        String users = msg.substring(6);
        String[] userArray = users.split(",");

        userList.removeAll();
        for (String user : userArray) {
            userList.add(user);
        }

    } else {
        chatArea.append(msg + "\n");
    }
}
            } catch (Exception e) {
                chatArea.append("Disconnected from server\n");
            }
        }).start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String message = messageField.getText().trim();

        if (!message.isEmpty()) {
            out.println(message); 
            messageField.setText("");
        }
    }
    
    public static void main(String[] args) {
        new ChatGUI();
    }
}