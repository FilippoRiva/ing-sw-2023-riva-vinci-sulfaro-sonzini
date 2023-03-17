package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.ItemTile;

public class CommonGoalTwoLines extends CommonGoal{
    /**
     *
     * @param bookshelf is a generic n x m matrix of ItemTile
     * @return true if exists 2 rows with all ItemTiles different, false otherwise
     *
     */
    @Override
    public boolean isVerified(ItemTile[][] bookshelf) {
        int height = bookshelf.length-1;
        int width = bookshelf[0].length-1;
        int row_count=0;
        for(int i = 0; i<=height; i++) {
            if (bookshelf[i][0] != ItemTile.EMPTY) {
                if(checkRow(bookshelf[i], width)) row_count++;
            }
        }
        return row_count >= 2;
    }

    private boolean checkRow(ItemTile[] row, int width){
        for(int i=0; i<width; i++){
            for(int j=0; j<i; j++){
                if(row[i]==row[j]) return false;
            }
        }
        return true;
    }
}
