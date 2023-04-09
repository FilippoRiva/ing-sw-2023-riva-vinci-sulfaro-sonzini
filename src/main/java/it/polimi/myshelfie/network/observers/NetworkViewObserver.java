package it.polimi.myshelfie.network.observers;

import it.polimi.myshelfie.network.ServerConnectionHandler;
import it.polimi.myshelfie.network.events.ViewEvent;
import it.polimi.myshelfie.utils.Observer;
import it.polimi.myshelfie.view.ViewBuffer;

import java.rmi.RemoteException;

public class NetworkViewObserver implements Observer<ViewEvent> {
    ServerConnectionHandler server;
    ViewBuffer viewBuffer;
    public NetworkViewObserver(ServerConnectionHandler server, ViewBuffer viewBuffer){
        this.server = server;
        this.viewBuffer= viewBuffer;
    }

    @Override
    public void update(ViewEvent evt) {
        new Thread(()->{
            try {
                server.send(viewBuffer.getUpdateParameters(),evt);
            } catch (RemoteException ignored) {
            }
        }).start();
    }
}
