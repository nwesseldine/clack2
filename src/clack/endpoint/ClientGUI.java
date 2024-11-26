package clack.endpoint;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * This is a GUI client class for creating and handling the user interface.
 * This class is not responsible for handling the exchanges between the
 * Client and Server, only for the exchanges between the interface and the user.
 * <p>
 * To begin a conversation, the 'Connection' panel will be used. Then, the 'Conversation' panel.
 * The text-entry box allows communication.
 * <p>
 * The 'Control' panel includes buttons “Log In”, “Log Out”, “List Users”, “Help”, “Send File”,
 * and “Clear Conversation”.
 */


public class ClientGUI
{
    private Client client;
    /**
     * Constructs a client GUI.
     */
    public ClientGUI(int x, int y, Client client)
    {
        final Border BORDER =
                BorderFactory.createLineBorder(Color.LIGHT_GRAY);

        // Connection panel: host, port, buttons
        final JLabel hostLabel = new JLabel("Host: ");
        final JTextField hostField = new JTextField("localhost", 20);
        final JLabel portLabel = new JLabel("Port: ");
        final JTextField portField = new JTextField("4466", 5);
        final JPanel connectionInfoPanel = new JPanel();
        connectionInfoPanel.setLayout(new BoxLayout(connectionInfoPanel, BoxLayout.LINE_AXIS));
        connectionInfoPanel.add(hostLabel);
        connectionInfoPanel.add(hostField);
        connectionInfoPanel.add(portLabel);
        connectionInfoPanel.add(portField);

        // Control panel: host, port, buttons
        final JPanel controlInfoPanel = new JPanel ();
        controlInfoPanel.setLayout(new BoxLayout(controlInfoPanel, BoxLayout.LINE_AXIS));
        controlInfoPanel.add(hostLabel);
        controlInfoPanel.add(hostField);
        controlInfoPanel.add(portLabel);
        controlInfoPanel.add(portField);

        // Connect and Disconnect buttons, each centered in their own
        // small pane, and these panes side-by-side.
        final JButton connectButton = new JButton("Connect");
        final JButton disconnectButton = new JButton("Disconnect");

        // Buttons for "Log In", "Log Out", "List Users", "Help", "Send File", and "Clear Conversation".
        final JButton logInButton = new JButton("Log In");
        final JButton logOutButton = new JButton("Log Out");
        final JButton listUsersButton = new JButton("List Users");
        final JButton helpButton = new JButton("Help");
        final JButton sendFileButton = new JButton("Send File");
        final JButton clearConversationButton = new JButton("Clear Conversation");

        // Connection Buttons
        final JPanel connectionButtonsPanel = new JPanel();
        connectionButtonsPanel.setLayout(new FlowLayout());
        connectionButtonsPanel.add(connectButton);
        connectionButtonsPanel.add(Box.createRigidArea((new Dimension(20, 0))));
        connectionButtonsPanel.add(disconnectButton);

        // Add new control panel with buttons.
        final JPanel controlButtonsPanel = new JPanel();
        controlButtonsPanel.setLayout(new FlowLayout());
        controlButtonsPanel.add(logInButton);
        controlButtonsPanel.add(logOutButton);
        controlButtonsPanel.add(listUsersButton);
        controlButtonsPanel.add(helpButton);
        controlButtonsPanel.add(sendFileButton);
        controlButtonsPanel.add(clearConversationButton);
        controlButtonsPanel.add(Box.createRigidArea((new Dimension(20, 0))));

        // Connection Panel
        final JPanel connectionPanel = new JPanel();
        connectionPanel.setBorder(BORDER);
        connectionPanel.setLayout(
                new BoxLayout(connectionPanel, BoxLayout.PAGE_AXIS));
        connectionPanel.add(connectionInfoPanel);
        connectionPanel.add(connectionButtonsPanel);

        // Control Panel
        final JPanel controlPanel = new JPanel();
        controlPanel.setBorder(BORDER);
        controlPanel.setLayout(
                new BoxLayout(controlPanel, BoxLayout.PAGE_AXIS));
        controlPanel.add(controlInfoPanel);
        controlPanel.add(controlButtonsPanel);

        // Build logPane: conversation log
        final JTextArea log = new JTextArea();
        log.setEditable(false);
        log.setRows(6);
        log.setLineWrap(true);
        final JScrollPane logPane = new JScrollPane(log);

        // Build messagePanel: where user enters messages
        final JLabel messageLabel = new JLabel("Your message: ");
        final JTextField messageField = new JTextField(30);
        final JPanel messagePanel = new JPanel();
        messagePanel.setBorder(BORDER);
        messagePanel.setLayout(
                new BoxLayout(messagePanel, BoxLayout.LINE_AXIS));
        messagePanel.setBorder(
                BorderFactory.createEmptyBorder(10, 5, 50, 5));
        messagePanel.add(messageLabel);
        messagePanel.add(messageField);

        // Put panels together
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Clack");
        frame.setLayout(new BorderLayout());
        frame.add(connectionPanel, BorderLayout.EAST);
        frame.add(controlPanel, BorderLayout.NORTH); // NEW
        frame.add(logPane, BorderLayout.CENTER);
        frame.add(messagePanel, BorderLayout.SOUTH);

        // Wire up the listeners
        connectButton.addActionListener((e) ->
                {
                    log.append("Connect Button clicked, connect to "
                            + hostField.getText() + ":" + portField.getText() + "\n");
                    hostField.setEnabled(false);
                    portField.setEnabled(false);
                    connectButton.setEnabled(false);
                    logInButton.setEnabled(true);
                    logOutButton.setEnabled(false);
                    listUsersButton.setEnabled(true);
                    clearConversationButton.setEnabled(true);
                    helpButton.setEnabled(true);
                    sendFileButton.setEnabled(true);
                }
        );
        disconnectButton.addActionListener((e) ->
                {
                    log.append("Disconnect Button clicked\n");
                    hostField.setEnabled(true);
                    portField.setEnabled(true);
                    connectButton.setEnabled(true);
                    logInButton.setEnabled(false);
                    logOutButton.setEnabled(true);
                    listUsersButton.setEnabled(false);
                    clearConversationButton.setEnabled(false);
                    helpButton.setEnabled(false);
                    sendFileButton.setEnabled(false);
                }
        );
        messageField.addActionListener((e) ->
                log.append("User entered: '" + messageField.getText() + "'\n")
        );

        //Dialogue panel
        JPanel dialoguePanel = new JPanel();
        dialoguePanel.setLayout(new BorderLayout());

        JLabel loginLabel = new JLabel("Enter your login details", SwingConstants.CENTER);
        JTextField usernameField = new JTextField(15);
        JPasswordField passwordField = new JPasswordField(15);

        dialoguePanel.add(loginLabel, BorderLayout.WEST);

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("username:"));
        inputPanel.add(usernameField);
        inputPanel.add(new JLabel("Password:"));
        inputPanel.add(passwordField);
        dialoguePanel.add(inputPanel, BorderLayout.CENTER);

