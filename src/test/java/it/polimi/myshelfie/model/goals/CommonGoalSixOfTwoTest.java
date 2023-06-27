package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.ItemTileType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalSixOfTwoTest {
    @Test
    void isVerified() {
        CommonGoalSixOfTwo sixOfTwoGoal = new CommonGoalSixOfTwo();
        assertNotNull(sixOfTwoGoal.getDescription());

        ItemTile[][] bookshelf = {
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0)},
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0)},
                {new ItemTile(ItemTileType.TROPHY, 0), new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.GAME, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0)},
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0)},
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.GAME, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0)},
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0)}
        };
        assertTrue(sixOfTwoGoal.isVerified(bookshelf), "5x6 verifiable bookshelf should be verified");

        bookshelf = new ItemTile[][]{
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0)},
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.TROPHY, 0), new ItemTile(ItemTileType.TROPHY, 0)},
                {new ItemTile(ItemTileType.TROPHY, 0), new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.GAME, 0), new ItemTile(ItemTileType.FRAME, 0), new ItemTile(ItemTileType.FRAME, 0)},
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.BOOK, 0)},
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.GAME, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.PLANT, 0)},
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.GAME, 0), new ItemTile(ItemTileType.GAME, 0)}
        };
        assertTrue(sixOfTwoGoal.isVerified(bookshelf), "5x6 verifiable bookshelf should be verified");

        bookshelf = new ItemTile[][]{
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0)},
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0)},
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.GAME, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.GAME, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.TROPHY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.BOOK, 0)}
        };
        assertFalse(sixOfTwoGoal.isVerified(bookshelf), "Only 5 has same tiles, should not be verified");
    }
}
