package it.polimi.myshelfie.controller.interfaces;

import it.polimi.myshelfie.GameConfig;

import java.util.List;

public interface LobbyController {
    /**
     *
     * @param playerName is the name of the first player that creates the current game.
     * @param nameRoom is the name of the room.
     */
    void createGame(String playerName, String nameRoom) throws Exception;
    void setGameConfig(String nameRoom, GameConfig configs) throws Exception;
    List<String> askGameList();
    /**
     *
     * @param playerName is the name of the player.
     * @param nameRoom  is the name of the room entered by the player
     */
    void joinGame(String playerName, String nameRoom) throws Exception;
    void exitGame(String playerName, String roomName) throws Exception;
}
