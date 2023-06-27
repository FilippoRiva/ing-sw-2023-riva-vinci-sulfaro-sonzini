package it.polimi.myshelfie;

import it.polimi.myshelfie.model.exceptions.InvalidParameterException;

import java.awt.*;
import java.io.Serializable;

/**
 * Represents all the general settings of the game.
 */
public class GameConfig implements Serializable {
    private final int numPlayers;
    private final Dimension bookshelfDimension;
    private final int numCommonGoal;
    private final int numTilesPerType;
    private final int livingRoomSize;
    private final int numPersonalGoals;
    private final double personalGoalRatio;

    /**
     * Default constructor.
     * @param numPlayers the number of players in the game.
     * @throws InvalidParameterException if the number of players is not valid (smaller than 2).
     */
    public GameConfig (int numPlayers) throws InvalidParameterException {
        if(numPlayers<2) throw new InvalidParameterException("too few players");
        this.numPlayers = numPlayers;
        this.bookshelfDimension = new Dimension(5, 6);
        this.numCommonGoal = 2;
        this.numTilesPerType = 22;
        this.livingRoomSize = 9;
        this.numPersonalGoals = 1;
        this.personalGoalRatio = 0;
    }

    /**
     * Costume constructor.
     * @param numPlayers the number of players in the game.
     * @param bookshelfDimension the dimension of the bookshelf.
     * @param numCommonGoal the number of common goals per player.
     * @param numTilesPerType the number of tiles per type.
     * @param livingRoomSize the dimension of the living room
     * @param numPersonalGoals the number of personal goals per player.
     * @param personalGoalRatio the filling percentage of the personal goal.
     * @throws InvalidParameterException if the number of players or personal gaols is not valid, or the size of the bookshelf and the living room is not valid.
     */
    public GameConfig (int numPlayers, Dimension bookshelfDimension, int numCommonGoal, int numTilesPerType, int livingRoomSize, int numPersonalGoals, double personalGoalRatio)
            throws InvalidParameterException{
        //exceptions for all attributes
        this.numPlayers = numPlayers;
        this.bookshelfDimension = bookshelfDimension;
        this.numCommonGoal = numCommonGoal;
        this.numTilesPerType = numTilesPerType;
        this.livingRoomSize = livingRoomSize;
        this.numPersonalGoals = numPersonalGoals;
        this.personalGoalRatio = personalGoalRatio;
        if(numPersonalGoals*numPlayers>12) throw new InvalidParameterException("Invalid number of players or number of personal goals");
        if(bookshelfDimension.width> bookshelfDimension.height) throw new InvalidParameterException("Invalid bookshelf size");
        if(livingRoomSize%2 == 0 || livingRoomSize < 9) throw new InvalidParameterException("Invalid livingRoom Size");
    }

    /**
     *
     * @return the number of players in the game.
     */
    public int getNumPlayers() {
        return numPlayers;
    }

    /**
     *
     * @return the bookshelf dimension.
     */
    public Dimension getBookshelfDimension() {
        return bookshelfDimension;
    }

    /**
     *
     * @return the number of common goal.
     */
    public int getNumCommonGoal() {
        return numCommonGoal;
    }

    /**
     *
     * @return the number of personal goal.
     */
    public int getNumPersonalGoals(){ return numPersonalGoals; }

    /**
     *
     * @return the number of tiles per type.
     */
    public int getNumTilesPerType() {
        return numTilesPerType;
    }

    /**
     *
     * @return the living room size.
     */
    public int getLivingRoomSize() {
        return livingRoomSize;
    }

    /**
     *
     * @return the filling percentage of the personal goals.
     */
    public double getPersonalGoalRatio(){
        return personalGoalRatio;
    }

}
