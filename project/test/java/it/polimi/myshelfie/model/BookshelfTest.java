package it.polimi.myshelfie.model;

import it.polimi.myshelfie.model.exceptions.FullColumnException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BookshelfTest {
    @Test
    void setUp() {
        Bookshelf bookshelf = new Bookshelf(new Dimension(5,6));
        ItemTile[][] bookshelfItems = bookshelf.getBookshelf();
        for(ItemTile[] a : bookshelfItems){
            for(ItemTile tile : a){
                if(tile != ItemTile.EMPTY) fail();
            }
        }
        bookshelf = new Bookshelf(new Dimension(10,10));
        bookshelfItems = bookshelf.getBookshelf();
        for(ItemTile[] a : bookshelfItems){
            for(ItemTile tile : a){
                if(tile != ItemTile.EMPTY) fail();
            }
        }
    }

    @Test
    void insertTile() throws FullColumnException {
        Bookshelf bookshelf = new Bookshelf(new Dimension(5,6));
        ItemTile[] tiles = new ItemTile[]{ItemTile.CAT, ItemTile.CAT, ItemTile.CAT, ItemTile.CAT, ItemTile.CAT, ItemTile.CAT, ItemTile.CAT};
        try {
            for (ItemTile t : tiles) bookshelf.insertTile(0, t);
            fail("We should get an exception here");
        }catch (FullColumnException e){
           e.getMessage();
        }

        bookshelf = new Bookshelf(new Dimension(5,6));
        tiles = new ItemTile[]{ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT};
        for(ItemTile t: tiles) bookshelf.insertTile(0,t);
        tiles = new ItemTile[]{ItemTile.BOOK,ItemTile.BOOK,ItemTile.BOOK,ItemTile.BOOK};
        for(ItemTile t: tiles) bookshelf.insertTile(1,t);
        tiles = new ItemTile[]{ItemTile.TROPHY,ItemTile.TROPHY,ItemTile.TROPHY,ItemTile.TROPHY,ItemTile.TROPHY};
        for(ItemTile t: tiles) bookshelf.insertTile(2,t);
        tiles = new ItemTile[]{ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT};
        for(ItemTile t: tiles) bookshelf.insertTile(3,t);
        tiles = new ItemTile[]{ItemTile.FRAME,ItemTile.FRAME,ItemTile.FRAME,ItemTile.FRAME};
        for(ItemTile t: tiles) bookshelf.insertTile(4,t);
        ItemTile[][] bookshelfTiles = bookshelf.getBookshelf();
        assertTrue(matrixEqual(bookshelfTiles,
        new ItemTile [][]{
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.TROPHY,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.CAT  ,ItemTile.BOOK ,ItemTile.TROPHY,ItemTile.CAT,ItemTile.FRAME},
                {ItemTile.CAT  ,ItemTile.BOOK ,ItemTile.TROPHY,ItemTile.CAT,ItemTile.FRAME},
                {ItemTile.CAT  ,ItemTile.BOOK ,ItemTile.TROPHY,ItemTile.CAT,ItemTile.FRAME},
                {ItemTile.CAT  ,ItemTile.BOOK ,ItemTile.TROPHY,ItemTile.CAT,ItemTile.FRAME  }
        }));
        //full
        bookshelf = new Bookshelf(new Dimension(5,6));
        tiles = new ItemTile[]{ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT};
        for(ItemTile t: tiles) bookshelf.insertTile(0,t);
        tiles = new ItemTile[]{ItemTile.BOOK,ItemTile.BOOK,ItemTile.BOOK,ItemTile.BOOK,ItemTile.BOOK,ItemTile.BOOK};
        for(ItemTile t: tiles) bookshelf.insertTile(1,t);
        tiles = new ItemTile[]{ItemTile.TROPHY,ItemTile.TROPHY,ItemTile.TROPHY,ItemTile.TROPHY,ItemTile.TROPHY,ItemTile.TROPHY};
        for(ItemTile t: tiles) bookshelf.insertTile(2,t);
        tiles = new ItemTile[]{ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT};
        for(ItemTile t: tiles) bookshelf.insertTile(3,t);
        tiles = new ItemTile[]{ItemTile.FRAME,ItemTile.FRAME,ItemTile.FRAME,ItemTile.FRAME,ItemTile.FRAME,ItemTile.FRAME};
        for(ItemTile t: tiles) bookshelf.insertTile(4,t);
        bookshelfTiles = bookshelf.getBookshelf();
        assertTrue(matrixEqual(bookshelfTiles,
                new ItemTile [][]{
                        {ItemTile.CAT,ItemTile.BOOK,ItemTile.TROPHY,ItemTile.CAT,ItemTile.FRAME},
                        {ItemTile.CAT,ItemTile.BOOK,ItemTile.TROPHY,ItemTile.CAT,ItemTile.FRAME},
                        {ItemTile.CAT  ,ItemTile.BOOK ,ItemTile.TROPHY,ItemTile.CAT,ItemTile.FRAME},
                        {ItemTile.CAT  ,ItemTile.BOOK ,ItemTile.TROPHY,ItemTile.CAT,ItemTile.FRAME},
                        {ItemTile.CAT  ,ItemTile.BOOK ,ItemTile.TROPHY,ItemTile.CAT,ItemTile.FRAME},
                        {ItemTile.CAT  ,ItemTile.BOOK ,ItemTile.TROPHY,ItemTile.CAT,ItemTile.FRAME  }
                }));
        //bigger one
        bookshelf = new Bookshelf(new Dimension(9,11));
        for(int column = 0; column <9;column++) {
            tiles = new ItemTile[]{ItemTile.CAT, ItemTile.CAT, ItemTile.CAT, ItemTile.CAT, ItemTile.CAT, ItemTile.CAT, ItemTile.CAT, ItemTile.CAT, ItemTile.CAT, ItemTile.CAT, ItemTile.CAT};
            for (ItemTile t : tiles) bookshelf.insertTile(column, t);
        }
        bookshelfTiles = bookshelf.getBookshelf();
        assertTrue(matrixEqual(bookshelfTiles,
                new ItemTile [][]{
                        {ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT},
                        {ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT},
                        {ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT},
                        {ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT},
                        {ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT},
                        {ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT},
                        {ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT},
                        {ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT},
                        {ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT},
                        {ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT},
                        {ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT},
                }));
    }

    private boolean matrixEqual(ItemTile[][]A,ItemTile[][] B){
        if(A.length!=B.length) return false;
        if(A[0].length!=B[0].length) return false;
        for(int y = 0; y<A.length; y++){
            for(int x = 0; x<A[0].length; x++){
                if(A[y][x]!=B[y][x]) return false;
            }
        }
        return true;
    }
}