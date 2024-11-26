package clack.message;

/**
 * This is a simple List Users class. Creates a ist of users to send messages to when requested.
 */
public class ListUsersMessage
        extends Message
{
    /**
     * Constructs a ListUsersMessage with the username of the sender.
     * @param username The username of the sender.
     */
    public ListUsersMessage(String username)
    {
        super(username, MsgTypeEnum.LISTUSERS);
    }

    /**
     * Creates the list users message, converted to a string.
     * @return Returns the List Users Message.
     */
    @Override
    public String toString()
    {
        return "ListUsersMessage{"
                + super.toString()
                + "}";
    }
}
