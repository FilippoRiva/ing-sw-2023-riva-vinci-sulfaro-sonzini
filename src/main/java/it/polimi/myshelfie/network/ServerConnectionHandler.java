package it.polimi.myshelfie.network;

import it.polimi.myshelfie.controller.interfaces.ChatController;
import it.polimi.myshelfie.controller.interfaces.GameController;
import it.polimi.myshelfie.controller.interfaces.LobbyController;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * It's the interface used by a client to communicate with the server.
 */
public interface ServerConnectionHandler extends Remote {

    /**
     * Used to send a network message.
     *
     * @param networkMessage the message to send.
     * @throws RemoteException if a communication-related exceptions has occurred.
     */
    void send(NetworkMessage networkMessage) throws RemoteException;

    /**
     * Associates the game controller.
     *
     * @param gameController the controller to set.
     * @throws RemoteException if a communication-related exceptions has occurred.
     */
    void setGameController(GameController gameController) throws RemoteException;

    /**
     * Associates the chat controller.
     *
     * @param chatController the controller to set.
     * @throws RemoteException if a communication-related exceptions has occurred.
     */
    void setChatController(ChatController chatController) throws RemoteException;

    /**
     * Associates the lobby controller.
     *
     * @param lobbyController the controller to set.
     * @throws RemoteException if a communication-related exceptions has occurred.
     */
    void setLobbyController(LobbyController lobbyController) throws RemoteException;

    /**
     * It blocks forever the calling thread until the client disconnects.
     *
     * @throws RemoteException if a communication-related exceptions has occurred.
     */
    void lifeline() throws RemoteException;

    /**
     * Closes the connection.
     *
     * @throws RemoteException if a communication-related exceptions has occurred.
     */
    void close() throws RemoteException;

    /**
     * Resets the ping's timer.
     *
     * @throws RemoteException if a communication-related exceptions has occurred.
     */
    void ping() throws RemoteException;
    GameController getGameController() throws RemoteException;
}
