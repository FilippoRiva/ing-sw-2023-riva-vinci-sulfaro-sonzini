package com.myshelfie.model.chatting;

public class Message {
    //TODO Players or Strings?
    final private String sender;
    final private String receiver;
    final private String message;

    public Message(String sender,String receiver, String message){
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
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
