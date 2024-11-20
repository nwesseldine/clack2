package clack.message;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoginMessageTest {

    /**
     * Test getPassword.
     */
    @Test
    void getPassword() {
        LoginMessage lm = new LoginMessage("user", "opensesame");
        assertEquals("opensesame", lm.getPassword());
    }

    /**
     * Test toString. Match all fields except for timestamp.
     */
    @Test
    void testToString() {
        LoginMessage lm = new LoginMessage("user", "opensesame");
        String expected = "LoginMessage{"
                + "Message{msgTypeEnum=LOGIN"
                + ", timestamp=omitted"
                + ", username='user'"
                + "}, password='**********'}";
        String actual = lm.toString().replaceFirst(
                "timestamp=.*, username=",
                "timestamp=omitted, username=");
        assertEquals(expected, actual);
    }
}