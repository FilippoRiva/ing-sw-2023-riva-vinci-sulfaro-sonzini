package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.ItemTile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CommonGoalStairsTest {
    @Test
    void isVerified(){
        CommonGoalStairs stairGoal = new CommonGoalStairs();

        ItemTile [][] bookshelf = {
                {ItemTile.EMPTY  ,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY  },
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.PLANT},
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.PLANT,ItemTile.BOOK},
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.GAME,ItemTile.TROPHY,ItemTile.GAME},
                {ItemTile.EMPTY,ItemTile.BOOK,ItemTile.TROPHY,ItemTile.BOOK,ItemTile.GAME},
                {ItemTile.CAT  ,ItemTile.TROPHY,ItemTile.CAT,ItemTile.GAME,ItemTile.CAT  }
        };
        assertTrue(stairGoal.isVerified(bookshelf),"5x6 verifiable bookshelf should be verified");

        bookshelf = new ItemTile[][]{
                {ItemTile.EMPTY  ,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY  },
                {ItemTile.FRAME,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.FRAME,ItemTile.TROPHY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.FRAME,ItemTile.PLANT,ItemTile.PLANT,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.FRAME,ItemTile.TROPHY,ItemTile.GAME,ItemTile.TROPHY,ItemTile.EMPTY},
                {ItemTile.CAT  ,ItemTile.GAME,ItemTile.CAT,ItemTile.GAME,ItemTile.CAT  }
        };
        assertTrue(stairGoal.isVerified(bookshelf),"5x6 verifiable bookshelf should be verified");

        bookshelf = new ItemTile[][]{
                {ItemTile.FRAME,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.FRAME,ItemTile.TROPHY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.FRAME,ItemTile.PLANT,ItemTile.PLANT,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.FRAME,ItemTile.TROPHY,ItemTile.GAME,ItemTile.TROPHY,ItemTile.EMPTY},
                {ItemTile.CAT  ,ItemTile.GAME,ItemTile.CAT,ItemTile.GAME,ItemTile.CAT  },
                {ItemTile.FRAME  ,ItemTile.GAME,ItemTile.TROPHY,ItemTile.GAME,ItemTile.CAT  }
        };
        assertTrue(stairGoal.isVerified(bookshelf),"5x6 verifiable bookshelf should be verified");

        bookshelf = new ItemTile[][]{
                {ItemTile.EMPTY  ,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY  },
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.PLANT,ItemTile.BOOK},
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.GAME,ItemTile.TROPHY,ItemTile.GAME},
                {ItemTile.EMPTY,ItemTile.BOOK,ItemTile.TROPHY,ItemTile.BOOK,ItemTile.GAME},
                {ItemTile.CAT  ,ItemTile.TROPHY,ItemTile.CAT,ItemTile.GAME,ItemTile.CAT  }
        };
        assertFalse(stairGoal.isVerified(bookshelf),"Only 4 has right columns, should not be verified");

        bookshelf = new ItemTile[][]{
                {ItemTile.EMPTY  ,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.PLANT  },
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.PLANT},
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.PLANT,ItemTile.BOOK},
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.GAME,ItemTile.TROPHY,ItemTile.GAME},
                {ItemTile.EMPTY,ItemTile.BOOK,ItemTile.TROPHY,ItemTile.BOOK,ItemTile.GAME},
                {ItemTile.CAT  ,ItemTile.TROPHY,ItemTile.CAT,ItemTile.GAME,ItemTile.CAT  }
        };
        assertFalse(stairGoal.isVerified(bookshelf),"last column has one more, should not be verified");


    }
}
