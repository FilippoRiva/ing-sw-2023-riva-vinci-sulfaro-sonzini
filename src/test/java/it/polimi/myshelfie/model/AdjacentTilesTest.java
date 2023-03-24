package it.polimi.myshelfie.model;

import it.polimi.myshelfie.model.exceptions.FullColumnException;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AdjacentTilesTest {

    @Test
    void adjacentTest(){
        ItemTile[] tiles;
        //test 1
        Bookshelf bookshelf = new Bookshelf(new Dimension(5, 6));
        tiles = new ItemTile[]{new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT, 0)};
        for(ItemTile t: tiles) {
            try {
                bookshelf.insertTile(0, t);
            } catch (FullColumnException e) {
                e.getMessage();
            }
        }
        tiles = new ItemTile[]{new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT, 0)};
        for(ItemTile t: tiles) {
            try {
                bookshelf.insertTile(1, t);
            } catch (FullColumnException e) {
                e.getMessage();
            }
        }
        tiles = new ItemTile[]{new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT, 0)};

        for(ItemTile t: tiles) {
            try {
                bookshelf.insertTile(2, t);
            } catch (FullColumnException e) {
                e.getMessage();
            }
        }
        tiles = new ItemTile[]{new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT, 0)};

        for(ItemTile t: tiles) {
            try {
                bookshelf.insertTile(3, t);
            } catch (FullColumnException e) {
                e.getMessage();
            }
        }
        tiles = new ItemTile[]{new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT, 0)};

        for(ItemTile t: tiles) {
            try {
                bookshelf.insertTile(4, t);
            } catch (FullColumnException e) {
                e.getMessage();
            }
        }

        assertEquals(8, bookshelf.achievedAdjacentTiles());
        //test 2
        bookshelf = new Bookshelf(new Dimension(5, 6));
        tiles = new ItemTile[]{new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.BOOK,0)};
        for(ItemTile t: tiles) {
            try {
                bookshelf.insertTile(0, t);
            } catch (FullColumnException e) {
                e.getMessage();
            }
        }
        tiles = new ItemTile[]{new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.BOOK,0), new ItemTile(ItemTileType.FRAME,0)};
        for(ItemTile t: tiles) {
            try {
                bookshelf.insertTile(1, t);
            } catch (FullColumnException e) {
                e.getMessage();
            }
        }
        tiles = new ItemTile[]{new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.FRAME,0), new ItemTile(ItemTileType.FRAME,0)};

        for(ItemTile t: tiles) {
            try {
                bookshelf.insertTile(2, t);
            } catch (FullColumnException e) {
                e.getMessage();
            }
        }
        tiles = new ItemTile[]{new ItemTile(ItemTileType.GAME,0), new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.FRAME,0), new ItemTile(ItemTileType.FRAME,0)};

        for(ItemTile t: tiles) {
            try {
                bookshelf.insertTile(3, t);
            } catch (FullColumnException e) {
                e.getMessage();
            }
        }
        tiles = new ItemTile[]{new ItemTile(ItemTileType.FRAME,0), new ItemTile(ItemTileType.GAME,0)};

        for(ItemTile t: tiles) {
            try {
                bookshelf.insertTile(4, t);
            } catch (FullColumnException e) {
                e.getMessage();
            }
        }
        assertEquals(10, bookshelf.achievedAdjacentTiles());
        //test 3
        bookshelf = new Bookshelf(new Dimension(5, 6));
        assertEquals(0, bookshelf.achievedAdjacentTiles());
        //test 4
        bookshelf = new Bookshelf(new Dimension(5, 6));
        tiles = new ItemTile[]{new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.FRAME,0), new ItemTile(ItemTileType.FRAME,0), new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.TROPHY,0)};
        for(ItemTile t: tiles) {
            try {
                bookshelf.insertTile(0, t);
            } catch (FullColumnException e) {
                e.getMessage();
            }
        }
        tiles = new ItemTile[]{new ItemTile(ItemTileType.FRAME,0), new ItemTile(ItemTileType.FRAME,0), new ItemTile(ItemTileType.GAME,0), new ItemTile(ItemTileType.GAME,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0)};
        for(ItemTile t: tiles) {
            try {
                bookshelf.insertTile(1, t);
            } catch (FullColumnException e) {
                e.getMessage();
            }
        }
        tiles = new ItemTile[]{new ItemTile(ItemTileType.GAME,0), new ItemTile(ItemTileType.GAME,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.BOOK,0), new ItemTile(ItemTileType.BOOK,0)};

        for(ItemTile t: tiles) {
            try {
                bookshelf.insertTile(2, t);
            } catch (FullColumnException e) {
                e.getMessage();
            }
        }
        tiles = new ItemTile[]{new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.PLANT,0), new ItemTile(ItemTileType.PLANT,0), new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.TROPHY,0)};

        for(ItemTile t: tiles) {
            try {
                bookshelf.insertTile(3, t);
            } catch (FullColumnException e) {
                e.getMessage();
            }
        }
        tiles = new ItemTile[]{new ItemTile(ItemTileType.PLANT,0), new ItemTile(ItemTileType.PLANT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.PLANT,0), new ItemTile(ItemTileType.PLANT,0)};

        for(ItemTile t: tiles) {
            try {
                bookshelf.insertTile(4, t);
            } catch (FullColumnException e) {
                e.getMessage();
            }
        }
        assertEquals(0, bookshelf.achievedAdjacentTiles());
    }
}