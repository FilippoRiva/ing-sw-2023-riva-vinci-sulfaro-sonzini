package it.polimi.myshelfie.network;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Represents the Client interface.
 */
public interface Client extends Remote {

    /**
     * Returns the hostname.
     * @return the hostname.
     * @throws RemoteException if a communication-related exceptions has occurred.
     */
    String getHostName() throws RemoteException;

    /**
     * Used to start the connection with the server connection handler.
     *
     * @param server the server connection handler.
     * @throws RemoteException if a communication-related exceptions has occurred.
     */
    void handshake(ServerConnectionHandler server) throws RemoteException;

    /**
     * Used to send a network message.
     *
     * @param networkMessage the message to send.
     * @throws RemoteException if a communication-related exceptions has occurred.
     */
    void send(NetworkMessage networkMessage) throws RemoteException;

    /**
     * It blocks forever the rmi server calling thread until the client disconnects throwing a RemoteException to the server.
     *
     * @throws RemoteException if a communication-related exceptions has occurred.
     */
    void lifeline() throws RemoteException;
}
