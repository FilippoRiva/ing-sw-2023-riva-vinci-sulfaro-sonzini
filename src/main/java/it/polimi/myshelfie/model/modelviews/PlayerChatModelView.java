package it.polimi.myshelfie.model.modelviews;

import it.polimi.myshelfie.model.chatting.Message;
import it.polimi.myshelfie.model.chatting.PlayerChat;

import java.io.Serializable;

public class PlayerChatModelView implements Serializable {
    private final Message lastMessage;
    public PlayerChatModelView(PlayerChat playerChat){
        this.lastMessage=playerChat.getLastMessage();
    }
    public Message getLastMessage() {
        return lastMessage;
    }
}
