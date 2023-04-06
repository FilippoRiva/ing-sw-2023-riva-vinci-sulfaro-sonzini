package it.polimi.myshelfie.model.interfaces;

import it.polimi.myshelfie.model.chatting.Message;
import it.polimi.myshelfie.model.modelviews.PlayerChatModelView;
import it.polimi.myshelfie.network.observers.NetworkChatObserver;

public interface ChatModel {
    /**
     *
     * @param message is a new message to add to the chat.
     */
    void addMessage(Message message);

    void addChatObserver(NetworkChatObserver obs);
    PlayerChatModelView getChatModelView(String playerName);
}
