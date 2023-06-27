package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.ItemTileType;

public class CommonGoalFourOfFour extends CommonGoal{
    /**
     * Checks the common goal.
     * @param bookshelf is a generic n x m matrix of ItemTile
     * @return true if exists 4 groups of 4 tiles with the same ItemTile value, false otherwise
     *
     */
    @Override
    public boolean isVerified(ItemTile[][] bookshelf) {
        int height = bookshelf.length;
        int width = bookshelf[0].length;
        boolean[][] take = new boolean[height][width];
        int counter = 0;
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                if(!take[i][j] && bookshelf[i][j].getType() != ItemTileType.EMPTY) {
                    if (i < 3) {
                        if (j < 2) {
                            if (checkHorizontal(bookshelf, take, i, j)) {
                                counter++;
                                take[i][j] = true;
                                take[i][j+1] = true;
                                take[i][j+2] = true;
                                take[i][j+3] = true;
                            }
                        }
                        if (!take[i][j] && checkVertical(bookshelf, take, i, j)) {
                            counter++;
                            take[i][j] = true;
                            take[i+1][j] = true;
                            take[i+2][j] = true;
                            take[i+3][j] = true;
                        }
                    } else if (j < 2) {
                        if (checkHorizontal(bookshelf, take, i, j)) {
                            //first two columns of the last three columns
                            counter++;
                            take[i][j+1] = true;
                            take[i][j+2] = true;
                            take[i][j+3] = true;
                        }
                    }
                    if (counter==4) return true;
                }
            }
        }
        return false;
    }
    private boolean checkHorizontal(ItemTile[][] bookshelf, boolean[][] take, int i, int j){
        for(int k = 0; k < 3; k++){
            if(take[i][j] || bookshelf[i][j].getType() != bookshelf[i][j+1].getType()) return false;
            j++;
        }
        return true;
    }
    private boolean checkVertical(ItemTile[][] bookshelf, boolean[][] take, int i, int j){
        for(int k = 0; k < 3; k++){
            if(take[i][j] || bookshelf[i][j].getType() != bookshelf[i+1][j].getType()) return false;
            i++;
        }
        return true;
    }

    @Override
    public int getId() {
        return 2;
    }

    @Override
    public String getDescription() {
        return "Four groups each containing at least 4 tiles of the same type.\n" +
                "The tile of one group can be different from those of another group";
    }
}
