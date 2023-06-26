package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.ItemTileType;

public class CommonGoalTwoLines extends CommonGoal{
    /**
     * @param bookshelf is a generic n x m matrix of ItemTile
     * @return true if exists 2 rows with all ItemTiles different, false otherwise
     *
     */
    @Override
    public boolean isVerified(ItemTile[][] bookshelf) {
        int height = bookshelf.length-1;
        int width = bookshelf[0].length-1;
        int row_count=0;
        for(int i = 0; i<=height; i++) {
            if (bookshelf[i][0].getType() != ItemTileType.EMPTY) {
                if(checkRow(bookshelf[i], width)) row_count++;
            }
        }
        return row_count >= 2;
    }

    @Override
    public int getId() {
        return 5;
    }

    @Override
    public String getDescription() {
        return "Two lines each formed by 5 different types of tiles";
    }

    private boolean checkRow(ItemTile[] row, int width){
        for(int i=0; i<=width; i++){
            if(row[i].getType() == ItemTileType.EMPTY) return false;
            for(int j=0; j<i; j++){
                if(row[i].getType()==row[j].getType()) return false;
            }
        }
        return true;
    }
}
