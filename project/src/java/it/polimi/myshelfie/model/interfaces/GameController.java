package it.polimi.myshelfie.model.interfaces;

import it.polimi.myshelfie.controller.DrawMove;
import it.polimi.myshelfie.model.*;
import it.polimi.myshelfie.model.goals.PersonalGoal;

import java.util.ArrayList;
import java.util.Observer;

public interface GameController {
    String getName();
    boolean init();
    ArrayList<ItemTile> getBag();
    LivingRoom getLivingRoom();
    void setLivingRoom(LivingRoom livingRoom);
    void addToken(String playerName, Token token);
    void refillGame();
    Bookshelf getBookshelf(String playerName);
    void nextTurn();
    void setState(GameState state);
    void addPoints(String playerName, int points);
    PersonalGoal getPersonalGoal(String playerName);
    int getNumberOfPlayers();
    GameState getState();
    String getCurrentPlayer();
    String getLastPlayer();
    void addObserver(Observer observer, String playerName);
}
