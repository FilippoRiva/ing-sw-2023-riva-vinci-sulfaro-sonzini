package it.polimi.myshelfie.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class PositionTest {

    @Test
    public void positionsTest() {
        int x = 1;
        int y = 2;
        Position pos = new Position(x, y);
        assertEquals(x, pos.getX());
        assertEquals(y, pos.getY());
        assertEquals(pos, pos);
        assertNotEquals(pos, null);
        assertNotEquals(pos, 3);
        assertNotEquals(pos, new Position(1, 1));
        assertEquals(pos, new Position(1, 2));
        assertEquals(pos.hashCode(), 102);
        Position centered = pos.centered(9);
        assertEquals(centered, new Position(-3, 2));
    }
}