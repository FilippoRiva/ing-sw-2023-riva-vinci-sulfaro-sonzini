package it.polimi.myshelfie.model;

public class Position {
    private final int x;
    private final int y;
    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public boolean isValid(int livingRoomDimension) {
        //TODO is outside the no-go cells
        if (x < -livingRoomDimension / 2 || x > livingRoomDimension / 2)
            return false;
        if (y < -livingRoomDimension / 2 || y > livingRoomDimension / 2)
            return false;

        return true;
    }
}
