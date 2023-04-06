package it.polimi.myshelfie.network.observers;

import it.polimi.myshelfie.network.ServerConnectionHandler;
import it.polimi.myshelfie.network.events.ViewEvent;
import it.polimi.myshelfie.utils.Observer;
import it.polimi.myshelfie.view.View;

import java.rmi.RemoteException;

public class NetworkViewObserver implements Observer<ViewEvent> {
    ServerConnectionHandler server;
    View view;
    public NetworkViewObserver(ServerConnectionHandler server, View view){
        this.server = server;
        this.view = view;
    }

    /**
     * used to dynamically change view type (textual or graphical).
     * @param view the new view to be used
     */
    public void setView(View view){
        this.view = view;
    }

    @Override
    public void update(ViewEvent evt) {
        try {
            server.send(view.getUpdateParameters(),evt);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
