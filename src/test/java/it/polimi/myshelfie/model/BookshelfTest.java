package it.polimi.myshelfie.model;

import it.polimi.myshelfie.model.exceptions.FullColumnException;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class BookshelfTest {
    @Test
    void setUp() {
        Bookshelf bookshelf = new Bookshelf(new Dimension(5,6));
        ItemTile[][] bookshelfItems = bookshelf.getBookshelfItems();
        for(ItemTile[] a : bookshelfItems){
            for(ItemTile tile : a){
                if(tile.getType() != ItemTileType.EMPTY) fail();
            }
        }
        bookshelf = new Bookshelf(new Dimension(10,10));
        bookshelfItems = bookshelf.getBookshelfItems();
        for(ItemTile[] a : bookshelfItems){
            for(ItemTile tile : a){
                if(tile.getType() != ItemTileType.EMPTY) fail();
            }
        }
    }

    @Test
    void insertTile() throws FullColumnException {
        Bookshelf bookshelf = new Bookshelf(new Dimension(5,6));
        ItemTile[] tiles = new ItemTile[]{new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0)};
        try {
            for (ItemTile t : tiles) bookshelf.insertTile(0, t);
            fail("We should get an exception here");
        }catch (FullColumnException e){
            e.getMessage();
        }

        bookshelf = new Bookshelf(new Dimension(5,6));
        tiles = new ItemTile[]{new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0)};
        for(ItemTile t: tiles) bookshelf.insertTile(0,t);
        tiles = new ItemTile[]{new ItemTile(ItemTileType.BOOK,0), new ItemTile(ItemTileType.BOOK,0), new ItemTile(ItemTileType.BOOK,0), new ItemTile(ItemTileType.BOOK,0)};
        for(ItemTile t: tiles) bookshelf.insertTile(1,t);
        tiles = new ItemTile[]{new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.TROPHY,0)};
        for(ItemTile t: tiles) bookshelf.insertTile(2,t);
        tiles = new ItemTile[]{new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0)};
        for(ItemTile t: tiles) bookshelf.insertTile(3,t);
        tiles = new ItemTile[]{new ItemTile(ItemTileType.FRAME,0), new ItemTile(ItemTileType.FRAME,0), new ItemTile(ItemTileType.FRAME,0), new ItemTile(ItemTileType.FRAME,0)};
        for(ItemTile t: tiles) bookshelf.insertTile(4,t);
        ItemTile[][] bookshelfTiles = bookshelf.getBookshelfItems();
        assertTrue(matrixEqual(bookshelfTiles,
        new ItemTile[][]{
                {new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0)},
                {new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0)},
                {new ItemTile(ItemTileType.CAT,0)  , new ItemTile(ItemTileType.BOOK,0) , new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.FRAME,0)},
                {new ItemTile(ItemTileType.CAT,0)  , new ItemTile(ItemTileType.BOOK,0) , new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.FRAME,0)},
                {new ItemTile(ItemTileType.CAT,0)  , new ItemTile(ItemTileType.BOOK,0) , new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.FRAME,0)},
                {new ItemTile(ItemTileType.CAT,0)  , new ItemTile(ItemTileType.BOOK,0) , new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.FRAME,0)  }
        }));
        //full
        bookshelf = new Bookshelf(new Dimension(5,6));
        tiles = new ItemTile[]{new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0)};
        for(ItemTile t: tiles) bookshelf.insertTile(0,t);
        tiles = new ItemTile[]{new ItemTile(ItemTileType.BOOK,0), new ItemTile(ItemTileType.BOOK,0), new ItemTile(ItemTileType.BOOK,0), new ItemTile(ItemTileType.BOOK,0), new ItemTile(ItemTileType.BOOK,0), new ItemTile(ItemTileType.BOOK,0)};
        for(ItemTile t: tiles) bookshelf.insertTile(1,t);
        tiles = new ItemTile[]{new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.TROPHY,0)};
        for(ItemTile t: tiles) bookshelf.insertTile(2,t);
        tiles = new ItemTile[]{new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0)};
        for(ItemTile t: tiles) bookshelf.insertTile(3,t);
        tiles = new ItemTile[]{new ItemTile(ItemTileType.FRAME,0), new ItemTile(ItemTileType.FRAME,0), new ItemTile(ItemTileType.FRAME,0), new ItemTile(ItemTileType.FRAME,0), new ItemTile(ItemTileType.FRAME,0), new ItemTile(ItemTileType.FRAME,0)};
        for(ItemTile t: tiles) bookshelf.insertTile(4,t);
        bookshelfTiles = bookshelf.getBookshelfItems();
        assertTrue(matrixEqual(bookshelfTiles,
                new ItemTile[][]{
                        {new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.BOOK,0), new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.FRAME,0)},
                        {new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.BOOK,0), new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.FRAME,0)},
                        {new ItemTile(ItemTileType.CAT,0)  , new ItemTile(ItemTileType.BOOK,0) , new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.FRAME,0)},
                        {new ItemTile(ItemTileType.CAT,0)  , new ItemTile(ItemTileType.BOOK,0) , new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.FRAME,0)},
                        {new ItemTile(ItemTileType.CAT,0)  , new ItemTile(ItemTileType.BOOK,0) , new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.FRAME,0)},
                        {new ItemTile(ItemTileType.CAT,0)  , new ItemTile(ItemTileType.BOOK,0) , new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.FRAME,0)  }
                }));
        //bigger one
        bookshelf = new Bookshelf(new Dimension(9,11));
        for(int column = 0; column <9;column++) {
            tiles = new ItemTile[]{new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0)};
            for (ItemTile t : tiles) bookshelf.insertTile(column, t);
        }
        bookshelfTiles = bookshelf.getBookshelfItems();
        assertTrue(matrixEqual(bookshelfTiles,
                new ItemTile[][]{
                        {new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0)},
                        {new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0)},
                        {new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0)},
                        {new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0)},
                        {new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0)},
                        {new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0)},
                        {new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0)},
                        {new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0)},
                        {new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0)},
                        {new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0)},
                        {new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0)},
                }));
    }

    private boolean matrixEqual(ItemTile[][]A, ItemTile[][] B){
        if(A.length!=B.length) return false;
        if(A[0].length!=B[0].length) return false;
        for(int y = 0; y<A.length; y++){
            for(int x = 0; x<A[0].length; x++){
                if(A[y][x].getType()!=B[y][x].getType()) return false;
            }
        }
        return true;
    }
}