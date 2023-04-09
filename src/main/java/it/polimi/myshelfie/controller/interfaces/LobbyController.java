package it.polimi.myshelfie.controller.interfaces;

import it.polimi.myshelfie.GameConfig;
import it.polimi.myshelfie.network.observers.NetworkLobbyObserver;

public interface LobbyController {

    /**
     *
     * @param playerName is the name of the player that leaves the current game.
     */
    void exitLobby(String playerName);

    /**
     *
     * @param config is the game's settings.
     */
    void setConfig(GameConfig config);

    /**
     *
     * @param playerName is the name of the owner that starts the game.
     */

    GameController startGame(String playerName);

    void addLobbyObserver(NetworkLobbyObserver networkLobbyObserver);
}
