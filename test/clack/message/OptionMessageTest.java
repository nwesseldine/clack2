package clack.message;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OptionMessageTest {

    /**
     * Test all OptionEnum values.
     */
    @Test
    void getOption() {
        for (OptionEnum opt : OptionEnum.values()) {
            OptionMessage om = new OptionMessage("user", opt, "setting");
            assertEquals(opt, om.getOption());
        }
    }

    /**
     * Test for value of null, empty string, non-empty string.
     */
    @Test
    void getValue() {
        OptionEnum opt = OptionEnum.CIPHER_KEY;
        String[] values = {null, "", "playfair"};
        for (String v : values) {
            OptionMessage om = new OptionMessage("user", opt, v);
            assertEquals(v, om.getValue());
        }
    }

    /**
     * Test toString. Match all fields except for timestamp.
     * Test for value of null, empty string, non-empty string.
     */
    @Test
    void testToString() {
        OptionEnum opt = OptionEnum.CIPHER_KEY;
        String[] values = {null, "", "playfair"};
        for (String v : values) {
            OptionMessage om = new OptionMessage("user", opt, v);
            String expected = "OptionMessage{"
                    + "Message{msgTypeEnum=OPTION"
                    + ", timestamp=omitted"
                    + ", username='user'"
                    + "}"
                    + ", option=" + opt.toString()
                    + ", value='" + v
                    + "'}";
            String actual = om.toString().replaceFirst(
                    "timestamp=.*, username=",
                    "timestamp=omitted, username=");
            assertEquals(expected, actual);
        }
    }
}