package it.polimi.myshelfie.model.interfaces;

import it.polimi.myshelfie.GameConfig;
import it.polimi.myshelfie.model.events.LobbyEvent;
import it.polimi.myshelfie.model.exceptions.InvalidParameterException;
import it.polimi.myshelfie.model.exceptions.LobbyFullException;
import it.polimi.myshelfie.model.modelviews.LobbyModelView;
import it.polimi.myshelfie.utils.Observer;

/**
 * Represents the lobby's interface.
 */
public interface LobbyModel {

    /**
     * Initializes the game with the GameConfig that have been previously set.
     * If no custom GameConfig is set, the default one is used.
     * Notifies every view on the lobby.
     */
    void initialize() throws LobbyFullException;

    /**
     * Sets the game config to be used by the game.
     *
     * @param config are the configurations for the game.
     */
    void setGameConfig(GameConfig config) throws InvalidParameterException;

    /**
     * Adds a player to the lobby.
     *
     * @param playerName is the name of the new player that has to be added to the game.
     * @throws LobbyFullException        if the lobby is full.
     * @throws InvalidParameterException if the username is not valid.
     */
    void addPlayer(String playerName) throws LobbyFullException, InvalidParameterException;

    /**
     * Removes a player from the lobby.
     *
     * @param playerName is the name of the player that has to be removed from the game.
     */
    void removePlayer(String playerName) throws InvalidParameterException;

    /**
     * Checks whether the lobby is empty.
     *
     * @return true if the lobby is full, false otherwise.
     */
    boolean isLobbyEmpty();

    /**
     * Returns the Chat Model of this lobby.
     *
     * @return the chat model.
     */
    ChatModel getChatModel();

    /**
     * Returns the GameModel of this Lobby
     * @return the game model.
     */
    GameModel getGameModel();

    /**
     * Returns the lobby name
     * @return the lobby name
     */
    String getLobbyName();

    /**
     * Adds a lobby observer.
     *
     * @param obs the observer to add.
     */
    void addLobbyObserver(Observer<LobbyEvent, LobbyModelView> obs);

    /**
     * Removes a lobby observer.
     *
     * @param obs the observer to remove.
     */
    void removeLobbyObserver(Observer<LobbyEvent, LobbyModelView> obs);

    /**
     * Returns the lobby model view.
     * @return the lobby model view.
     */
    LobbyModelView getLobbyModelView();

    /**
     * Returns the lobby name.
     * @return the lobby name.
     */
    String getName();

    /**
     * Checks if the player is the room owner (the one who created it).
     *
     * @param username the name of the player to check.
     * @return true if the player is the room owner, false otherwise.
     */
    boolean isRoomOwner(String username);

    /**
     * Checks whether the game has started.
     *
     * @return true if the game has started, false otherwise.
     */
    boolean hasStarted();

    /**
     * Removes lobbies' observers.
     */
    void removeLobbyObservers();
}
