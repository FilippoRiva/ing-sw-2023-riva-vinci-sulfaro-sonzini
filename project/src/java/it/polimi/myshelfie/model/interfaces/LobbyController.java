package it.polimi.myshelfie.model.interfaces;

import it.polimi.myshelfie.GameConfig;

import java.util.Observer;

public interface LobbyController {
    void setGameConfig(GameConfig config);
    boolean isPlayerNameValid(String name);
    void addPlayer(String name);
    void removePlayer(String name);
    void addObserver(Observer observer, String player);
}
