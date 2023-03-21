package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.ItemTile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CommonGoalTwoSquaresTest {
    @Test
    void isVerified(){
        CommonGoalTwoSquares twoSquaresGoal = new CommonGoalTwoSquares();

        ItemTile[][] bookshelf = {
                {ItemTile.CAT  ,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT  },
                {ItemTile.CAT,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT  ,ItemTile.CAT},
                {ItemTile.TROPHY,ItemTile.PLANT,ItemTile.PLANT,ItemTile.TROPHY,ItemTile.CAT},
                {ItemTile.CAT,ItemTile.PLANT,ItemTile.PLANT,ItemTile.PLANT,ItemTile.CAT    },
                {ItemTile.CAT,ItemTile.GAME,ItemTile.BOOK,ItemTile.CAT,ItemTile.CAT},
                {ItemTile.CAT  ,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT  }
        };
        assertTrue(twoSquaresGoal.isVerified(bookshelf),"5x6 verifiable bookshelf should be verified");

       bookshelf = new ItemTile[][]{
                {ItemTile.CAT  ,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT  },
                {ItemTile.CAT,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT  ,ItemTile.CAT},
                {ItemTile.TROPHY,ItemTile.PLANT,ItemTile.PLANT,ItemTile.TROPHY,ItemTile.CAT},
                {ItemTile.CAT,ItemTile.GAME,ItemTile.GAME,ItemTile.PLANT,ItemTile.CAT    },
                {ItemTile.CAT,ItemTile.GAME,ItemTile.GAME,ItemTile.GAME,ItemTile.GAME},
                {ItemTile.CAT  ,ItemTile.CAT,ItemTile.CAT,ItemTile.GAME,ItemTile.GAME  }
        };
        assertTrue(twoSquaresGoal.isVerified(bookshelf),"5x6 verifiable bookshelf should be verified");

        bookshelf = new ItemTile[][]{
                {ItemTile.CAT  ,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT  },
                {ItemTile.CAT,ItemTile.TROPHY,ItemTile.TROPHY,ItemTile.PLANT,ItemTile.CAT    },
                {ItemTile.CAT,ItemTile.TROPHY,ItemTile.TROPHY,ItemTile.FRAME,ItemTile.FRAME},
                {ItemTile.GAME,ItemTile.GAME,ItemTile.EMPTY,ItemTile.FRAME  ,ItemTile.FRAME},
                {ItemTile.GAME,ItemTile.GAME,ItemTile.PLANT,ItemTile.TROPHY,ItemTile.TROPHY},
                {ItemTile.CAT  ,ItemTile.CAT,ItemTile.CAT,ItemTile.TROPHY,ItemTile.TROPHY  }
        };
        assertTrue(twoSquaresGoal.isVerified(bookshelf),"5x6 verifiable bookshelf should be verified");

        bookshelf = new ItemTile[][]{
                {ItemTile.CAT  ,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT  },
                {ItemTile.CAT,ItemTile.CAT,ItemTile.EMPTY,ItemTile.CAT  ,ItemTile.PLANT},
                {ItemTile.CAT,ItemTile.PLANT,ItemTile.GAME,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.BOOK,ItemTile.BOOK,ItemTile.EMPTY,ItemTile.CAT,ItemTile.EMPTY    },
                {ItemTile.BOOK,ItemTile.BOOK,ItemTile.BOOK,ItemTile.CAT,ItemTile.BOOK},
                {ItemTile.TROPHY  ,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.BOOK  }
        };
        assertFalse(twoSquaresGoal.isVerified(bookshelf),"Only 1 square, should not be verified");
    }
}
