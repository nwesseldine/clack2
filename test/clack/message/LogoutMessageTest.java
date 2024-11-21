package clack.message;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LogoutMessageTest {

    /**
     * Test toString. Match all fields except for timestamp.
     */
    @Test
    void testToString() {
        LogoutMessage lm = new LogoutMessage("user");
        String expected = "LogoutMessage{"
                + "Message{msgTypeEnum=LOGOUT"
                + ", timestamp=omitted"
                + ", username='user'"
                + "}}";
        String actual = lm.toString().replaceFirst(
                "timestamp=.*, username=",
                "timestamp=omitted, username=") ;
        assertEquals(expected, actual);
    }
}