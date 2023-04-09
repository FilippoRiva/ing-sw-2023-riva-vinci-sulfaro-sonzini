package it.polimi.myshelfie.network.observers;

import it.polimi.myshelfie.model.interfaces.ChatModel;
import it.polimi.myshelfie.network.Client;
import it.polimi.myshelfie.network.events.ChatEvent;
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
        new Thread(()->{
            try {
                client.send(chatModel.getChatModelView(playerName),evt);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
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
