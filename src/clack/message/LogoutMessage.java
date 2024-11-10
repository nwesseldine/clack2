package clack.message;

public class LogoutMessage
        extends Message
{
    public LogoutMessage(String username)
    {
        super(username, MsgType.LOGOUT);
    }

    @Override
    public String toString()
    {
        return "LogoutMessage{"
                + super.toString()
                + "}";
    }
}

