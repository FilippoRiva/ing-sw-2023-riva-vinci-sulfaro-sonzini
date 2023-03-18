package it.polimi.myshelfie.model;

import it.polimi.myshelfie.model.exceptions.FullColumnException;

import java.awt.*;

public class Bookshelf {
    ItemTile[][] bookshelfItems;
    int width;
    int height;

    /**
     * Default Constructor
     * @param bookshelfDimension is the dimension the bookshelf
     */
    public Bookshelf(Dimension bookshelfDimension){
        width = bookshelfDimension.width;
        height = bookshelfDimension.height;
        bookshelfItems = new ItemTile[height][width];

        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                bookshelfItems[y][x] = ItemTile.EMPTY;
            }
        }
    }

    /**
     *
     * @param column the column in which to insert the tile
     * @param tile the tile to insert
     * @throws FullColumnException if the selected column is full
     */
    public void insertTile(int column, ItemTile tile) throws FullColumnException {
        if(tile.equals(ItemTile.EMPTY)) return;
        for(int y = height-1; y >= 0; y--){
            if(bookshelfItems[y][column] == ItemTile.EMPTY) {
                bookshelfItems[y][column] = tile;
                return;
            }
        }
        throw new FullColumnException("Column "+column+" is full");
    }

    /**
     *
     * @return a copy of the items in this bookshelf
     */
    public ItemTile[][] getBookshelfItems() {
        ItemTile[][] returnValue = new ItemTile[height][width];
        for(int y = 0; y < height; y++){
            System.arraycopy(bookshelfItems[y], 0, returnValue[y], 0, width);
        }
        return returnValue;
    }
}
