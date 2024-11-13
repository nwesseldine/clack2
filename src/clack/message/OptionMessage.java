package clack.message;

public class OptionMessage extends Message
{
    private final OptionEnum option;
    private final String value;

    public OptionMessage(String username, OptionEnum op, String val)
    {
        super(username, MsgTypeEnum.OPTION);
        this.option = op;
        this.value = val;
    }

   public OptionEnum getOption() {
        return this.option;
   }

   public String getValue() {
        return this.value;

   }

}
