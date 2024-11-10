package clack.message;

public class OptionMessage extends Message
{
    public OptionMessage(String username)
    {
        super(username, MsgType.OPTION);
    }

    @Override
    public String toString()
    {
        return "OptionMessage{"
                + super.toString()
                + "} ";
    }
}
