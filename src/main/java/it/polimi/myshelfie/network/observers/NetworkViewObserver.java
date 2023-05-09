package it.polimi.myshelfie.network.observers;

import it.polimi.myshelfie.network.NetworkMessage;
import it.polimi.myshelfie.network.ServerConnectionHandler;
import it.polimi.myshelfie.view.events.ViewEvent;
import it.polimi.myshelfie.utils.Observer;
import it.polimi.myshelfie.view.View;

import java.rmi.RemoteException;

public class NetworkViewObserver implements Observer<ViewEvent, NetworkMessage> {
    ServerConnectionHandler server;
    View view;
    public NetworkViewObserver(ServerConnectionHandler server, View view){
        this.server = server;
        this.view= view;
    }

    @Override
    public void update(ViewEvent evt, NetworkMessage unused) {
        if(evt==null) return;
        try {
            server.send(view.getMessage());
        } catch (RemoteException ignored) {
        }
    }
}
