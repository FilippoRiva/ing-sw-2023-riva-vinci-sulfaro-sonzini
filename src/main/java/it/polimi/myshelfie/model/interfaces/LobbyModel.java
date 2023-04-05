package it.polimi.myshelfie.model.interfaces;

import it.polimi.myshelfie.GameConfig;
import it.polimi.myshelfie.model.modelviews.LobbyModelView;
import it.polimi.myshelfie.network.LobbyEvent;
import it.polimi.myshelfie.utils.Observable;
import it.polimi.myshelfie.utils.Observer;
import it.polimi.myshelfie.model.exceptions.InvalidParameterException;
import it.polimi.myshelfie.model.exceptions.LobbyFullException;

public interface LobbyModel {

    /**
     * Initialize game with the GameConfig that have been previously set.
     * If no custom GameConfig is set the default one is used.
     * Notifies every view on the lobby.
     */
    void initialize();

    /**
     * sets the game config to be used by the game
     * @param config configurations for the game
     */
    void setGameConfig(GameConfig config) throws InvalidParameterException;
    /**
     *
     * @param playerName is the name of the new player that has to be added to the game
     */
    void addPlayer(String playerName) throws LobbyFullException, InvalidParameterException;

    /**
     *
     * @param playerName is the name of the player that has to be removed from the game
     */
    void removePlayer(String playerName) throws InvalidParameterException;

    void refillLivingRoom();

    boolean isLobbyEmpty();

    ChatModel getChatModel();

    GameModel getGameModel();

    String getLobbyName();

    void setRoomOwner(String owner);
    void addLobbyObserver(Observer<? extends Observable<?>, LobbyEvent> obs);
    LobbyModelView getLobbyModelView();
    String getName();

}
