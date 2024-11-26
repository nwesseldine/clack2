package clack.message;

/**
 * This is a simple Logout Message class that creates a logout message.
 */
public class LogoutMessage
        extends Message
{
    /**
     * Constructs a logout message using the username of the sender.
     * @param username username of the sender.
     */
    public LogoutMessage(String username)
    {
        super(username, MsgTypeEnum.LOGOUT);
    }

    /**
     * Converts the logout message to a string.
     * @return Returns logout message.
     */
    @Override
    public String toString()
    {
        return "LogoutMessage{"
                + super.toString()
                + "}";
    }
}

