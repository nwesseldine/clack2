package clack.message;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * This is the FileMessage class for gathering a file's name and its contents.
 *   <p>
 *   To read a file path, the constructors will take the filepath and single out the name of
 *   the file to read and save its name.
 *   <p>
 *   The file contents will then be read and saved, then printed to string when necessary.
*/
public class FileMessage extends Message
{
    private final String fileContents;
    private final String fileName;

    /**
     * Constructs a username and file path using a different constructor.
     * @param username the username of the sender.
     * @param fileReadPath the path of the file to be read.
     * @throws IOException thrown when filepath is invalid. //TODO: check this?
     */
    public FileMessage(String username, String fileReadPath)
            throws IOException
    {
        this(username, fileReadPath, fileReadPath);
    }

    /**
     * This constructor includes a file path, a username, and a file name. The file name is
     * taken from the file path, minus the excess details about the file path. The file contents
     * gathered by following the file path.
     * @param username the username of the sender.
     * @param fileReadPath the path to be read for the file being sent.
     * @param fileSaveName the name taken from the file path of the file.
     * @throws IOException thrown when filepath is invalid. //TODO: check this?
     */
    public FileMessage(String username, String fileReadPath, String fileSaveName)
            throws IOException
    {
        super(username, MsgTypeEnum.FILE);
        this.fileContents = Files.readString(Path.of(fileReadPath));
        this.fileName = String.valueOf(Path.of(fileSaveName).getFileName());
    }

    /**
     * Gets file contents.
     * @return returns file contents.
     */
    public String getFileContents()
    {
        return this.fileContents;
    }

    /**
     * Gets file name.
     * @return returns file contents.
     */
    public String getFileName()
    {
        return this.fileName;
    }

    /**
     * Brings the second constructor to a string.
     * @return returns the file message's details.
     */
    @Override
    public String toString()
    {
        return "FileMessage{"
                + super.toString()
                + "}";
    }
}
