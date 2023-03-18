package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.ItemTile;

import java.util.Arrays;

public class CommonGoalTwoSquares extends CommonGoal{
    /**
     *
     * @param bookshelf is a generic n x m matrix of ItemTile
     * @return true if exists 2 squares (2x2) with all ItemTiles equals, false otherwise
     *
     */
    @Override
    public boolean isVerified(ItemTile[][] bookshelf) {
        int height = bookshelf.length;
        int width = bookshelf[0].length;
        boolean[][] take = new boolean[height][width];
        int[] counter = new int[ItemTile.values().length];

        for(int i = 0; i < height-1; i++) {
            for(int j = 0; j < width-1; j++) {
                if(!take[i][j] && bookshelf[i][j] != ItemTile.EMPTY){
                    if(check(bookshelf, i, j)) {
                        map(bookshelf[i][j], counter);
                        take[i+1][j]=true;
                        take[i][j+1]=true;
                        take[i+1][j+1]=true;
                    }
                }
                if(Arrays.stream(counter).anyMatch(num -> num == 2)) return true;
            }
        }
        return false;
    }

    private boolean check (ItemTile[][] bookshelf, int i, int j){
        if(bookshelf[i][j]!=bookshelf[i+1][j+1]) return false;
        if(bookshelf[i][j]!=bookshelf[i][j+1]) return false;
        if(bookshelf[i][j]!=bookshelf[i+1][j]) return false;
        return true;
    }

    private void map(ItemTile tile, int[] counter){
        switch (tile){
            case CAT-> counter[0]++;
            case BOOK -> counter[1]++;
            case GAME -> counter[2]++;
            case FRAME -> counter[3]++;
            case TROPHY -> counter[4]++;
            case PLANT -> counter[5]++;
        }
    }
}
