package it.polimi.myshelfie.model;

import it.polimi.myshelfie.model.Token;
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
     * @return an array of length 2 containing two random common goals with Tokens already stacked on top
     */
    public static List<CommonGoal> getGoals(int playerNum,int commonGoalNum){
        //TODO generico per numero di common goal
        Random random = new Random();
        int first = random.nextInt(12);
        int second;
        do{
            second = random.nextInt(12);
        }while(first == second);
        CommonGoal[] result = new CommonGoal[2];
        result[0] = goals.get(first);
        result[1] = goals.get(second);
        // adding tokens given the number of players
        switch (playerNum) {
            case 2 -> {
                result[0].pushToken(Token.FOURFIRST);
                result[0].pushToken(Token.EIGHTFIRST);
                result[1].pushToken(Token.FOURSECOND);
                result[1].pushToken(Token.EIGHTSECOND);
            }
            case 3 -> {
                result[0].pushToken(Token.FOURFIRST);
                result[0].pushToken(Token.SIXFIRST);
                result[0].pushToken(Token.EIGHTFIRST);
                result[1].pushToken(Token.FOURSECOND);
                result[1].pushToken(Token.EIGHTSECOND);
                result[1].pushToken(Token.SIXSECOND);
            }
            case 4 -> {
                result[0].pushToken(Token.TWOFIRST);
                result[0].pushToken(Token.FOURFIRST);
                result[0].pushToken(Token.SIXFIRST);
                result[0].pushToken(Token.EIGHTFIRST);
                result[1].pushToken(Token.TWOSECOND);
                result[1].pushToken(Token.FOURSECOND);
                result[1].pushToken(Token.EIGHTSECOND);
                result[1].pushToken(Token.SIXSECOND);
            }
        }
        return result;
    }
}
