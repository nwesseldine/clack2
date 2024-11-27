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
            "[Server listening. 'Logout' (case insensitive) closes connection.]\n Enter LOGININFO";
    private static final String GOOD_BYE =
            "[Closing connection, good-bye.]";
    private boolean loggedIn = false;
    private Properties options;
    private String login_status = "";

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
        options.setProperty("CIPHER_KEY", "empty");
        options.setProperty("CIPHER_NAME", "empty");
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

    /** Handles Option type.
     * @param inMsg is the message type, given by the user
     * @return TextMessage that shows option type*/
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

    /** checks if logged in
     * @param username is the username
     * @param password is the user's password
     * @return true or false if user is logged in*/
    public boolean isLoggedIn(String username, String password){
        StringBuilder sb = new StringBuilder(username);
        String reversed_pass = new String(sb.reverse());
        return(reversed_pass.equals(password));
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

                    do {
                        do {
                            // Connection made. Greet client.
                            outMsg = new TextMessage(serverName, login_status +GREETING);
                            outObj.writeObject(outMsg);
                            outObj.flush();

                            if (SHOW_TRAFFIC) {
                                System.out.println("=> " + outMsg);
                            }
                            inMsg = (Message) inObj.readObject();
                        } while (inMsg.getMsgType() != MsgTypeEnum.LOGIN);
                        LoginMessage login = (LoginMessage) inMsg;
                        if (isLoggedIn(login.getUsername(), login.getPassword())) {
                            login_status = "Login Accepted, Now in Command Loop ";
                            loggedIn = true;
                        } else {
                            login_status = "Login Failed, Try Again ";
                        }
                    } while(loggedIn == false);
                    outMsg = new TextMessage(serverName, login_status);
                    outObj.writeObject(outMsg);
                    outObj.flush();


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
                            case MsgTypeEnum.HELP -> new TextMessage(serverName, "HELP\nLIST USERS\nLOGIN\nLOGOUT\nOPTION [] {option_value}\n"
                                    + "SEND FILE {filepath} AS {filename}");
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
            } // while loop
        } // Server socket closed by try-with-resources.
    }
}