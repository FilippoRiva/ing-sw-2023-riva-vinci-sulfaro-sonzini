package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.ItemTileType;

import java.security.InvalidParameterException;
import java.util.Random;

public class PersonalGoal {
    /**
     * The n x m personal goal matrix
     */
    private final ItemTile[][] goal;
    private final int width;
    private final int height;

    /**
     * The default constructor for PersonalGoal
     * @param personalGoalDefault the PersonalGoalDefault type for this personalGoal
     */
    public PersonalGoal(PersonalGoalDefaults personalGoalDefault){
        this.width = 5;
        this.height = 6;
        this.goal = personalGoalDefault.getGoal();
    }

    /**
     * The personal Goal constructor when the size of the board is not 5x6
     * The height must greater equal than it's width
     * @param width board width
     * @param height board height
     * @param personalGoalRatio percentage of cells with non-empty tiles to be generated
     * @throws InvalidParameterException if the size of the bookshelf isn't a valid size
     */
    public PersonalGoal(int width,int height,double personalGoalRatio) throws InvalidParameterException{
        this.width = width;
        this.height = height;
        if(width == 5 && height == 6) throw new InvalidParameterException("This Constructor should be used only when " +
                "the size of the bookshelf isn't the default one");
        if(width<height) throw new InvalidParameterException("Height must be greater equal than width");
        goal = new ItemTile[height][width];
        Random rand = new Random();
        for(int x = 0; x<width; x++){
            for(int y = 0; y<height; y++){
                if(rand.nextDouble()>=1-personalGoalRatio){
                    goal[y][x] = new ItemTile(ItemTileType.random(),rand.nextInt(3));
                }else goal[y][x] = new ItemTile(ItemTileType.EMPTY, rand.nextInt(3));
            }
        }
    }

    /**
     * Used to calculate the points that a certain player has earned on this particular personal goal
     * @param bookshelfItems the player's bookshelfItems
     * @return the points to be gained by the player
     */
    public int isVerified(ItemTile[][] bookshelfItems){
        int verifiedCount = 0;
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                if(goal[y][x].getType()!= ItemTileType.EMPTY) {
                    if(bookshelfItems[y][x].getType()==goal[y][x].getType()) verifiedCount++;
                }
            }
        }
        switch (verifiedCount){
            case 0 ->{
                return 0;
            }
            case 1 -> {
                return 1;
            }
            case 2 -> {
                return 2;
            }
            case 3 -> {
                return 4;
            }
            case 4 -> {
                return 6;
            }
            case 5 -> {
                return 9;
            }
            case 6 -> {
                return 12;
            }
            default -> {
                return 12 + (verifiedCount-6)*3; // each tile match after 6 gives 3 points
            }
        }
    }

    public ItemTile[][] getGoal() {
        ItemTile[][] returnValue = new ItemTile[height][width];
        for(int y = 0; y < height; y++){
            System.arraycopy(goal[y], 0, returnValue[y], 0, width);
        }
        return returnValue;
    }
}
