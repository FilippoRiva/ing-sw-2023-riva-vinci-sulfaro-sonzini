package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.ItemTileType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommonGoalFourLinesTest {


    @Test
    void isVerified(){
        CommonGoalFourLines fourLinesGoal = new CommonGoalFourLines();
        ItemTile[][] bookshelf = {
                {new ItemTile(ItemTileType.CAT,0)  , new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.BOOK,0)  },
                {new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.BOOK,0), new ItemTile(ItemTileType.PLANT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.PLANT,0)},
                {new ItemTile(ItemTileType.BOOK,0), new ItemTile(ItemTileType.PLANT,0), new ItemTile(ItemTileType.FRAME,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0)},
                {new ItemTile(ItemTileType.BOOK,0), new ItemTile(ItemTileType.PLANT,0), new ItemTile(ItemTileType.PLANT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.PLANT,0)},
                {new ItemTile(ItemTileType.PLANT,0), new ItemTile(ItemTileType.BOOK,0), new ItemTile(ItemTileType.PLANT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.FRAME,0)},
                {new ItemTile(ItemTileType.CAT,0)  , new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0)}
        };
        assertTrue(fourLinesGoal.isVerified(bookshelf),"5x6 verifiable bookshelf should be verified");
        bookshelf = new ItemTile[][]{
                {new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.BOOK,0), new ItemTile(ItemTileType.BOOK,0), new ItemTile(ItemTileType.PLANT,0), new ItemTile(ItemTileType.CAT,0)}, //OK
                {new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.BOOK,0), new ItemTile(ItemTileType.PLANT,0), new ItemTile(ItemTileType.FRAME,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0)},
                {new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.PLANT,0), new ItemTile(ItemTileType.PLANT,0)}, //OK
                {new ItemTile(ItemTileType.PLANT,0), new ItemTile(ItemTileType.PLANT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.BOOK,0), new ItemTile(ItemTileType.FRAME,0), new ItemTile(ItemTileType.FRAME,0)},
                {new ItemTile(ItemTileType.BOOK,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.BOOK,0), new ItemTile(ItemTileType.PLANT,0)}, //OK
                {new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.BOOK,0), new ItemTile(ItemTileType.FRAME,0), new ItemTile(ItemTileType.PLANT,0), new ItemTile(ItemTileType.CAT,0)},
                {new ItemTile(ItemTileType.BOOK,0), new ItemTile(ItemTileType.BOOK,0), new ItemTile(ItemTileType.BOOK,0), new ItemTile(ItemTileType.BOOK,0), new ItemTile(ItemTileType.BOOK,0), new ItemTile(ItemTileType.BOOK,0)}, //OK
                {new ItemTile(ItemTileType.BOOK,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.FRAME,0), new ItemTile(ItemTileType.PLANT,0), new ItemTile(ItemTileType.PLANT,0)},
                {new ItemTile(ItemTileType.BOOK,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.FRAME,0), new ItemTile(ItemTileType.PLANT,0), new ItemTile(ItemTileType.PLANT,0)},
                {new ItemTile(ItemTileType.BOOK,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.FRAME,0), new ItemTile(ItemTileType.PLANT,0), new ItemTile(ItemTileType.PLANT,0)}
        };
        assertTrue(fourLinesGoal.isVerified(bookshelf),"6x10 verifiable bookshelf should be verified");
        bookshelf = new ItemTile[][]{
                {new ItemTile(ItemTileType.CAT,0)  , new ItemTile(ItemTileType.PLANT,0), new ItemTile(ItemTileType.BOOK,0), new ItemTile(ItemTileType.FRAME,0), new ItemTile(ItemTileType.TROPHY,0)  },
                {new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.BOOK,0), new ItemTile(ItemTileType.FRAME,0), new ItemTile(ItemTileType.TROPHY,0)},
                {new ItemTile(ItemTileType.PLANT,0), new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.BOOK,0), new ItemTile(ItemTileType.BOOK,0)},
                {new ItemTile(ItemTileType.BOOK,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.FRAME,0), new ItemTile(ItemTileType.PLANT,0), new ItemTile(ItemTileType.PLANT,0)},
                {new ItemTile(ItemTileType.FRAME,0), new ItemTile(ItemTileType.BOOK,0), new ItemTile(ItemTileType.PLANT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.TROPHY,0)},
                {new ItemTile(ItemTileType.CAT,0)  , new ItemTile(ItemTileType.BOOK,0), new ItemTile(ItemTileType.PLANT,0), new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.GAME,0)  }
        };
        assertFalse(fourLinesGoal.isVerified(bookshelf),"rows with more than three different elements should not be verified");
        bookshelf = new ItemTile[][]{
                {new ItemTile(ItemTileType.CAT,0)  , new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0)  },
                {new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0)},             //OK
                {new ItemTile(ItemTileType.PLANT,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.EMPTY,0)},
                {new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0)},       //OK
                {new ItemTile(ItemTileType.FRAME,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.EMPTY,0)},
                {new ItemTile(ItemTileType.CAT,0)  , new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.GAME,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0)  }         //OK
        };
        assertFalse(fourLinesGoal.isVerified(bookshelf), "rows with empty element should not be verified");
        bookshelf = new ItemTile[][]{
                {new ItemTile(ItemTileType.CAT,0)  , new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.CAT,0)  },         //OK
                {new ItemTile(ItemTileType.BOOK,0), new ItemTile(ItemTileType.BOOK,0), new ItemTile(ItemTileType.BOOK,0), new ItemTile(ItemTileType.BOOK,0), new ItemTile(ItemTileType.BOOK,0)},        //OK
                {new ItemTile(ItemTileType.PLANT,0), new ItemTile(ItemTileType.PLANT,0), new ItemTile(ItemTileType.PLANT,0), new ItemTile(ItemTileType.PLANT,0), new ItemTile(ItemTileType.PLANT,0)},   //OK
                {new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.PLANT,0), new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.GAME,0), new ItemTile(ItemTileType.FRAME,0)},
                {new ItemTile(ItemTileType.FRAME,0), new ItemTile(ItemTileType.GAME,0), new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.PLANT,0)},
                {new ItemTile(ItemTileType.CAT,0)  , new ItemTile(ItemTileType.GAME,0), new ItemTile(ItemTileType.FRAME,0), new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.PLANT,0)  }
        };
        assertFalse(fourLinesGoal.isVerified(bookshelf), "Three or less right rows should not be verified");
    }
}

