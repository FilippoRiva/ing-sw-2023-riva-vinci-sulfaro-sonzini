package it.polimi.myshelfie.network;

import it.polimi.myshelfie.network.events.*;
import it.polimi.myshelfie.view.ViewType;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote {
    void handshake(ServerConnectionHandler server) throws RemoteException;
    void send(Object obj, ChatEvent evt) throws RemoteException;
    void send(Object obj, ExceptionEvent evt) throws RemoteException;
    void send(Object obj, GameEvent evt) throws RemoteException;
    void send(Object obj, LobbyEvent evt) throws RemoteException;
    void send(Object obj, ModelEvent evt) throws RemoteException;
    void setViewType(ViewType type) throws RemoteException;
}
