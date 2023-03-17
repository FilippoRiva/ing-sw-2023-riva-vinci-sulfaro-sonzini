package it.polimi.myshelfie.model.chatting;

import it.polimi.myshelfie.model.interfaces.ChatController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observer;

public class ChatSystem implements ChatController
{

    private final ArrayList<Message> messages;
    private final HashMap<String,PlayerChat> playerChats;

    public ChatSystem(){
        messages = new ArrayList<>();
        playerChats = new HashMap<>();
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }
    @Override
    public void addMessage(Message message) {
        //to everyone
        if(message.getReceiver().equals(Messages.toEveryOne)){
            messages.add(message);
            for(PlayerChat pc : playerChats.values()) pc.addMessage(message);
            return;
        }
        //not a player
        if(!playerChats.containsKey(message.getSender())) return;
        //to a player
        messages.add(message);
        playerChats.get(message.getReceiver()).addMessage(message);
    }

    @Override
    public void addPlayerToChat(String player,Observer observer){
        playerChats.put(player,new PlayerChat(player,observer));
    }

    public PlayerChatView getPlayerChatView(String player){
        if(!playerChats.containsKey(player)) return null;
        return playerChats.get(player).getPlayerChatView();
    }
}
