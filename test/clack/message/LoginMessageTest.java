package clack.message;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class LoginMessageTest
{
    @org.junit.jupiter.api.Test
    void testConstructor(){
        LoginMessage msg = new LoginMessage("test user", "resu tset");
        Instant now = Instant.now();

        assertEquals("test user", msg.getUsername());
        assertEquals(MsgTypeEnum.LOGIN,msg.getMsgType());

        assertTrue(Math.abs((now.getEpochSecond()
                - msg.getTimestamp().getEpochSecond())) <= 1);
        //what else
    }

    @Test
    void getPassword()
    {
    }

    @Test
    void testToString()
    {
    }
}