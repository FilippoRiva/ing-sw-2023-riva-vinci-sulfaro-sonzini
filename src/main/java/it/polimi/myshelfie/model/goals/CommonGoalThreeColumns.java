package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.ItemTileType;


/**
 * Common goal: Three columns. Maximum three different types per column. Different columns can have different combinations.
 */
public class CommonGoalThreeColumns extends CommonGoal {
    /**
     * Checks the common goal.
     *
     * @param bookshelf is a generic n x m matrix of ItemTile
     * @return true if exists 3 columns with at most 3 different type of ItemTile, false otherwise
     */
    @Override
    public boolean isVerified(ItemTile[][] bookshelf) {
        int width = bookshelf[0].length - 1;
        int col_count = 0;

        for (int i = 0; i <= width; i++) {
            if (bookshelf[0][i].getType() != ItemTileType.EMPTY) {
                if (checkColumn(bookshelf, i)) col_count++;
            }
        }
        return col_count >= 3;
    }

    private boolean checkColumn(ItemTile[][] bookshelf, int i) {
        int maxNumber = 3;
        boolean found;
        int height = bookshelf.length - 1, type_count = 0;
        for (int j = height; j >= 0; j--) {
            found = false;
            if (bookshelf[j][i].getType() == ItemTileType.EMPTY) return false;
            for (int k = height; k > j; k--) {
                if (bookshelf[j][i].getType() == bookshelf[k][i].getType()) {
                    found = true;
                    break;
                }
            }
            if (!found)
                type_count++;
        }
        return type_count <= maxNumber;
    }

    /**
     * {@inheritDoc}
     *
     * @return the id
     */
    @Override
    public int getId() {
        return 4;
    }

    /**
     * {@inheritDoc}
     *
     * @return the description
     */
    @Override
    public String getDescription() {
        return "Three columns. Maximum three different types per column.\n" +
                "Different columns can have different combinations";
    }
}
