package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.ItemTileType;

public class CommonGoalFourLines extends CommonGoal{
    private final int id = 6;
    /**
     *
     * @param bookshelf is a generic n x m matrix of ItemTile
     * @return true if exists 4 lines with at most 3 different type of ItemTile, false otherwise
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
        return row_count>=4;
    }
    private boolean checkRow(ItemTile[] row, int width){
        int maxNumber = 3;
        boolean found;
        int typeCount=0;
        for(int i=0; i<=width; i++){
            found=false;
            if(row[i].getType() == ItemTileType.EMPTY) return false;
            for(int j=0; j<i; j++){
                if (row[i].getType() == row[j].getType()) {
                    found = true;
                    break;
                }
            }
            if(!found)
                typeCount++;

        }
        return typeCount <= maxNumber;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getDescription() {
        return "Four lines formed by 5 tiles of maximum three different types";
    }
}
