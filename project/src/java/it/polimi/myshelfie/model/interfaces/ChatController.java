package it.polimi.myshelfie.model.interfaces;

import it.polimi.myshelfie.model.chatting.Message;

import java.util.Observer;

public interface ChatController {
    /**
     *
     * @param message is a new message to add to the chat.
     */
    void addMessage(Message message);

    /**
     *
     * @param player is the name of a new player that has to be added to the chat
     * @param observer is the observer of the corresponding PlayerChatView representing his chat
     */
    void addPlayerToChat(String player,Observer observer);
}
