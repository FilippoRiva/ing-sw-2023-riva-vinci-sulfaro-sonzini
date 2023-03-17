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
        height = bookshelf.length-1;
        width = bookshelf[0].length-1;

        for(int j=height-1; j>=width-1; j--){
            if(bookshelf[0][j] == ItemTile.EMPTY){
                if(checkRight(bookshelf, j)) return true;
                break;
            }
        }
        for(int j=height-1; j>=width-1; j--){
            if(bookshelf[width-1][j] == ItemTile.EMPTY){
                if(checkLeft(bookshelf, width-1, j)) return true;
                break;
            }
        }
        return false;
    }

    private boolean checkRight(ItemTile[][] bookshelf, int j){
        for(int k=1; k<width-2; k++) {
            if (bookshelf[k][j-k] == ItemTile.EMPTY) return false;
            if (bookshelf[k-1][j-k] != ItemTile.EMPTY) return false;
        }
        return true;
    }
    private boolean checkLeft(ItemTile[][] bookshelf, int i, int j){
        for(int k=1; k<width-2; k++) {
            if (bookshelf[i-k][j-k] == ItemTile.EMPTY) return false;
            if (bookshelf[i-k-1][j-k] != ItemTile.EMPTY) return false;
        }
        return true;
    }
}
