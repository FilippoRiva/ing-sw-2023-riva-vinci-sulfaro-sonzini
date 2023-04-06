package it.polimi.myshelfie.controller.interfaces;

import it.polimi.myshelfie.model.chatting.Message;

public interface ChatController {
    /**
     *
     * @param message is a message written by a player and sent to one or more players.
     *
     */
    void addMessage(Message message);

    //TODO methods to manage Observer/Listener between model and view

}
