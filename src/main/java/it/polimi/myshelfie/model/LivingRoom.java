package it.polimi.myshelfie.model;

import java.security.InvalidParameterException;
import java.util.*;

public class LivingRoom {
    private final Stack<ItemTile> bag;
    private final ItemTile[][] table;
    private final int size;
    private final int offset;
    private final int numberOfPlayers;

    /**
     * Creates a new empty livingRoom of size x size dimension. The livingRoom will contain a bag of item tiles with
     * a set number of tile per each tile type. The coordinate system has 0,0 at the center of the board.
     * Example of a 9x9 board<br>
     * -----------------------------------------------------<br>
     * -----|-----|-----|-1,+4|+0,+4|-----|-----|-----|-----<br>
     * -----------------------------------------------------<br>
     * -----|-----|-----|-1,+3|+0,+3|+1,+3|-----|-----|-----<br>
     * -----------------------------------------------------<br>
     * -----|-----|-2,+2|-1,+2|+0,+2|+1,+2|+2,+2|-----|-----<br>
     * -----------------------------------------------------<br>
     * -----|-3,+1|-2,+1|-1,+1|+0,+1|+1,+1|+2,+1|+3,+1|+4,+1<br>
     * -----------------------------------------------------<br>
     * -4,+0|-3,+0|-2,+0|-1,+0|+0,+0|+1,+0|+2,+0|+3,+0|+4,+0<br>
     * -----------------------------------------------------<br>
     * -4,-1|-3,-1|-2,-1|-1,-1|+0,-1|+1,-1|+2,-1|+3,-1|-----<br>
     * -----------------------------------------------------<br>
     * -----|-----|-2,-2|-1,-2|+0,-2|+1,-2|+2,-2|-----|-----<br>
     * -----------------------------------------------------<br>
     * -----|-----|-----|-1,-3|+0,-3|+1,-3|-----|-----|-----<br>
     * -----------------------------------------------------<br>
     * -----|-----|-----|-----|+0,-4|+1,-4|-----|-----|-----<br>
     * -----------------------------------------------------<br>
     * @param numTilesPerType the number of tiles of a single tile type
     * @param size the odd size of the new board
     */
    public LivingRoom(int numTilesPerType, int size, int numberOfPlayers) throws InvalidParameterException {
        if(size<9) throw new InvalidParameterException("Size too small");
        if(size%2==0) throw new InvalidParameterException("Even size not implemented");
        bag = new Stack<>();
        ItemTileType[] itemTiles = ItemTileType.values();
        for (ItemTileType itemTile : itemTiles) {
            if(itemTile!= ItemTileType.EMPTY) {
                for (int j = 0; j < numTilesPerType; j++) {
                    bag.add(new ItemTile(itemTile,j%3));
                }
            }
        }
        Collections.shuffle(bag);
        this.numberOfPlayers = numberOfPlayers;
        this.table = new ItemTile[size][size];
        this.size = size;
        this.offset = (size-1)/2;
        for(int x = 0; x<size;x++){
            for(int y = 0; y<size;y++){
                table[x][y]= new ItemTile(ItemTileType.EMPTY,0);
            }
        }
    }

    /**
     * If the board is refillable it refills the board with the remaining tiles in the bag.
     * If it isn't it does nothing.
     */
    public void refill(){
        if(!isRefillable()) return;
        boolean emptyBag = bag.empty();
        if(emptyBag) return;
        int wingSize = offset-1;
        if(size>9) {
            //central square
            fillArea(-1, 1, 3, 3);
            //rectangles
            fillArea(2, 1, wingSize, 2);
            fillArea(0, -2, 2, wingSize);
            fillArea(-offset, 0, wingSize, 2);
            fillArea(-1,offset,2,wingSize);
            //stairs
            fillStair(-wingSize,wingSize-1,wingSize-1,0);
            fillStair(-wingSize+1,-2,wingSize-1,1);
            fillStair(2,-1,wingSize-1,2);
            fillStair(1,wingSize,wingSize-1,3);
        }
        else if (size == 9){
            switch (numberOfPlayers){
                case 2 -> {
                   fillArea(-1,1,3,3);

                   fillArea(-3,0,2,2);
                   fillArea(-1,3,2,2);
                   fillArea(2,1,2,2);
                   fillArea(0,-2,2,2);

                   fillArea(-2,1,1,1);
                   fillArea(1,2,1,1);
                   fillArea(2,-1,1,1);
                   fillArea(-1,-2,1,1);
                }
                case 3 ->{
                    fillArea(-2,2,5,5);

                    fillArea(-3,0,1,2);
                    fillArea(3,1,1,2);
                    fillArea(-1,3,2,1);
                    fillArea(0,-3,2,1);

                    fillArea(-4,-1,1,1);
                    fillArea(4,1,1,1);
                    fillArea(-1,4,1,1);
                    fillArea(1,-4,1,1);
                }
                case default ->{
                    fillArea(-2,2,5,5);

                    fillArea(-4,0,2,2);
                    fillArea(3,1,2,2);
                    fillArea(-1,4,2,2);
                    fillArea(0,-3,2,2);

                    fillArea(-3,1,1,1);
                    fillArea(1,3,1,1);
                    fillArea(3,-1,1,1);
                    fillArea(-1,-3,1,1);
                }
            }
        }
    }

