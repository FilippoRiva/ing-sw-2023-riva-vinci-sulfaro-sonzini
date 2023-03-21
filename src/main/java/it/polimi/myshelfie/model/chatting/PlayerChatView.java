package it.polimi.myshelfie.model.chatting;

import java.util.List;
import java.util.Observable;

public class PlayerChatView extends Observable{
    PlayerChat playerChat;
    public PlayerChatView(PlayerChat playerChat){
        this.playerChat = playerChat;
    }
    public Message getLastMessage(){
        return playerChat.getLastMessage();
    }
    public List<Message> getMessages(){
        return playerChat.getMessages();
    }
    public void setChangedStatus() { setChanged();}
}
