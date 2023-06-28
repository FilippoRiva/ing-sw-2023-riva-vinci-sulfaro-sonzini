package it.polimi.myshelfie.model;


import it.polimi.myshelfie.model.exceptions.InvalidParameterException;

import java.util.Collections;
import java.util.Stack;

/**
 * Represents the board of the game.
 * It has a bag of tiles, a grid with all the tiles that can be drawn, a size and the number of players playing, because the number of tiles on the board depends on it.
 */
public class LivingRoom {
    private final Stack<ItemTile> bag;
    private final ItemTile[][] table;
    private final boolean[][] validPositions;
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
     *
     * @param numTilesPerType the number of tiles of a single tile type
     * @param size            the odd size of the new board
     */
    public LivingRoom(int numTilesPerType, int size, int numberOfPlayers) throws InvalidParameterException {
        if (size < 9) throw new InvalidParameterException("Size of the livingRoom too small");
        if (size % 2 == 0) throw new InvalidParameterException("Even size not implemented");
        bag = new Stack<>();
        ItemTileType[] itemTiles = ItemTileType.values();
        for (ItemTileType itemTile : itemTiles) {
            if (itemTile != ItemTileType.EMPTY) {
                for (int j = 0; j < numTilesPerType; j++) {
                    bag.add(new ItemTile(itemTile, j % 3));
                }
            }
        }
        Collections.shuffle(bag);
        this.numberOfPlayers = numberOfPlayers;
        this.table = new ItemTile[size][size];
        this.validPositions = new boolean[size][size];
        this.size = size;
        this.offset = (size - 1) / 2;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                table[x][y] = null;
                validPositions[x][y] = false;
            }
        }
        initializeValidPositions();
    }

    /**
     * Initializes the valid positions of the living room.
     */
    private void initializeValidPositions() {
        int wingSize = offset - 1;
        if (size > 9) {
            //central square
            fillArea(-1, 1, 3, 3);
            //rectangles
            fillArea(2, 1, wingSize, 2);
            fillArea(0, -2, 2, wingSize);
            fillArea(-offset, 0, wingSize, 2);
            fillArea(-1, offset, 2, wingSize);
            //stairs
            fillStair(-wingSize, wingSize - 1, wingSize - 1, 0);
            fillStair(-wingSize + 1, -2, wingSize - 1, 1);
            fillStair(2, -1, wingSize - 1, 2);
            fillStair(1, wingSize, wingSize - 1, 3);
        } else if (size == 9) {
            switch (numberOfPlayers) {
                case 2 -> {
                    fillArea(-1, 1, 3, 3);

                    fillArea(-3, 0, 2, 2);
                    fillArea(-1, 3, 2, 2);
                    fillArea(2, 1, 2, 2);
                    fillArea(0, -2, 2, 2);

                    fillArea(-2, 1, 1, 1);
                    fillArea(1, 2, 1, 1);
                    fillArea(2, -1, 1, 1);
                    fillArea(-1, -2, 1, 1);
                }
                case 3 -> {
                    fillArea(-2, 2, 5, 5);

                    fillArea(-3, 0, 1, 2);
                    fillArea(3, 1, 1, 2);
                    fillArea(-1, 3, 2, 1);
                    fillArea(0, -3, 2, 1);

                    fillArea(-4, -1, 1, 1);
                    fillArea(4, 1, 1, 1);
                    fillArea(-1, 4, 1, 1);
                    fillArea(1, -4, 1, 1);
                }
                default -> {
                    fillArea(-2, 2, 5, 5);

                    fillArea(-4, 0, 2, 2);
                    fillArea(3, 1, 2, 2);
                    fillArea(-1, 4, 2, 2);
                    fillArea(0, -3, 2, 2);

                    fillArea(-3, 1, 1, 1);
                    fillArea(1, 3, 1, 1);
                    fillArea(3, -1, 1, 1);
                    fillArea(-1, -3, 1, 1);
                }
            }
        }
        refill();
    }


    /**
     * Fills a certain area of the table until the bag of ItemTiles is empty
     *
     * @param x      the upper left corner's x coordinate
     * @param y      the upper left corner's y coordinate
     * @param width  the width of the area to fill
     * @param height the height of the area to fill
     */
    private void fillArea(int x, int y, int width, int height) {
        for (int xPos = x; xPos < x + width; xPos++) {
            for (int yPos = y; yPos > y - height; yPos--) {
                setValid(xPos, yPos);
            }
        }
    }

    /**
     * @param x           upper left corner x coordinate
     * @param y           upper left corner y coordinate
     * @param size        size of the stair (they are always squares)
     * @param orientation orientation of the stair.
     *                    The orientation parameters can be:<br>
     *                    0 - from lower left to upper right<br>
     *                    1 - from lower right to upper left<br>
     *                    2 - from upper right to lower left<br>
     *                    3 - from upper left to lower right
     */
    private void fillStair(int x, int y, int size, int orientation) {
        if (bag.empty()) return;
        boolean[][] stair = new boolean[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                stair[i][j] = j <= i;
            }
        }
        switch (orientation) {
            case 1 -> flipVertical(stair);
            case 2 -> {
                flipHorizontal(stair);
                flipVertical(stair);
            }
            case 3 -> flipHorizontal(stair);
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (stair[i][j]) setValid(x + i, y - size + 1 + j);
            }
        }

    }

    /**
     * Flips a matrix horizontally.
     *
     * @param matrix the matrix to flip.
     */
    private void flipHorizontal(boolean[][] matrix) {
        int size = matrix.length;
        for (int x = 0; x < size / 2; x++) {
            for (int y = 0; y < size; y++) {
                boolean temp = matrix[x][y];
                matrix[x][y] = matrix[size - x - 1][y];
                matrix[size - x - 1][y] = temp;
            }
        }
    }

    /**
     * Flips a matrix vertically.
     *
     * @param matrix the matrix to flip.
     */
    private void flipVertical(boolean[][] matrix) {
        int size;
        size = matrix.length;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size / 2; y++) {
                boolean temp = matrix[x][y];
                matrix[x][y] = matrix[x][size - y - 1];
                matrix[x][size - y - 1] = temp;
            }
        }
    }


    /**
     * Sets valid positions.
     *
     * @param x the x coordinate.
     * @param y the y coordinate.
     */
    private void setValid(int x, int y) {
        validPositions[x + offset][offset - y] = true;
    }


    /**
     * If the board is refillable it refills the board with the remaining tiles in the bag.
     * If it isn't it does nothing.
     */
    public void refill() {
        if (!isRefillable()) return;
        boolean emptyBag = bag.empty();
        if (emptyBag) return;
        for (int x = -offset; x <= offset; x++) {
            for (int y = -offset; y <= offset; y++) {
                if (isValid(x, y)) putTile(bag.pop(), x, y);
                if (bag.isEmpty()) break;
            }
            if (bag.isEmpty()) break;
        }
    }

    /**
     * Let you see what tile is at a certain table coordinate
     *
     * @param x x coordinate
     * @param y y coordinate
     * @return the ItemTile at [x,y] position
     */
    public ItemTile getTile(int x, int y) {
        return table[x + offset][offset - y];
    }

    /**
     * Puts a certain tile into a position of the table
     *
     * @param tile ItemTile to place into the table
     * @param x    x coordinate
     * @param y    y coordinate
     */
    private void putTile(ItemTile tile, int x, int y) {
        table[x + offset][offset - y] = tile;
    }

    /**
     * Removes a tile give a certain position on the table
     *
     * @param x x coordinate
     * @param y y coordinate
     * @return the ItemTile removed
     */
    public ItemTile removeTile(int x, int y) {
        ItemTile oldTile = table[x + offset][offset - y];
        table[x + offset][offset - y] = new ItemTile(ItemTileType.EMPTY, 0);
        return oldTile;
    }

    //TODO return copy
    public ItemTile[][] getTable() {
        return table;
    }

    /**
     * @return true if every remaining tile has no neighbour tile
     */
    public boolean isRefillable() {
        for (int y = offset; y >= -offset; y--) {
            for (int x = -offset; x <= offset; x++) {
                if (getTile(x, y) != null && getTile(x, y).getType() != ItemTileType.EMPTY) {
                    if (x != offset && getTile(x + 1, y) != null && getTile(x + 1, y).getType() != ItemTileType.EMPTY)
                        return false;
                    if (y != -offset && getTile(x, y - 1) != null && getTile(x, y - 1).getType() != ItemTileType.EMPTY)
                        return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks whether a position is valid, given the position.
     *
     * @param p the position to check.
     * @return true if the position is valid, false otherwise.
     */
    public boolean isValid(Position p) {
        int x = p.getX();
        int y = p.getY();
        if (x < -offset || x > offset)
            return false;
        if (y < -offset || y > offset)
            return false;
        return isValid(x, y);
    }

    /**
     * Checks whether a position is valid, given the coordinates
     *
     * @param x the x coordinate.
     * @param y the y coordinate.
     * @return true if the coordinates are valid, false otherwise.
     */
    private boolean isValid(int x, int y) {
        return validPositions[x + offset][offset - y];
    }

    /**
     * Check whether a position is empty.
     *
     * @param p the position to check.
     * @return true if the position is empty, false otherwise.
     */
    public boolean emptyPosition(Position p) {
        return getTile(p.getX(), p.getY()).getType().equals(ItemTileType.EMPTY);
    }

    /**
     * Checks if a tile can be drawn, so whether it has at least one side free.
     *
     * @param p the position to check.
     * @return true if it's drawable, false otherwise.
     */
    public boolean atLeastOneSideFree(Position p) {
        int x = p.getX();
        int y = p.getY();

        if (p.getY() == offset || getTile(x, y + 1) == null || getTile(x, y + 1).getType() == ItemTileType.EMPTY)
            return true;
        if (p.getX() == offset || getTile(x + 1, y) == null || getTile(x + 1, y).getType() == ItemTileType.EMPTY)
            return true;
        if (p.getY() == -offset || getTile(x, y - 1) == null || getTile(x, y - 1).getType() == ItemTileType.EMPTY)
            return true;
        return p.getX() == -offset || getTile(x - 1, y) == null || getTile(x - 1, y).getType() == ItemTileType.EMPTY;
    }

    public Stack<ItemTile> getBag() {
        return bag;
    }
}
