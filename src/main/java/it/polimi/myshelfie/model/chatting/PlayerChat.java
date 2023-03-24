package it.polimi.myshelfie.model.chatting;

import it.polimi.myshelfie.modelview.PlayerChatView;

import java.util.ArrayList;
import java.util.Observer;

public class PlayerChat {
    //TODO new observer pattern
    private final ArrayList<Message> messages;
    private final String player;
    private final PlayerChatView playerChatView;

    /**
     * Instantiates a new PlayerChat
     * @param player the name of the owner of this PlayerChat
     * @param observer the Observer of the PlayerChatView
     */
    public PlayerChat(String player, Observer observer) {
        this.player = player;
        this.messages = new ArrayList<>();
        this.playerChatView = new PlayerChatView(this);
        this.playerChatView.addObserver(observer);
    }

    /**
     * Adds a message to this player's chat and triggers Observers
     * @param message the new message to be added
     */
    public void addMessage (Message message){
        messages.add(message);
        playerChatView.setChangedStatus();
        playerChatView.notifyObservers();
    }

    /**
     *
     * @return the entire list of received messages by the player
     */
    public ArrayList<Message> getMessages() {
        return messages;
    }

    /**
     *
     * @return the last message received by the player. If the player has never received any message it returns
     * an Empty Chat Message
     */
    public Message getLastMessage(){
        if(messages.size()==0) return new Message(Messages.fromNobody,Messages.toEveryOne,Messages.emptyChat);
        return messages.get(messages.size()-1);
    }

    /**
     *
     * @return the PlayerChatView of this PlayerChat
     */
    public PlayerChatView getPlayerChatView(){
        return playerChatView;
    }
}
