package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.ItemTileType;

public class CommonGoalX extends CommonGoal{
    private final int id = 9;
    /**
     *
     * @param bookshelf is a generic n x m matrix of ItemTile
     * @return true if exists an X of 5 ItemTiles of same type, false otherwise
     *
     */
    @Override
    public boolean isVerified(ItemTile[][] bookshelf) {
        int height = bookshelf.length;
        int width = bookshelf[0].length;

        for(int i = 1; i < height-1; i++) {
            for(int j = 1; j < width-1; j++) {
                if(bookshelf[i][j].getType() != ItemTileType.EMPTY && check(bookshelf, i, j)) return true;
            }
        }
        return false;
    }

    private boolean check(ItemTile[][] bookshelf, int i, int j){
        if(bookshelf[i][j].getType()!=bookshelf[i+1][j+1].getType()) return false;
        if(bookshelf[i][j].getType()!=bookshelf[i-1][j+1].getType()) return false;
        if(bookshelf[i][j].getType()!=bookshelf[i+1][j-1].getType()) return false;
        if(bookshelf[i][j].getType()!=bookshelf[i-1][j-1].getType()) return false;
        return bookshelf[i][j].getType() == bookshelf[i - 1][j - 1].getType();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getDescription() {
        return "Five tiles of the same type forming an X";
    }
}
