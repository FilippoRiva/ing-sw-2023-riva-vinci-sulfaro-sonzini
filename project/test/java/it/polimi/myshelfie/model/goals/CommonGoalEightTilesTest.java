package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.ItemTile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalEightTilesTest {
    @Test
    void isVerified(){
        CommonGoalEightTiles eightTilesGoal = new CommonGoalEightTiles();

        ItemTile[][] bookshelf = {
                {ItemTile.CAT  ,ItemTile.EMPTY,ItemTile.CAT,ItemTile.EMPTY,ItemTile.CAT  },
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT  ,ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.PLANT,ItemTile.GAME,ItemTile.EMPTY,ItemTile.CAT},
                {ItemTile.BOOK,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT    },
                {ItemTile.EMPTY,ItemTile.GAME,ItemTile.BOOK,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.CAT  ,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT  }
        };
        assertTrue(eightTilesGoal.isVerified(bookshelf),"5x6 verifiable bookshelf should be verified");

        bookshelf = new ItemTile[][]{
                {ItemTile.CAT, ItemTile.TROPHY, ItemTile.FRAME, ItemTile.EMPTY, ItemTile.CAT, ItemTile.EMPTY},
                {ItemTile.BOOK, ItemTile.FRAME, ItemTile.CAT, ItemTile.BOOK, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.TROPHY, ItemTile.CAT, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.BOOK, ItemTile.EMPTY, ItemTile.FRAME, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY, ItemTile.BOOK, ItemTile.EMPTY, ItemTile.CAT, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.CAT  , ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.TROPHY, ItemTile.CAT, ItemTile.EMPTY},
                {ItemTile.BOOK  , ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.TROPHY, ItemTile.GAME, ItemTile.EMPTY}
        };
        assertFalse(eightTilesGoal.isVerified(bookshelf),"Only 7 has same tiles, should not be verified");
    }
}
