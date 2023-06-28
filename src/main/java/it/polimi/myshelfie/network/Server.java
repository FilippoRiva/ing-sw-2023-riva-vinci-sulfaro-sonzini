package it.polimi.myshelfie.network;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Represents the server interface.
 */
public interface Server extends Remote {

    /**
     * Used to connect a client.
     *
     * @param client the client to connect.
     * @throws RemoteException
     */
    void connect(Client client) throws RemoteException;
}
