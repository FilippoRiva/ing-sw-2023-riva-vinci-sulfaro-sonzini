package it.polimi.myshelfie.model.chatting;

import java.util.ArrayList;
import java.util.List;

public class PlayerChat {
    private List<Message> messages;
    private String player;

    public PlayerChat() {
        messages = new ArrayList<>();
    }

    public void addMessage (Message message){
        messages.add(message);
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }
}
