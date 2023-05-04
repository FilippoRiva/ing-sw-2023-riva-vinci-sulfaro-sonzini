package it.polimi.myshelfie.network.observers;

import it.polimi.myshelfie.model.interfaces.ChatModel;
import it.polimi.myshelfie.network.Client;
import it.polimi.myshelfie.network.NetworkMessage;
import it.polimi.myshelfie.model.events.ChatEvent;
import it.polimi.myshelfie.utils.Observer;

import java.rmi.RemoteException;

public class NetworkChatObserver extends NetworkObserver implements Observer<ChatEvent> {

    String playerName;
    ChatModel chatModel;
    public NetworkChatObserver(Client client, String playerName){
        this.client = client;
        this.playerName = playerName;
    }
    @Override
    public void update(ChatEvent evt) {
        if(evt==null) return;
        new Thread(()->{
            try {
                client.send(new NetworkMessage(evt, chatModel.getChatModelView(playerName)));
            } catch (RemoteException ignored) {
            }
        }).start();
    }

    public void setChatModel(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public String getPlayerName(){
        return playerName;
    }
}
