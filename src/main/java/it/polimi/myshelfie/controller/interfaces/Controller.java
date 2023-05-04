package it.polimi.myshelfie.controller.interfaces;


import it.polimi.myshelfie.network.Client;
import it.polimi.myshelfie.network.ServerConnectionHandler;
import it.polimi.myshelfie.network.observers.NetworkExceptionsObserver;
import it.polimi.myshelfie.network.observers.NetworkModelObserver;

public interface Controller {
    /**
     *
     * @param playerName is the name of the first player that creates the current game.
     * @param lobbyName is the name of the lobby.
     */
    LobbyController createLobby(Client client, ServerConnectionHandler connectionHandler, String playerName, String lobbyName);
    /**
     *
     * @param playerName is the name of the player.
     * @param lobbyName  is the name of the lobby entered by the player
     */
    LobbyController joinLobby(Client client, ServerConnectionHandler connectionHandler, String playerName, String lobbyName);

    void addClient(Client client,NetworkModelObserver networkModelObserver, NetworkExceptionsObserver networkExceptionsObserver);

    void quit(Client client);
}
