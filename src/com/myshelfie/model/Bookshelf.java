package com.myshelfie.model;

import com.myshelfie.model.exceptions.FullColumnException;

public class Bookshelf {
    ItemTile[][] items;

    public Bookshelf(){
        items = new ItemTile[5][6];
        for(int x = 0; x < 5; x++){
            for(int y = 0;y<6; y++){
                items[x][y]=ItemTile.EMPTY;
            }
        }
    }

    public void insertTile(int column, ItemTile tile) throws FullColumnException {
        for(int y = 5; y>=0; y--){
            if(items[column][y]!=ItemTile.EMPTY) {
                items[column][y] = tile;
                return;
            }
        }
        throw new FullColumnException("Column "+column+" is full");
    }

    public ItemTile[][] getItems() {
        ItemTile[][] returnValue = new ItemTile[5][6];
        for(int x = 0; x < 5; x++){
            System.arraycopy(items[x], 0, returnValue[x], 0, 6);
        }
        return returnValue;
    }
}
