package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.ItemTile;

public class CommonGoalFourOfFour extends CommonGoal{
    @Override
    public boolean isVerified(ItemTile[][] bookshelf) {

        /*
         * ov ov v v v
         * ov ov v v v
         * ov ov v v v
         * o  o
         * o  o
         * o  o
         */

        int height = bookshelf.length-1;
        int width = bookshelf[0].length-1;
        int cont = 0;

        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                if(i < 3){
                    if(j<2){
                        //horizontal
                        if(check(bookshelf, i, j))
                            cont++;
                    }else{
                        //vertical
                        if(check(bookshelf, j, i))
                            cont++;
                    }
                }else if(j<2){
                    //horizontal
                    if(check(bookshelf, i, j))
                        cont++;
                }
            }
            if(cont == 4) return true;
        }
        return false;
    }

    private boolean check(ItemTile[][] bookshelf, int i, int j){
        for(int k = 0; k < 3; k++){
            if(bookshelf[i][j] != bookshelf[i][j+1]) return false;
            j++;
        }
        return true;
    }
}
