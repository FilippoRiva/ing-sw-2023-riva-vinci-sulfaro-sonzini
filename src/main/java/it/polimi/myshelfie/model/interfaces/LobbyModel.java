package it.polimi.myshelfie.model.interfaces;

import it.polimi.myshelfie.GameConfig;
import it.polimi.myshelfie.model.modelviews.LobbyModelView;
import it.polimi.myshelfie.network.events.LobbyEvent;
import it.polimi.myshelfie.utils.Observer;
import it.polimi.myshelfie.model.exceptions.InvalidParameterException;
import it.polimi.myshelfie.model.exceptions.LobbyFullException;

public interface LobbyModel {

    /**
     * Initializes game with the GameConfig that have been previously set.
     * If no custom GameConfig is set the default one is used.
     * Notifies every view on the lobby.
     */
    void initialize();

    /**
     * Sets the game config to be used by the game.
     * @param config are the configurations for the game.
     */
    void setGameConfig(GameConfig config) throws InvalidParameterException;
    /**
     * Adds a player to the lobby.
     * @param playerName is the name of the new player that has to be added to the game.
     */
    void addPlayer(String playerName) throws LobbyFullException, InvalidParameterException;

    /**
     * Removes a player form the lobby.
     * @param playerName is the name of the player that has to be removed from the game.
     */
    void removePlayer(String playerName) throws InvalidParameterException;

    void refillLivingRoom();

    /**
     * Return true if the lobby is empty.
     * @return true or false.
     */
    boolean isLobbyEmpty();

    /**
     *
     * @return the chat model.
     */
    ChatModel getChatModel();

    /**
     *
     * @return the game model.
     */
    GameModel getGameModel();

    /**
     *
     * @return the lobby name
     */
    String getLobbyName();

    /**
     * Set the room owner.
     * @param owner is the name of the player who's the owner.
     */
    void setRoomOwner(String owner);

    /**
     * Add a lobby observer.
     * @param obs is the observer to add.
     */
    void addLobbyObserver(Observer<LobbyEvent> obs);

    /**
     *
     * @return the lobby model view.
     */
    LobbyModelView getLobbyModelView();

    /**
     *
     * @return the lobby name.
     */
    String getName();

    boolean isRoomOwner(String username);

    boolean hasStarted();

    boolean isLobbyFull();
}
