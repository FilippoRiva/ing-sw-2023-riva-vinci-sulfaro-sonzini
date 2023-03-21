package it.polimi.myshelfie.controller.interfaces;

import it.polimi.myshelfie.GameConfig;

import java.util.List;

public interface LobbyView {
    /**
     *
     * @param namePlayer is the name of the first player that creates the current game.
     * @param nameRoom is the name of the room.
     */
    void createGame(String namePlayer, String nameRoom) throws Exception;
    List<String> askGameList();
    /**
     *
     * @param namePlayer is the name of the player.
     * @param nameRoom  is the name of the room entered by the player
     */
    void joinGame(String namePlayer, String nameRoom) throws Exception;
    void setGameConfig(String nameRoom, GameConfig configs) throws Exception;
    void exitGame(String namePlayer, String roomName) throws Exception;
}
