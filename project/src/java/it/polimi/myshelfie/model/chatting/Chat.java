package it.polimi.myshelfie.model.chatting;

import java.util.ArrayList;
import java.util.List;

public class Chat {
    private ArrayList<Message> messages;
    private List<PlayerChat> playerChats;

    public Chat(){
        messages = new ArrayList<>();
        playerChats = new ArrayList<>();
    }

    public void addMessage(Message message){
        messages.add(message);
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }
}
