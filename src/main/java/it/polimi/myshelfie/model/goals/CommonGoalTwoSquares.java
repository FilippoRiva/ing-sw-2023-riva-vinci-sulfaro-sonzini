package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.ItemTileType;

import java.util.Arrays;

public class CommonGoalTwoSquares extends CommonGoal{
    private final int id = 6;
    /**
     *
     * @param bookshelf is a generic n x m matrix of ItemTile
     * @return true if exists 2 squares (2x2) with all ItemTiles equals, false otherwise
     *
     */
    @Override
    public boolean isVerified(ItemTile[][] bookshelf) {
        int height = bookshelf.length;
        int width = bookshelf[0].length;
        boolean[][] take = new boolean[height][width];
        int[] counter = new int[ItemTileType.values().length];

        for(int i = 0; i < height-1; i++) {
            for(int j = 0; j < width-1; j++) {
                if(!take[i][j] && bookshelf[i][j].getType() != ItemTileType.EMPTY){
                    if(check(bookshelf, i, j)) {
                        map(bookshelf[i][j], counter);
                        take[i+1][j]=true;
                        take[i][j+1]=true;
                        take[i+1][j+1]=true;
                    }
                }
                if(Arrays.stream(counter).anyMatch(num -> num == 2)) return true;
            }
        }
        return false;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getDescription() {
        return "Two groups each containing 4 tiles of the same type in a 2x2 square";
    }

    private boolean check (ItemTile[][] bookshelf, int i, int j){
        if(bookshelf[i][j].getType()!=bookshelf[i+1][j+1].getType()) return false;
        if(bookshelf[i][j].getType()!=bookshelf[i][j+1].getType()) return false;
        return bookshelf[i][j].getType() == bookshelf[i + 1][j].getType();
    }

    private void map(ItemTile tile, int[] counter){
        switch (tile.getType()){
            case CAT-> counter[0]++;
            case BOOK -> counter[1]++;
            case GAME -> counter[2]++;
            case FRAME -> counter[3]++;
            case TROPHY -> counter[4]++;
            case PLANT -> counter[5]++;
        }
    }
}
