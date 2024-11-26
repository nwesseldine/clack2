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
        StringBuilder asterix = new StringBuilder();
        for(int i = 0; i < password.length(); i++) {
            asterix.append('*');
        }
        return "LoginMessage{"
                + super.toString() +
                ", password=" + "'" + asterix + "'"
                + "}";
    }
}