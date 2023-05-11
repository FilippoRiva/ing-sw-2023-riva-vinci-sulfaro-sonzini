package it.polimi.myshelfie.controller.interfaces;

import it.polimi.myshelfie.GameConfig;
import it.polimi.myshelfie.model.events.LobbyEvent;
import it.polimi.myshelfie.model.modelviews.LobbyModelView;
import it.polimi.myshelfie.utils.Observer;

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
    void setConfig(String playerName,GameConfig config);

    /**
     *
     * @param playerName is the name of the owner that starts the game.
     */

    void startGame(String playerName);

    void addLobbyObserver(String playerName, Observer<LobbyEvent, LobbyModelView> networkLobbyObserver);
}
