package it.polimi.myshelfie.controller.interfaces;

import it.polimi.myshelfie.GameConfig;

import java.util.List;

public interface LobbyView {
    /**
     *
     * @param namePlayer is the name of the first player that creates the current game.
     * @param nameRoom is the name of the room.
     * @param numPlayers is the numbers of players that play the game.
     */
    void createGame(String namePlayer, String nameRoom, int numPlayers);
    List<String> askGameList();

    /**
     *
     * @param namePlayer is the name of the player.
     * @param nameRoom  is the name of the room entered by the player
     */
    void joinGame(String namePlayer, String nameRoom);
    void setGameConfig(GameConfig configs);
    void exitGame();
}
