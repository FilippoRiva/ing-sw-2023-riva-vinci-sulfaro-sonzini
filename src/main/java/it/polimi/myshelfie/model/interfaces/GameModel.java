package it.polimi.myshelfie.model.interfaces;

import it.polimi.myshelfie.model.*;
import it.polimi.myshelfie.model.goals.CommonGoal;
import it.polimi.myshelfie.model.goals.PersonalGoal;

import java.util.List;
import java.util.Observer;

public interface GameModel {
    /**
     * @return game's name
     */
    String getName();
    void init();

    void takeTilesFromLivingRoom(List<Position> takenTiles);
    void addTilesInBookshelf(String playerName, List<ItemTile> takenTiles, int column);

    /**
     *
     * @param playerName is the name of the player that has completed a common goal.
     * @param token is the token obtained from the completed common goal.
     */
    void addToken(String playerName, Token token);
    void refillGame() throws Exception;

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
     * @return the numbers of players
     */
    int getNumberOfPlayers();
    GameState getState();
    Player getCurrentPlayer();
    String getLastPlayer();
    void checkCommonGoal(String playerName);
    void refillLivingRoom();
    void addObserver(Observer observer, String playerName);
}
