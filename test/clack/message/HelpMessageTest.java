package clack.message;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Simple Help Message class. Uses the username of the sender, the timestamp of the message, and creates a help message.
 */
class HelpMessageTest {

    /**
     * Test toString. Match all fields except for timestamp.
     */
    @Test
    void testToString() {
        HelpMessage hm = new HelpMessage("user");
        String expected = "HelpMessage{"
                + "Message{msgTypeEnum=HELP"
                + ", timestamp=omitted"
                + ", username='user'"
                + "}}";
        String actual = hm.toString().replaceFirst(
                "timestamp=.*, username=",
                "timestamp=omitted, username=");
        assertEquals(expected, actual);
    }
}