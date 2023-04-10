package it.polimi.myshelfie.model;

import java.io.Serializable;

public class Position implements Serializable {
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

}