        JButton submitButton = new JButton("Submit");
        dialoguePanel.add(submitButton, BorderLayout.SOUTH);

        frame.add(dialoguePanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();

        //"Submit" Functionality
        dialoguePanel.setVisible(false);
        inputPanel.setVisible(false);
        logInButton.setEnabled(false);
        logOutButton.setEnabled(false);
        listUsersButton.setEnabled(false);
        clearConversationButton.setEnabled(false);
        helpButton.setEnabled(false);
        sendFileButton.setEnabled(false);

        // New action listeners
        submitButton.addActionListener((e) ->
                {
                    String username = usernameField.getText();
                    String password = new String(passwordField.getPassword());
                    JOptionPane.showMessageDialog(frame, "Login Successful: " + username);
                    frame.remove(dialoguePanel);
                    frame.revalidate();
                    frame.repaint();
                    inputPanel.setVisible(false);
//                    log.append("Submit button clicked\n");
//                    hostField.setEnabled(false);
//                    portField.setEnabled(false);
//                    logInButton.setEnabled(false);
//                    logOutButton.setEnabled(true);
//                    listUsersButton.setEnabled(true);
//                    clearConversationButton.setEnabled(true);
//                    helpButton.setEnabled(true);
//                    sendFileButton.setEnabled(true);
                }
        );
        logInButton.addActionListener((e) ->
                {
                    dialoguePanel.setVisible(true);
                    inputPanel.setVisible(true);
                    log.append("Login button clicked\n");
                    hostField.setEnabled(false);
                    portField.setEnabled(false);
                    logInButton.setEnabled(false);
                    logOutButton.setEnabled(true);
                    listUsersButton.setEnabled(true);
                    clearConversationButton.setEnabled(true);
                    helpButton.setEnabled(true);
                    sendFileButton.setEnabled(true);
                }
        );
        logOutButton.addActionListener((e) ->
                {
                    JOptionPane.showMessageDialog(frame, "Are you sure you want to log out?");
                    frame.remove(dialoguePanel);
                    frame.revalidate();
                    frame.repaint();
                    log.append("Logout button clicked\n");
                    frame.dispose();
                }
        );

        listUsersButton.addActionListener((e) ->
                {
                    log.append("List Users button clicked\n");
                    hostField.setEnabled(false);
                    portField.setEnabled(false);
                }
        );
        sendFileButton.addActionListener((e) ->
                {
                    JOptionPane.showMessageDialog(frame, "Send this file?");
                    frame.remove(dialoguePanel);
                    frame.revalidate();
                    frame.repaint();
                    log.append("Send File button clicked\n");
                    hostField.setEnabled(false);
                    portField.setEnabled(false);
                }
        );
        helpButton.addActionListener((e) ->
                {
                    log.append("Help button clicked\n");
                    hostField.setEnabled(false);
                    portField.setEnabled(false);
                }
        );
        clearConversationButton.addActionListener((e) ->
                {
                    log.setText(null);
                    log.append("Clear Conversation button clicked\n");
                    hostField.setEnabled(false);
                    portField.setEnabled(false);
                }
        );
        

        frame.pack();
        frame.setLocation(x, y);
        frame.setVisible(true);
    }

    /**
     * Create a "main-like" environment to declare a client gui object.
     * @param args Arguments of the main function.
     */
    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            Client client = new Client("localhost", 4466);
            new ClientGUI(100,100,client);
        });
    }


}
