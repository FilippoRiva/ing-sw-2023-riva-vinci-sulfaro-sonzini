package it.polimi.myshelfie.network.observers;

import it.polimi.myshelfie.model.interfaces.LobbyModel;
import it.polimi.myshelfie.network.Client;
import it.polimi.myshelfie.network.NetworkMessage;
import it.polimi.myshelfie.network.events.LobbyEvent;
import it.polimi.myshelfie.utils.Observer;

import java.rmi.RemoteException;

public class NetworkLobbyObserver extends NetworkObserver implements Observer<LobbyEvent> {
    private LobbyModel lobby;
    private boolean firstNotify;
    public NetworkLobbyObserver(Client client){
        this.client = client;
        this.firstNotify = false;
    }

    public void setLobby(LobbyModel lobby) {
        this.lobby = lobby;
    }

    @Override
    public void update(LobbyEvent evt) {
        if(evt==null && firstNotify) return;
        new Thread(()->{
            try {
                client.send(new NetworkMessage(evt,lobby.getLobbyModelView()));
                firstNotify = true;
            } catch (RemoteException e) {
                System.err.println("Unable to reach a client");
            }
        }).start();
    }
}
