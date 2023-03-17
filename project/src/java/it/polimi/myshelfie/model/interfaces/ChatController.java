package it.polimi.myshelfie.model.interfaces;

import it.polimi.myshelfie.model.chatting.Message;

import java.util.Observer;

public interface ChatController {
    void addMessage(Message message);
    void addPlayerToChat(String player,Observer observer);
}
