import java.awt.*;
import java.awt.event.*;
import java.io.PrintWriter;

public class eventhandler implements ActionListener {

    private TextField messageField;
    private TextArea chatArea;
    private PrintWriter out;

    public eventhandler(TextField messageField, TextArea chatArea, PrintWriter out) {
        this.messageField = messageField;
        this.chatArea = chatArea;
        this.out = out;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String message = messageField.getText().trim();

            if (!message.isEmpty()) {
                out.println(message);
                chatArea.append("Me: " + message + "\n");
                messageField.setText("");
            }

        } catch (Exception ex) {
            chatArea.append("Error sending message\n");
        }
    }
}