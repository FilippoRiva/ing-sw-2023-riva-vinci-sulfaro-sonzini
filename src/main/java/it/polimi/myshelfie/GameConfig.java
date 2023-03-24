package it.polimi.myshelfie;

import java.awt.*;

public class GameConfig {
    private final int numPlayers;
    private final Dimension bookshelfDimension;
    private final int numCommonGoal;
    private final int numTilesPerType;
    private final int livingRoomSize;
    private final int numPersonalGoals;

    public GameConfig (int numPlayers){
        this.numPlayers = numPlayers;
        this.bookshelfDimension = new Dimension(5, 6);
        this.numCommonGoal = 2;
        this.numTilesPerType = 22;
        this.livingRoomSize = 9;
        this.numPersonalGoals = 1;
    }

    public GameConfig (int numPlayers, Dimension bookshelfDimension, int numCommonGoal, int numTilesPerType, int livingRoomSize, int numPersonalGoals) throws Exception{
        this.numPlayers = numPlayers;
        this.bookshelfDimension = bookshelfDimension;
        this.numCommonGoal = numCommonGoal;
        this.numTilesPerType = numTilesPerType;
        this.livingRoomSize = livingRoomSize;
        this.numPersonalGoals = numPersonalGoals;
        if(numPersonalGoals*numPlayers>12) throw new Exception("Invalid number of players or number of personal goals");
        if(bookshelfDimension.width> bookshelfDimension.height) throw new Exception("Invalid bookshelf size");
        if(livingRoomSize%2 == 0 || livingRoomSize < 9) throw new Exception("Invalid livingRoom Size");
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

}
