package it.polimi.myshelfie.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Represents the position of a tile on the board. The centre of the board has coordinates (0,0).
 */
public class Position implements Serializable{
    private final int x;
    private final int y;
    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Constructor.
     * @param p the position.
     *
     */
    public Position(Position p){
        this.x = p.getX();
        this.y = p.getY();
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
        p = (Position) obj;
        return p.getX() == x && p.getY() == y;
    }

    /**
     * Converts a position in a y x matrix position system to an in-the-middle centered matrix position
     * @param livingRoomSize the size of the matrix
     * @return a new Position centered to an in-the-middle centered matrix
     */
    public Position centered(int livingRoomSize){
        int offset = (livingRoomSize-1)/2;
        return new Position(x-offset, offset-y);
    }

    /**
     * Converts a position in an x y matrix position system to an in-the-middle centered matrix position
     * @param livingRoomSize the size of the matrix
     * @return a new Position centered to an in-the-middle centered matrix
     */
    public Position centeredMatrix(int livingRoomSize){
        int offset = (livingRoomSize-1)/2;
        return new Position(y-offset, offset-x);
    }

    /**
     * Checks whether if the positions are not adjacent to each other or not aligned in a column or in a row.
     * Returns true if they are.
     * @param positions the positions to check
     * @return true if the positions are misaligned
     */
    public static boolean arePositionsMisaligned(List<Position> positions){
        if(positions.size()==1) return false;
        int maxIndex = positions.size()-1;

        //Should not affect the initial order for GUI
        List<Position> sortedPositions = new ArrayList<>();
        for(Position p : positions){
            sortedPositions.add(new Position(p));
        }

        sortedPositions.sort(Comparator.comparingInt(Position::getX));
        int maxX = sortedPositions.get(maxIndex).getX();
        int minX = sortedPositions.get(0).getX();

        sortedPositions.sort(Comparator.comparingInt(Position::getY));
        int maxY = sortedPositions.get(maxIndex).getY();
        int minY = sortedPositions.get(0).getY();

        if(maxX != minX && maxY != minY) return true; //not in a line
        if(maxX == minX && maxY-minY+1 != sortedPositions.size()) return true; //vertical line but not consecutive
        return maxY == minY && maxX - minX + 1 != sortedPositions.size(); //horizontal line but not consecutive
    }

    @Override
    public String toString(){
        return "["+this.getX()+","+this.getY()+"]";
    }
}
