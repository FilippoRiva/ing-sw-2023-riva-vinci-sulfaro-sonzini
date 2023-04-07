package it.polimi.myshelfie.network;

import it.polimi.myshelfie.model.modelviews.*;
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
        new Thread(()->view.addObserver(new NetworkViewObserver(server,view))).start();
    }

    @Override
    public void send(Object obj, ModelEvent evt) throws RemoteException {
        new Thread(()->view.setModelView((ModelView) obj)).start();
    }

    @Override
    public void send(Object obj, ExceptionEvent evt) throws RemoteException {
        new Thread(()->view.setExceptionView((ClientExceptionsNotifierView)obj)).start();
    }

    @Override
    public void send(Object obj, ChatEvent evt) throws RemoteException {
        new Thread(()->view.setChatView((PlayerChatModelView) obj)).start();
    }

    @Override
    public void send(Object obj, LobbyEvent evt) throws RemoteException {
        new Thread(()->view.setLobbyModelView((LobbyModelView) obj)).start();
    }

    @Override
    public void send(Object obj, GameEvent evt) throws RemoteException {
        new Thread(()->view.setGameModelView((GameModelView) obj)).start();
    }

    @Override
    public void setViewType(ViewType type) throws RemoteException {
        new Thread(()->view.setView(type)).start();
    }
}
