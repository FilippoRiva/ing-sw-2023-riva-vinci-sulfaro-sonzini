package it.polimi.myshelfie.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTileTest {

    @Test
    void tileTest() {
        for(int i = 0; i<3;i++){
            ItemTile tile = new ItemTile(ItemTileType.EMPTY,i);
            assertEquals("Empty",tile.getType().getName());
            assertEquals(i,tile.getId());
            assertEquals(tile, tile);
            assertNotEquals(tile,null);
            assertNotEquals(tile,new ItemTile(ItemTileType.CAT,i));
            assertNotEquals(tile,3);
            assertNotEquals(tile,new ItemTile[1]);
            assertNotEquals(tile,"Test");
        }
    }
}