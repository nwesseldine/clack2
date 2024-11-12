package clack.message;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class HelpMessageTest
{
    @org.junit.jupiter.api.Test
    void testConstructor(){
        HelpMessage msg = new HelpMessage("test user");
        Instant now = Instant.now();

        assertEquals("test user", msg.getUsername());
        assertEquals(MsgTypeEnum.HELP,msg.getMsgType());

        assertTrue(Math.abs((now.getEpochSecond()
                - msg.getTimestamp().getEpochSecond())) <= 1);
    }
    @org.junit.jupiter.api.Test
    void testToString()
    {
        HelpMessage msg = new HelpMessage ("test user");
        String expected =
                "HelpMessage{Message{msgTpeEnum=HELP, username='test user', timestamp=}}";
        String actual =
                msg.toString().replaceFirst("timestamp=.*$", "timestamp=}}");
        assertEquals(expected, actual);
    }
}