package clack.endpoint;

import clack.message.*;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
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
public class Client
{
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
        ClientGUI gui = new ClientGUI(5, 5, client);

        System.out.println("Attempting connection to " + hostname + ":" + port);
        Scanner keyboard = new Scanner(System.in);
        try (
                Socket socket = new Socket(hostname, port)) {
            try (
                    ObjectOutputStream outObj = new ObjectOutputStream(socket.getOutputStream());
                    ObjectInputStream inObj = new ObjectInputStream(socket.getInputStream());


            ) {
                Message inMsg;
                Message outMsg;
                String userInput;

                // Take turns talking. Server goes first.
                do {
                    // Get server message and show it to user.
                    inMsg = (Message) inObj.readObject();
                    System.out.println(
                            switch (inMsg.getMsgType()) {
                                case FILE -> "File and its contents is " + inMsg;
                                case TEXT -> ((TextMessage) inMsg).getText();
                                default -> "UNEXPECTED RESPONSE: " + inMsg;
                            });

                    String[] tokens;

                    do {
                        // Get user input
                        System.out.print(prompt);
                        userInput = keyboard.nextLine();
                        tokens = userInput.trim().split("\\s+");
                    } while (tokens.length == 0);

                    // DEBUG
                    System.out.println("tokens: " + Arrays.toString(tokens));

                    // Construct Message based on user input and send it to server.
                    outMsg = switch (tokens[0].toUpperCase()) {

                        case "LOGOUT" -> new LogoutMessage(username);
                        case "LIST" -> {
                            if ((tokens.length > 1) && tokens[1].equalsIgnoreCase("USERS")) {
                                yield new ListUsersMessage(username);
                            } else {
                                yield new TextMessage(username, userInput);
                            }
                        }
                        case "LOGIN" -> {
                            if (tokens.length < 2) {
                                yield new TextMessage(username, "Invalid LOGIN ");
                            }
                            yield new LoginMessage(username, tokens[1]);
                        }
                        case "HELP" -> new HelpMessage(username);
                        case "OPTION" -> {
                            if (tokens.length == 2 || tokens.length == 3) {
                                try {
                                    OptionEnum option = OptionEnum.valueOf(tokens[1].toUpperCase());
                                    if (tokens.length == 3) {
                                        yield new OptionMessage(username, option, tokens[2]);
                                    } else {
                                        yield new OptionMessage(username, option, null);
                                    }
                                } catch (IllegalArgumentException e) {
                                    System.out.println("Invalid option: " + tokens[1]);
                                    yield new HelpMessage(username);
                                }
                            } else {
                                yield new TextMessage(username, userInput);
                            }
                        }
                        case "SEND" -> {
                            if (tokens[1].equalsIgnoreCase("FILE")) {
                                try {
                                    if (tokens.length == 5) {
                                        if (tokens[3].equalsIgnoreCase("AS")) {
                                            yield new FileMessage(username, tokens[2], tokens[4]);
                                        } else {
                                            System.out.println("Incorrect file format");
                                            yield new HelpMessage(username);
                                        }
                                    } else if (tokens.length == 3) {
                                        yield new FileMessage(username, tokens[2]);
                                    } else {
                                        System.out.println("Incorrect file format");
                                        yield new HelpMessage(username);
                                    }
                                } catch (IOException e) {
                                    System.out.println("Couldn't open/find");
                                    yield new HelpMessage(username);
                                }
                            } else {
                                yield new TextMessage(username, userInput);
                            }

                        }
                        default -> new TextMessage(username, userInput);
                    };

                    // Sends message to server
                    outObj.writeObject(outMsg);
                    outObj.flush();

                } while (outMsg.getMsgType() != MsgTypeEnum.LOGOUT);

                // Get server's closing reply and show it to user.
                inMsg = (Message) inObj.readObject();
                if (inMsg.getMsgType() == MsgTypeEnum.TEXT) {
                    System.out.println(((TextMessage) inMsg).getText());
                } else {
                    System.out.println("UNEXPECTED RESPONSE: " + inMsg);
                }

            }   // Streams and sockets closed by try-with-resources

            System.out.println("Connection to " + hostname + ":" + port
                    + " closed, exiting.");
        }
    }
}