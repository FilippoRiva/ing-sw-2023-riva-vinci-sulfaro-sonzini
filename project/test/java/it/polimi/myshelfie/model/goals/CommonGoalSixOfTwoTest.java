package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.ItemTile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CommonGoalSixOfTwoTest {
    @Test
    void isVerified(){
        CommonGoalSixOfTwo sixOfTwoGoal = new CommonGoalSixOfTwo();

        ItemTile[][] bookshelf = {
                {ItemTile.CAT  ,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT  },
                {ItemTile.CAT,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT  ,ItemTile.CAT},
                {ItemTile.TROPHY,ItemTile.PLANT,ItemTile.GAME,ItemTile.CAT,ItemTile.CAT},
                {ItemTile.CAT,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT,ItemTile.CAT    },
                {ItemTile.CAT,ItemTile.GAME,ItemTile.BOOK,ItemTile.CAT,ItemTile.CAT},
                {ItemTile.CAT  ,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT  }
        };
        assertTrue(sixOfTwoGoal.isVerified(bookshelf),"5x6 verifiable bookshelf should be verified");

        bookshelf = new ItemTile[][]{
                {ItemTile.CAT   ,ItemTile.CAT  ,ItemTile.CAT  ,ItemTile.CAT   ,ItemTile.CAT},
                {ItemTile.CAT   ,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.TROPHY,ItemTile.TROPHY},
                {ItemTile.TROPHY,ItemTile.PLANT,ItemTile.GAME ,ItemTile.FRAME ,ItemTile.FRAME},
                {ItemTile.CAT   ,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.BOOK  ,ItemTile.BOOK},
                {ItemTile.CAT   ,ItemTile.GAME ,ItemTile.BOOK ,ItemTile.PLANT ,ItemTile.PLANT},
                {ItemTile.CAT   ,ItemTile.CAT  ,ItemTile.CAT  ,ItemTile.GAME  ,ItemTile.GAME}
        };
        assertTrue(sixOfTwoGoal.isVerified(bookshelf),"5x6 verifiable bookshelf should be verified");

        bookshelf = new ItemTile[][]{
                {ItemTile.CAT   ,ItemTile.CAT  ,ItemTile.CAT  ,ItemTile.CAT  ,ItemTile.CAT  },
                {ItemTile.CAT   ,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT  ,ItemTile.CAT  },
                {ItemTile.CAT   ,ItemTile.PLANT,ItemTile.GAME ,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.BOOK  ,ItemTile.EMPTY ,ItemTile.EMPTY,ItemTile.CAT  ,ItemTile.EMPTY},
                {ItemTile.PLANT,ItemTile.GAME ,ItemTile.BOOK ,ItemTile.CAT  ,ItemTile.EMPTY },
                {ItemTile.TROPHY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.BOOK }
        };
        assertFalse(sixOfTwoGoal.isVerified(bookshelf),"Only 5 has same tiles, should not be verified");
    }
}
