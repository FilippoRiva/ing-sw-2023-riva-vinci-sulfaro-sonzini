package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.ItemTileType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CommonGoalThreeColumnsTest {


    @Test
    void isVerified() {
        CommonGoalThreeColumns threeColumnsGoal = new CommonGoalThreeColumns();
        assertNotNull(threeColumnsGoal.getDescription());
        ItemTile[][] bookshelf = {
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0)}
        };
        assertTrue(threeColumnsGoal.isVerified(bookshelf), "5x6 verifiable bookshelf should be verified");
        bookshelf = new ItemTile[][]{
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0)},
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.FRAME, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0)},
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.PLANT, 0)},
                {new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.PLANT, 0)},
                {new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.PLANT, 0)},
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.FRAME, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0)},
                {new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0)},
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.PLANT, 0)},
                {new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.PLANT, 0)},
                {new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.PLANT, 0)}
        };
        assertTrue(threeColumnsGoal.isVerified(bookshelf), "6x10 verifiable bookshelf should be verified");
        bookshelf = new ItemTile[][]{
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.FRAME, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.FRAME, 0), new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.FRAME, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0)}
        };
        assertFalse(threeColumnsGoal.isVerified(bookshelf), "columns with more than three different elements should not be verified");
        bookshelf = new ItemTile[][]{
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0)},
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.FRAME, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0)}
        };
        assertFalse(threeColumnsGoal.isVerified(bookshelf), "columns with empty elements should not be verified");
        bookshelf = new ItemTile[][]{
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.FRAME, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)}
        };
        assertFalse(threeColumnsGoal.isVerified(bookshelf), "two or less right columns should not be verified");
    }
}
