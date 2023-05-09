package it.polimi.myshelfie.network.observers;

import it.polimi.myshelfie.network.NetworkMessage;
import it.polimi.myshelfie.network.ServerConnectionHandler;
import it.polimi.myshelfie.view.events.ViewEvent;
import it.polimi.myshelfie.utils.Observer;
import it.polimi.myshelfie.view.View;

import java.rmi.RemoteException;

//TODO to remove -> replaced with lambda
public class NetworkViewObserver implements Observer<ViewEvent, NetworkMessage> {
    ServerConnectionHandler server;
    public NetworkViewObserver(ServerConnectionHandler server){
        this.server = server;
    }

    @Override
    public void update(ViewEvent evt, NetworkMessage message) {
        if(evt==null) return;
        try {
            server.send(message);
        } catch (RemoteException ignored) {
        }
    }
}
