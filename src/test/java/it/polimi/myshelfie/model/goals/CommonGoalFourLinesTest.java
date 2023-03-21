package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.ItemTile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommonGoalFourLinesTest {


    @Test
    void isVerified(){
        CommonGoalFourLines fourLinesGoal = new CommonGoalFourLines();
        ItemTile[][] bookshelf = {
                {ItemTile.CAT  ,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.BOOK  },
                {ItemTile.CAT,ItemTile.BOOK,ItemTile.PLANT,ItemTile.CAT,ItemTile.PLANT},
                {ItemTile.BOOK,ItemTile.PLANT,ItemTile.FRAME,ItemTile.CAT,ItemTile.CAT},
                {ItemTile.BOOK,ItemTile.PLANT,ItemTile.PLANT,ItemTile.CAT,ItemTile.PLANT},
                {ItemTile.PLANT,ItemTile.BOOK,ItemTile.PLANT,ItemTile.CAT,ItemTile.FRAME},
                {ItemTile.CAT  ,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT}
        };
        assertTrue(fourLinesGoal.isVerified(bookshelf),"5x6 verifiable bookshelf should be verified");
        bookshelf = new ItemTile[][]{
                {ItemTile.CAT,ItemTile.CAT, ItemTile.BOOK, ItemTile.BOOK, ItemTile.PLANT, ItemTile.CAT}, //OK
                {ItemTile.CAT,ItemTile.BOOK, ItemTile.PLANT, ItemTile.FRAME, ItemTile.CAT, ItemTile.CAT},
                {ItemTile.CAT,ItemTile.CAT, ItemTile.CAT, ItemTile.CAT, ItemTile.PLANT, ItemTile.PLANT}, //OK
                {ItemTile.PLANT,ItemTile.PLANT, ItemTile.CAT, ItemTile.BOOK, ItemTile.FRAME, ItemTile.FRAME},
                {ItemTile.BOOK,ItemTile.CAT, ItemTile.CAT, ItemTile.CAT, ItemTile.BOOK, ItemTile.PLANT}, //OK
                {ItemTile.CAT,ItemTile.CAT, ItemTile.BOOK, ItemTile.FRAME, ItemTile.PLANT, ItemTile.CAT},
                {ItemTile.BOOK,ItemTile.BOOK, ItemTile.BOOK, ItemTile.BOOK, ItemTile.BOOK, ItemTile.BOOK}, //OK
                {ItemTile.BOOK,ItemTile.CAT, ItemTile.CAT, ItemTile.FRAME, ItemTile.PLANT, ItemTile.PLANT},
                {ItemTile.BOOK,ItemTile.CAT, ItemTile.CAT, ItemTile.FRAME, ItemTile.PLANT, ItemTile.PLANT},
                {ItemTile.BOOK,ItemTile.CAT, ItemTile.CAT, ItemTile.FRAME, ItemTile.PLANT, ItemTile.PLANT}
        };
        assertTrue(fourLinesGoal.isVerified(bookshelf),"6x10 verifiable bookshelf should be verified");
        bookshelf = new ItemTile[][]{
                {ItemTile.CAT  ,ItemTile.PLANT,ItemTile.BOOK,ItemTile.FRAME,ItemTile.TROPHY  },
                {ItemTile.CAT,ItemTile.TROPHY,ItemTile.BOOK,ItemTile.FRAME,ItemTile.TROPHY},
                {ItemTile.PLANT,ItemTile.TROPHY,ItemTile.CAT,ItemTile.BOOK,ItemTile.BOOK},
                {ItemTile.BOOK,ItemTile.CAT,ItemTile.FRAME,ItemTile.PLANT,ItemTile.PLANT},
                {ItemTile.FRAME,ItemTile.BOOK,ItemTile.PLANT,ItemTile.CAT,ItemTile.TROPHY},
                {ItemTile.CAT  ,ItemTile.BOOK,ItemTile.PLANT,ItemTile.TROPHY,ItemTile.GAME  }
        };
        assertFalse(fourLinesGoal.isVerified(bookshelf),"rows with more than three different elements should not be verified");
        bookshelf = new ItemTile[][]{
                {ItemTile.CAT  ,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT,ItemTile.CAT  },
                {ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT},             //OK
                {ItemTile.PLANT,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT,ItemTile.EMPTY},
                {ItemTile.CAT,ItemTile.TROPHY,ItemTile.TROPHY,ItemTile.CAT,ItemTile.CAT},       //OK
                {ItemTile.FRAME,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT,ItemTile.EMPTY},
                {ItemTile.CAT  ,ItemTile.CAT,ItemTile.GAME,ItemTile.CAT,ItemTile.CAT  }         //OK
        };
        assertFalse(fourLinesGoal.isVerified(bookshelf), "rows with empty element should not be verified");
        bookshelf = new ItemTile[][]{
                {ItemTile.CAT  ,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT  },         //OK
                {ItemTile.BOOK,ItemTile.BOOK,ItemTile.BOOK,ItemTile.BOOK,ItemTile.BOOK},        //OK
                {ItemTile.PLANT,ItemTile.PLANT,ItemTile.PLANT,ItemTile.PLANT,ItemTile.PLANT},   //OK
                {ItemTile.CAT,ItemTile.PLANT,ItemTile.TROPHY,ItemTile.GAME,ItemTile.FRAME},
                {ItemTile.FRAME,ItemTile.GAME,ItemTile.TROPHY,ItemTile.CAT,ItemTile.PLANT},
                {ItemTile.CAT  ,ItemTile.GAME,ItemTile.FRAME,ItemTile.TROPHY,ItemTile.PLANT  }
        };
        assertFalse(fourLinesGoal.isVerified(bookshelf), "Three or less right rows should not be verified");
    }
}

