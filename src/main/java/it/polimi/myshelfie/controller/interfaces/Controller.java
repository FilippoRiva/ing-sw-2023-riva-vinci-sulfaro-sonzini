package it.polimi.myshelfie.controller.interfaces;


import it.polimi.myshelfie.model.events.ExceptionEvent;
import it.polimi.myshelfie.model.events.ModelEvent;
import it.polimi.myshelfie.model.exceptions.InvalidParameterException;
import it.polimi.myshelfie.model.modelviews.ClientExceptionsNotifierView;
import it.polimi.myshelfie.model.modelviews.ModelView;
import it.polimi.myshelfie.network.Client;
import it.polimi.myshelfie.network.ServerConnectionHandler;
import it.polimi.myshelfie.utils.Observer;

/**
 * Represents a general controller that create a lobby and allows to join it, and add or disconnect a client.
 */
public interface Controller {

    /**
     * Creates a Lobby where the players can enter to play the game.
     * @param client the client representing the player who's connecting.
     * @param connectionHandler the ServerConnectionHandler used for the player's connection.
     * @param playerName the name of this first player which is now the room owner and created this lobby.
     * @param lobbyName the name of the created lobby.
     * @return the LobbyController associated to that specific lobby.
     */
    LobbyController createLobby(Client client, ServerConnectionHandler connectionHandler, String playerName, String lobbyName);

    /**
     * Allows a player, that is not the first one to enter the game, to join an existing lobby.
     * @param client the client representing the player who's connecting.
     * @param connectionHandler the ServerConnectionHandler used for the player's connection.
     * @param playerName the name of the player that wants to join this lobby.
     * @param lobbyName the name of the joined lobby.
     * @return the LobbyController associated to that specific lobby.
     */
    LobbyController joinLobby(Client client, ServerConnectionHandler connectionHandler, String playerName, String lobbyName);

    /**
     * Adds a Client to the game.
     * @param client the client representing the player who's connecting.
     * @param networkModelObserver the network model observer.
     * @param networkExceptionsObserver thw network exceptions observer.
     */

    void addClient(Client client,Observer<ModelEvent, ModelView> networkModelObserver, Observer<ExceptionEvent, ClientExceptionsNotifierView> networkExceptionsObserver) throws InvalidParameterException;

    /**
     * Removes a client from the game.
     * @param client the client quitting.
     */
    void quit(Client client);
}
