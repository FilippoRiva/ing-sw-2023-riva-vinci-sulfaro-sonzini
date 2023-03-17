package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.ItemTile;

import java.util.Arrays;

public class CommonGoalEightTiles extends CommonGoal{
    /**
     *
     * @param bookshelf is a generic n x m matrix of ItemTile
     * @return true if 8 elements has the same ItemTile value, false otherwise
     *
     */
    @Override
    public boolean isVerified(ItemTile[][] bookshelf) {
        int height = bookshelf.length-1;
        int width = bookshelf[0].length-1;
        int[] counter = new int[ItemTile.values().length];

        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                switch (bookshelf[i][j]){
                    case CAT-> counter[0]++;
                    case BOOK -> counter[1]++;
                    case GAME -> counter[2]++;
                    case FRAME -> counter[3]++;
                    case TROPHY -> counter[4]++;
                    case PLANT -> counter[5]++;
                }
            }
        }
        return Arrays.stream(counter).anyMatch(num -> num >= 8);
    }
}
