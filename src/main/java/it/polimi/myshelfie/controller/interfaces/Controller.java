package it.polimi.myshelfie.controller.interfaces;

import it.polimi.myshelfie.GameConfig;

public interface Controller {
    /**
     *
     * @param playerName is the name of the first player that creates the current game.
     * @param lobbyName is the name of the lobby.
     */
    void createLobby(String playerName, String lobbyName);
    /**
     *
     * @param playerName is the name of the player.
     * @param lobbyName  is the name of the lobby entered by the player
     */
    void joinLobby(String playerName, String lobbyName);

    void setGameConfig(String playerName, String lobbyName, GameConfig configs);

    void exitLobby(String playerName, String lobbyName);

    //TODO methods to manage Observer/Listener between model and view

}
