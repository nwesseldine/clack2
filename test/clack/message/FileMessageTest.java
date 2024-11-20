package clack.message;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;

import static java.time.Duration.between;
import static org.junit.jupiter.api.Assertions.*;

class FileMessageTest {

    /* Initialized at run time (OS's temp dir) */
    static Path TEMP_DIR_PATH;
    static String TEMP_DIR_STR;
    static Path TEST_FILE_PATH;
    static String TEST_FILE_STR;
    static String TEST_FILE_NAME;
    static String FILE_CONTENTS;

    static final String USERNAME = "the user";
    static final String TEST_FILE_AS_NAME = "clack_test_send_as.txt";


    /**
     * Create a temporary directory within the OS's default temporary
     * directory, and create a test file in that directory.
     *
     * @throws IOException if directory or file creation fails.
     */
    @BeforeAll
    static void createTestFile() throws IOException {
        TEMP_DIR_PATH = Files.createTempDirectory("clack2-").toRealPath();
        TEMP_DIR_STR = TEMP_DIR_PATH.toString();
        TEST_FILE_PATH = Files.createTempFile(TEMP_DIR_PATH, "test-", null);
        TEST_FILE_STR = TEST_FILE_PATH.toString();
        TEST_FILE_NAME = TEST_FILE_PATH.getFileName().toString();
        FILE_CONTENTS = "This is a test file\nwith three lines,\n"
                + "created at " + Instant.now() + ".";
        try (
                PrintWriter out =
                        new PrintWriter(TEST_FILE_PATH.toFile());
        ) {
            out.print(FILE_CONTENTS);
        }   // PrintWriter autocloses here.
    }

    /**
     * Delete the test file and the test directory.
     *
     * @throws IOException if file or directory deletion fails.
     */
    @AfterAll
    static void deleteTestFile() throws IOException {
        Files.delete(TEST_FILE_PATH);
        Files.delete(TEMP_DIR_PATH);
    }

    /**
     * Test constructors for:
     * <ul>
     *     <li>Non-existent directories in path: should throw.</li>
     *     <li>Non-existent file: should throw.</li>
     *     <li>Path with redundant elements (.. and such):
     *          should be OK.</li>
     *     <li>Correct file contents and "save as" filename.</li>
     * </ul>
     * @throws IOException if file can't be found/read when it
     *  should be findable/readable.
     */
    @Test
    void testConstructors() throws IOException {
        // Non-existent directories in path.
        assertThrows(IOException.class, () -> {
            Path p = TEMP_DIR_PATH.resolve("NOPE")
                    .resolve(TEST_FILE_NAME);
            FileMessage fm = new FileMessage(USERNAME,
                    p.toString());
        });

        // Directory components OK but file doesn't exist.
        assertThrows(IOException.class, () -> {
            Path p = TEMP_DIR_PATH.resolve("NOT_HERE.txt");
            FileMessage fm = new FileMessage(USERNAME,
                    p.toString());
        });

        // Path with redundant elements (should succeed)
        Path temp_dir = TEMP_DIR_PATH.getFileName();
        Path p = TEMP_DIR_PATH.resolve("..")
                .resolve(temp_dir)
                .resolve(TEST_FILE_NAME);
        FileMessage fm1 = new FileMessage(USERNAME, p.toString());
        assertEquals(FILE_CONTENTS, fm1.getFileContents());
        assertEquals(TEST_FILE_NAME, fm1.getFileName());

        // Readfile only, no save-as file name
        FileMessage fm2 = new FileMessage(USERNAME, TEST_FILE_STR);
        assertEquals(FILE_CONTENTS, fm2.getFileContents());
        assertEquals(TEST_FILE_PATH.getFileName().toString(),
                fm2.getFileName());

        // Both file names
        FileMessage fm3 = new FileMessage(USERNAME, TEST_FILE_STR,
                TEMP_DIR_PATH.resolve(TEST_FILE_AS_NAME).toString());
        assertEquals(FILE_CONTENTS, fm3.getFileContents());
        assertEquals(TEST_FILE_AS_NAME, fm3.getFileName());
    }

    /**
     * Test getMsgType inherited from Message.
     *
     * @throws IOException if test file can't be found or read.
     */
    @Test
    void getMsgType() throws IOException {
        FileMessage fm0 = new FileMessage(USERNAME,
                TEST_FILE_STR);
        FileMessage fm1 = new FileMessage(USERNAME,
                TEST_FILE_STR, TEST_FILE_AS_NAME);
        assertEquals(MsgTypeEnum.FILE, fm0.getMsgType());
        assertEquals(MsgTypeEnum.FILE, fm1.getMsgType());
    }

    /**
     * Test getUsername inherited from Message.
     *
     * @throws IOException if test file can't be found or read.
     */
    @Test
    void getUsername() throws IOException {
        FileMessage fm0 = new FileMessage(USERNAME,
                TEST_FILE_STR);
        FileMessage fm1 = new FileMessage(USERNAME,
                TEST_FILE_STR, TEST_FILE_AS_NAME);
        assertEquals(USERNAME, fm0.getUsername());
        assertEquals(USERNAME, fm1.getUsername());
    }

    /**
     * Test getTimestamp inherited from Message. Test by creating
     * a FileMessage object, getting Instant.now() right after
     * the constructor returns, and then testing that the
     * FileMessage timestamp is no more than a second away from "now".
     *
     * @throws IOException if test file can't be found or read.
     */
    @Test
    void getTimestamp() throws IOException {
        FileMessage fm = new FileMessage(USERNAME,
                TEST_FILE_STR);
        Instant now = Instant.now();
        Duration duration = between(fm.getTimestamp(), now);
        long timeDiff = Math.abs(duration.toSeconds());
        assertTrue(timeDiff <= 1);
    }

    /**
     * Test hashCode inherited from Message. There's not much that
     * can be tested, given the timestamp difference between Message
     * object, so we create two otherwise identical FileMessages a
     * short time apart, then make sure the hashCodes are unequal.
     *
     * @throws IOException if test file can't be found or read.
     */
    @Test
    void testHashCode()
            throws IOException, InterruptedException
    {
        FileMessage fm0 = new FileMessage(USERNAME,
                TEST_FILE_STR);
        Thread.sleep(10);
        FileMessage fm1 = new FileMessage(USERNAME,
                TEST_FILE_STR);
        assertNotEquals(fm0.hashCode(), fm1.hashCode());
    }

    /**
     * Test toString. Match all fields except for timestamp.
     */
    @Test
    void testToString() throws IOException {
        FileMessage fm = new FileMessage(USERNAME,
                TEST_FILE_STR);
        String expected = "FileMessage{Message{msgTypeEnum=FILE"
                + ", timestamp=omitted"
                + ", username='" + USERNAME + "'}"
                + ", fileName='" + TEST_FILE_NAME + "'"
                + ", fileContents='" + FILE_CONTENTS + "'}";
        String actual = fm.toString().replaceFirst(
                "timestamp.*, username=",
                "timestamp=omitted, username=");
        assertEquals(expected, actual);
    }

}