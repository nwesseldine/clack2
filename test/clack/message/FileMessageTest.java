package clack.message;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileMessageTest
{

    @Test
    void getFileContents()
    {
    }

    @Test
    void getFileName()
    {
    }

    @Test
    void testToString() throws IOException
    {
        FileMessage msg = new FileMessage ("test user", "filereadpath");
        String expected =
                "FileMessage{Message{msgTpeEnum=FILE, username='test user', fileReadPath= 'filereadpath', timestamp=}}";
        String actual =
                msg.toString().replaceFirst("timestamp=.*$", "timestamp=}}");
        assertEquals(expected, actual);
    }
}