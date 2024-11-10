package clack.message;

public class TextMessage
        extends Message
{
    private final String text;

    public TextMessage(String username, String text)
    {
        super(username, MsgType.TEXT);
        this.text = text;
    }

    public String getText()
    {
        return text;
    }

    @Override
    public String toString()
    {
        return "TextMessage{"
                + super.toString()
                + ",text='" + text + '\'' +
                '}';
    }
}
