package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.ItemTile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonalGoalDefaultsTest {

    @Test
    void getGoal() {
        assertTrue(matrixEqual(PersonalGoalDefaults.FIRST.getGoal(),new ItemTile[][]{
                new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY ,ItemTile.EMPTY,ItemTile.EMPTY },
                new ItemTile[]{ItemTile.FRAME,ItemTile.EMPTY,ItemTile.EMPTY ,ItemTile.GAME ,ItemTile.EMPTY },
                new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.PLANT ,ItemTile.EMPTY ,ItemTile.EMPTY },
                new ItemTile[]{ItemTile.EMPTY,ItemTile.CAT  ,ItemTile.EMPTY ,ItemTile.EMPTY,ItemTile.TROPHY},
                new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY ,ItemTile.EMPTY,ItemTile.EMPTY },
                new ItemTile[]{ItemTile.BOOK ,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY },
        }));
        assertTrue(matrixEqual(PersonalGoalDefaults.SECOND.getGoal(),new ItemTile[][]{
            new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.BOOK,ItemTile.EMPTY,ItemTile.EMPTY},
            new ItemTile[]{ItemTile.EMPTY,ItemTile.PLANT,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
            new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.FRAME,ItemTile.EMPTY,ItemTile.EMPTY},
            new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.TROPHY,ItemTile.EMPTY},
            new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.GAME},
            new ItemTile[]{ItemTile.CAT,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
        }));
        assertTrue(matrixEqual(PersonalGoalDefaults.THIRD.getGoal(),new ItemTile[][]{
            new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
            new ItemTile[]{ItemTile.EMPTY,ItemTile.PLANT,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
            new ItemTile[]{ItemTile.CAT,ItemTile.EMPTY,ItemTile.GAME,ItemTile.EMPTY,ItemTile.EMPTY},
            new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.BOOK},
            new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.TROPHY,ItemTile.EMPTY},
            new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.FRAME},
        }));
        assertTrue(matrixEqual(PersonalGoalDefaults.FOURTH.getGoal(),new ItemTile[][]{
            new ItemTile[]{ItemTile.PLANT,ItemTile.EMPTY,ItemTile.FRAME,ItemTile.EMPTY,ItemTile.EMPTY},
            new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT},
            new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.BOOK,ItemTile.EMPTY},
            new ItemTile[]{ItemTile.EMPTY,ItemTile.GAME,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
            new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
            new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.TROPHY,ItemTile.EMPTY,ItemTile.EMPTY},
                }));
        assertTrue(matrixEqual(PersonalGoalDefaults.FIFTH.getGoal(),new ItemTile[][]{
                new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.PLANT,ItemTile.EMPTY,ItemTile.EMPTY},
                new ItemTile[]{ItemTile.EMPTY,ItemTile.BOOK,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                new ItemTile[]{ItemTile.GAME,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.FRAME,ItemTile.EMPTY,ItemTile.EMPTY},
                new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT},
                new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.TROPHY,ItemTile.EMPTY},
        }));
        assertTrue(matrixEqual(PersonalGoalDefaults.SIXTH.getGoal(),new ItemTile[][]{
                new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.TROPHY},
                new ItemTile[]{ItemTile.EMPTY,ItemTile.GAME,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                new ItemTile[]{ItemTile.BOOK,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT,ItemTile.EMPTY},
                new ItemTile[]{ItemTile.EMPTY,ItemTile.FRAME,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.PLANT,ItemTile.EMPTY},
        }));
        assertTrue(matrixEqual(PersonalGoalDefaults.SEVENTH.getGoal(),new ItemTile[][]{
                new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.FRAME},
                new ItemTile[]{ItemTile.EMPTY,ItemTile.CAT,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.TROPHY,ItemTile.EMPTY,ItemTile.EMPTY},
                new ItemTile[]{ItemTile.PLANT,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.BOOK,ItemTile.EMPTY},
                new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.GAME,ItemTile.EMPTY},
        }));
        assertTrue(matrixEqual(PersonalGoalDefaults.EIGHTH.getGoal(),new ItemTile[][]{
                new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.GAME,ItemTile.EMPTY,ItemTile.EMPTY},
                new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT,ItemTile.EMPTY,ItemTile.EMPTY},
                new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.BOOK},
                new ItemTile[]{ItemTile.EMPTY,ItemTile.TROPHY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.PLANT},
                new ItemTile[]{ItemTile.FRAME,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
        }));
        assertTrue(matrixEqual(PersonalGoalDefaults.NINTH.getGoal(),new ItemTile[][]{
                new ItemTile[]{ItemTile.CAT,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.FRAME,ItemTile.EMPTY},
                new ItemTile[]{ItemTile.EMPTY,ItemTile.PLANT,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                new ItemTile[]{ItemTile.TROPHY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.GAME},
                new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.BOOK,ItemTile.EMPTY,ItemTile.EMPTY},
        }));
        assertTrue(matrixEqual(PersonalGoalDefaults.TENTH.getGoal(),new ItemTile[][]{
                new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.GAME},
                new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                new ItemTile[]{ItemTile.TROPHY,ItemTile.EMPTY,ItemTile.FRAME,ItemTile.EMPTY,ItemTile.EMPTY},
                new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.PLANT,ItemTile.EMPTY},
                new ItemTile[]{ItemTile.EMPTY,ItemTile.BOOK,ItemTile.CAT,ItemTile.EMPTY,ItemTile.EMPTY},
                new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
        }));
        assertTrue(matrixEqual(PersonalGoalDefaults.ELEVENTH.getGoal(),new ItemTile[][]{
                new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                new ItemTile[]{ItemTile.EMPTY,ItemTile.TROPHY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                new ItemTile[]{ItemTile.EMPTY,ItemTile.FRAME,ItemTile.BOOK,ItemTile.EMPTY,ItemTile.EMPTY},
                new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.PLANT},
                new ItemTile[]{ItemTile.GAME,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT,ItemTile.EMPTY},
        }));
        assertTrue(matrixEqual(PersonalGoalDefaults.TWELFTH.getGoal(),new ItemTile[][]{
                new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.TROPHY,ItemTile.EMPTY,ItemTile.CAT},
                new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.BOOK,ItemTile.EMPTY},
                new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
                new ItemTile[]{ItemTile.EMPTY,ItemTile.GAME,ItemTile.EMPTY,ItemTile.FRAME,ItemTile.EMPTY},
                new ItemTile[]{ItemTile.PLANT,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
        }));
    }

    private boolean matrixEqual(ItemTile[][]A,ItemTile[][] B){
        if(A.length!=B.length) return false;
        if(A[0].length!=B[0].length) return false;
        for(int y = 0; y<A.length; y++){
            for(int x = 0; x<A[0].length; x++){
                if(A[y][x]!=B[y][x]) return false;
            }
        }
        return true;
    }
}