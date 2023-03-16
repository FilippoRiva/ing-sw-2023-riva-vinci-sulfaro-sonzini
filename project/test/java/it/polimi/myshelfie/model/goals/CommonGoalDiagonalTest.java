package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.ItemTile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalDiagonalTest {

    @Test
    void isVerified() {
        CommonGoalDiagonal cornerGoal = new CommonGoalDiagonal();
        ItemTile[][] bookshelf = {
                {ItemTile.CAT  ,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.CAT  ,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT  ,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT  ,ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT  },
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY}
        };
        assertTrue(cornerGoal.isVerified(bookshelf),"5x6 descending verifiable bookshelf should be verified");
        bookshelf = new ItemTile[][]{
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.CAT  ,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.CAT  ,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT  ,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT  ,ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT  },
        };
        assertTrue(cornerGoal.isVerified(bookshelf),"5x6 descending verifiable bookshelf should be verified");
        bookshelf = new ItemTile[][]{
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT  },
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT  ,ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT  ,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.CAT  ,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.CAT  ,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
        };
        assertTrue(cornerGoal.isVerified(bookshelf),"5x6 ascending verifiable bookshelf should be verified");
        bookshelf = new ItemTile[][]{
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT  },
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT  ,ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT  ,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.CAT  ,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.CAT  ,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
        };
        assertTrue(cornerGoal.isVerified(bookshelf),"5x6 ascending verifiable bookshelf should be verified");
        bookshelf = new ItemTile[][]{
                {ItemTile.CAT,  ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.CAT  , ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY, ItemTile.CAT  , ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.CAT  , ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.CAT  , ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.CAT  },
                {ItemTile.EMPTY,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
        };
        assertTrue(cornerGoal.isVerified(bookshelf),"6x10 descending verifiable bookshelf should be verified");
        bookshelf = new ItemTile[][]{
                {ItemTile.EMPTY,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.CAT  },
                {ItemTile.EMPTY,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.CAT  , ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.CAT  , ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY, ItemTile.CAT  , ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.CAT  , ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.CAT,  ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
        };
        assertTrue(cornerGoal.isVerified(bookshelf),"6x10 ascending verifiable bookshelf should be verified");
        bookshelf = new ItemTile[][]{
                {ItemTile.EMPTY,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.CAT,  ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.CAT  , ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY, ItemTile.CAT  , ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.CAT  , ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.CAT  , ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.CAT  },
                {ItemTile.EMPTY,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
        };
        assertTrue(cornerGoal.isVerified(bookshelf),"6x10 descending verifiable bookshelf should be verified");
        bookshelf = new ItemTile[][]{
                {ItemTile.EMPTY,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.CAT,  ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.CAT  , ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY, ItemTile.CAT  , ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.CAT  , ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.CAT  , ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.CAT  },
                {ItemTile.EMPTY,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
        };
        assertTrue(cornerGoal.isVerified(bookshelf),"6x10 descending verifiable bookshelf should be verified");
        bookshelf = new ItemTile[][]{
                {ItemTile.EMPTY,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.CAT,  ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.CAT  , ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY, ItemTile.CAT  , ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.CAT  , ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.CAT  , ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.CAT  },
                {ItemTile.EMPTY,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
        };
        assertTrue(cornerGoal.isVerified(bookshelf),"6x10 descending verifiable bookshelf should be verified");
        bookshelf = new ItemTile[][]{
                {ItemTile.EMPTY,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.CAT,  ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.CAT  , ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY, ItemTile.CAT  , ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.CAT  , ItemTile.EMPTY, ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.CAT  , ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.CAT  },
        };
        assertTrue(cornerGoal.isVerified(bookshelf),"6x10 descending verifiable bookshelf should be verified");
        bookshelf = new ItemTile[][]{
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.CAT  ,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT  ,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT  ,ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT  },
        };
        assertFalse(cornerGoal.isVerified(bookshelf),"First element missing should not be verified");
        bookshelf = new ItemTile[][]{
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.CAT  ,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT  ,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT  ,ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT  },
        };
        assertFalse(cornerGoal.isVerified(bookshelf),"Second element missing should not be verified");
        bookshelf = new ItemTile[][]{
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.CAT  ,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.CAT  ,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT  ,ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT  },
        };
        assertFalse(cornerGoal.isVerified(bookshelf),"Third element missing should not be verified");
        bookshelf = new ItemTile[][]{
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.CAT  ,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.CAT  ,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT  ,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT  },
        };
        assertFalse(cornerGoal.isVerified(bookshelf),"Fourth element missing should not be verified");
        bookshelf = new ItemTile[][]{
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.CAT  ,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.CAT  ,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT  ,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT  ,ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
        };
        assertFalse(cornerGoal.isVerified(bookshelf),"Last element missing should not be verified");
        bookshelf = new ItemTile[][]{
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT  ,ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT  ,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.CAT  ,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.CAT  ,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
        };
        assertFalse(cornerGoal.isVerified(bookshelf),"Last element missing should not be verified");
        bookshelf = new ItemTile[][]{
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT  },
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT  ,ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT  ,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.CAT  ,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
        };
        assertFalse(cornerGoal.isVerified(bookshelf),"First element missing should not be verified");
    }
}