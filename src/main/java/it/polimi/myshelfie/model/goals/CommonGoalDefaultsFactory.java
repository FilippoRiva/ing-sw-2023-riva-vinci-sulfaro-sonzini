package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.Token;
import it.polimi.myshelfie.model.exceptions.InvalidParameterException;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommonGoalDefaultsFactory {

    /**
     * @param playerNum number of players for this game
     * @return a list containing random common goals with Tokens already stacked on top
     */
    public static List<CommonGoal> getGoals(int playerNum, int commonGoalNum) throws InvalidParameterException{
        if(playerNum<2) throw new InvalidParameterException("invalid player number");
        if(commonGoalNum>12 || commonGoalNum == 0 ) throw new InvalidParameterException("invalid common goal number");

        List<CommonGoal> result = new ArrayList<>(commonGoalNum);
        List<Integer> randomValues = generateDistinctRandomIntegers(commonGoalNum);
        for(Integer i : randomValues) result.add(generateGoal(i));

        // adding tokens given the number of players
        int i = 0;
        switch (playerNum) {
            case 2 -> {
                for(CommonGoal commonGoal : result){
                    if(i%2==0){
                        commonGoal.pushToken(Token.FOUR_FIRST);
                        commonGoal.pushToken(Token.EIGHT_FIRST);
                    }else {
                        commonGoal.pushToken(Token.FOUR_SECOND);
                        commonGoal.pushToken(Token.EIGHT_SECOND);
                    }
                    i++;
                }
            }
            case 3 -> {
                for(CommonGoal commonGoal : result){
                    if(i%2==0){
                        commonGoal.pushToken(Token.FOUR_FIRST);
                        commonGoal.pushToken(Token.SIX_FIRST);
                        commonGoal.pushToken(Token.EIGHT_FIRST);
                    }else {
                        commonGoal.pushToken(Token.FOUR_SECOND);
                        commonGoal.pushToken(Token.SIX_SECOND);
                        commonGoal.pushToken(Token.EIGHT_SECOND);
                    }
                    i++;
                }
            }
            default -> {
                for(CommonGoal commonGoal : result){
                    if(i%2==0){
                        commonGoal.pushToken(Token.TWO_FIRST);
                        commonGoal.pushToken(Token.FOUR_FIRST);
                        commonGoal.pushToken(Token.SIX_FIRST);
                        commonGoal.pushToken(Token.EIGHT_FIRST);
                    }else {
                        commonGoal.pushToken(Token.TWO_SECOND);
                        commonGoal.pushToken(Token.FOUR_SECOND);
                        commonGoal.pushToken(Token.SIX_SECOND);
                        commonGoal.pushToken(Token.EIGHT_SECOND);
                    }
                    i++;
                }
            }
        }
        return result;
    }

    private static List<Integer> generateDistinctRandomIntegers(int num) {
        List<Integer> result = new ArrayList<>();
        for(int i = 0; i<12; i++) result.add(i);
        Collections.shuffle(result);
        result = result.subList(0,num);
        return result;
    }

    private static CommonGoal generateGoal(int id){
        switch (id){
            case 0 -> {
                return new CommonGoalSixOfTwo();
            }
            case 1 -> {
                return new CommonGoalDiagonal();
            }
            case 2 -> {
                return new CommonGoalCorner();
            }
            case 3 -> {
                return new CommonGoalFourLines();
            }
            case 4 -> {
                return new CommonGoalFourOfFour();
            }
            case 5 -> {
                return new CommonGoalTwoColumns();
            }
            case 6 -> {
                return new CommonGoalTwoSquares();
            }
            case 7 -> {
                return new CommonGoalTwoLines();
            }
            case 8 -> {
                return new CommonGoalThreeColumns();
            }
            case 9 -> {
                return new CommonGoalX();
            }
            case 10 -> {
                return new CommonGoalEightTiles();
            }
            default -> {
                return new CommonGoalStairs();
            }
        }
    }
}
