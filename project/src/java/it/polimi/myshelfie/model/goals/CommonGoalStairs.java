package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.ItemTile;

public class CommonGoalStairs extends CommonGoal{
    int height;
    int width;
    /**
     *
     * @param bookshelf is a generic n x m matrix of ItemTile
     * @return true if I have 5 columns of height growing of decreasing, false otherwise
     *
     */
    @Override
    public boolean isVerified(ItemTile[][] bookshelf) {
        height = bookshelf.length;
        width = bookshelf[0].length;

        for(int i=height-1; i>=width-2; i--){
            if(bookshelf[i][0] == ItemTile.EMPTY){
                if(checkRight(bookshelf, i)) return true;
                break;
            }
        }
        for(int i=height-1; i>=width-2; i--){
            if(bookshelf[i][width-1] == ItemTile.EMPTY){
                if(checkLeft(bookshelf, i, width-1)) return true;
                break;
            }
        }
        return false;
    }

    private boolean checkRight(ItemTile[][] bookshelf, int i){
        for(int k=1; k<width; k++) {
            if (bookshelf[i-k+1][k] == ItemTile.EMPTY) return false;
            if ((i-k)>=0 && bookshelf[i-k][k] != ItemTile.EMPTY) return false;
        }
        return true;
    }
    private boolean checkLeft(ItemTile[][] bookshelf, int i, int j){
        for(int k=1; k<width; k++) {
            if (bookshelf[i-k+1][j-k] == ItemTile.EMPTY) return false;
            if ((i-k)>=0 && bookshelf[i-k][j-k] != ItemTile.EMPTY) return false;
        }
        return true;
    }
}
