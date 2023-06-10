package it.polimi.myshelfie.controller.interfaces;

import it.polimi.myshelfie.model.chatting.Message;
import it.polimi.myshelfie.model.events.ChatEvent;
import it.polimi.myshelfie.model.modelviews.PlayerChatModelView;
import it.polimi.myshelfie.utils.Observer;

public interface ChatController {
    /**
     * Adds a message to the chat.
     * @param message is a message written by a player and sent to one or more players.
     *
     */
    void addMessage(String playerName, Message message);

    /**
     * Adds a chat observer.
     * @param playerName the name of the player.
     * @param networkChatObserver the observer to add.
     */
    void addChatObserver(String playerName, Observer<ChatEvent, PlayerChatModelView> networkChatObserver);

}
