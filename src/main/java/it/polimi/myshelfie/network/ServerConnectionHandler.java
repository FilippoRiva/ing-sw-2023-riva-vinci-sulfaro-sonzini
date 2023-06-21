package it.polimi.myshelfie.network;

import it.polimi.myshelfie.controller.interfaces.ChatController;
import it.polimi.myshelfie.controller.interfaces.GameController;
import it.polimi.myshelfie.controller.interfaces.LobbyController;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerConnectionHandler extends Remote {
    void send(NetworkMessage networkMessage) throws RemoteException;
    void setGameController(GameController gameController) throws RemoteException;
    void setChatController(ChatController chatController) throws RemoteException;
    void setLobbyController(LobbyController lobbyController) throws RemoteException;
    void lifeline() throws RemoteException;
    void close() throws RemoteException;
    void ping() throws RemoteException;
}
