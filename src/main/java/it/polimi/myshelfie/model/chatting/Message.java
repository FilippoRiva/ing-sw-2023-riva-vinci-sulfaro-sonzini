package it.polimi.myshelfie.model.chatting;

import java.io.Serializable;
import java.time.LocalTime;

public class Message implements Serializable {
    final private String sender;
    final private String receiver;
    final private String message;
    final private LocalTime time;

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
     * @return the message String.
     */

    public String getMessage() {
        return message;
    }

    public LocalTime getTime() {
        return time;
    }
    public int getHour(){
        return time.getHour();
    }
    public int getMinute(){
        return time.getMinute();
    }
    public String getTimeHHmm(){
        return time.toString().substring(0, 5);
    }
}
