package it.polimi.myshelfie.model.interfaces;

import it.polimi.myshelfie.model.chatting.Message;
import it.polimi.myshelfie.model.modelviews.PlayerChatModelView;
import it.polimi.myshelfie.network.ChatEvent;
import it.polimi.myshelfie.utils.Observable;
import it.polimi.myshelfie.utils.Observer;

public interface ChatModel {
    /**
     *
     * @param message is a new message to add to the chat.
     */
    void addMessage(Message message);

    void addChatObserver(String playerName, Observer<? extends Observable<?>, ChatEvent> obs);
    PlayerChatModelView getChatModelView(String playerName);
}
