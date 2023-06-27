package it.polimi.myshelfie.model.chatting;

import java.io.Serializable;
import java.time.LocalTime;

/**
 * Represents a chat message.
 * It has the name of the sender and the receiver, the text message and the time when it was sent.
 */
public class Message implements Serializable {
    final private String sender;
    final private String receiver;
    final private String message;
    final private LocalTime time;

    /**
     * Constructor
     * @param sender who sent the message.
     * @param receiver who received a message.
     * @param message the text message.
     */
    public Message(String sender,String receiver, String message){
        if(sender == null) this.sender = Messages.fromNobody;
        else this.sender = sender;
        if(receiver == null) this.receiver = Messages.toEveryOne;
        else this.receiver = receiver;
        if(message == null) this.message = Messages.emptyMessage;
        else this.message = message;

        time = LocalTime.now();
    }

    /**
     *
     * @return the sender's name.
     */
    public String getSender() {
        return sender;
    }

    /**
     *
     * @return the receiver's name.
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     *
     * @return the text message String.
     */

    public String getMessage() {
        return message;
    }

    /**
     *
     * @return the local time.
     */
    public LocalTime getTime() { return time; }

    /**
     *
     * @return the current hour.
     */
    public int getHour(){
        return time.getHour();
    }

    /**
     *
     * @return the current minute.
     */
    public int getMinute(){
        return time.getMinute();
    }

    /**
     *
     * @return the current hour and minute.
     */
    public String getTimeHHmm(){
        return time.toString().substring(0, 5);
    }
}
