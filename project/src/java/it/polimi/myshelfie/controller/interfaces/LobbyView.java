package it.polimi.myshelfie.controller.interfaces;

import java.util.List;

public interface LobbyView {
    void createGame(String namePlayer, String nameRoom, int numPlayers);
    List<String> askGameList();
    void joinGame(String namePlayer, String nameRoom);
    void setGameConfig(List<Integer> configs);
    void exitGame();
}
