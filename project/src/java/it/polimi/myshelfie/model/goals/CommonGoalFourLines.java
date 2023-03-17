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
        int type_count, row_count=0;
        boolean found=false;
        for(int i = 0; i<=width; i++) {
            type_count = 0;
            if (bookshelf[i][0] != ItemTile.EMPTY) {
                for (int j = height; j >=0; j--) {
                    found = false;
                    for (int k = j; k > j; k--) {
                        if (bookshelf[i][j] == bookshelf[i][k])
                            found = true;
                    }
                    if (!found)
                        type_count++;
                }
                if (type_count < 4)
                    row_count++;
            }
        }
        if(row_count>=2)
            return true;
        else
            return false;
    }}
