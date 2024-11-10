package clack.message;

public class ListUsersMessage
        extends Message
{
    public ListUsersMessage(String username)
    {
        super(username, MsgType.LISTUSERS);
    }

    @Override
    public String toString()
    {
        return "ListUsersMessage{"
                + super.toString()
                + "}";
    }
}
