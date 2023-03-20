package it.polimi.myshelfie.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    @Test
    public void positionsTest(){
        int x = 1;
        int y = 2;
        Position pos = new Position(x,y);
        assertEquals(x,pos.getX());
        assertEquals(y,pos.getY());
    }

}