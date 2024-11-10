package clack.endpoint;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * This is a GUI client class for creating and handling the user interface.
 * This class is not responsible for handling the exchanges between the
 * Client and Server, only for the exchanges between the interface and the user.
 * <p>
 * To begin a conversation, the GUI client will first prompt the user to connect.
 * //TODO: Fact check this
 * <p>
 * //TODO: How does it end?
 */

public class ClientGUI
{
    /**
     * Constructs a client GUI.
     */
    public ClientGUI(int x, int y)
    {
        final Border BORDER =
                BorderFactory.createLineBorder(Color.LIGHT_GRAY);
    }

    // Connection panel: host, port, buttons
    final JLabel hostLabel = new JLabel("Host: ");
    final JTextField hostField = new JTextField("localhost", 20);
    final JLabel portLabel = new JLabel("Port: ");
    final JTextField portField = new JTextField("4466", 5);
    final JPanel connectionInfoPanel = new JPanel();
        connectionInfoPanel.setLayout(
                new BoxLayout(connectionInfoPanel, BoxLayout.LINE_AXIS));
        connectionInfoPanel.add(hostLabel);
        connectionInfoPanel.add(hostField);
        connectionInfoPanel.add(portLabel);
        connectionInfoPanel.add(portField);

    // Connect and Disconnect buttons, each centered in their own
    // small pane, and these panes side-by-side.
    final JButton connectButton = new JButton("Connect");
    final JButton disconnectButton = new JButton("Disconnect");

    final JPanel connectionButtonsPanel = new JPanel();
        connectionButtonsPanel.setLayout(new FlowLayout());
        connectionButtonsPanel.add(connectButton);
        connectionButtonsPanel.add(Box.createRigidArea((new Dimension(20, 0))));
        connectionButtonsPanel.add(disconnectButton);

    final JPanel connectionPanel = new JPanel();
        connectionPanel.setBorder(BORDER);
        connectionPanel.setLayout(
                new BoxLayout(connectionPanel, BoxLayout.PAGE_AXIS));
        connectionPanel.add(connectionInfoPanel);
        connectionPanel.add(connectionButtonsPanel);

    // Build logPane: conversation log
    final JTextArea log = new JTextArea();
        log.setEditable(false);
        log.setRows(6);
        log.setLineWrap(true);
    final JScrollPane logPane = new JScrollPane(log);

    // Build questionPanel: where user enters questions
    final JLabel questionLabel = new JLabel("Your message: ");
    final JTextField questionField = new JTextField(30);
    final JPanel questionPanel = new JPanel();
        questionPanel.setBorder(BORDER);
        questionPanel.setLayout(
                new BoxLayout(questionPanel, BoxLayout.LINE_AXIS));
        questionPanel.setBorder(
                BorderFactory.createEmptyBorder(10, 5, 10, 5));
        questionPanel.add(questionLabel);
        questionPanel.add(questionField);

    // Put panels together
    final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Clack");
        frame.setLayout(new BorderLayout());
        frame.add(connectionPanel, BorderLayout.NORTH);
        frame.add(logPane, BorderLayout.CENTER);
        frame.add(questionPanel, BorderLayout.SOUTH);

    // Wire up the listeners
        connectButton.addActionListener((e) ->
    {
        log.append("Connect Button clicked, connect to "
                + hostField.getText() + ":" + portField.getText() + "\n");
        hostField.setEnabled(false);
        portField.setEnabled(false);
        connectButton.setEnabled(false);
    }
        );
        disconnectButton.addActionListener((e) ->
    {
        log.append("Disconnect Button clicked\n");
        hostField.setEnabled(true);
        portField.setEnabled(true);
        connectButton.setEnabled(true);
    }
        );
        questionField.addActionListener((e) ->
        log.append("User entered: '" + questionField.getText() + "'\n")
        );

        frame.pack();
        frame.setLocation(x, y);
        frame.setVisible(true);
}


}
