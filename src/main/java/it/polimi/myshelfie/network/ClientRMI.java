package it.polimi.myshelfie.network;

import it.polimi.myshelfie.model.modelviews.*;
import it.polimi.myshelfie.network.events.*;
import it.polimi.myshelfie.network.observers.NetworkViewObserver;
import it.polimi.myshelfie.view.ViewBuffer;
import it.polimi.myshelfie.view.ViewType;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientRMI extends UnicastRemoteObject implements Client {
    ServerConnectionHandler server;
    ViewBuffer viewBuffer;
    public ClientRMI(ViewBuffer viewBuffer) throws RemoteException {
        this.viewBuffer = viewBuffer;
    }
    public void handshake(ServerConnectionHandler server) throws RemoteException {
        this.server = server;
        new Thread(()-> viewBuffer.addObserver(new NetworkViewObserver(server, viewBuffer))).start();
    }

    @Override
    public void send(Object obj, ModelEvent evt) throws RemoteException {
        new Thread(()-> viewBuffer.setModelView((ModelView) obj)).start();
    }

    @Override
    public void send(Object obj, ExceptionEvent evt) throws RemoteException {
        new Thread(()-> viewBuffer.setExceptionView((ClientExceptionsNotifierView)obj)).start();
    }

    @Override
    public void send(Object obj, ChatEvent evt) throws RemoteException {
        new Thread(()-> viewBuffer.setChatView((PlayerChatModelView) obj)).start();
    }

    @Override
    public void send(Object obj, LobbyEvent evt) throws RemoteException {
        new Thread(()-> viewBuffer.setLobbyModelView((LobbyModelView) obj)).start();
    }

    @Override
    public void send(Object obj, GameEvent evt) throws RemoteException {
        new Thread(()-> viewBuffer.setGameModelView((GameModelView) obj)).start();
    }

    @Override
    public void setViewType(ViewType type) throws RemoteException {
        new Thread(()-> viewBuffer.setView(type)).start();
    }
}
