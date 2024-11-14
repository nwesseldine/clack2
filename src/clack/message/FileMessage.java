package clack.message;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileMessage extends Message
{
    private final String fileContents;
    private final String fileName;

    public FileMessage(String username, String fileReadPath)
            throws IOException
    {
        this(username, fileReadPath, fileReadPath);
    }

    public FileMessage(String username, String fileReadPath, String fileSaveName)
            throws IOException
    {
        super(username, MsgTypeEnum.FILE);
        this.fileContents = Files.readString(Path.of(fileReadPath));
        this.fileName = String.valueOf(Path.of(fileSaveName).getFileName());
    }
    public String getFileContents()
    {
        return this.fileContents;
    }

    public String getFileName()
    {
        return this.fileName;
    }
    @Override
    public String toString()
    {
        return "FileMessage{"
                + super.toString()
                + "}";
    }
}
