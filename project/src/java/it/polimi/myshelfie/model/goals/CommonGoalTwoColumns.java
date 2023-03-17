package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.ItemTile;

public class CommonGoalTwoColumns extends CommonGoal{
    /**
     *
     * @param bookshelf is a generic n x m matrix of ItemTile
     * @return true if exists 2 columns with all ItemTiles different, false otherwise
     *
     */
    @Override
    public boolean isVerified(ItemTile[][] bookshelf) {
        int height = bookshelf.length-1;
        int width = bookshelf[0].length-1;
        int col_count=0;
        boolean found=false;
        for(int i = 0; i<=width; i++) {
            if (bookshelf[0][i] != ItemTile.EMPTY) {
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
                if (bookshelf[j][i] == bookshelf[k][i])
                    return false;
            }
        }
        return true;
    }
}
