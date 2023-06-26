package it.polimi.myshelfie.controller.interfaces;

import it.polimi.myshelfie.GameConfig;
import it.polimi.myshelfie.model.events.LobbyEvent;
import it.polimi.myshelfie.model.modelviews.LobbyModelView;
import it.polimi.myshelfie.utils.Observer;

/**
 * Represents a controller for the lobby.
 */
public interface LobbyController {

    /**
     * Allows a player to exit the lobby he joined.
     * @param playerName the name of the player that leaves the lobby.
     */
    void exitLobby(String playerName);

    /**
     * Allows the room owner to set the game settings.
     * @param config the game's settings.
     */
    void setConfig(String playerName,GameConfig config);

    /**
     * Allows the room owner to start the game when ready.
     * @param playerName the name of the player that can start the game.
     */
    void startGame(String playerName);

    /**
     * Adds a lobby observer.
     * @param playerName the name of the player.
     * @param networkLobbyObserver the observer to add.
     */
    void addLobbyObserver(String playerName, Observer<LobbyEvent, LobbyModelView> networkLobbyObserver);
}
