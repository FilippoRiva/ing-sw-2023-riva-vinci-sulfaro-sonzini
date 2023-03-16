package it.polimi.myshelfie.model;

import it.polimi.myshelfie.model.exceptions.FullColumnException;

import java.awt.*;

public class Bookshelf {
    ItemTile[][] bookshelf;

    public Bookshelf(Dimension bookshelfDimension){
        int width = bookshelfDimension.width;
        int height = bookshelfDimension.height;
        bookshelf = new ItemTile[width][height];

        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                bookshelf[x][y] = ItemTile.EMPTY;
            }
        }
    }

    public void insertTile(int column, ItemTile tile) throws FullColumnException {
        for(int y = bookshelf.length-1; y >= 0; y--){
            if(bookshelf[y][column] == ItemTile.EMPTY) {
                bookshelf[y][column] = tile;
                return;
            }
        }
        throw new FullColumnException("Column "+column+" is full");
    }

    public ItemTile[][] getBookshelf() {
        ItemTile[][] returnValue = new ItemTile[bookshelf.length-1][bookshelf[0].length-1];
        for(int x = 0; x < bookshelf.length-1; x++){
            System.arraycopy(bookshelf[x], 0, returnValue[x], 0, bookshelf.length-1);
        }
        return returnValue;
    }
}
