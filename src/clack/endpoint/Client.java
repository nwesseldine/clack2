package clack.endpoint;

import clack.message.*;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * This is a simple client class for exchanging Message objects
 * with a server. The exchange is conversational, that is, one
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
public class Client {
    public static final String DEFAULT_USERNAME = "client";

    private final String hostname;
    private final int port;
    private final String prompt;
    private final String username;

    /**
     * Creates a client for exchanging Message objects.
     *
     * @param hostname the hostname of the server.
     * @param port     the service's port on the server.
     * @param username username to include in Messages.
     * @throws IllegalArgumentException if port not in range [1-49151]
     */
    public Client(String hostname, int port, String username)
    {
        if (port < 1 || port > 49151) {
            throw new IllegalArgumentException(
                    "Port " + port + " not in range 1 - 49151.");
        }
        this.hostname = hostname;
        this.port = port;
        this.username = username;
        this.prompt = "hostname:" + port + "> ";
    }

    /**
     * Creates a client for exchanging Message objects, using the
     * default username (Client.DEFAULT_USERNAME).
     *
     * @param hostname the hostname of the server.
     * @param port     the service's port on the server.
     * @throws IllegalArgumentException if port not in range [1-49151]
     */
    public Client(String hostname, int port)
    {
        this(hostname, port, DEFAULT_USERNAME);
    }

    /**
     * Starts this client, connecting to the server and port that
     * it was given when constructed.
     *
     * @throws UnknownHostException if hostname is not resolvable.
     * @throws IOException          if socket creation, wrapping, or IO fails.
     */
    public void start() throws UnknownHostException, IOException, ClassNotFoundException
    {
        // LANEY'S ADDITION
        ClientGUI gui = new ClientGUI(5, 5);

        System.out.println("Attempting connection to " + hostname + ":" + port);
        Scanner keyboard = new Scanner(System.in);

        try (
                // TODO Create a socket (named 'socket') using hostname and port.

                // TODO Construct an ObjectInputStream and ObjectOutputStream on the socket.
                // TODO Name them 'inObj' and 'outObj'

                // DELARA ADDED: creates a new socket using hostname and port
                Socket socket = new Socket(hostname, port);

                // DELARA ADDED: constructs ObjectOutputStream (outObj) on the socket
                OutputStream outStream = socket.getOutputStream();
                ObjectOutputStream outObj = new ObjectOutputStream(outStream);

                // DELARA ADDED: constructs ObjectInputStream (inObj) on socket
                InputStream inStream = socket.getInputStream();
                ObjectInputStream inObj = new ObjectInputStream(inStream);
        )

        {
            String userInput = "";
            Message inMsg;
            Message outMsg;

            // Take turns talking. Server goes first.
            do {
                // Get server message and show it to user.
                inMsg = (Message) inObj.readObject();
                // TODO use a switch statement or expression, based on MsgType,
                // TODO to decide what to show the user.

                // DELARA ADDED: printing out a case for each message type in a switch statement
                // should there be a case for option?
                switch (inMsg.getMsgType()) {

                    case LOGOUT -> {
                        outMsg = new LogoutMessage("client"); }

                    case LISTUSERS -> {
                        outMsg = new ListUsersMessage("client"); }

                    case LOGIN -> {
                        outMsg = new LoginMessage("client", ""); }

                    default -> {
                        outMsg = new TextMessage("client", userInput); }

                };

                System.out.print(prompt);
                userInput = keyboard.nextLine();
                String[] tokens = userInput.trim().split("\\s+");
                
                // DEBUG
                // System.out.println("tokens: " + Arrays.toString(tokens));

                // Construct Message based on user input and send it to server.
                // TODO use a switch statement or expression, based on MsgType,
                // TODO to decide what Message object to construct.

                // TODO send it to the server.

                // DELARA ADDED: constructs message based on user input
                outMsg = switch (tokens[0].toUpperCase()) {
                    
                    case "LOGOUT" -> new LogoutMessage("client");
                    case "LISTUSERS" -> new ListUsersMessage("client");
                    default -> new TextMessage("client", userInput);
                };

                // DELARA ADDED: sends message to server
                    outObj.writeObject(outMsg);
                    outObj.flush();

            } while (outMsg.getMsgType() != MsgTypeEnum.LOGOUT);

            // Get server's closing reply and show it to user.
            inMsg = (Message) inObj.readObject();
            System.out.println(
                    switch (inMsg.getMsgType()) {
                        case LISTUSERS -> "UNEXPECTED RESPONSE: " + inMsg;
                        case LOGOUT -> "UNEXPECTED RESPONSE: " + inMsg;
                        case TEXT -> ((TextMessage) inMsg).getText();

                        // DELARA ADDED: not sure what the actual printed statement should be for login and option
                        case LOGIN -> "LOGIN: " + inMsg;
                        case OPTION -> "UNEXPECTED RESPONSE: " + inMsg;
                        case FILE -> null;
                        case HELP -> null;

                    });
        }   // Streams and sockets closed by try-with-resources

        System.out.println("Connection to " + hostname + ":" + port
                + " closed, exiting.");
    }
}
