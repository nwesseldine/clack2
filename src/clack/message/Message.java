package clack.message;

import java.io.Serializable;
import java.time.Instant;

public class Message implements Serializable
{
    private final MsgType msgType;
    private final Instant timestamp;
    private final String username;

    public Message(String username, MsgType msgType)
    {
        this.msgType = msgType;
        this.timestamp = Instant.now();
        this.username = username;
    }

    public MsgType getMsgType()
    {
        return msgType;
    }

    public Instant getTimestamp()
    {
        return timestamp;
    }

    public String getUsername()
    {
        return username;
    }

    @Override
    public String toString()
    {
        return "Message{" +
                "msgType=" + msgType +
                ", timestamp=" + timestamp +
                ", username='" + username + '\'' +
                '}';
    }
}
