package it.polimi.myshelfie.model;

import it.polimi.myshelfie.model.exceptions.FullColumnException;

import java.awt.*;

public class Bookshelf {
    ItemTile[][] bookshelf;
    int width;
    int height;

    public Bookshelf(Dimension bookshelfDimension){
        width = bookshelfDimension.width;
        height = bookshelfDimension.height;
        bookshelf = new ItemTile[height][width];

        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                bookshelf[y][x] = ItemTile.EMPTY;
            }
        }
    }

    public void insertTile(int column, ItemTile tile) throws FullColumnException {
        if(tile.equals(ItemTile.EMPTY)) return;
        for(int y = height-1; y >= 0; y--){
            if(bookshelf[y][column] == ItemTile.EMPTY) {
                bookshelf[y][column] = tile;
                return;
            }
        }
        throw new FullColumnException("Column "+column+" is full");
    }

    public ItemTile[][] getBookshelf() {
        ItemTile[][] returnValue = new ItemTile[height][width];
        for(int y = 0; y < height; y++){
            System.arraycopy(bookshelf[y], 0, returnValue[y], 0, width);
        }
        return returnValue;
    }
}
