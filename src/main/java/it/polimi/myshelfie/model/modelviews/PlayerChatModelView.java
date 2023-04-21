package it.polimi.myshelfie.model.modelviews;

import it.polimi.myshelfie.model.chatting.Message;
import it.polimi.myshelfie.model.chatting.PlayerChat;

import java.io.Serializable;
import java.util.List;

public class PlayerChatModelView implements Serializable {
    private final Message lastMessage;
    private final List<Message> messages;
    public PlayerChatModelView(PlayerChat playerChat){
        this.lastMessage=playerChat.getLastMessage();
        this.messages = playerChat.getMessages();
    }
    public Message getLastMessage() {
        return lastMessage;
    }
    public List<Message> getMessages() {return messages;}
}
