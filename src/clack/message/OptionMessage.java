package clack.message;

/**
 * This is a simple OptionMessage Message class that allows users to choose their cipher option.
 */
public class OptionMessage extends Message
{
    private final OptionEnum option;
    private final String value;

    /**
     * Constructs an option message with the username of the sender, their option, and the value they input.
     * @param username username of the sender.
     * @param op cipher option that the user chooses.
     * @param val value the user inputs.
     */
    public OptionMessage(String username, OptionEnum op, String val)
    {
        super(username, MsgTypeEnum.OPTION);
        this.option = op;
        //value of cipher key/cipher sdlkfnsl
        this.value = val;
    }

    /**
     * Get method for retrieving the option.
     * @return Cipher option.
     */
   public OptionEnum getOption() {

        return this.option;
   }

    /**
     * Get method for retrieving the value of the message.
     * @return Value that the user inputs.
     */
   public String getValue() {
        return this.value;

   }

    /**
     * Converts the option message to a string.
     * @return returns option message.
     */
    @Override
    public String toString()
    {
        return "OptionMessage{"
                + super.toString() +
                ", option=" + option +
                ", value='" + value + '\''
                + '}';
    }

}
