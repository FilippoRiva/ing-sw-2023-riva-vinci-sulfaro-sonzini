package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.ItemTile;

public class CommonGoalThreeColumns extends CommonGoal{
    @Override
    public boolean isVerified(ItemTile[][] bookshelf) {
        int height = bookshelf.length-1;
        int width = bookshelf[0].length-1;
        int type_count, col_count=0;
        boolean found=false;
        for(int i = 0; i<=width; i++) {
            type_count = 0;
            if (bookshelf[0][i] != ItemTile.EMPTY) {
                for (int j = height; j >=0; j--) {
                    found = false;
                    for (int k = j; k > j; k--) {
                        if (bookshelf[j][i] == bookshelf[k][i])
                            found = true;
                    }
                    if (!found)
                        type_count++;
                }
                if (type_count < 4)
                    col_count++;
            }
        }
        if(col_count>=3)
            return true;
        else
            return false;
    }
}
