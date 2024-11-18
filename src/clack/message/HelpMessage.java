package clack.message;

/**
 * This is a help message class for handling messages of the "Help" type.
 * The message is read in and handled by forming a help message, which is then
 * converted to a string.
 */
public class HelpMessage extends Message
{
    /**
     * Created a help message including the username of the message sender.
     * @param username the username of the message sender.
     */
    public HelpMessage(String username)
    {
        super(username, MsgTypeEnum.HELP);
    }


    /**
     * Converts the help message into a string.
     * @return returns the help message in string form.
     */
    @Override
    public String toString()
    {
        return "HelpMessage{"
                + super.toString()
                + "}";
    }
}
