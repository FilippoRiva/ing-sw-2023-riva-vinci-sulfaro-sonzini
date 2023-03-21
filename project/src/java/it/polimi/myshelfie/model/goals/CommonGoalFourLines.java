package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.ItemTile;

public class CommonGoalFourLines extends CommonGoal{
    /**
     *
     * @param bookshelf is a generic n x m matrix of ItemTile
     * @return true if exists 4 lines with at most 3 different type of ItemTile, false otherwise
     *
     */
    @Override
    public boolean isVerified(ItemTile[][] bookshelf) {
        int height = bookshelf.length-1;
        int width = bookshelf[0].length-1;
        int row_count=0;
        boolean found=false;
        for(int i = 0; i<=height; i++) {
            if (bookshelf[i][0] != ItemTile.EMPTY) {
                if(checkRow(bookshelf[i], 3, width)) row_count++;
            }
        }
        return row_count>=4;
    }
    private boolean checkRow(ItemTile[] row, int maxNumber, int width){
        boolean found;
        int typeCount=0;
        for(int i=0; i<=width; i++){
            found=false;
            if(row[i] == ItemTile.EMPTY) return false;
            for(int j=0; j<i; j++){
                if(row[i]==row[j])
                    found=true;
            }
            if(!found)
                typeCount++;

        }
        return typeCount <= maxNumber;
    }

}
