package it.polimi.myshelfie.model.chatting;

import java.util.ArrayList;

public class Chat {
    ArrayList<Message> messages = new ArrayList<>();

    public void addMessage(Message message){
        messages.add(message);
    }

}
