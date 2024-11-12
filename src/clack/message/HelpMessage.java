package clack.message;

public class HelpMessage extends Message
{
    public HelpMessage(String username)
    {
        super(username, MsgTypeEnum.HELP);
    }

    @Override
    public String toString()
    {
        return "HelpMessage{"
                + super.toString()
                + "}";
    }
}
