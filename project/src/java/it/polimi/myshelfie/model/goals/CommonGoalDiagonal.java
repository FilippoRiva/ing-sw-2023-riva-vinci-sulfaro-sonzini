package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.ItemTile;

public class CommonGoalDiagonal extends CommonGoal{
    private int height;
    private int width;
    /**
     *
     * @param bookshelf is a generic matrix[x][y] of ItemTile.
     * @return true if there's a diagonal of x elements, false otherwise.
     *          If the height of the matrix is lower than it's width returns false.
     */
    @Override
    public boolean isVerified(ItemTile[][] bookshelf) {
        height = bookshelf.length - 1;
        width = bookshelf[0].length - 1;
        for(int startingPosition = height-width; startingPosition >= 0 ; startingPosition --){
            if(checkDescendingDiagonal(startingPosition,bookshelf)) return true;
            if(checkAscendingDiagonal(startingPosition,bookshelf)) return true;
        }
        return false;
    }

    private boolean checkDescendingDiagonal(int startingPosition, ItemTile[][] bookshelf){
        ItemTile tileType = bookshelf[startingPosition][0];
        if (tileType == ItemTile.EMPTY) return false;
        for(int pos = 1; pos <= width; pos++){
            if(bookshelf[startingPosition+pos][pos] != tileType) return false;
        }
        return true;
    }

    private boolean checkAscendingDiagonal(int startingPosition, ItemTile[][] bookshelf){
        ItemTile tileType = bookshelf[startingPosition][width];
        if (tileType == ItemTile.EMPTY) return false;
        for(int pos = 1; pos <= width; pos++){
            if(bookshelf[startingPosition+pos][width-pos] != tileType) return false;
        }
        return true;
    }

}
