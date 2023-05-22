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

    @Override
    public int hashCode() {
        return (x*100)+y;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==null || obj.getClass()!=getClass()) return false;
        Position p;
        try {
            p = (Position) obj;
        }catch (Exception e){
            return false;
        }
        return p.getX() == x && p.getY() == y;
    }

    public Position centered(int livingRoomSize){
        int offset = (livingRoomSize-1)/2;
        return new Position(x-offset, offset-y);
    }
}
