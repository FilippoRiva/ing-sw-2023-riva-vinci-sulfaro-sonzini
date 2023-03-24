package it.polimi.myshelfie.model;

import it.polimi.myshelfie.model.exceptions.FullColumnException;

import java.awt.*;

public class Bookshelf {
    private final ItemTile[][] bookshelfItems;
    private final int width;
    private final int height;

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
                bookshelfItems[y][x] = new ItemTile(ItemTileType.EMPTY,0);
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
        if(tile.getType().equals(ItemTileType.EMPTY)) return;
        for(int y = height-1; y >= 0; y--){
            if(bookshelfItems[y][column].getType() == ItemTileType.EMPTY) {
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

    public int achievedAdjacentTiles(){
        boolean[][] boolBookshelf = new boolean[height][width];
        int points = 0, n;


        for(int i=0; i<height;i++){
            for(int j=0; j<width;j++){
                if(!boolBookshelf[i][j] && bookshelfItems[i][j].getType()!=ItemTileType.EMPTY) {
                    n = checkAdjacent(boolBookshelf, i, j);
                    switch (n) {
                        case 1, 2 -> {}
                        case 3 -> points += 2;
                        case 4 -> points += 3;
                        case 5 -> points += 5;
                        default -> points += 8;
                    }
                }
            }
        }
        return points;
    }

    private int checkAdjacent(boolean[][] boolBookshelf, int i, int j){
        int n=0;
        boolBookshelf[i][j]=true;
        if (j - 1 >= 0 && !boolBookshelf[i][j-1] && bookshelfItems[i][j-1].getType() == bookshelfItems[i][j].getType()) {
            n+= checkAdjacent(boolBookshelf, i, j-1);
        }
        if (i - 1 >= 0 && !boolBookshelf[i-1][j] && bookshelfItems[i-1][j].getType() == bookshelfItems[i][j].getType()) {
            n+= checkAdjacent(boolBookshelf, i-1, j);
        }
        if (j+1 < width && !boolBookshelf[i][j+1] && bookshelfItems[i][j+1].getType() == bookshelfItems[i][j].getType()) {
            n+=checkAdjacent(boolBookshelf, i, j+1);
        }
        if (i + 1 <height && !boolBookshelf[i+1][j] && bookshelfItems[i+1][j].getType() == bookshelfItems[i][j].getType()) {
            n+= checkAdjacent(boolBookshelf, i+1, j);
        }
        return 1+n;
    }
}
