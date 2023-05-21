package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.ItemTileType;

public class CommonGoalCorner extends CommonGoal{
    private final int id = 7;
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
        if(bookshelf[0][0].getType() == ItemTileType.EMPTY) return false;
        if(bookshelf[0][0].getType() != bookshelf[0][width].getType()) return false;
        if(bookshelf[0][width].getType() != bookshelf[height][width].getType()) return false;
        return bookshelf[height][width].getType() == bookshelf[height][0].getType();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getDescription() {
        return "Four tiles of the same type in the four corners";
    }
}
