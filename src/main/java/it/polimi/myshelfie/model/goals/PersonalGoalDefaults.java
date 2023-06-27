package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.ItemTileType;

/**
 * The default personal goals out of the "My Shelfie" box.
 */
public enum PersonalGoalDefaults{
    FIRST(new ItemTile[][]{
            new ItemTile[]{new ItemTile(ItemTileType.PLANT,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.FRAME,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.CAT,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.BOOK,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.GAME,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.TROPHY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
    },1),
    SECOND(new ItemTile[][]{
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.PLANT,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.GAME,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.BOOK,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.TROPHY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.FRAME,0)},
    },2),
    THIRD(new ItemTile[][]{
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0),new ItemTile(ItemTileType.EMPTY,0) },
            new ItemTile[]{new ItemTile(ItemTileType.FRAME,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.GAME , 0),new ItemTile( ItemTileType.EMPTY,0) },
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.PLANT,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) },
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.CAT,0)  , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0), new ItemTile (ItemTileType.TROPHY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0), new ItemTile (ItemTileType.EMPTY ,0)},
            new ItemTile[]{new ItemTile(ItemTileType.BOOK,0) , new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) },
    },3),
    FOURTH(new ItemTile[][]{
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.GAME,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.FRAME,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.PLANT,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.BOOK,0), new ItemTile(ItemTileType.CAT,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
    },4),
    FIFTH(new ItemTile[][]{
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.FRAME,0), new ItemTile(ItemTileType.BOOK,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.PLANT,0)},
            new ItemTile[]{new ItemTile(ItemTileType.GAME,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.CAT,0) , new ItemTile(ItemTileType.EMPTY,0)},
    },5),
    SIXTH(new ItemTile[][]{
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.TROPHY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.CAT,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.BOOK,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.GAME,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.FRAME,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.PLANT,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
    },6),
    SEVENTH(new ItemTile[][]{
            new ItemTile[]{new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.FRAME,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.PLANT,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.GAME,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.BOOK,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
    },7),
    EIGHTH(new ItemTile[][]{
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.FRAME,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.TROPHY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.PLANT,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.BOOK,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.GAME,0) , new ItemTile(ItemTileType.EMPTY,0)},
    },8),
    NINTH(new ItemTile[][]{
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.GAME,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.CAT,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.BOOK,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.TROPHY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.PLANT,0)},
            new ItemTile[]{new ItemTile(ItemTileType.FRAME,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
    },9),
    TENTH(new ItemTile[][]{
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.TROPHY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.GAME,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.BOOK,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.CAT,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.FRAME,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.PLANT,0) , new ItemTile(ItemTileType.EMPTY,0)},
    },10),
    ELEVENTH(new ItemTile[][]{
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.PLANT,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.BOOK,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.GAME,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.FRAME,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.CAT,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.TROPHY,0) , new ItemTile(ItemTileType.EMPTY,0)},
    },11),
    TWELFTH(new ItemTile[][]{
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.BOOK,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.PLANT,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.FRAME,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.TROPHY,0) , new ItemTile(ItemTileType.EMPTY,0)},
            new ItemTile[]{new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.GAME,0)},
            new ItemTile[]{new ItemTile(ItemTileType.CAT,0), new ItemTile(ItemTileType.EMPTY,0), new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0) , new ItemTile(ItemTileType.EMPTY,0)},
    },12);

    private final ItemTile[][] goal;
    private final int id;

    /**
     *
     * @return a copy of the goal.
     */
    public ItemTile[][] getGoal() {
        ItemTile[][] returnValue = new ItemTile[6][5];
        for(int y = 0; y < 6; y++){
            System.arraycopy(goal[y], 0, returnValue[y], 0, 5);
        }
        return returnValue;
    }

    /**
     *
     * @return the id of this default personal goal.
     */
    public int getId(){
        return id;
    }

    /**
     * Constructor.
     * @param goal the matrix of item tiles.
     * @param id the id of the personal goal.
     */
    PersonalGoalDefaults(ItemTile[][] goal,int id){
        this.goal = goal;
        this.id = id;
    }
}
