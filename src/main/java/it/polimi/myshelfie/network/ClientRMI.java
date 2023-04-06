package it.polimi.myshelfie.network;

import it.polimi.myshelfie.model.modelviews.ClientExceptionsNotifierView;
import it.polimi.myshelfie.model.modelviews.LobbyModelView;
import it.polimi.myshelfie.model.modelviews.ModelView;
import it.polimi.myshelfie.network.events.*;
import it.polimi.myshelfie.network.observers.NetworkViewObserver;
import it.polimi.myshelfie.view.View;
import it.polimi.myshelfie.view.ViewType;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientRMI extends UnicastRemoteObject implements Client {
    ServerConnectionHandler server;
    View view;
    public ClientRMI(View view) throws RemoteException {
        this.view = view;
    }
    public void handshake(ServerConnectionHandler server) throws RemoteException {
        this.server = server;
        view.addObserver(new NetworkViewObserver(server,view));
    }

    @Override
    public void send(Object obj, ChatEvent evt) throws RemoteException {
        if(evt==ChatEvent.NEW_MESSAGE){
            System.out.println((String) obj);
        }
    }

    @Override
    public void send(Object obj, ExceptionEvent evt) throws RemoteException {
        System.err.println("E : "+ ((ClientExceptionsNotifierView)obj).getLastException().getMessage());
    }

    @Override
    public void send(Object obj, GameEvent evt) throws RemoteException {

    }

    @Override
    public void send(Object obj, LobbyEvent evt) throws RemoteException {
        view.setLobbyModelView((LobbyModelView) obj);
    }

    @Override
    public void send(Object obj, ModelEvent evt) throws RemoteException {
        view.setModelView((ModelView) obj);
    }

    @Override
    public void setViewType(ViewType type) throws RemoteException {
        view.setView(type);
    }
}
