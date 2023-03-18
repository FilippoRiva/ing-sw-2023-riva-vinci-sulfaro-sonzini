package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.ItemTile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommonGoalTwoColumnsTest {



    @Test
    void isVerified(){
        CommonGoalTwoColumns twoColumnsGoal = new CommonGoalTwoColumns();
        ItemTile[][] bookshelf = {
                {ItemTile.CAT  ,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.BOOK  },
                {ItemTile.PLANT,ItemTile.PLANT,ItemTile.PLANT,ItemTile.CAT,ItemTile.PLANT},
                {ItemTile.TROPHY,ItemTile.TROPHY,ItemTile.FRAME,ItemTile.CAT,ItemTile.CAT},
                {ItemTile.GAME,ItemTile.GAME,ItemTile.PLANT,ItemTile.CAT,ItemTile.PLANT},
                {ItemTile.FRAME,ItemTile.FRAME,ItemTile.PLANT,ItemTile.CAT,ItemTile.FRAME},
                {ItemTile.BOOK  ,ItemTile.BOOK,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT}
        };
        assertTrue(twoColumnsGoal.isVerified(bookshelf),"5x6 verifiable bookshelf should be verified");
        //cannot verify bookshelf with height>6
        bookshelf = new ItemTile[][]{
                {ItemTile.CAT  ,ItemTile.PLANT,ItemTile.BOOK,ItemTile.FRAME,ItemTile.TROPHY  },
                {ItemTile.CAT,ItemTile.TROPHY,ItemTile.BOOK,ItemTile.FRAME,ItemTile.TROPHY},
                {ItemTile.PLANT,ItemTile.TROPHY,ItemTile.CAT,ItemTile.BOOK,ItemTile.BOOK},
                {ItemTile.BOOK,ItemTile.CAT,ItemTile.FRAME,ItemTile.PLANT,ItemTile.PLANT},
                {ItemTile.FRAME,ItemTile.BOOK,ItemTile.PLANT,ItemTile.CAT,ItemTile.TROPHY},
                {ItemTile.CAT  ,ItemTile.BOOK,ItemTile.PLANT,ItemTile.TROPHY,ItemTile.GAME  }
        };
        assertFalse(twoColumnsGoal.isVerified(bookshelf),"columns with duplicated tiles should not be verified");
        bookshelf = new ItemTile[][]{
                {ItemTile.CAT  ,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT  },
                {ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                {ItemTile.PLANT,ItemTile.PLANT,ItemTile.PLANT,ItemTile.PLANT,ItemTile.PLANT},
                {ItemTile.TROPHY,ItemTile.TROPHY,ItemTile.TROPHY,ItemTile.TROPHY,ItemTile.TROPHY},
                {ItemTile.FRAME,ItemTile.FRAME,ItemTile.FRAME,ItemTile.FRAME,ItemTile.FRAME},
                {ItemTile.GAME  ,ItemTile.GAME,ItemTile.GAME,ItemTile.GAME,ItemTile.GAME  }
        };
        assertFalse(twoColumnsGoal.isVerified(bookshelf), "columns with empty element should not be verified");
        bookshelf = new ItemTile[][]{
                {ItemTile.CAT  ,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT,ItemTile.CAT  },
                {ItemTile.PLANT,ItemTile.BOOK,ItemTile.BOOK,ItemTile.BOOK,ItemTile.BOOK},
                {ItemTile.TROPHY,ItemTile.PLANT,ItemTile.PLANT,ItemTile.PLANT,ItemTile.PLANT},
                {ItemTile.GAME,ItemTile.PLANT,ItemTile.TROPHY,ItemTile.GAME,ItemTile.FRAME},
                {ItemTile.FRAME,ItemTile.GAME,ItemTile.TROPHY,ItemTile.CAT,ItemTile.PLANT},
                {ItemTile.BOOK  ,ItemTile.GAME,ItemTile.FRAME,ItemTile.TROPHY,ItemTile.PLANT  }
        };
        assertFalse(twoColumnsGoal.isVerified(bookshelf), "One or less right columns should not be verified");
    }
}

