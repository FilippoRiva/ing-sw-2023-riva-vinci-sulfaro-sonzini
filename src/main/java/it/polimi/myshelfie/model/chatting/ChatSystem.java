package it.polimi.myshelfie.model.chatting;

import it.polimi.myshelfie.model.events.ChatEvent;
import it.polimi.myshelfie.model.interfaces.ChatModel;
import it.polimi.myshelfie.model.modelviews.PlayerChatModelView;
import it.polimi.myshelfie.utils.Observer;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatSystem implements ChatModel {

    private final ArrayList<Message> messages;
    private final HashMap<String, PlayerChat> playerChats;

    public HashMap<String, PlayerChat> getPlayerChats() {
        return playerChats;
    }

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
    public synchronized void addMessage(Message message) {
        //ignoring empty messages or messages without sender
        if(message.getSender().equals(Messages.fromNobody) || message.getMessage().equals(Messages.emptyMessage)) return;
        //to everyone
        if(message.getReceiver().equals(Messages.toEveryOne)){
            messages.add(message);
            for(PlayerChat pc : playerChats.values()) pc.addMessage(message);
            return;
        }
        //not a player
        if(!playerChats.containsKey(message.getSender())) return;
        if(!playerChats.containsKey(message.getReceiver())) return;
        //ignoring messages to yourself
        if(message.getSender().equals(message.getReceiver())) return;
        //to a specific player
        messages.add(message);
        playerChats.get(message.getReceiver()).addMessage(message);
        playerChats.get(message.getSender()).addMessage(message);
    }

    @Override
    public synchronized void addChatObserver(String playerName, Observer<ChatEvent, PlayerChatModelView> obs) {
        PlayerChat pc = playerChats.get(playerName);
        if(pc==null) return;
        pc.addObserver(obs);
        pc.setChanged();
        pc.notifyObservers(new PlayerChatModelView(pc));
    }

    @Override
    public synchronized void removeChatObserver(String playerName, Observer<ChatEvent, PlayerChatModelView> obs) {
        PlayerChat pc = playerChats.get(playerName);
        if(pc == null) return;
        pc.deleteObserver(obs);
        playerChats.remove(pc);
    }

    @Override
    public synchronized void removeChatObservers(){
        for(PlayerChat pc : playerChats.values()){
            pc.deleteObservers();
        }
    }

    /**
     * Adds a player to the chat system.
     * @param player is the name of a new player that has to be added to this chat system.
     */
    public void addPlayerToChat(String player){
        playerChats.put(player,new PlayerChat(player));
    }
}
