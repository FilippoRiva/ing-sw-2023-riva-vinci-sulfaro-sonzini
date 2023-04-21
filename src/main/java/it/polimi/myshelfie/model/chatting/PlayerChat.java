package it.polimi.myshelfie.model.chatting;


import it.polimi.myshelfie.network.events.ChatEvent;
import it.polimi.myshelfie.utils.Observable;

import java.util.ArrayList;

public class PlayerChat extends Observable<ChatEvent> {
    private final ArrayList<Message> messages;
    private final String player;

    /**
     * Instantiates a new PlayerChat.
     * @param player the name of the owner of this PlayerChat.
     */
    public PlayerChat(String player) {
        this.player = player;
        this.messages = new ArrayList<>();
    }

    /**
     * Adds a message to this player's chat and triggers Observers.
     * @param message the new message to be added.
     */
    public void addMessage (Message message){
        messages.add(message);
        setChanged();
        notifyObservers(ChatEvent.NEW_MESSAGE);
    }

    /**
     *
     * @return the entire list of received messages by the player.
     */
    public ArrayList<Message> getMessages() {
        return messages;
    }

    /**
     *
     * @return the last message received by the player. If the player has never received any message it returns
     * an Empty Chat Message.
     */
    public Message getLastMessage(){
        if(messages.size()==0) return new Message(Messages.fromNobody,Messages.toEveryOne,Messages.emptyChat);
        return messages.get(messages.size()-1);
    }
}
