package clack.endpoint;

import clack.cipher.CharacterCipher;
import clack.message.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

/**
 * This is a simple server class for exchanging Message objects
 * with a client. The exchange is conversational, that is, one
 * side sends a Message, then waits for a reply Message from the
 * other side, then sends another Message, waits for a reply,
 * and so on.
 * <p>
 * To begin a conversation, a client connects to the server
 * and waits for the server to send the first Message.
 * <p>
 * The conversation ends when the client sends a LogoutMessage.
 * The server replies with a last TextMessage, closes the
 * connection, and waits for a new connection.
 */
public class Server
{
    public static final String DEFAULT_SERVERNAME = "server";

    // For strings sent to client.
    private static final String GREETING =
            "[Server listening. 'Logout' (case insensitive) closes connection.]";
    private static final String GOOD_BYE =
            "[Closing connection, good-bye.]";
    //    private String optionCipherkey = null;
//    private String optionCipherName = null;
//    private String optionCipherEnable = null;
    private boolean loggedIn = false;
    private Properties options;
    private CharacterCipher cipher;

    // Object variables.
    private final int port;
    private final String serverName;
    private final boolean SHOW_TRAFFIC = true;      // FOR DEBUGGING


    /**
     * Creates a server for exchanging Message objects.
     *
     * @param port       the port to listen on.
     * @param serverName the name to use when constructing Message objects.
     * @throws IllegalArgumentException if port not in range [1024, 49151].
     */
    public Server(int port, String serverName)
            throws IllegalArgumentException
    {
        if (port < 1024 || port > 49151) {
            throw new IllegalArgumentException(
                    "Port " + port + " not in range 1024-49151.");
        }
        this.port = port;
        this.serverName = serverName;
        options = new Properties();
        options.setProperty("CIPHER_KEY", "");
        options.setProperty("CIPHER_NAME", "");
        options.setProperty("CIPHER_ENABLE", "false");
    }

    /**
     * Creates a server for exchanging Message objects, using the
     * default servername (Server.DEFAULT_SERVERNAME).
     *
     * @param port the port to listen on.
     * @throws IllegalArgumentException if port not in range [1024, 49151].
     */
    public Server(int port)
    {
        this(port, DEFAULT_SERVERNAME);
    }

    public Message handleOption(Message inMsg)
    {
        String option = ((OptionMessage) inMsg).getOption().toString();
        String value = ((OptionMessage) inMsg).getValue();
        if (value == null) {
            return (new TextMessage(serverName, "option " + option + ": " + options.getProperty(option)));
        } else {
            options.setProperty(option, value);
            return (new TextMessage(serverName, "option " + option + ": " + options.getProperty(option)));
        }
    }

    /**
     * Starts this server, listening on the port it was
     * constructed with.
     *
     * @throws IOException if ServerSocket creation, connection
     *                     acceptance, wrapping, or IO fails.
     */
    public void start() throws IOException, ClassNotFoundException
    {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server starting on port " + port + ".");
            System.out.println("Ctrl + C to exit.");
            while (true) {
                try (
                        // Wait for connection.
                        Socket clientSocket = serverSocket.accept();

                        // Build streams on the socket.
                        ObjectInputStream inObj =
                                new ObjectInputStream(clientSocket.getInputStream());
                        ObjectOutputStream outObj =
                                new ObjectOutputStream(clientSocket.getOutputStream());
                ) {
                    Message inMsg;
                    Message outMsg;

                    // Connection made. Greet client.
                    outMsg = new TextMessage(serverName, GREETING);
                    outObj.writeObject(outMsg);
                    outObj.flush();
                    if (SHOW_TRAFFIC) {
                        System.out.println("=> " + outMsg);
                    }
                    do {
                        outMsg = new TextMessage(serverName, "Enter LOGIN Information");
                        inMsg = (Message) inObj.readObject();

                        if (inMsg.getMsgType() != MsgTypeEnum.LOGIN) {
                            break;
                        } else {
                            String password = ((LoginMessage) inMsg).getPassword();
                            StringBuilder sb = new StringBuilder(password);
                            sb.reverse();
                            String reversed_pass = new String(sb);
                            if (inMsg.getUsername().equals(reversed_pass)) {
                                outMsg = new TextMessage(serverName, "LOGIN accepted");
                                loggedIn = true;
                            } else {
                                outMsg = new TextMessage(serverName, "LOGIN failed");
                            }
                        }
                    } while (loggedIn == false);

                    // Converse with client.
                    do {
                        inMsg = (Message) inObj.readObject();
                        if (SHOW_TRAFFIC) {
                            System.out.println("<= " + inMsg);
                        }
                        // Process the received message
                        // Set or report the option.
                        outMsg = switch (inMsg.getMsgType()) {
                            case MsgTypeEnum.LISTUSERS -> new TextMessage(serverName, "LISTUSERS requested");
                            case MsgTypeEnum.LOGOUT -> new TextMessage(serverName, GOOD_BYE);
                            case MsgTypeEnum.TEXT -> new TextMessage(serverName,
                                    "TEXT: '" + ((TextMessage) inMsg).getText() + "'");
                            case MsgTypeEnum.LOGIN -> new TextMessage(serverName, "Already Logged In");
                            case MsgTypeEnum.OPTION -> handleOption(((OptionMessage) inMsg));
                            case MsgTypeEnum.FILE -> new TextMessage(serverName,
                                    "File Saved: Name of File is " + ((FileMessage) inMsg).getFileName());
                            case MsgTypeEnum.HELP -> new TextMessage(serverName, "HELP requested");
                            default -> throw new IllegalArgumentException();
                        };

                        outObj.writeObject(outMsg);
                        outObj.flush();
                        if (SHOW_TRAFFIC) {
                            System.out.println("=> " + outMsg);
                        }
                    } while (inMsg.getMsgType() != MsgTypeEnum.LOGOUT);

                    System.out.println("=== Terminating connection. ===");
                }   // Streams and socket closed by try-with-resources.
            }
        } // Server socket closed by try-with-resources.
    }
}
