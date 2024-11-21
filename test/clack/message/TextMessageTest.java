package clack.message;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TextMessageTest {

    @Test
    void getText() {
        TextMessage hm = new TextMessage("user", "this is the text");
        assertEquals("this is the text", hm.getText());
    }

    /**
     * Test toString. Match all fields except for timestamp.
     */
    @Test
    void testToString() {
        TextMessage hm = new TextMessage("user", "this is the text");
        String expected = "TextMessage{"
                + "Message{msgTypeEnum=TEXT"
                + ", timestamp=omitted"
                + ", username='user'"
                + "}, text='this is the text'}";
        String actual = hm.toString().replaceFirst(
                "timestamp=.*, username=",
                "timestamp=omitted, username=") ;
        assertEquals(expected, actual);
    }
}