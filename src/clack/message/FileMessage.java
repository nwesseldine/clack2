package clack.message;

public class FileMessage extends Message
{
    private final String fileContents;
    private final String fileName;

    public FileMessage(String username, String fileReadPath)
    {
        super(username, MsgTypeEnum.FILE);
    }
    public FileMessage(String username, String fileReadPath, String fileReadName)
    {
        super(username, MsgTypeEnum.FILE);
    }

}
