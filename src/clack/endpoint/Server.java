package clack.endpoint;

import clack.message.LoginMessage;
import clack.message.Message;
import clack.message.MsgTypeEnum;
import clack.message.TextMessage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

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
    private String optionCipherkey = null;
    private String optionCipherName = null;
    private boolean optionCipherEnable = false;
    private boolean loggedIn = true;
    //cipher obj

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
    }

    /**
     * Creates a server for exchanging Message objects, using the
     * default servername (Server.DEFAULT_SERVERNAME).
     *
     * @param port       the port to listen on.
     * @throws IllegalArgumentException if port not in range [1024, 49151].
     */
    public Server(int port) {
        this(port, DEFAULT_SERVERNAME);
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
            try (
                    // Wait for connection.
                    Socket clientSocket = serverSocket.accept();

                    // Build streams on the socket.
                    ObjectInputStream inObj =
                            new ObjectInputStream(clientSocket.getInputStream());
                    ObjectOutputStream outObj =
                            new ObjectOutputStream(clientSocket.getOutputStream());
            )
            {
                Message inMsg;
                Message outMsg;

                // Connection made. Greet client.
                outMsg = new TextMessage(serverName, GREETING);
                outObj.writeObject(outMsg);
                outObj.flush();
                if (SHOW_TRAFFIC) {
                    System.out.println("=> " + outMsg);
                }

                // Converse with client.
                do {
                    inMsg = (Message) inObj.readObject();
                    if (SHOW_TRAFFIC) {
                        System.out.println("<= " + inMsg);
                    }
                    while(loggedIn) {
                        // Process the received message
                        // Set or report the option.
                        switch (inMsg.getMsgType()) {
                            case MsgTypeEnum.LISTUSERS:
                                outMsg = new TextMessage(serverName, "LISTUSERS requested");
                                break;
                            case MsgTypeEnum.LOGOUT:
                                outMsg = new TextMessage(serverName, GOOD_BYE);
                                break;
                            case MsgTypeEnum.TEXT:
                                outMsg = new TextMessage(serverName,
                                        "TEXT: '" + ((TextMessage) inMsg).getText() + "'");
                                break;
                            case MsgTypeEnum.LOGIN:
                                String password = ((LoginMessage) inMsg).getPassword();
                                StringBuilder sb = new StringBuilder(password);
                                sb.reverse();
                                String reversed_pass = new String(sb);
                                if(username == reversed_pass) {
                                    outMsg = new TextMessage(serverName, "LOGIN accepted");
                                }
                                else {
                                    loggedIn == false;
                                    outMsg = new TextMessage(serverName, "LOGIN failed");
                                }
                                break;
                            case MsgTypeEnum.OPTION:
                                outMsg = new TextMessage(serverName, "OPTION requested");
                                break;
                            case MsgTypeEnum.FILE:
                                outMsg = new TextMessage(serverName, "FILE requested");
                                break;
                            case MsgTypeEnum.HELP:
                                outMsg = new TextMessage(serverName, "HELP requested");
                                break;
                            default:
                                throw new IllegalArgumentException();
                        }
                    }

                    outObj.writeObject(outMsg);
                    outObj.flush();
                    if (SHOW_TRAFFIC) {
                        System.out.println("=> " + outMsg);
                    }
                } while (inMsg.getMsgType() != MsgTypeEnum.LOGOUT);

                System.out.println("=== Terminating connection. ===");
            }   // Streams and socket closed by try-with-resources.
        } // Server socket closed by try-with-resources.
    }
}
