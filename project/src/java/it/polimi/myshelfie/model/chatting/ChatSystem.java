package it.polimi.myshelfie.model.chatting;

import it.polimi.myshelfie.model.interfaces.ChatModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observer;

public class ChatSystem implements ChatModel
{

    private final ArrayList<Message> messages;
    private final HashMap<String,PlayerChat> playerChats;

    /**
     * Creates a Chat System
     */
    public ChatSystem(){
        messages = new ArrayList<>();
        playerChats = new HashMap<>();
    }

    /**
     *
     * @return all the messages ever sent to this game
     */
    public ArrayList<Message> getMessages() {
        return messages;
    }

    /**
     *
     * @param message a new message to add to this chat system. This message will be redirected to the corresponding
     *                PlayerChats. If this message is to be sent to a non-existing player it will be discarded
     */
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

    /**
     *
     * @param player is the name of a new player that has to be added to this chat system
     * @param observer is the Observer of the corresponding PlayerChatView representing his chat
     */
    @Override
    public void addPlayerToChat(String player,Observer observer){
        playerChats.put(player,new PlayerChat(player,observer));
    }

    /**
     *
     * @param player the name of the player whose chat is requested
     * @return the PlayerChatView of the request player
     */
    public PlayerChatView getPlayerChatView(String player){
        if(!playerChats.containsKey(player)) return null;
        return playerChats.get(player).getPlayerChatView();
    }
}
