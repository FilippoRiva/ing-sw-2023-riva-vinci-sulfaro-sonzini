package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.ItemTileType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CommonGoalStairsTest {
    @Test
    void isVerified(){
        CommonGoalStairs stairGoal = new CommonGoalStairs();

        ItemTile[][] bookshelf = {
                {new ItemTile(ItemTileType.EMPTY,0)  , new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0)  },
                {new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.PLANT,0)},
                {new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.PLANT,0), new ItemTile(ItemTileType.BOOK,0)},
                {new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.GAME,0), new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.GAME,0)},
                {new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.BOOK,0), new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.BOOK,0), new ItemTile(ItemTileType.GAME,0)},
                {new ItemTile(ItemTileType.CAT,0)  , new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.GAME,0), new ItemTile(ItemTileType.CAT,0)  }
        };
        assertTrue(stairGoal.isVerified(bookshelf),"5x6 verifiable bookshelf should be verified");

        bookshelf = new ItemTile[][]{
                {new ItemTile(ItemTileType.EMPTY,0)  , new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0)  },
                {new ItemTile(ItemTileType.FRAME,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0)},
                {new ItemTile(ItemTileType.FRAME,0), new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0)},
                {new ItemTile(ItemTileType.FRAME,0), new ItemTile(ItemTileType.PLANT,0), new ItemTile(ItemTileType.PLANT,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0)},
                {new ItemTile(ItemTileType.FRAME,0), new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.GAME,0), new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.EMPTY,0)},
                {new ItemTile(ItemTileType.CAT,0)  , new ItemTile(ItemTileType.GAME,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.GAME,0), new ItemTile(ItemTileType.CAT,0)  }
        };
        assertTrue(stairGoal.isVerified(bookshelf),"5x6 verifiable bookshelf should be verified");

        bookshelf = new ItemTile[][]{
                {new ItemTile(ItemTileType.FRAME,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0)},
                {new ItemTile(ItemTileType.FRAME,0), new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0)},
                {new ItemTile(ItemTileType.FRAME,0), new ItemTile(ItemTileType.PLANT,0), new ItemTile(ItemTileType.PLANT,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0)},
                {new ItemTile(ItemTileType.FRAME,0), new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.GAME,0), new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.EMPTY,0)},
                {new ItemTile(ItemTileType.CAT,0)  , new ItemTile(ItemTileType.GAME,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.GAME,0), new ItemTile(ItemTileType.CAT,0)  },
                {new ItemTile(ItemTileType.FRAME,0)  , new ItemTile(ItemTileType.GAME,0), new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.GAME,0), new ItemTile(ItemTileType.CAT,0)  }
        };
        assertTrue(stairGoal.isVerified(bookshelf),"5x6 verifiable bookshelf should be verified");

        bookshelf = new ItemTile[][]{
                {new ItemTile(ItemTileType.EMPTY,0)  , new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0)  },
                {new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0)},
                {new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.PLANT,0), new ItemTile(ItemTileType.BOOK,0)},
                {new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.GAME,0), new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.GAME,0)},
                {new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.BOOK,0), new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.BOOK,0), new ItemTile(ItemTileType.GAME,0)},
                {new ItemTile(ItemTileType.CAT,0)  , new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.GAME,0), new ItemTile(ItemTileType.CAT,0)  }
        };
        assertFalse(stairGoal.isVerified(bookshelf),"Only 4 has right columns, should not be verified");

        bookshelf = new ItemTile[][]{
                {new ItemTile(ItemTileType.EMPTY,0)  , new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.PLANT,0)  },
                {new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.PLANT,0)},
                {new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.PLANT,0), new ItemTile(ItemTileType.BOOK,0)},
                {new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.GAME,0), new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.GAME,0)},
                {new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.BOOK,0), new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.BOOK,0), new ItemTile(ItemTileType.GAME,0)},
                {new ItemTile(ItemTileType.CAT,0)  , new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.GAME,0), new ItemTile(ItemTileType.CAT,0)  }
        };
        assertFalse(stairGoal.isVerified(bookshelf),"last column has one more, should not be verified");


    }
}
