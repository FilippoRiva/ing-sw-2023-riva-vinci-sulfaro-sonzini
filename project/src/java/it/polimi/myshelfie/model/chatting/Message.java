package it.polimi.myshelfie.model.chatting;

public class Message {
    final private String sender;
    final private String receiver;
    final private String message;

    public Message(String sender,String receiver, String message){
        if(sender == null) this.sender = Messages.fromNobody;
        else this.sender = sender;
        if(receiver == null) this.receiver = Messages.toEveryOne;
        else this.receiver = receiver;
        if(message == null) this.message = Messages.emptyMessage;
        else this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getMessage() {
        return message;
    }
}
