package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.ItemTileType;


public enum PersonalGoalDefaults{
    /**
     * The default PersonalGoals out of the "my Shelfie" box
     */
    FIRST(new ItemTile[][]{
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0),new ItemTile(ItemTileType.EMPTY,0) },
            new ItemTile[]{new ItemTile(ItemTileType.FRAME,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.GAME , 0),new ItemTile( ItemTileType.EMPTY,0) },
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.PLANT,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) },
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.CAT,0)  , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0), new ItemTile (ItemTileType.TROPHY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0), new ItemTile (ItemTileType.EMPTY ,0)},
            new ItemTile[]{new ItemTile(ItemTileType.BOOK,0) , new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) },
    }),
    SECOND(new ItemTile[][]{
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.BOOK,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.PLANT,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.FRAME,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.TROPHY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.GAME,0)},
            new ItemTile[]{new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
    }),
    THIRD(new ItemTile[][]{
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.PLANT,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.GAME,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.BOOK,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.TROPHY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.FRAME,0)},
    }),
    FOURTH(new ItemTile[][]{
            new ItemTile[]{new ItemTile(ItemTileType.PLANT,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.FRAME,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.CAT,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.BOOK,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.GAME,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.TROPHY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
    }),
    FIFTH(new ItemTile[][]{
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.PLANT,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.BOOK,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.GAME,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.FRAME,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.CAT,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.TROPHY,0) , new ItemTile(ItemTileType.EMPTY,0)},
    }),
    SIXTH(new ItemTile[][]{
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.TROPHY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.GAME,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.BOOK,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.CAT,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.FRAME,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.PLANT,0) , new ItemTile(ItemTileType.EMPTY,0)},
    }),
    SEVENTH(new ItemTile[][]{
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.FRAME,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.TROPHY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.PLANT,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.BOOK,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.GAME,0) , new ItemTile(ItemTileType.EMPTY,0)},
    }),
    EIGHTH(new ItemTile[][]{
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.GAME,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.CAT,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.BOOK,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.PLANT,0)},
            new ItemTile[]{new ItemTile(ItemTileType.FRAME,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
    }),
    NINTH(new ItemTile[][]{
            new ItemTile[]{new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.FRAME,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.PLANT,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.GAME,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.BOOK,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
    }),
    TENTH(new ItemTile[][]{
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.GAME,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.FRAME,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.PLANT,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.BOOK,0), new ItemTile(ItemTileType.CAT,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
    }),
    ELEVENTH(new ItemTile[][]{
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.FRAME,0), new ItemTile(ItemTileType.BOOK,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.PLANT,0)},
            new ItemTile[]{new ItemTile(ItemTileType.GAME,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.CAT,0) , new ItemTile(ItemTileType.EMPTY,0)},
    }),
    TWELFTH(new ItemTile[][]{
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.TROPHY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.CAT,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.BOOK,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.GAME,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.FRAME,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.PLANT,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
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
