package it.polimi.myshelfie.network;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote {
    void handshake(ServerConnectionHandler server) throws RemoteException;
    void send(NetworkMessage networkMessage) throws RemoteException;
}
