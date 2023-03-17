package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.ItemTile;

import java.util.Arrays;

public class CommonGoalTwoSquares extends CommonGoal{

    @Override
    public boolean isVerified(ItemTile[][] bookshelf) {
        int cont = 0;
        int height = bookshelf.length-1;
        int width = bookshelf[0].length-1;
        boolean[][] take = new boolean[height][width];
        for (boolean[] row: take)
            Arrays.fill(row, false);

        for(int i = 0; i < width-1; i++) {
            for(int j = 0; j < height-1; j++) {
                if(!take[i][j]){
                    if(check(bookshelf, i, j)) {
                        cont++;
                        //take[i][j] = true;
                        take[i+1][j]=true;
                        take[i][j+1]=true;
                        take[i+1][j+1]=true;
                    }
                }
                if(cont == 2) return true;
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
}
