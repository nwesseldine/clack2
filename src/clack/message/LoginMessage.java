package clack.message;

public class LoginMessage extends Message
{
    private final String password;
    public LoginMessage(String username, String pass)
    {
        super(username, MsgTypeEnum.LOGIN);
        this.password = pass;
    }

    public String getPassword()
    {
        return this.password;
    }

    @Override
    public String toString()
    {
        return "LoginMessage{"
                + super.toString()
                + "} ";
    }
}