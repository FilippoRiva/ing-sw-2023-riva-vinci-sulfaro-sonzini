package it.polimi.myshelfie.model.interfaces;

import it.polimi.myshelfie.GameConfig;

public interface LobbyModel {
    /**
     * sets the game config to be used by the game
     * @param config
     */
    void setGameConfig(GameConfig config);
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
    void addPlayer(String playerName) ;

    /**
     *
     * @param playerName is the name of the player that has to be removed from the game
     */
    void removePlayer(String playerName);

    boolean isLobbyFull();

    ChatModel getChatModel();

    GameModel getGameModel();

    //TODO methods to manage Observer/Listener

}
