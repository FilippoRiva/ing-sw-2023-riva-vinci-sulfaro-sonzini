package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.ItemTile;

public class CommonGoalFourLines extends CommonGoal{
    @Override
    public boolean isVerified(ItemTile[][] bookshelf) {
        /*
        * ov ov v v v
        * ov ov v v v
        * ov ov v v v
        * o  o
        * o  o
        * o  o
        */
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 6; j++){

            }
        }
        return false;
    }
}
