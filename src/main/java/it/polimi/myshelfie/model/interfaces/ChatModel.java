package it.polimi.myshelfie.model.interfaces;

import it.polimi.myshelfie.model.chatting.Message;
import it.polimi.myshelfie.model.events.ChatEvent;
import it.polimi.myshelfie.model.modelviews.PlayerChatModelView;
import it.polimi.myshelfie.utils.Observer;

/**
 * Represents the chat's interface.
 */
public interface ChatModel {
    /**
     * Adds a message to the chat.
     * @param message the new message to add to the chat.
     */
    void addMessage(Message message);

    /**
     * Adds a chat observer.
     * @param obs the new network chat observer.
     */
    void addChatObserver(String playerName, Observer<ChatEvent, PlayerChatModelView> obs);

    /**
     * Removes a chat observer
     * @param obs the observer to remove
     */
    void removeChatObserver(String playerName, Observer<ChatEvent, PlayerChatModelView> obs);

    void removeChatObservers();
}
