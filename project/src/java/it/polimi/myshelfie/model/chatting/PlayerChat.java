package it.polimi.myshelfie.model.chatting;

import java.util.ArrayList;
import java.util.Observer;

public class PlayerChat {
    private final ArrayList<Message> messages;
    private final String player;
    private final PlayerChatView playerChatView;

    public PlayerChat(String player, Observer observer) {
        this.player = player;
        this.messages = new ArrayList<>();
        this.playerChatView = new PlayerChatView(this);
        this.playerChatView.addObserver(observer);
    }

    public void addMessage (Message message){
        messages.add(message);
        playerChatView.setChangedStatus();
        playerChatView.notifyObservers();
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public Message getLastMessage(){
        return messages.get(messages.size()-1);
    }
    public PlayerChatView getPlayerChatView(){
        return playerChatView;
    }
}
