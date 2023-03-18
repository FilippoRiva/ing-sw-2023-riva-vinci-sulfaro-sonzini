package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.ItemTile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommonGoalThreeColumnsTest {


    @Test
    void isVerified(){
        CommonGoalThreeColumns threeColumnsGoal = new CommonGoalThreeColumns();
        ItemTile [][] bookshelf = {
                {ItemTile.CAT  ,ItemTile.EMPTY,ItemTile.CAT,ItemTile.CAT,ItemTile.EMPTY  },
                {ItemTile.CAT,ItemTile.EMPTY,ItemTile.BOOK,ItemTile.CAT,ItemTile.EMPTY},
                {ItemTile.BOOK,ItemTile.EMPTY,ItemTile.CAT,ItemTile.CAT,ItemTile.EMPTY},
                {ItemTile.BOOK,ItemTile.EMPTY,ItemTile.PLANT,ItemTile.CAT,ItemTile.EMPTY},
                {ItemTile.PLANT,ItemTile.EMPTY,ItemTile.PLANT,ItemTile.CAT,ItemTile.EMPTY},
                {ItemTile.CAT  ,ItemTile.EMPTY,ItemTile.PLANT,ItemTile.CAT,ItemTile.EMPTY  }
        };
        assertTrue(threeColumnsGoal.isVerified(bookshelf),"5x6 verifiable bookshelf should be verified");
        bookshelf = new ItemTile[][]{
                {ItemTile.CAT,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.BOOK, ItemTile.EMPTY, ItemTile.CAT},
                {ItemTile.CAT,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.FRAME, ItemTile.EMPTY, ItemTile.CAT},
                {ItemTile.CAT,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.CAT, ItemTile.EMPTY, ItemTile.PLANT},
                {ItemTile.PLANT,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.BOOK, ItemTile.EMPTY, ItemTile.PLANT},
                {ItemTile.BOOK,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.CAT, ItemTile.EMPTY, ItemTile.PLANT},
                {ItemTile.CAT,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.FRAME, ItemTile.EMPTY, ItemTile.CAT},
                {ItemTile.BOOK,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.CAT, ItemTile.EMPTY, ItemTile.CAT},
                {ItemTile.CAT,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.BOOK, ItemTile.EMPTY, ItemTile.PLANT},
                {ItemTile.PLANT,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.CAT, ItemTile.EMPTY, ItemTile.PLANT},
                {ItemTile.PLANT,ItemTile.EMPTY, ItemTile.EMPTY, ItemTile.BOOK, ItemTile.EMPTY, ItemTile.PLANT}
        };
        assertTrue(threeColumnsGoal.isVerified(bookshelf),"6x10 verifiable bookshelf should be verified");
        bookshelf = new ItemTile[][]{
                {ItemTile.CAT  ,ItemTile.EMPTY,ItemTile.CAT,ItemTile.CAT,ItemTile.EMPTY  },
                {ItemTile.CAT,ItemTile.EMPTY,ItemTile.BOOK,ItemTile.FRAME,ItemTile.EMPTY},
                {ItemTile.PLANT,ItemTile.EMPTY,ItemTile.CAT,ItemTile.BOOK,ItemTile.EMPTY},
                {ItemTile.BOOK,ItemTile.EMPTY,ItemTile.FRAME,ItemTile.PLANT,ItemTile.EMPTY},
                {ItemTile.FRAME,ItemTile.EMPTY,ItemTile.PLANT,ItemTile.CAT,ItemTile.EMPTY},
                {ItemTile.CAT  ,ItemTile.EMPTY,ItemTile.PLANT,ItemTile.CAT,ItemTile.EMPTY  }
        };
        assertFalse(threeColumnsGoal.isVerified(bookshelf),"columns with more than three different elements should not be verified");
        bookshelf = new ItemTile[][]{
                {ItemTile.CAT  ,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT,ItemTile.CAT  },
                {ItemTile.CAT,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT,ItemTile.EMPTY},
                {ItemTile.PLANT,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT,ItemTile.EMPTY},
                {ItemTile.CAT,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT,ItemTile.EMPTY},
                {ItemTile.FRAME,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT,ItemTile.EMPTY},
                {ItemTile.CAT  ,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT,ItemTile.CAT  }
        };
        assertFalse(threeColumnsGoal.isVerified(bookshelf), "columns with empty elements should not be verified");
        bookshelf = new ItemTile[][]{
                {ItemTile.CAT  ,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY  },
                {ItemTile.CAT,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.PLANT,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.CAT,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.FRAME,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.CAT  ,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY  }
        };
        assertFalse(threeColumnsGoal.isVerified(bookshelf), "two or less right columns should not be verified");
    }
}
