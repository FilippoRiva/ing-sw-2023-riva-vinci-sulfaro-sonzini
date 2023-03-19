package it.polimi.myshelfie.model.interfaces;

import it.polimi.myshelfie.GameConfig;

import java.util.Observer;

public interface LobbyController {
    void setGameConfig(GameConfig config);
    boolean isPlayerNameValid(String playerName);
    void addPlayer(String playerName);
    void removePlayer(String playerName);
    void addObserver(Observer observer, String playerName);
}
