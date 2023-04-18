package it.polimi.myshelfie.network;

import it.polimi.myshelfie.controller.GameControllerImpl;
import it.polimi.myshelfie.controller.interfaces.ChatController;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerConnectionHandler extends Remote {
    void send(NetworkMessage networkMessage) throws RemoteException;
    void setGameController(GameControllerImpl gameController) throws RemoteException;
    void setChatController(ChatController chatController) throws RemoteException;
}
