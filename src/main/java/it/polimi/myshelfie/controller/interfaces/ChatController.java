package it.polimi.myshelfie.controller.interfaces;

import it.polimi.myshelfie.model.chatting.Message;
import it.polimi.myshelfie.network.observers.NetworkChatObserver;

public interface ChatController {
    /**
     *
     * @param message is a message written by a player and sent to one or more players.
     *
     */
    void addMessage(Message message);

    void addChatObserver(NetworkChatObserver networkChatObserver);

}
