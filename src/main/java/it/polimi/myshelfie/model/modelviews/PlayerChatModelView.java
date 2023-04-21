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

    /**
     *
     * @return the player's last message sent in his personal chat.
     */
    public Message getLastMessage() {
        return lastMessage;
    }

    /**
     *
     * @return the player's list of messages in the chat.
     */
    public List<Message> getMessages() {return messages;}
}
