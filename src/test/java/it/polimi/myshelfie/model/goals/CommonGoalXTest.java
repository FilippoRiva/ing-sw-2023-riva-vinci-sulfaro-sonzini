package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.ItemTile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CommonGoalXTest {
    @Test
    void isVerified(){
        CommonGoalX xGoal = new CommonGoalX();

        ItemTile[][] bookshelf = {
                {ItemTile.CAT  ,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT  },
                {ItemTile.CAT,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT  ,ItemTile.CAT},
                {ItemTile.TROPHY,ItemTile.PLANT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT},
                {ItemTile.CAT,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT,ItemTile.CAT    },
                {ItemTile.CAT,ItemTile.GAME,ItemTile.BOOK,ItemTile.CAT,ItemTile.CAT},
                {ItemTile.CAT  ,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT  }
        };
        assertTrue(xGoal.isVerified(bookshelf),"5x6 verifiable bookshelf should be verified");

        bookshelf = new ItemTile[][]{
                {ItemTile.CAT  ,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT  },
                {ItemTile.CAT,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT  ,ItemTile.CAT},
                {ItemTile.FRAME,ItemTile.PLANT,ItemTile.FRAME,ItemTile.CAT,ItemTile.CAT},
                {ItemTile.CAT,ItemTile.FRAME,ItemTile.EMPTY,ItemTile.CAT,ItemTile.CAT    },
                {ItemTile.FRAME,ItemTile.GAME,ItemTile.BOOK,ItemTile.CAT,ItemTile.CAT},
                {ItemTile.CAT  ,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT  }
        };
        assertFalse(xGoal.isVerified(bookshelf),"Only 4 of 5, should not be verified");
    }
}
