package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.ItemTileType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CommonGoalTwoLinesTest {


    @Test
    void isVerified() {
        CommonGoalTwoLines twoLinesGoal = new CommonGoalTwoLines();
        assertNotNull(twoLinesGoal.getDescription());
        ItemTile[][] bookshelf = {
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.FRAME, 0), new ItemTile(ItemTileType.GAME, 0)},
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.FRAME, 0), new ItemTile(ItemTileType.GAME, 0)},
                {new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.FRAME, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0)},
                {new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.PLANT, 0)},
                {new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.FRAME, 0)},
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0)}
        };
        assertTrue(twoLinesGoal.isVerified(bookshelf), "5x6 verifiable bookshelf should be verified");
        //cannot verify bookshelf with width>6
        bookshelf = new ItemTile[][]{
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.FRAME, 0), new ItemTile(ItemTileType.GAME, 0)},
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.FRAME, 0), new ItemTile(ItemTileType.GAME, 0)},
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.PLANT, 0)},
                {new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.FRAME, 0), new ItemTile(ItemTileType.FRAME, 0)},
                {new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.PLANT, 0)},
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.FRAME, 0), new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.CAT, 0)},
                {new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.BOOK, 0)},
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.FRAME, 0), new ItemTile(ItemTileType.GAME, 0)},
                {new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.FRAME, 0), new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.PLANT, 0)},
                {new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.FRAME, 0), new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.PLANT, 0)}
        };
        assertTrue(twoLinesGoal.isVerified(bookshelf), "6x10 verifiable bookshelf should be verified");
        bookshelf = new ItemTile[][]{
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.FRAME, 0), new ItemTile(ItemTileType.TROPHY, 0)},
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.TROPHY, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.FRAME, 0), new ItemTile(ItemTileType.TROPHY, 0)},
                {new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.TROPHY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.BOOK, 0)},
                {new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.FRAME, 0), new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.PLANT, 0)},
                {new ItemTile(ItemTileType.FRAME, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.TROPHY, 0)},
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.TROPHY, 0), new ItemTile(ItemTileType.TROPHY, 0)}
        };
        assertFalse(twoLinesGoal.isVerified(bookshelf), "rows with duplicated elements should not be verified");
        bookshelf = new ItemTile[][]{
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.FRAME, 0)},
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.FRAME, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.GAME, 0)},
                {new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.TROPHY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.GAME, 0)},
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.TROPHY, 0), new ItemTile(ItemTileType.FRAME, 0), new ItemTile(ItemTileType.BOOK, 0)},
                {new ItemTile(ItemTileType.FRAME, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.GAME, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.TROPHY, 0)},
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.GAME, 0), new ItemTile(ItemTileType.FRAME, 0), new ItemTile(ItemTileType.TROPHY, 0)}
        };
        assertFalse(twoLinesGoal.isVerified(bookshelf), "rows with empty element should not be verified");
        bookshelf = new ItemTile[][]{
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.FRAME, 0), new ItemTile(ItemTileType.GAME, 0)},
                {new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.BOOK, 0)},
                {new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.PLANT, 0)},
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.TROPHY, 0), new ItemTile(ItemTileType.GAME, 0), new ItemTile(ItemTileType.PLANT, 0)},
                {new ItemTile(ItemTileType.FRAME, 0), new ItemTile(ItemTileType.GAME, 0), new ItemTile(ItemTileType.TROPHY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.FRAME, 0)},
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.GAME, 0), new ItemTile(ItemTileType.FRAME, 0), new ItemTile(ItemTileType.TROPHY, 0), new ItemTile(ItemTileType.CAT, 0)}
        };
        assertFalse(twoLinesGoal.isVerified(bookshelf), "one or less right rows should not be verified");
    }
}
