package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.ItemTileType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PersonalGoalDefaultsTest {

    public static boolean matrixEqual(ItemTile[][] A, ItemTile[][] B) {
        if (A.length != B.length) return false;
        if (A[0].length != B[0].length) return false;
        for (int y = 0; y < A.length; y++) {
            for (int x = 0; x < A[0].length; x++) {
                if (A[y][x] == null || B[y][x] == null) {
                    if (A[y][x] != B[y][x]) return false;
                } else {
                    if (A[y][x].getType() != B[y][x].getType()) return false;
                }
            }
        }
        return true;
    }

    @Test
    void getGoal() {

        assertTrue(matrixEqual(PersonalGoalDefaults.FIRST.getGoal(), (new ItemTile[][]{
                new ItemTile[]{new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.FRAME, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.GAME, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.TROPHY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
        })));
        assertTrue(matrixEqual(PersonalGoalDefaults.SECOND.getGoal(), (new ItemTile[][]{
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.GAME, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.BOOK, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.TROPHY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.FRAME, 0)},
        })));
        assertTrue(matrixEqual(PersonalGoalDefaults.THIRD.getGoal(), (new ItemTile[][]{
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.FRAME, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.GAME, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.TROPHY, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
        })));
        assertTrue(matrixEqual(PersonalGoalDefaults.FOURTH.getGoal(), (new ItemTile[][]{
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.GAME, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.TROPHY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.FRAME, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
        })));
        assertTrue(matrixEqual(PersonalGoalDefaults.FIFTH.getGoal(), (new ItemTile[][]{
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.TROPHY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.FRAME, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.PLANT, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.GAME, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0)},
        })));
        assertTrue(matrixEqual(PersonalGoalDefaults.SIXTH.getGoal(), (new ItemTile[][]{
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.TROPHY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.GAME, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.FRAME, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
        })));
        assertTrue(matrixEqual(PersonalGoalDefaults.SEVENTH.getGoal(), (new ItemTile[][]{
                new ItemTile[]{new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.FRAME, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.TROPHY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.GAME, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
        })));
        assertTrue(matrixEqual(PersonalGoalDefaults.EIGHTH.getGoal(), (new ItemTile[][]{
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.FRAME, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.TROPHY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.GAME, 0), new ItemTile(ItemTileType.EMPTY, 0)},
        })));
        assertTrue(matrixEqual(PersonalGoalDefaults.NINTH.getGoal(), (new ItemTile[][]{
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.GAME, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.BOOK, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.TROPHY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.PLANT, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.FRAME, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
        })));
        assertTrue(matrixEqual(PersonalGoalDefaults.TENTH.getGoal(), (new ItemTile[][]{
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.TROPHY, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.GAME, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.FRAME, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.EMPTY, 0)},
        })));
        assertTrue(matrixEqual(PersonalGoalDefaults.ELEVENTH.getGoal(), (new ItemTile[][]{
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.GAME, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.FRAME, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.CAT, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.TROPHY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
        })));
        assertTrue(matrixEqual(PersonalGoalDefaults.TWELFTH.getGoal(), (new ItemTile[][]{
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.BOOK, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.PLANT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.FRAME, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.TROPHY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.GAME, 0)},
                new ItemTile[]{new ItemTile(ItemTileType.CAT, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0), new ItemTile(ItemTileType.EMPTY, 0)},
        })));
    }
}