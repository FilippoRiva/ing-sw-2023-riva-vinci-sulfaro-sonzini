package it.polimi.myshelfie.model.interfaces;

import it.polimi.myshelfie.GameConfig;

import java.util.Observer;

public interface LobbyController {
    void setGameConfig(GameConfig config);
    GameConfig getGameConfig();

    /**
     *
     * @param playerName is the name of the player.
     * @return false if the name is not available.
     */
    boolean isPlayerNameValid(String playerName);

    /**
     *
     * @param playerName is the name of the new player that has to be added to the game
     */
    void addPlayer(String playerName);

    /**
     *
     * @param playerName is the name of the player that has to be removed from the game
     */
    void removePlayer(String playerName);
    void addObserver(Observer observer, String playerName);

    ChatController getChatController();

    GameController getGameController();
}
