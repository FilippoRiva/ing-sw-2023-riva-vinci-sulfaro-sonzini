package it.polimi.myshelfie;

import it.polimi.myshelfie.model.exceptions.InvalidParameterException;

import java.awt.*;
import java.io.Serializable;

public class GameConfig implements Serializable {
    private final int numPlayers;
    private final Dimension bookshelfDimension;
    private final int numCommonGoal;
    private final int numTilesPerType;
    private final int livingRoomSize;
    private final int numPersonalGoals;
    private final double personalGoalRatio;

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

    public int getNumPlayers() {
        return numPlayers;
    }
    public Dimension getBookshelfDimension() {
        return bookshelfDimension;
    }
    public int getNumCommonGoal() {
        return numCommonGoal;
    }
    public int getNumPersonalGoals(){ return numPersonalGoals; }
    public int getNumTilesPerType() {
        return numTilesPerType;
    }
    public int getLivingRoomSize() {
        return livingRoomSize;
    }
    public double getPersonalGoalRatio(){
        return personalGoalRatio;
    }

}
