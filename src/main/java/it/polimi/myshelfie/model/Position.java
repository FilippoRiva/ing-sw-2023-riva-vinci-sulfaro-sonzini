package it.polimi.myshelfie.model;

import java.io.Serializable;
import java.util.Comparator;

/**
 * It represents the position of a tile on the board. The centre of the board has coordinates (0,0).
 */
public class Position implements Serializable{
    private final int x;
    private final int y;
    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x coordinate of the tile's position.
     * @return the x coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y coordinate of the tile's position.
     * @return the y coordinate.
     */
    public int getY() {
        return y;
    }

}
