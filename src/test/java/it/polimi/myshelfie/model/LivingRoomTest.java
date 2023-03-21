package it.polimi.myshelfie.model;

import it.polimi.myshelfie.GameConfig;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class LivingRoomTest {

    private LivingRoom livingRoom;
    @Test
    void defaultLivingRoomCheck() throws Exception {
        for(int players = 2;players<11;players++){
            GameConfig gc = new GameConfig(players);
            livingRoom = new LivingRoom(gc.getNumTilesPerType(),gc.getLivingRoomSize(),gc.getNumPlayers());
            assertTrue(livingRoom.isRefillable());
            livingRoom.refill();
            try{
                livingRoom.refill();
                fail();
            }catch (Exception e) {
            }
        }
        for(int size = 11 ;size<=51;size+=2){
            GameConfig gc = new GameConfig(4,new Dimension(5,6),2,22*size/9,size);
            livingRoom = new LivingRoom(gc.getNumTilesPerType(),gc.getLivingRoomSize(),gc.getNumPlayers());
            assertTrue(livingRoom.isRefillable());
            livingRoom.refill();
            try{
                livingRoom.refill();
                fail();
            }catch (Exception e) {
            }
        }

        try{
            livingRoom = new LivingRoom(22,2,5);
            livingRoom.refill();
            fail();
        }catch (Exception e){
        }

        livingRoom = new LivingRoom(22,9,4);
        livingRoom.refill();
        assertNotEquals(ItemTileType.EMPTY,livingRoom.removeTile(0,0).getType());
        assertNotEquals(ItemTileType.EMPTY,livingRoom.removeTile(4,0).getType());
        assertNotEquals(ItemTileType.EMPTY,livingRoom.removeTile(-4,0).getType());
        assertNotEquals(ItemTileType.EMPTY,livingRoom.removeTile(0,4).getType());
        assertNotEquals(ItemTileType.EMPTY,livingRoom.removeTile(0,-4).getType());
    }
}