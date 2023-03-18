package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.ItemTile;

public enum PersonalGoalDefaults{
    /**
     * The default PersonalGoals out of the "my Shelfie" box
     */
    FIRST(new ItemTile[][]{
            new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY ,ItemTile.EMPTY,ItemTile.EMPTY },
            new ItemTile[]{ItemTile.FRAME,ItemTile.EMPTY,ItemTile.EMPTY ,ItemTile.GAME ,ItemTile.EMPTY },
            new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.PLANT ,ItemTile.EMPTY ,ItemTile.EMPTY },
            new ItemTile[]{ItemTile.EMPTY,ItemTile.CAT  ,ItemTile.EMPTY ,ItemTile.EMPTY,ItemTile.TROPHY},
            new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY ,ItemTile.EMPTY,ItemTile.EMPTY },
            new ItemTile[]{ItemTile.BOOK ,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY },
    }),
    SECOND(new ItemTile[][]{
            new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.BOOK,ItemTile.EMPTY,ItemTile.EMPTY},
            new ItemTile[]{ItemTile.EMPTY,ItemTile.PLANT,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
            new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.FRAME,ItemTile.EMPTY,ItemTile.EMPTY},
            new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.TROPHY,ItemTile.EMPTY},
            new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.GAME},
            new ItemTile[]{ItemTile.CAT,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
    }),
    THIRD(new ItemTile[][]{
            new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
            new ItemTile[]{ItemTile.EMPTY,ItemTile.PLANT,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
            new ItemTile[]{ItemTile.CAT,ItemTile.EMPTY,ItemTile.GAME,ItemTile.EMPTY,ItemTile.EMPTY},
            new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.BOOK},
            new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.TROPHY,ItemTile.EMPTY},
            new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.FRAME},
    }),
    FOURTH(new ItemTile[][]{
            new ItemTile[]{ItemTile.PLANT,ItemTile.EMPTY,ItemTile.FRAME,ItemTile.EMPTY,ItemTile.EMPTY},
            new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT},
            new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.BOOK,ItemTile.EMPTY},
            new ItemTile[]{ItemTile.EMPTY,ItemTile.GAME,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
            new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
            new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.TROPHY,ItemTile.EMPTY,ItemTile.EMPTY},
    }),
    FIFTH(new ItemTile[][]{
            new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.PLANT,ItemTile.EMPTY,ItemTile.EMPTY},
            new ItemTile[]{ItemTile.EMPTY,ItemTile.BOOK,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
            new ItemTile[]{ItemTile.GAME,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
            new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.FRAME,ItemTile.EMPTY,ItemTile.EMPTY},
            new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT},
            new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.TROPHY,ItemTile.EMPTY},
    }),
    SIXTH(new ItemTile[][]{
            new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.TROPHY},
            new ItemTile[]{ItemTile.EMPTY,ItemTile.GAME,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
            new ItemTile[]{ItemTile.BOOK,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
            new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT,ItemTile.EMPTY},
            new ItemTile[]{ItemTile.EMPTY,ItemTile.FRAME,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
            new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.PLANT,ItemTile.EMPTY},
    }),
    SEVENTH(new ItemTile[][]{
            new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.FRAME},
            new ItemTile[]{ItemTile.EMPTY,ItemTile.CAT,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
            new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.TROPHY,ItemTile.EMPTY,ItemTile.EMPTY},
            new ItemTile[]{ItemTile.PLANT,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
            new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.BOOK,ItemTile.EMPTY},
            new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.GAME,ItemTile.EMPTY},
    }),
    EIGHTH(new ItemTile[][]{
            new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.GAME,ItemTile.EMPTY,ItemTile.EMPTY},
            new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
            new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT,ItemTile.EMPTY,ItemTile.EMPTY},
            new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.BOOK},
            new ItemTile[]{ItemTile.EMPTY,ItemTile.TROPHY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.PLANT},
            new ItemTile[]{ItemTile.FRAME,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
    }),
    NINTH(new ItemTile[][]{
            new ItemTile[]{ItemTile.CAT,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
            new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.FRAME,ItemTile.EMPTY},
            new ItemTile[]{ItemTile.EMPTY,ItemTile.PLANT,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
            new ItemTile[]{ItemTile.TROPHY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
            new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.GAME},
            new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.BOOK,ItemTile.EMPTY,ItemTile.EMPTY},
    }),
    TENTH(new ItemTile[][]{
            new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.GAME},
            new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
            new ItemTile[]{ItemTile.TROPHY,ItemTile.EMPTY,ItemTile.FRAME,ItemTile.EMPTY,ItemTile.EMPTY},
            new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.PLANT,ItemTile.EMPTY},
            new ItemTile[]{ItemTile.EMPTY,ItemTile.BOOK,ItemTile.CAT,ItemTile.EMPTY,ItemTile.EMPTY},
            new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
    }),
    ELEVENTH(new ItemTile[][]{
            new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
            new ItemTile[]{ItemTile.EMPTY,ItemTile.TROPHY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
            new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
            new ItemTile[]{ItemTile.EMPTY,ItemTile.FRAME,ItemTile.BOOK,ItemTile.EMPTY,ItemTile.EMPTY},
            new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.PLANT},
            new ItemTile[]{ItemTile.GAME,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.CAT,ItemTile.EMPTY},
    }),
    TWELFTH(new ItemTile[][]{
            new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.TROPHY,ItemTile.EMPTY,ItemTile.CAT},
            new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
            new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.BOOK,ItemTile.EMPTY},
            new ItemTile[]{ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
            new ItemTile[]{ItemTile.EMPTY,ItemTile.GAME,ItemTile.EMPTY,ItemTile.FRAME,ItemTile.EMPTY},
            new ItemTile[]{ItemTile.PLANT,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY,ItemTile.EMPTY},
    });

    private final ItemTile[][] goal;

    public ItemTile[][] getGoal() {
        ItemTile[][] returnValue = new ItemTile[6][5];
        for(int y = 0; y < 6; y++){
            System.arraycopy(goal[y], 0, returnValue[y], 0, 5);
        }
        return returnValue;
    }

    PersonalGoalDefaults(ItemTile[][] goal){
        this.goal = goal;
    }
}
