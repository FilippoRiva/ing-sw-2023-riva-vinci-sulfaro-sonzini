package it.polimi.myshelfie.model.chatting;

import it.polimi.myshelfie.model.interfaces.ChatModel;
import it.polimi.myshelfie.model.modelviews.PlayerChatModelView;
import it.polimi.myshelfie.network.observers.NetworkChatObserver;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatSystem implements ChatModel {

    private final ArrayList<Message> messages;
    private final HashMap<String, PlayerChat> playerChats;

    /**
     * Creates a Chat System.
     */
    public ChatSystem(){
        messages = new ArrayList<>();
        playerChats = new HashMap<>();
    }

    /**
     *
     * @return all the messages ever sent to this game.
     */
    public ArrayList<Message> getMessages() {
        return messages;
    }

    /**
     * Adds a message to the chat system.
     * @param message a new message to add to this chat system. This message will be redirected to the corresponding
     *                PlayerChats. If this message is to be sent to a non-existing player it will be discarded.
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
        //to a specific player
        messages.add(message);
        playerChats.get(message.getReceiver()).addMessage(message);
        playerChats.get(message.getSender()).addMessage(message);
    }

    @Override
    public void addChatObserver(NetworkChatObserver obs) {
        String playerName = obs.getPlayerName();
        PlayerChat pc = playerChats.get(playerName);
        pc.addObserver(obs);
        pc.setChanged();
        pc.notifyObservers();
    }

    @Override
    public synchronized PlayerChatModelView getChatModelView(String playerName) {
        return new PlayerChatModelView(playerChats.get(playerName));
    }

    /**
     * Adds a player to the chat system.
     * @param player is the name of a new player that has to be added to this chat system.
     */
    public void addPlayerToChat(String player){
        playerChats.put(player,new PlayerChat(player));
    }
}
