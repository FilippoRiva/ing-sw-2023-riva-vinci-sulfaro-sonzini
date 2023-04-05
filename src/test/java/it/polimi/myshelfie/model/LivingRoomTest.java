package it.polimi.myshelfie.model;

import it.polimi.myshelfie.GameConfig;
import it.polimi.myshelfie.model.exceptions.InvalidParameterException;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class LivingRoomTest {

    @Test
    void livingRoomCheck() throws InvalidParameterException {
        LivingRoom livingRoom;
        for(int players = 2; players<11; players++){
            GameConfig gc = new GameConfig(players);
            livingRoom = new LivingRoom(gc.getNumTilesPerType(),gc.getLivingRoomSize(),gc.getNumPlayers());
            assertFalse(livingRoom.isRefillable());
            livingRoom.refill();
            ItemTile[][] table = livingRoom.getTable();
            livingRoom.refill();
            assertTrue(tableEqual(table, livingRoom.getTable()));
        }
        for(int size = 11 ;size<=51;size+=2){
            GameConfig gc = new GameConfig(4,new Dimension(5,6),2,22*size/9,size, 1,0);
            livingRoom = new LivingRoom(gc.getNumTilesPerType(),gc.getLivingRoomSize(),gc.getNumPlayers());
            assertFalse(livingRoom.isRefillable());
            livingRoom.refill();
            ItemTile[][] table = livingRoom.getTable();
            livingRoom.refill();
            assertTrue(tableEqual(table, livingRoom.getTable()));
        }

        try{
            new LivingRoom(22,2,5);
            fail();
        }catch (InvalidParameterException ignored){
        }

        livingRoom = new LivingRoom(22,9,4);
        livingRoom.refill();
        assertNotEquals(ItemTileType.EMPTY, livingRoom.removeTile(0,0).getType());
        assertNotEquals(ItemTileType.EMPTY, livingRoom.removeTile(4,0).getType());
        assertNotEquals(ItemTileType.EMPTY, livingRoom.removeTile(-4,0).getType());
        assertNotEquals(ItemTileType.EMPTY, livingRoom.removeTile(0,4).getType());
        assertNotEquals(ItemTileType.EMPTY, livingRoom.removeTile(0,-4).getType());
    }

    @Test
    public void isValidPositionTest() throws InvalidParameterException {
        GameConfig gc = new GameConfig(4);
        LivingRoom lr = new LivingRoom(gc.getNumTilesPerType(),gc.getLivingRoomSize(),gc.getNumPlayers());
        assertTrue(lr.isValid(new Position(0,0)));
        assertFalse(lr.isValid(new Position(gc.getLivingRoomSize(),gc.getLivingRoomSize())));
        assertFalse(lr.isValid(new Position(0,gc.getLivingRoomSize())));
    }

    private boolean tableEqual(ItemTile[][] table, ItemTile[][] table1) {
        if(table.length!=table1.length) return false;
        int size = table.length;
        for(int x = 0; x<size; x++){
            for(int y = 0; y<size; y++){
                if(table[x][y]==null && table1[x][y]!=null) return false;
                if(table[x][y]!= null && table[x][y].getType()!=table1[x][y].getType()) return false;
            }
        }
        return true;
    }
}