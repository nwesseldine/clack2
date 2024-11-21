package clack.message;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ListUsersMessageTest {

    /**
     * Test toString. Match all fields except for timestamp.
     */
    @Test
    void testToString() {
        ListUsersMessage lm = new ListUsersMessage("user") ;
        String expected = "ListUsersMessage{"
                + "Message{msgTypeEnum=LISTUSERS"
                + ", timestamp=omitted"
                + ", username='user'"
                + "}}";
        String actual = lm.toString().replaceFirst(
                "timestamp=.*, username=",
                "timestamp=omitted, username=");
        assertEquals(expected, actual);
    }
}