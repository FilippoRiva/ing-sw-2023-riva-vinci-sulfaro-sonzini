package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.ItemTile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalCornerTest {

    @Test
    void isVerified() {
        CommonGoalCorner cornerGoal = new CommonGoalCorner();
        ItemTile [][] bookshelf = {
                {ItemTile.CAT  ,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT  },
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.CAT  ,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT  }
        };
        assertTrue(cornerGoal.isVerified(bookshelf),"5x6 verifiable bookshelf should be verified");
        bookshelf = new ItemTile[][]{
                {ItemTile.CAT,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.CAT},
                {ItemTile.EMPTY,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.CAT,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.CAT}
        };
        assertTrue(cornerGoal.isVerified(bookshelf),"6x10 verifiable bookshelf should be verified");
        bookshelf = new ItemTile[][]{
                {ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.CAT},
                {ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.CAT  , ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.CAT}
        };
        assertFalse(cornerGoal.isVerified(bookshelf),"Missing upper left corner should not be verified");
        bookshelf = new ItemTile[][]{
                {ItemTile.CAT, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.CAT, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.CAT}
        };
        assertFalse(cornerGoal.isVerified(bookshelf),"Missing upper right corner should not be verified");
        bookshelf = new ItemTile[][]{
                {ItemTile.CAT, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.CAT},
                {ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.CAT, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY}
        };
        assertFalse(cornerGoal.isVerified(bookshelf),"Missing lower right corner should not be verified");
        bookshelf = new ItemTile[][]{
                {ItemTile.CAT, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.CAT},
                {ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.CAT}
        };
        assertFalse(cornerGoal.isVerified(bookshelf),"Missing lower left corner should not be verified");
        bookshelf = new ItemTile[][]{
                {ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY}
        };
        assertFalse(cornerGoal.isVerified(bookshelf),"Every corner Empty should not be verified");
    }
}