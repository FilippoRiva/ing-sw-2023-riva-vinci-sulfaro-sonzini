package it.polimi.myshelfie.network;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote {
    void connect(Client client) throws RemoteException;
}
