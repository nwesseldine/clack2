package clack.message;

/**
 * This is a simple text message class for the 'text message' message type.
 */
public class TextMessage
        extends Message
{
    private final String text;

    /**
     * Constructs a text message with the username and text of the message.
     * @param username Username of the sender.
     * @param text Text within the text message.
     */
    public TextMessage(String username, String text)
    {
        super(username, MsgTypeEnum.TEXT);
        this.text = text;
    }

    /**
     * Get method to return the text of the message.
     * @return returns text.
     */
    public String getText()
    {
        return text;
    }

    /**
     * Converts the text message to a string.
     * @return returns text message.
     */
    @Override
    public String toString()
    {
        return "TextMessage{"
                + super.toString()
                + ", text='" + text + '\'' +
                '}';
    }
}
