package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.ItemTile;

import java.awt.*;
import java.util.Arrays;

public class CommonGoalEightTiles extends CommonGoal{
    //TODO take dimension in GameConfig
    @Override
    public boolean isVerified(ItemTile[][] bookshelf) {
        int[] counter = new int[ItemTile.values().length];
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 6; j++){
                switch (bookshelf[i][j]){
                    case CAT-> counter[0]++;
                    case BOOK -> counter[1]++;
                    case GAME -> counter[2]++;
                    case FRAME -> counter[3]++;
                    case TROPHY -> counter[4]++;
                    case PLANT -> counter[5]++;
                }
            }
        }
        return Arrays.stream(counter).anyMatch(num -> num >= 8);
    }
}
