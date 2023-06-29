package it.polimi.myshelfie.model.chatting;


import it.polimi.myshelfie.model.events.ChatEvent;
import it.polimi.myshelfie.model.modelviews.PlayerChatModelView;
import it.polimi.myshelfie.utils.Observable;

import java.util.ArrayList;

/**
 * Represents the player chat. It contains a list of messages.
 */
public class PlayerChat extends Observable<ChatEvent, PlayerChatModelView> {
    private final ArrayList<Message> messages;

    /**
     * Constructor. Instantiates a new PlayerChat.
     */
    public PlayerChat() {
        this.messages = new ArrayList<>();
    }

    /**
     * Adds a message to this player's chat and triggers Observers.
     *
     * @param message the new message to be added.
     */
    public void addMessage(Message message) {
        messages.add(message);
        setChanged();
        notifyObservers(ChatEvent.NEW_MESSAGE, new PlayerChatModelView(this));
    }

    /**
     * Returns the entire list of received messages by the player.
     *
     * @return the entire list of received messages by the player.
     */
    public ArrayList<Message> getMessages() {
        return new ArrayList<>(messages);
    }
}
