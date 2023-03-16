package it.polimi.myshelfie;

import java.awt.*;

public class GameConfig {
    private int numPlayers;
    private Dimension bookshelfDimension;
    private int numCommonGoal;
    private int numTilesPerType;

    public GameConfig (int numPlayers){
        this.numPlayers = numPlayers;
        bookshelfDimension = new Dimension(5, 6);
        numCommonGoal = 2;
        numTilesPerType = 22;
    }

    public GameConfig (int numPlayers, Dimension bookshelfDimension, int numCommonGoal, int numTilesPerType){
        this.numPlayers = numPlayers;
        this.bookshelfDimension = bookshelfDimension;
        this.numCommonGoal = numCommonGoal;
        this.numTilesPerType = numTilesPerType;
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
}
