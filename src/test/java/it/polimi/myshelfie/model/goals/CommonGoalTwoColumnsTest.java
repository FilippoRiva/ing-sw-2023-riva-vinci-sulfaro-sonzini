package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.ItemTileType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CommonGoalTwoColumnsTest {


    @Test
    void isVerified() {
        CommonGoalTwoColumns twoColumnsGoal = new CommonGoalTwoColumns();
        assertNotNull(twoColumnsGoal.getDescription());
        ItemTile[][] bookshelf = {
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.BOOK, 0)},
                {new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.PLANT, 0)},
                {new ItemTile(ItemTileType.TROPHY, 0), new ItemTile(ItemTileType.TROPHY, 0), new ItemTile(ItemTileType.FRAME, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0)},
                {new ItemTile(ItemTileType.GAME, 0), new ItemTile(ItemTileType.GAME, 0), new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.PLANT, 0)},
                {new ItemTile(ItemTileType.FRAME, 0), new ItemTile(ItemTileType.FRAME, 0), new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.FRAME, 0)},
                {new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0)}
        };
        assertTrue(twoColumnsGoal.isVerified(bookshelf), "5x6 verifiable bookshelf should be verified");
        //cannot verify bookshelf with height>6
        bookshelf = new ItemTile[][]{
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.FRAME, 0), new ItemTile(ItemTileType.TROPHY, 0)},
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.TROPHY, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.FRAME, 0), new ItemTile(ItemTileType.TROPHY, 0)},
                {new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.TROPHY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.BOOK, 0)},
                {new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.FRAME, 0), new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.PLANT, 0)},
                {new ItemTile(ItemTileType.FRAME, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.TROPHY, 0)},
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.TROPHY, 0), new ItemTile(ItemTileType.GAME, 0)}
        };
        assertFalse(twoColumnsGoal.isVerified(bookshelf), "columns with duplicated tiles should not be verified");
        bookshelf = new ItemTile[][]{
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.PLANT, 0)},
                {new ItemTile(ItemTileType.TROPHY, 0), new ItemTile(ItemTileType.TROPHY, 0), new ItemTile(ItemTileType.TROPHY, 0), new ItemTile(ItemTileType.TROPHY, 0), new ItemTile(ItemTileType.TROPHY, 0)},
                {new ItemTile(ItemTileType.FRAME, 0), new ItemTile(ItemTileType.FRAME, 0), new ItemTile(ItemTileType.FRAME, 0), new ItemTile(ItemTileType.FRAME, 0), new ItemTile(ItemTileType.FRAME, 0)},
                {new ItemTile(ItemTileType.GAME, 0), new ItemTile(ItemTileType.GAME, 0), new ItemTile(ItemTileType.GAME, 0), new ItemTile(ItemTileType.GAME, 0), new ItemTile(ItemTileType.GAME, 0)}
        };
        assertFalse(twoColumnsGoal.isVerified(bookshelf), "columns with empty element should not be verified");
        bookshelf = new ItemTile[][]{
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0)},
                {new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.BOOK, 0)},
                {new ItemTile(ItemTileType.TROPHY, 0), new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.PLANT, 0)},
                {new ItemTile(ItemTileType.GAME, 0), new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.TROPHY, 0), new ItemTile(ItemTileType.GAME, 0), new ItemTile(ItemTileType.FRAME, 0)},
                {new ItemTile(ItemTileType.FRAME, 0), new ItemTile(ItemTileType.GAME, 0), new ItemTile(ItemTileType.TROPHY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.PLANT, 0)},
                {new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.GAME, 0), new ItemTile(ItemTileType.FRAME, 0), new ItemTile(ItemTileType.TROPHY, 0), new ItemTile(ItemTileType.PLANT, 0)}
        };
        assertFalse(twoColumnsGoal.isVerified(bookshelf), "One or less right columns should not be verified");
    }
}

