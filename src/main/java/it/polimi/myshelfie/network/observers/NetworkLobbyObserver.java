package it.polimi.myshelfie.network.observers;

import it.polimi.myshelfie.model.interfaces.LobbyModel;
import it.polimi.myshelfie.network.Client;
import it.polimi.myshelfie.network.events.LobbyEvent;
import it.polimi.myshelfie.utils.Observer;

import java.rmi.RemoteException;

public class NetworkLobbyObserver extends NetworkObserver implements Observer<LobbyEvent> {
    private LobbyModel lobby;
    public NetworkLobbyObserver(Client client){
        this.client = client;
    }

    public void setLobby(LobbyModel lobby) {
        this.lobby = lobby;
    }

    @Override
    public void update(LobbyEvent evt) {
        new Thread(()->{
            try {
                client.send(lobby.getLobbyModelView(),evt);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
