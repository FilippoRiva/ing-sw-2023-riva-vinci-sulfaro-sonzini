package it.polimi.myshelfie.model.interfaces;

import it.polimi.myshelfie.controller.DrawMove;
import it.polimi.myshelfie.model.*;
import it.polimi.myshelfie.model.goals.PersonalGoal;

import java.util.ArrayList;
import java.util.Observer;

public interface GameController {
    /**
     * @return game's name
     */
    String getName();
    boolean init();

    /**
     * @return the bag which is a list of tiles
     */
    ArrayList<ItemTile> getBag();

    /**
     * @return the living room which has the bag and the board
     */
    LivingRoom getLivingRoom();
    void setLivingRoom(LivingRoom livingRoom);

    /**
     *
     * @param playerName is the name of the player that has completed a common goal.
     * @param token is the token obtained from the completed common goal.
     */
    void addToken(String playerName, Token token);
    void refillGame();
    Bookshelf getBookshelf(String playerName);

    /**
     *
     */
    void nextTurn();
    void setState(GameState state);

    /**
     *
     * @param playerName is the name of the player playing.
     * @param points ae the points the player obtained after his turn.
     */
    void addPoints(String playerName, int points);

    /**
     *
     * @param playerName is the name of the player.
     * @return his personal goal
     */
    PersonalGoal getPersonalGoal(String playerName);

    /**
     *
     * @return the numbers of players
     */
    int getNumberOfPlayers();
    GameState getState();
    String getCurrentPlayer();
    String getLastPlayer();
    void addObserver(Observer observer, String playerName);
}
