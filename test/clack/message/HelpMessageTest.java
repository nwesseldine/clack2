package clack.message;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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