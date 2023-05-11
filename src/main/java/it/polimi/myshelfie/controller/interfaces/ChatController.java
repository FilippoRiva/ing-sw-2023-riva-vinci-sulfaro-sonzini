package it.polimi.myshelfie.controller.interfaces;

import it.polimi.myshelfie.model.chatting.Message;
import it.polimi.myshelfie.model.events.ChatEvent;
import it.polimi.myshelfie.model.modelviews.PlayerChatModelView;
import it.polimi.myshelfie.utils.Observer;

public interface ChatController {
    /**
     *
     * @param message is a message written by a player and sent to one or more players.
     *
     */
    void addMessage(Message message);

    void addChatObserver(String playerName, Observer<ChatEvent, PlayerChatModelView> networkChatObserver);

}
