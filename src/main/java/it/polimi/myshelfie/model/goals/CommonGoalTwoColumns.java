package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.ItemTileType;

public class CommonGoalTwoColumns extends CommonGoal{
    /**
     *
     * @param bookshelf is a generic n x m matrix of ItemTile
     * @return true if exists 2 columns with all ItemTiles different, false otherwise
     *
     */
    @Override
    public boolean isVerified(ItemTile[][] bookshelf) {
        int width = bookshelf[0].length-1;
        int col_count=0;
        for(int i = 0; i<=width; i++) {
            if (bookshelf[0][i].getType() != ItemTileType.EMPTY) {
                if(checkColumn(bookshelf, i))
                    col_count++;
            }
        }
        return col_count >= 2;
    }

    private boolean checkColumn(ItemTile[][] bookshelf, int i) {
        int height = bookshelf.length - 1;
        for (int j = height; j >= 0; j--) {
            for (int k = height; k > j; k--) {
                if (bookshelf[j][i].getType() == ItemTileType.EMPTY || bookshelf[j][i].getType() == bookshelf[k][i].getType())
                    return false;
            }
        }
        return true;
    }
}
