package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.ItemTile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommonGoalTwoLinesTest {



    @Test
    void isVerified(){
        CommonGoalTwoLines twoLinesGoal = new CommonGoalTwoLines();
        ItemTile[][] bookshelf = {
                {ItemTile.CAT  ,ItemTile.BOOK,ItemTile.PLANT,ItemTile.FRAME,ItemTile.GAME},
                {ItemTile.CAT  ,ItemTile.BOOK,ItemTile.PLANT,ItemTile.FRAME,ItemTile.GAME},
                {ItemTile.BOOK,ItemTile.PLANT,ItemTile.FRAME,ItemTile.CAT,ItemTile.CAT},
                {ItemTile.BOOK,ItemTile.PLANT,ItemTile.PLANT,ItemTile.CAT,ItemTile.PLANT},
                {ItemTile.PLANT,ItemTile.BOOK,ItemTile.PLANT,ItemTile.CAT,ItemTile.FRAME},
                {ItemTile.CAT  ,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT}
        };
        assertTrue(twoLinesGoal.isVerified(bookshelf),"5x6 verifiable bookshelf should be verified");
        //cannot verify bookshelf with width>6
        bookshelf = new ItemTile[][]{
                {ItemTile.CAT ,ItemTile.BOOK,ItemTile.PLANT,ItemTile.FRAME,ItemTile.GAME},
                {ItemTile.CAT, ItemTile.BOOK,ItemTile.PLANT,ItemTile.FRAME,ItemTile.GAME},
                {ItemTile.CAT, ItemTile.CAT, ItemTile.CAT, ItemTile.CAT, ItemTile.PLANT, ItemTile.PLANT},
                {ItemTile.PLANT,ItemTile.PLANT, ItemTile.CAT, ItemTile.BOOK, ItemTile.FRAME, ItemTile.FRAME},
                {ItemTile.BOOK,ItemTile.CAT, ItemTile.CAT, ItemTile.CAT, ItemTile.BOOK, ItemTile.PLANT},
                {ItemTile.CAT,ItemTile.CAT, ItemTile.BOOK, ItemTile.FRAME, ItemTile.PLANT, ItemTile.CAT},
                {ItemTile.BOOK,ItemTile.BOOK, ItemTile.BOOK, ItemTile.BOOK, ItemTile.BOOK, ItemTile.BOOK},
                {ItemTile.CAT  ,ItemTile.BOOK,ItemTile.PLANT,ItemTile.FRAME,ItemTile.GAME},
                {ItemTile.BOOK,ItemTile.CAT, ItemTile.CAT, ItemTile.FRAME, ItemTile.PLANT, ItemTile.PLANT},
                {ItemTile.BOOK,ItemTile.CAT, ItemTile.CAT, ItemTile.FRAME, ItemTile.PLANT, ItemTile.PLANT}
        };
        assertTrue(twoLinesGoal.isVerified(bookshelf),"6x10 verifiable bookshelf should be verified");
        bookshelf = new ItemTile[][]{
                {ItemTile.CAT  ,ItemTile.CAT,ItemTile.BOOK,ItemTile.FRAME,ItemTile.TROPHY  },
                {ItemTile.CAT,ItemTile.TROPHY,ItemTile.BOOK,ItemTile.FRAME,ItemTile.TROPHY},
                {ItemTile.PLANT,ItemTile.TROPHY,ItemTile.CAT,ItemTile.BOOK,ItemTile.BOOK},
                {ItemTile.BOOK,ItemTile.CAT,ItemTile.FRAME,ItemTile.PLANT,ItemTile.PLANT},
                {ItemTile.FRAME,ItemTile.BOOK,ItemTile.PLANT,ItemTile.PLANT,ItemTile.TROPHY},
                {ItemTile.CAT  ,ItemTile.BOOK,ItemTile.PLANT,ItemTile.TROPHY,ItemTile.TROPHY  }
        };
        assertFalse(twoLinesGoal.isVerified(bookshelf),"rows with duplicated elements should not be verified");
        bookshelf = new ItemTile[][]{
                {ItemTile.CAT  ,ItemTile.EMPTY,ItemTile.BOOK,ItemTile.PLANT,ItemTile.FRAME  },
                {ItemTile.CAT,ItemTile.EMPTY,ItemTile.FRAME,ItemTile.BOOK,ItemTile.GAME},
                {ItemTile.PLANT,ItemTile.EMPTY,ItemTile.TROPHY,ItemTile.CAT,ItemTile.GAME},
                {ItemTile.CAT,ItemTile.EMPTY,ItemTile.TROPHY,ItemTile.FRAME,ItemTile.BOOK},
                {ItemTile.FRAME,ItemTile.EMPTY,ItemTile.GAME,ItemTile.CAT,ItemTile.TROPHY},
                {ItemTile.CAT  ,ItemTile.EMPTY,ItemTile.GAME,ItemTile.FRAME,ItemTile.TROPHY  }
        };
        assertFalse(twoLinesGoal.isVerified(bookshelf), "rows with empty element should not be verified");
        bookshelf = new ItemTile[][]{
                {ItemTile.CAT  ,ItemTile.BOOK,ItemTile.PLANT,ItemTile.FRAME,ItemTile.GAME},
                {ItemTile.BOOK,ItemTile.BOOK,ItemTile.BOOK,ItemTile.BOOK,ItemTile.BOOK},
                {ItemTile.PLANT,ItemTile.PLANT,ItemTile.PLANT,ItemTile.PLANT,ItemTile.PLANT},
                {ItemTile.CAT,ItemTile.PLANT,ItemTile.TROPHY,ItemTile.GAME,ItemTile.PLANT},
                {ItemTile.FRAME,ItemTile.GAME,ItemTile.TROPHY,ItemTile.CAT,ItemTile.FRAME},
                {ItemTile.CAT  ,ItemTile.GAME,ItemTile.FRAME,ItemTile.TROPHY,ItemTile.CAT  }
        };
        assertFalse(twoLinesGoal.isVerified(bookshelf), "one or less right rows should not be verified");
    }
}
