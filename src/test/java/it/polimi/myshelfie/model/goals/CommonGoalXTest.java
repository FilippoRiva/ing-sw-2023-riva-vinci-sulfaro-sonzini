package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.ItemTileType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalXTest {
    @Test
    void isVerified() {
        CommonGoalX xGoal = new CommonGoalX();
        assertNotNull(xGoal.getDescription());

        ItemTile[][] bookshelf = {
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)}
        };
        assertTrue(xGoal.isVerified(bookshelf), "top left corner should be verified");

        bookshelf = new ItemTile[][]{
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)}
        };
        assertTrue(xGoal.isVerified(bookshelf), "left border should be verified");

        bookshelf = new ItemTile[][]{
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)}
        };
        assertTrue(xGoal.isVerified(bookshelf), "bottom left corner should be verified");

        bookshelf = new ItemTile[][]{
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0)}
        };
        assertTrue(xGoal.isVerified(bookshelf), "bottom border should be verified");

        bookshelf = new ItemTile[][]{
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)}
        };
        assertTrue(xGoal.isVerified(bookshelf), "top border should be verified");

        bookshelf = new ItemTile[][]{
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0)}
        };
        assertTrue(xGoal.isVerified(bookshelf), "bottom right corner should be verified");

        bookshelf = new ItemTile[][]{
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)}
        };
        assertTrue(xGoal.isVerified(bookshelf), "top right corner should be verified");

        bookshelf = new ItemTile[][]{
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)}
        };
        assertTrue(xGoal.isVerified(bookshelf), "right border should be verified");

        bookshelf = new ItemTile[][]{
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)}
        };
        assertTrue(xGoal.isVerified(bookshelf), "center should be verified");

        bookshelf = new ItemTile[][]{
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)}
        };
        assertFalse(xGoal.isVerified(bookshelf), "wrong bottom left type should not be verified");

        bookshelf = new ItemTile[][]{
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)}
        };
        assertFalse(xGoal.isVerified(bookshelf), "wrong top left type should not be verified");

        bookshelf = new ItemTile[][]{
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)}
        };
        assertFalse(xGoal.isVerified(bookshelf), "wrong center type should not be verified");

        bookshelf = new ItemTile[][]{
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)}
        };
        assertFalse(xGoal.isVerified(bookshelf), "wrong top right type should not be verified");

        bookshelf = new ItemTile[][]{
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                {new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)}
        };
        assertFalse(xGoal.isVerified(bookshelf), "wrong bottom right type should not be verified");

        bookshelf = new ItemTile[][]{
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0)},
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0)},
                {new ItemTile(ItemTileType.TROPHY, 0), new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0)},
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0)},
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.GAME, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0)},
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0)}
        };
        assertTrue(xGoal.isVerified(bookshelf), "5x6 verifiable bookshelf should be verified");

        bookshelf = new ItemTile[][]{
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0)},
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0)},
                {new ItemTile(ItemTileType.TROPHY, 0), new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0)},
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0)},
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.GAME, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0)},
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0)}
        };
        assertTrue(xGoal.isVerified(bookshelf), "5x6 verifiable bookshelf should be verified");

        bookshelf = new ItemTile[][]{
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0)},
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0)},
                {new ItemTile(ItemTileType.FRAME, 0), new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.FRAME, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0)},
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.FRAME, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0)},
                {new ItemTile(ItemTileType.FRAME, 0), new ItemTile(ItemTileType.GAME, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0)},
                {new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.CAT, 0)}
        };
        assertFalse(xGoal.isVerified(bookshelf), "Only 4 of 5, should not be verified");
    }
}
