package it.polimi.myshelfie.model.interfaces;

import it.polimi.myshelfie.model.chatting.Message;
import it.polimi.myshelfie.model.modelviews.PlayerChatModelView;
import it.polimi.myshelfie.network.observers.NetworkChatObserver;

public interface ChatModel {
    /**
     * Adds a message to the chat.
     * @param message is the new message to add to the chat.
     */
    void addMessage(Message message);

    /**
     * Adds a chat observer.
     * @param obs is the new network chat observer.
     */
    void addChatObserver(NetworkChatObserver obs);

    /**
     * Removes a chat observer
     * @param obs the observer to remove
     */
    void removeChatObserver(NetworkChatObserver obs);

    /**
     * Returns the chat view associated to that player.
     * @param playerName is the name of the player whose view I want.
     * @return this player chat view.
     */
    PlayerChatModelView getChatModelView(String playerName);
}
