package clack.message;

public class LoginMessage extends Message
{
    public LoginMessage(String username)
    {
        super(username, MsgType.LOGIN);
    }

    @Override
    public String toString()
    {
        return "LoginMessage{"
                + super.toString()
                + "} ";
    }
}