    /**
     *
     * @param x upper left corner x coordinate
     * @param y upper left corner y coordinate
     * @param size size of the stair (they are always squares)
     * @param orientation orientation of the stair.
     *                    The orientation parameters can be:<br>
     *                    0 - from lower left to upper right<br>
     *                    1 - from lower right to upper left<br>
     *                    2 - from upper right to lower left<br>
     *                    3 - from upper left to lower right
     */
    private void fillStair(int x,int y,int size, int orientation){
        if(bag.empty()) return;
        ItemTile[][] stair = new ItemTile[size][size];
        for(int i = 0;i<size;i++){
            for(int j = 0;j<size;j++){
                if(i>=j) stair[i][j] = bag.pop();
                else stair[i][j] = new ItemTile(ItemTileType.EMPTY,0);
                if(bag.empty()) break;
            }
            if(bag.empty()) break;
        }
        while(orientation!=0){
            stair = rotateMatrix(stair);
            orientation--;
        }
        for(int i = 0;i<size;i++){
            for(int j = 0;j<size;j++){
                putTile(stair[i][j],x+i,y+j-size+1);
            }
        }
    }

    /**
     * Rotates a matrix of 90 degrees
     * @param matrix the matrix to rotate
     * @return the rotated matrix
     */
    private ItemTile[][] rotateMatrix(ItemTile[][] matrix){
        return substituteTiles(findTransposition(matrix));
    }

    /**
     * Transpose a matrix taking elements below the diagonal and swapping them with the elements over the diagonal
     * @param matrix the matrix to transpose
     * @return the transposed matrix
     */
    private ItemTile[][] findTransposition(ItemTile[][] matrix){
        int size = matrix.length;
        ItemTile temp;
        for(int x = 0; x<size; x++){
            for(int y = 0; y<x ;y++){
               temp = matrix[x][y];
               matrix[x][y]=matrix[y][x];
               matrix[y][x]=temp;
            }
        }
        return matrix;
    }

    /**
     * Swaps the tiles of a matrix over the central axis
     * @param matrix to swap
     * @return the swapped matrix
     */
    private ItemTile[][] substituteTiles(ItemTile[][] matrix){
        int size = matrix.length;
        ItemTile temp;
        for(int y = size-1 ; y>=0;y--){
            for(int x = 0 ; x<size/2;x++){
                temp = matrix[x][y];
                matrix[x][y] = matrix[size-1-x][y];
                matrix[size-1-x][y] = temp;
            }
        }
        return matrix;
    }

    /**
     * Fills a certain area of the table until the bag of ItemTiles is empty
     * @param x the upper left corner's x coordinate
     * @param y the upper left corner's y coordinate
     * @param width the width of the area to fill
     * @param height the height of the area to fill
     */
    private void fillArea(int x,int y,int width, int height){
        if(bag.empty()) return;
        for(int xPos = x; xPos<x+width; xPos++){
            for(int yPos = y; yPos>y-height; yPos--){
                putTile(bag.pop(),xPos,yPos);
                if(bag.empty()) return;
            }
        }
    }

    /**
     * Puts a certain tile into a position of the table
     * @param tile ItemTile to place into the table
     * @param x x coordinate
     * @param y y coordinate
     */
    private void putTile(ItemTile tile, int x, int y){
        table[x+offset][offset-y] = tile;
    }

    /**
     * Removes a tile give a certain position on the table
     * @param x x coordinate
     * @param y y coordinate
     * @return the ItemTile removed
     */
    public ItemTile removeTile(int x, int y){
        ItemTile oldTile = table[x+offset][offset-y] ;
        table[x+offset][offset-y] = new ItemTile(ItemTileType.EMPTY,0);
        return oldTile;
    }

    /**
     * Let you see what tile is at a certain table coordinate
     * @param x x coordinate
     * @param y y coordinate
     * @return the ItemTile at [x,y] position
     */
    public ItemTile getTile(int x, int y){
        return table[x+offset][y+offset];
    }

    public ItemTile[][] getTable() {
        return table;
    }

    /**
     * @return true if every remaining tile has no neighbour tile
     */
    public boolean isRefillable(){
        for(int y = offset; y>=-offset; y--){
            for(int x = -offset; x<=offset; x++){
                if(getTile(x,y).getType()!= ItemTileType.EMPTY){
                    if(getTile(x+1,y).getType()!= ItemTileType.EMPTY ||getTile(x,y-1).getType()!= ItemTileType.EMPTY) return false;
                }
            }
        }
        return true;
    }
}
