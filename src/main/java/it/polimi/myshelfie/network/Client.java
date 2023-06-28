package it.polimi.myshelfie.network;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Represents the Client interface.
 */
public interface Client extends Remote {

    /**
     * @return the hostname.
     * @throws RemoteException
     */
    String getHostName() throws RemoteException;

    /**
     * Used to start the connection with the server connection handler.
     *
     * @param server the server connection handler.
     * @throws RemoteException
     */
    void handshake(ServerConnectionHandler server) throws RemoteException;

    /**
     * Used to send a network message.
     *
     * @param networkMessage the message to send.
     * @throws RemoteException
     */
    void send(NetworkMessage networkMessage) throws RemoteException;

    /**
     * It blocks forever the calling thread until the client disconnects.
     *
     * @throws RemoteException
     */
    void lifeline() throws RemoteException;
}
