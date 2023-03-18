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
        int height = bookshelf.length-1;
        int width = bookshelf[0].length-1;
        int cont = 0;
        boolean[][] take = new boolean[height][width];
//        for (boolean[] row: take)
//            Arrays.fill(row, false);

        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                if(!take[i][j] && bookshelf[i][j] != ItemTile.EMPTY){
                    if(i != height-1) { //down
                        if(bookshelf[i][j] == bookshelf[i+1][j]) {
                            cont++;
                            //take[i][j] = true;
                            take[i+1][j] = true;
                        }
                    }
                    if(j != width-1){ //right
                        if(bookshelf[i][j] == bookshelf[i][j+1]) {
                            cont++;
                            //take[i][j] = true;
                            take[i][j+1] = true;
                        }
                    }
                    if(cont == 6) return true;
                }
            }
        }
        return false;
    }
}
