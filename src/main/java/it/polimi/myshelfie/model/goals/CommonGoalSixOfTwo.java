package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.ItemTileType;


/**
 * Common goal: Six groups each containing at least 2 tiles of the same type. The tiles of one group can be different from those of another group.
 */
public class CommonGoalSixOfTwo extends CommonGoal {
    /**
     * Checks common goal.
     *
     * @param bookshelf is a generic n x m matrix of ItemTile
     * @return true if exists 6 groups of 2 tiles with the same ItemTile value, false otherwise
     */
    @Override
    public boolean isVerified(ItemTile[][] bookshelf) {
        int height = bookshelf.length;
        int width = bookshelf[0].length;
        boolean[][] take = new boolean[height][width];
        int counter = 0;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (!take[i][j] && bookshelf[i][j].getType() != ItemTileType.EMPTY) {
                    if (i != height - 1) { //down
                        if (bookshelf[i][j].getType() == bookshelf[i + 1][j].getType()) {
                            counter++;
                            take[i][j] = true;
                            take[i + 1][j] = true;
                        }
                    }
                    if (j != width - 1) { //right
                        if (!take[i][j] && bookshelf[i][j].getType() == bookshelf[i][j + 1].getType()) {
                            counter++;
                            take[i][j + 1] = true;
                        }
                    }
                    if (counter == 6) return true;
                }
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     *
     * @return the id
     */
    @Override
    public int getId() {
        return 3;
    }

    /**
     * {@inheritDoc}
     *
     * @return the description
     */
    @Override
    public String getDescription() {
        return "Six groups each containing at least 2 tiles of the same type.\n" +
                "The tiles of one group can be different from those of another group";
    }
}
