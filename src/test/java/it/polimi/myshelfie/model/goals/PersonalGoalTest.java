package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.ItemTileType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonalGoalTest {

    @Test
    void defaultPersonalGoalTestisVerified() {
        //PersonalGoal personalGoal = new PersonalGoal(PersonalGoalDefaults.FIRST);
        //assertTrue(matrixEqual(personalGoal.getGoal(),PersonalGoalDefaults.FIRST.getGoal()));
        //assertEquals(12,personalGoal.isVerified(PersonalGoalDefaults.FIRST.getGoal()));

        PersonalGoal personalGoal = new PersonalGoal(PersonalGoalDefaults.SECOND);
        assertTrue(matrixEqual(personalGoal.getGoal(),PersonalGoalDefaults.SECOND.getGoal()));
        assertEquals(12,personalGoal.achievedPoints(PersonalGoalDefaults.SECOND.getGoal()));

        personalGoal = new PersonalGoal(PersonalGoalDefaults.THIRD);
        assertTrue(matrixEqual(personalGoal.getGoal(),PersonalGoalDefaults.THIRD.getGoal()));
        assertEquals(12,personalGoal.achievedPoints(PersonalGoalDefaults.THIRD.getGoal()));

        personalGoal = new PersonalGoal(PersonalGoalDefaults.FOURTH);
        assertTrue(matrixEqual(personalGoal.getGoal(),PersonalGoalDefaults.FOURTH.getGoal()));
        assertEquals(12,personalGoal.achievedPoints(PersonalGoalDefaults.FOURTH.getGoal()));

        personalGoal = new PersonalGoal(PersonalGoalDefaults.FIFTH);
        assertTrue(matrixEqual(personalGoal.getGoal(),PersonalGoalDefaults.FIFTH.getGoal()));
        assertEquals(12,personalGoal.achievedPoints(PersonalGoalDefaults.FIFTH.getGoal()));

        personalGoal = new PersonalGoal(PersonalGoalDefaults.SIXTH);
        assertTrue(matrixEqual(personalGoal.getGoal(),PersonalGoalDefaults.SIXTH.getGoal()));
        assertEquals(12,personalGoal.achievedPoints(PersonalGoalDefaults.SIXTH.getGoal()));

        personalGoal = new PersonalGoal(PersonalGoalDefaults.SEVENTH);
        assertTrue(matrixEqual(personalGoal.getGoal(),PersonalGoalDefaults.SEVENTH.getGoal()));
        assertEquals(12,personalGoal.achievedPoints(PersonalGoalDefaults.SEVENTH.getGoal()));

        personalGoal = new PersonalGoal(PersonalGoalDefaults.EIGHTH);
        assertTrue(matrixEqual(personalGoal.getGoal(),PersonalGoalDefaults.EIGHTH.getGoal()));
        assertEquals(12,personalGoal.achievedPoints(PersonalGoalDefaults.EIGHTH.getGoal()));

        personalGoal = new PersonalGoal(PersonalGoalDefaults.NINTH);
        assertTrue(matrixEqual(personalGoal.getGoal(),PersonalGoalDefaults.NINTH.getGoal()));
        assertEquals(12,personalGoal.achievedPoints(PersonalGoalDefaults.NINTH.getGoal()));

        personalGoal = new PersonalGoal(PersonalGoalDefaults.TENTH);
        assertTrue(matrixEqual(personalGoal.getGoal(),PersonalGoalDefaults.TENTH.getGoal()));
        assertEquals(12,personalGoal.achievedPoints(PersonalGoalDefaults.TENTH.getGoal()));

        personalGoal = new PersonalGoal(PersonalGoalDefaults.ELEVENTH);
        assertTrue(matrixEqual(personalGoal.getGoal(),PersonalGoalDefaults.ELEVENTH.getGoal()));
        assertEquals(12,personalGoal.achievedPoints(PersonalGoalDefaults.ELEVENTH.getGoal()));

        personalGoal = new PersonalGoal(PersonalGoalDefaults.TWELFTH);
        assertTrue(matrixEqual(personalGoal.getGoal(),PersonalGoalDefaults.TWELFTH.getGoal()));
        assertEquals(12,personalGoal.achievedPoints(PersonalGoalDefaults.TWELFTH.getGoal()));

        personalGoal = new PersonalGoal(PersonalGoalDefaults.SECOND);
        assertEquals(9,personalGoal.achievedPoints(
        new ItemTile[][]{
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.BOOK,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0)},
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.PLANT,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0)},
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.FRAME,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0)},
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.EMPTY,0)},
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.GAME,0)},
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0)},
        }));
        assertEquals(6,personalGoal.achievedPoints(
            new ItemTile[][]{
                    new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.BOOK,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0)},
                    new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0)},
                    new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.FRAME,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0)},
                    new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.EMPTY,0)},
                    new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.GAME,0)},
                    new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0)},
            }));
        assertEquals(4,personalGoal.achievedPoints(
            new ItemTile[][]{
                    new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0)},
                    new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0)},
                    new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.FRAME,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0)},
                    new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.EMPTY,0)},
                    new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.GAME,0)},
                    new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0)},
            }));
        assertEquals(2,personalGoal.achievedPoints(
            new ItemTile[][]{
                    new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0)},
                    new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0)},
                    new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0)},
                    new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.EMPTY,0)},
                    new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.GAME,0)},
                    new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0)},
            }));
        assertEquals(1,personalGoal.achievedPoints(
            new ItemTile[][]{
                    new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0)},
                    new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0)},
                    new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0)},
                    new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0)},
                    new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.GAME,0)},
                    new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0)},
            }));
        assertEquals(0,personalGoal.achievedPoints(
            new ItemTile[][]{
                    new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0)},
                    new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0)},
                    new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0)},
                    new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0)},
                    new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0)},
                    new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0)},
            }));
    }

    @Test
    void customPersonalGoalTestIsVerified(){

        PersonalGoal personalGoal = new PersonalGoal(10,10,1);
        assertEquals(12+(10*10-6)*3,personalGoal.achievedPoints(personalGoal.getGoal()));
        personalGoal = new PersonalGoal(100,100,1);
        assertEquals(12+(100*100-6)*3,personalGoal.achievedPoints(personalGoal.getGoal()));

        personalGoal = new PersonalGoal(10,10,0.5);
        assertTrue(12<personalGoal.achievedPoints(personalGoal.getGoal()));
    }



    private boolean matrixEqual(ItemTile[][]A, ItemTile[][] B){
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