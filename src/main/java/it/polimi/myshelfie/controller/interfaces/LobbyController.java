package it.polimi.myshelfie.controller.interfaces;

import it.polimi.myshelfie.GameConfig;

public interface LobbyController {

    /**
     *
     * @param playerName is the name of the player that leaves the current game.
     */
    void exitLobby(String playerName) throws Exception;

    /**
     *
     * @param config has game settings.
     */
    void setConfig(GameConfig config) throws Exception;

    /**
     *
     * @param playerName is the name of the owner that starts the game.
     */

    GameController startGame(String playerName);

    //TODO methods to manage Observer/Listener between model and view
}
