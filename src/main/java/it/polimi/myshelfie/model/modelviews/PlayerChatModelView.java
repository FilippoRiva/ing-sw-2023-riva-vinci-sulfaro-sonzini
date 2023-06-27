package it.polimi.myshelfie.model.modelviews;

import it.polimi.myshelfie.model.chatting.Message;
import it.polimi.myshelfie.model.chatting.PlayerChat;

import java.io.Serializable;
import java.util.List;

/**
 * Represents the player chat model view.
 */
public class PlayerChatModelView implements Serializable {
    private final List<Message> messages;

    /**
     * Constructor.
     * @param playerChat the player chat I want the model view.
     */
    public PlayerChatModelView(PlayerChat playerChat){
        this.messages = playerChat.getMessages();
    }

    /**
     *
     * @return the player's last message sent in his personal chat.
     */
    public Message getLastMessage() {
        return messages.get(messages.size()-1);
    }

    /**
     *
     * @return the player's list of messages in the chat.
     */
    public List<Message> getMessages() {return messages;}
}
