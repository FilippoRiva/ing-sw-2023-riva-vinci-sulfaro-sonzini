package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.ItemTile;

import java.util.Arrays;

public class CommonGoalFourOfFour extends CommonGoal{
    /**
     *
     * @param bookshelf is a generic n x m matrix of ItemTile
     * @return true if exists 4 groups of 4 tiles with the same ItemTile value, false otherwise
     *
     */
    @Override
    public boolean isVerified(ItemTile[][] bookshelf) {

        /*
         *  ov ov v v v
         *  ov ov v v v
         *  ov ov v v v
         *  o  o  - - -
         *  o  o  - - -
         *  o  o  - - -
         */

        int height = bookshelf.length;
        int width = bookshelf[0].length;
        boolean[][] take = new boolean[height][width];
        int[] counter = new int[ItemTile.values().length];

        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                if(!take[i][j] && bookshelf[i][j] != ItemTile.EMPTY) {
                    if (i < 3) {
                        if (j < 2) {
                            if (checkHorizontal(bookshelf, take, i, j)) {
                                map(bookshelf[i][j], counter);
                                take[i][j] = true;
                                take[i][j+1] = true;
                                take[i][j+2] = true;
                                take[i][j+3] = true;
                            }
                        }
                        if (!take[i][j] && checkVertical(bookshelf, take, i, j)) {
                            map(bookshelf[i][j], counter);
                            take[i][j] = true;
                            take[i+1][j] = true;
                            take[i+2][j] = true;
                            take[i+3][j] = true;
                        }
                    } else if (j < 2) {
                        if (checkHorizontal(bookshelf, take, i, j)) {
                            map(bookshelf[i][j], counter);
                            take[i][j+1] = true;
                            take[i][j+2] = true;
                            take[i][j+3] = true;
                        }
                    }
                    if (Arrays.stream(counter).anyMatch(num -> num == 4)) return true;
                }
            }
        }
        return false;
    }

    private boolean checkHorizontal(ItemTile[][] bookshelf, boolean[][] take, int i, int j){
        for(int k = 0; k < 3; k++){
            if(take[i][j] || bookshelf[i][j] != bookshelf[i][j+1]) return false;
            j++;
        }
        return true;
    }
    private boolean checkVertical(ItemTile[][] bookshelf, boolean[][] take, int i, int j){
        for(int k = 0; k < 3; k++){
            if(take[i][j] || bookshelf[i][j] != bookshelf[i+1][j]) return false;
            i++;
        }
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
