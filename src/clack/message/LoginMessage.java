package clack.message;

/**
 * This is a simple Login Message class that creates a login message after the user has successfully entered the correct username amd password.
 */
public class LoginMessage extends Message
{
    private final String password;

    /**
     * Constructs a default username and password for the user.
     * @param username username of the user.
     * @param pass temporary password.
     */
    public LoginMessage(String username, String pass)
    {
        super(username, MsgTypeEnum.LOGIN);
        this.password = pass;
    }

    /**
     * Gets the password of the user's account.
     * @return Returns the user's password.
     */
    public String getPassword()
    {
        return this.password;
    }

    /**
     * Converts the login message to a string.
     * @return returns the login message.
     */
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