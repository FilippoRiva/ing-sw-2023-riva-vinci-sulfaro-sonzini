package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.ItemTile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalFourOfFourTest {
    @Test
    void isVerified(){
        CommonGoalFourOfFour fourOfFourGoal = new CommonGoalFourOfFour();

        ItemTile[][] bookshelf = {
                {ItemTile.CAT  ,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT  },
                {ItemTile.CAT,ItemTile.PLANT,ItemTile.EMPTY,ItemTile.CAT  ,ItemTile.CAT},
                {ItemTile.TROPHY,ItemTile.PLANT,ItemTile.GAME,ItemTile.CAT,ItemTile.CAT},
                {ItemTile.CAT,ItemTile.PLANT,ItemTile.EMPTY,ItemTile.CAT,ItemTile.CAT    },
                {ItemTile.CAT,ItemTile.PLANT,ItemTile.BOOK,ItemTile.CAT,ItemTile.CAT},
                {ItemTile.CAT  ,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT  }
        };
        assertTrue(fourOfFourGoal.isVerified(bookshelf),"5x6 verifiable bookshelf should be verified");

        bookshelf = new ItemTile[][]{
                {ItemTile.CAT   ,ItemTile.GAME ,ItemTile.GAME ,ItemTile.GAME ,ItemTile.GAME  },
                {ItemTile.CAT   ,ItemTile.PLANT,ItemTile.EMPTY,ItemTile.FRAME,ItemTile.TROPHY},
                {ItemTile.TROPHY,ItemTile.PLANT,ItemTile.GAME ,ItemTile.FRAME,ItemTile.TROPHY},
                {ItemTile.CAT   ,ItemTile.PLANT,ItemTile.EMPTY,ItemTile.FRAME,ItemTile.TROPHY},
                {ItemTile.CAT   ,ItemTile.PLANT,ItemTile.BOOK ,ItemTile.FRAME,ItemTile.TROPHY},
                {ItemTile.CAT   ,ItemTile.BOOK ,ItemTile.BOOK ,ItemTile.BOOK ,ItemTile.BOOK  }
        };
        assertTrue(fourOfFourGoal.isVerified(bookshelf),"5x6 verifiable bookshelf should be verified");

        bookshelf = new ItemTile[][]{
                {ItemTile.TROPHY,ItemTile.PLANT,ItemTile.GAME ,ItemTile.FRAME,ItemTile.TROPHY},
                {ItemTile.CAT   ,ItemTile.GAME ,ItemTile.EMPTY ,ItemTile.GAME ,ItemTile.GAME  },
                {ItemTile.CAT   ,ItemTile.PLANT,ItemTile.EMPTY,ItemTile.FRAME,ItemTile.TROPHY},
                {ItemTile.CAT   ,ItemTile.PLANT,ItemTile.PLANT,ItemTile.PLANT,ItemTile.PLANT},
                {ItemTile.CAT   ,ItemTile.TROPHY,ItemTile.TROPHY ,ItemTile.TROPHY,ItemTile.TROPHY},
                {ItemTile.CAT   ,ItemTile.BOOK ,ItemTile.BOOK ,ItemTile.BOOK ,ItemTile.BOOK  }
        };
        assertTrue(fourOfFourGoal.isVerified(bookshelf),"5x6 verifiable bookshelf should be verified");

        bookshelf = new ItemTile[][]{
                {ItemTile.CAT  ,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT  },
                {ItemTile.CAT,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT  ,ItemTile.CAT},
                {ItemTile.CAT,ItemTile.PLANT,ItemTile.PLANT,ItemTile.CAT,ItemTile.CAT},
                {ItemTile.BOOK,ItemTile.BOOK,ItemTile.PLANT,ItemTile.CAT,ItemTile.CAT    },
                {ItemTile.CAT,ItemTile.GAME,ItemTile.PLANT,ItemTile.CAT,ItemTile.CAT},
                {ItemTile.CAT  ,ItemTile.EMPTY,ItemTile.BOOK,ItemTile.EMPTY,ItemTile.CAT  }
        };
        assertFalse(fourOfFourGoal.isVerified(bookshelf),"Only 3 has same tiles, should not be verified");
    }
}
