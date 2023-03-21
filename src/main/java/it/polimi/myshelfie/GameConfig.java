package it.polimi.myshelfie;

import java.awt.*;

public class GameConfig {
    private final int numPlayers;
    private final Dimension bookshelfDimension;
    private final int numCommonGoal;
    private final int numTilesPerType;
    private final int livingRoomSize;

    public GameConfig (int numPlayers){
        this.numPlayers = numPlayers;
        this.bookshelfDimension = new Dimension(5, 6);
        this.numCommonGoal = 2;
        this.numTilesPerType = 22;
        this.livingRoomSize = 9;
    }

    public GameConfig (int numPlayers, Dimension bookshelfDimension, int numCommonGoal, int numTilesPerType, int livingRoomSize){
        this.numPlayers = numPlayers;
        this.bookshelfDimension = bookshelfDimension;
        this.numCommonGoal = numCommonGoal;
        this.numTilesPerType = numTilesPerType;
        this.livingRoomSize = livingRoomSize;
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


    public int getNumTilesPerType() {
        return numTilesPerType;
    }
    public int getLivingRoomSize() {
        return livingRoomSize;
    }

}
