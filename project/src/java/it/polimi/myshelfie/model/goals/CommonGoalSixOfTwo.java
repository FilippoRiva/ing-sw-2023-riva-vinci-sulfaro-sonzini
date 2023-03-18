package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.ItemTile;

import java.util.Arrays;

public class CommonGoalSixOfTwo extends CommonGoal{
    /**
     *
     * @param bookshelf is a generic n x m matrix of ItemTile
     * @return true if exists 6 groups of 2 tiles with the same ItemTile value, false otherwise
     *
     */
    @Override
    public boolean isVerified(ItemTile[][] bookshelf) {
        int height = bookshelf.length;
        int width = bookshelf[0].length;
        boolean[][] take = new boolean[height][width];
        int[] counter = new int[ItemTile.values().length];

        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                if(!take[i][j] && bookshelf[i][j] != ItemTile.EMPTY){
                    if(i != height-1) { //down
                        if(bookshelf[i][j] == bookshelf[i+1][j]) {
                            map(bookshelf[i][j], counter);
                            take[i][j] = true;
                            take[i+1][j] = true;
                        }
                    }
                    if(j != width-1){ //right
                        if(!take[i][j] && bookshelf[i][j] == bookshelf[i][j+1]) {
                            map(bookshelf[i][j], counter);
                            take[i][j+1] = true;
                        }
                    }
                    if(Arrays.stream(counter).anyMatch(num -> num == 6)) return true;
                }
            }
        }
        return false;
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
