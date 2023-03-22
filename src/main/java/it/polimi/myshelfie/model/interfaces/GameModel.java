package it.polimi.myshelfie.model.interfaces;

import it.polimi.myshelfie.GameConfig;
import it.polimi.myshelfie.model.*;
import it.polimi.myshelfie.model.goals.CommonGoal;

import java.util.List;
import java.util.Observer;

public interface GameModel {
    /**
     * initialize game
     */
    void init();

    /**
     * set the commonGoal of the game
     * @param commonGoals two random commonGoals
     */
    void setCommonGoals(CommonGoal[] commonGoals);

    /**
     * change the state of the game
     * @param state the new state
     */
    void setState(GameState state);

    /**
     * delete the tiles from the livingRoom
     * @param takenTiles list of coordinates where the player taken the tiles
     *
     */
    void deleteTilesFromLivingRoom(List<Position> takenTiles);

    /**
     * insert the tiles in the bookshelf
     * @param playerName player playing
     * @param takenTiles list of tiles taken by the player in order
     * @param column column choose by the player
     *
     */
    void addTilesInBookshelf(String playerName, List<ItemTile> takenTiles, int column);

    /**
     * change the current player playing
     */
    void nextTurn();

    /**
     *
     * @param playerName name of the player that has completed a common goal.
     * @param token is the token obtained from the completed common goal.
     */
    void addToken(String playerName, Token token);

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

    /**
     *
     * @return gameConfig of this game
     */
    GameConfig getGameConfig();

    /**
     * check if the player achieved the commonGoal
     * @param playerName player playing
     */
    void checkCommonGoal(String playerName);

    /**
     * refill the livingRoom
     */
    void refillLivingRoom();
    void addObserver(Observer observer, String playerName);
}
