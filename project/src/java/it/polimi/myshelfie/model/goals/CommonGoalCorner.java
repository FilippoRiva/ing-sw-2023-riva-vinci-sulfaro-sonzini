package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.ItemTile;

public class CommonGoalCorner extends CommonGoal{
    /**
     *
     * @param bookshelf is a generic n x m matrix of ItemTile
     * @return true if every corner of the matrix has the same ItemTile value, false otherwise
     *
     */
    @Override
    public boolean isVerified(ItemTile[][] bookshelf) {
        int height = bookshelf.length-1;
        int width = bookshelf[0].length-1;
        if(bookshelf[0][0] == ItemTile.EMPTY) return false;
        if(bookshelf[0][0] != bookshelf[0][width]) return false;
        if(bookshelf[0][width] != bookshelf[height][width]) return false;
        return bookshelf[height][width] == bookshelf[height][0];
    }
}
