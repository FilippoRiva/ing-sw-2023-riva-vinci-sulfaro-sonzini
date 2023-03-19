package it.polimi.myshelfie.controller;

import it.polimi.myshelfie.model.Position;

import java.util.ArrayList;

public class DrawMove {
    private final String player;
    private final ArrayList<Position> positions;
    private final int column;

    public DrawMove(String player, ArrayList<Position> positions, int column){
        this.player = player;
        this.positions = positions;
        this.column=column;
    }

    public String getPlayer() {
        return player;
    }

    public ArrayList<Position> getPositions() {
        return positions;
    }

    public int getColumn() {
        return column;
    }
}
