package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.ItemTile;

public class CommonGoalThreeColumns extends CommonGoal{
    /**
     *
     * @param bookshelf is a generic n x m matrix of ItemTile
     * @return true if exists 3 columns with at most 3 different type of ItemTile, false otherwise
     *
     */
    @Override
    public boolean isVerified(ItemTile[][] bookshelf) {
        int width = bookshelf[0].length-1;
        int col_count=0;

        for(int i = 0; i<=width; i++) {
            if (bookshelf[0][i] != ItemTile.EMPTY) {
                if (checkColumn(bookshelf, i, 3)) col_count++;
            }
        }
        return col_count >= 3;
    }

    private boolean checkColumn(ItemTile[][] bookshelf, int i, int maxNumber){
        boolean found=false;
        int height = bookshelf.length-1, type_count=0;
        for(int j=height; j>=0; j--){
            found =false;
            if(bookshelf[j][i] == ItemTile.EMPTY) return false;
            for(int k=height; k>j; k--) {
                if (bookshelf[j][i] == bookshelf[k][i])
                    found = true;
            }
            if(!found)
                type_count++;
        }
        return type_count <= maxNumber;
    }
}
