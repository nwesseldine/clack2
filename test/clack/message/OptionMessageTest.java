package clack.message;

import static org.junit.jupiter.api.Assertions.*;

class OptionMessageTest
{

    @org.junit.jupiter.api.Test
    void getOption()
    {
    }

    @org.junit.jupiter.api.Test
    void getValue()
    {
    }

    @org.junit.jupiter.api.Test
    void testToString()
    {
        OptionMessage msg = new OptionMessage ("test user", OptionEnum.CIPHER_NAME, "Playwright" );
        String expected =
                "OptionMessage{Message{msgTpeEnum=OPTION, username='test user', lfngerlnglreng, timestamp=}}";
        String actual =
                msg.toString().replaceFirst("timestamp=.*$", "timestamp=}}");
        assertEquals(expected, actual);
    }
}