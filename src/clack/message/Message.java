package clack.message;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * This is a message class for exchanging Message objects
 *  with a client. The exchange is conversational, that is, one
 *  side sends a Message, then waits for a reply Message from the
 *  other side, then sends another Message, waits for a reply,
 *  and so on.
 *  <p>
 *  This class handles assigning message types, assigning hashcode to each message, as well
 *  as locating a username and timestamp for the message.
 */
public abstract class Message implements Serializable
{
    private final MsgTypeEnum msgTypeEnum;
    private final Instant timestamp;
    private final String username;

    /**
     * Constructs a message including the username and message type, as well as a time stamp.
     * @param username the username of the message sender.
     * @param msgTypeEnum the message type of the message being sent.
     */
    public Message(String username, MsgTypeEnum msgTypeEnum)
    {
        this.msgTypeEnum = msgTypeEnum;
        this.timestamp = Instant.now();
        this.username = username;
    }

    /**
     * Gets message type of the message being sent.
     * @return returns message type.
     */
    public MsgTypeEnum getMsgType()
    {
        return msgTypeEnum;
    }

    /**
     * Gets the time stamp of the message being sent.
     * @return returns the time stamp.
     */
    public Instant getTimestamp()
    {
        return timestamp;
    }

    /**
     * Gets the username of the user sending a message.
     * @return returns the username.
     */
    public String getUsername()
    {
        return username;
    }

    /**
     * Tests whether this Message is equal to some other object.
     * @param o the other object
     * @return true iff o is equal to this object
     */
    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Message message = (Message) o;
        return msgTypeEnum == message.msgTypeEnum
                && Objects.equals(timestamp, message.timestamp)
                && Objects.equals(username, message.username);
    }

    /**
     * Assigns hashcode to the individual messages.
     * @return returns hashcode value for the message.
     */
    @Override
    public int hashCode()
    {
        return Objects.hash(msgTypeEnum, timestamp, username);
    }

    /**
     * Returns a string representation of this Message
     * @return a string representation of this Message
     */
    @Override
    public String toString()
    {
        return "Message{" +
                "msgTypeEnum=" + msgTypeEnum +
                ", timestamp=" + timestamp +
                ", username='" + username + '\'' +
                '}';
    }
}
