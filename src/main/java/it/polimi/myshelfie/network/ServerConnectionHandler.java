package it.polimi.myshelfie.network;

import it.polimi.myshelfie.network.events.*;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerConnectionHandler extends Remote {
    void send(Object[] args, ViewEvent evt) throws RemoteException;
}
