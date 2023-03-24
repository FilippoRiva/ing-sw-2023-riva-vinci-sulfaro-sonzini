package it.polimi.myshelfie.controller.interfaces;

import it.polimi.myshelfie.GameConfig;

public interface Controller {

    void askGameList();

    /**
     *
     * @param playerName is the name of the first player that creates the current game.
     * @param lobbyName is the name of the lobby.
     */
    void createGame(String playerName, String lobbyName) throws Exception;
    /**
     *
     * @param playerName is the name of the player.
     * @param lobbyName  is the name of the lobby entered by the player
     */
    void joinGame(String playerName, String lobbyName) throws Exception;

    void setGameConfig(String lobbyName, GameConfig configs) throws Exception;

    void exitGame(String playerName, String lobbyName) throws Exception;

    //TODO methods to manage Observer/Listener between model and view

}
