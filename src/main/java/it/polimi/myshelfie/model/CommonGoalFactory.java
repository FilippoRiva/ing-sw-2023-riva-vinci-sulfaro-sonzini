package it.polimi.myshelfie.model;

import it.polimi.myshelfie.model.goals.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CommonGoalFactory {
    private static ArrayList<CommonGoal> goals = initialize();

    private static ArrayList<CommonGoal> initialize(){
        goals = new ArrayList<>();
        goals.add(new CommonGoalCorner());
        goals.add(new CommonGoalDiagonal());
        goals.add(new CommonGoalEightTiles());
        goals.add(new CommonGoalFourLines());
        goals.add(new CommonGoalFourOfFour());
        goals.add(new CommonGoalSixOfTwo());
        goals.add(new CommonGoalStairs());
        goals.add(new CommonGoalThreeColumns());
        goals.add(new CommonGoalTwoColumns());
        goals.add(new CommonGoalTwoLines());
        goals.add(new CommonGoalTwoSquares());
        goals.add(new CommonGoalX());

        return goals;
    }

    /**
     * @param playerNum number of players for this game
     * @return a list containing random common goals with Tokens already stacked on top
     */
    public static List<CommonGoal> getGoals(int playerNum, int commonGoalNum){
        //TODO based on commonGoalNum

        Random random = new Random();
        int first = random.nextInt(12);
        int second;
        do{
            second = random.nextInt(12);
        }while(first == second);

        List<CommonGoal> result = new ArrayList<>(commonGoalNum);
        result.add(goals.get(first));
        result.add(goals.get(second));

        // adding tokens given the number of players
        switch (playerNum) {
            case 2 -> {
                result.get(0).pushToken(Token.FOUR_FIRST);
                result.get(0).pushToken(Token.EIGHT_FIRST);
                result.get(1).pushToken(Token.FOUR_SECOND);
                result.get(1).pushToken(Token.EIGHT_SECOND);
            }
            case 3 -> {
                result.get(0).pushToken(Token.FOUR_FIRST);
                result.get(0).pushToken(Token.SIX_FIRST);
                result.get(0).pushToken(Token.EIGHT_FIRST);
                result.get(1).pushToken(Token.FOUR_SECOND);
                result.get(1).pushToken(Token.EIGHT_SECOND);
                result.get(1).pushToken(Token.SIX_SECOND);
            }
            case 4 -> {
                result.get(0).pushToken(Token.TWO_FIRST);
                result.get(0).pushToken(Token.FOUR_FIRST);
                result.get(0).pushToken(Token.SIX_FIRST);
                result.get(0).pushToken(Token.EIGHT_FIRST);
                result.get(1).pushToken(Token.TWO_SECOND);
                result.get(1).pushToken(Token.FOUR_SECOND);
                result.get(1).pushToken(Token.EIGHT_SECOND);
                result.get(1).pushToken(Token.SIX_SECOND);
            }
        }
        return result;
    }
}
