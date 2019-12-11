import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUI extends JFrame implements ActionListener {
    public JFrame frame;
    private String chat = "", userName = "", message = "";
    private JTextArea textBox;
    private JButton sendButton, enterNameButton, leaveButton;
    private JTextField userNameField, messageField;
    private JLabel userNameLabel;

    public void createGUI() {
        Font font = new Font("Avenir", Font.PLAIN, 12);
        Color colorMain = new Color(132, 193, 240);
        Color colorComp = new Color(214, 234, 248);

        frame = new JFrame("Welcome to Chatty!");
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                message = ".";
                frame.dispose();
            }
        });
        frame.setSize(400, 400);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        BorderLayout layout = new BorderLayout();
        panel.setLayout(layout);

        JPanel northPanel = new JPanel();
        BorderLayout northLayout = new BorderLayout();
        northPanel.setLayout(northLayout);
        northPanel.setBackground(colorMain);

        userNameLabel = new JLabel(" Enter name: ");
        userNameLabel.setFont(font);
        userNameField = new JTextField(25);
        userNameField.setFont(font);
        enterNameButton = new JButton("Enter name");
        enterNameButton.setFont(font);
        enterNameButton.setActionCommand("e");
        enterNameButton.addActionListener(this);
        frame.getRootPane().setDefaultButton(enterNameButton);

        leaveButton = new JButton("Leave");
        leaveButton.setFont(font);
        leaveButton.setActionCommand(".");
        leaveButton.addActionListener(this);
        leaveButton.setEnabled(false);

        JPanel subPanel = new JPanel();
        BorderLayout subLayout = new BorderLayout();
        subPanel.setLayout(subLayout);
        subPanel.setBackground(colorMain);
        subPanel.add(enterNameButton, BorderLayout.WEST);
        subPanel.add(leaveButton, BorderLayout.EAST);

        northPanel.add(userNameLabel, BorderLayout.WEST);
        northPanel.add(userNameField, BorderLayout.CENTER);
        northPanel.add(subPanel, BorderLayout.EAST);

        JPanel centerPanel = new JPanel();
        BorderLayout centerLayout = new BorderLayout();
        centerPanel.setLayout(centerLayout);

        textBox = new JTextArea();
        textBox.setEditable(false);
        textBox.setBackground(colorComp);
        textBox.setFont(font);
        textBox.setLineWrap(true);
        textBox.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(textBox);
        centerPanel.add(scroll);

        JPanel southPanel = new JPanel();
        BorderLayout southLayout = new BorderLayout();
        southPanel.setLayout(southLayout);
        southPanel.setBackground(colorMain);

        sendButton = new JButton("Send");
        sendButton.setFont(font);
        sendButton.setActionCommand("s");
        sendButton.addActionListener(this);
        sendButton.setEnabled(false);

        messageField = new JTextField(32);
        messageField.setFont(font);
        messageField.setEnabled(false);

        southPanel.add(messageField, BorderLayout.WEST);
        southPanel.add(sendButton, BorderLayout.EAST);

        panel.add(northPanel, BorderLayout.NORTH);
        panel.add(centerPanel, BorderLayout.CENTER);
        panel.add(southPanel, BorderLayout.SOUTH);
        frame.add(panel);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();
        if(command.equals("s")) {
            message = messageField.getText();
            messageField.setText("");
        }
        else if(command.equals("e")){
            userName = userNameField.getText();
            frame.setTitle("Welcome to Chatty! - [" + userName + "]");

            enterNameButton.setEnabled(false);
            userNameField.setEnabled(false);
            sendButton.setEnabled(true);
            messageField.setEnabled(true);
            leaveButton.setEnabled(true);
            textBox.setText(chat);
            frame.getRootPane().setDefaultButton(sendButton);
        }
        else {
            message = command;
            frame.dispose();
        }
    }

    public String getUserName() {
        System.out.print("");
        return userName;
    }

    public void updateChat(String message) {
        chat = chat + message + "\n";
        textBox.setText(chat);
    }

    public String getMessage() {
        System.out.print("");
        String temp = message;
        message = "";
        return temp;
    }
}
