package it.polimi.myshelfie.model.interfaces;

import it.polimi.myshelfie.model.chatting.Message;

import java.util.Observer;

public interface ChatModel {
    /**
     *
     * @param message is a new message to add to the chat.
     */
    void addMessage(Message message);

    //TODO methods to manage Observer/Listener

}
