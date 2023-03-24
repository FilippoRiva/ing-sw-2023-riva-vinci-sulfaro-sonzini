package it.polimi.myshelfie;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class GameConfigTest {
    @Test
    public void configTest(){
        int numPlayers = 4;
        Dimension dimension = new Dimension(10,10);
        int numCommonGoal = 2;
        int numTilesPerType = 22;
        int livingRoomSize = 9;
        int numPersonalGoals = 1;
        GameConfig gc = null;
        try {
            gc = new GameConfig(numPlayers,dimension,numCommonGoal,numTilesPerType,livingRoomSize, numPersonalGoals);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        assertEquals(numPlayers,gc.getNumPlayers());
        assertEquals(dimension,gc.getBookshelfDimension());
        assertEquals(numCommonGoal,gc.getNumCommonGoal());
        assertEquals(numTilesPerType,gc.getNumTilesPerType());
        assertEquals(livingRoomSize,gc.getLivingRoomSize());
    }
}