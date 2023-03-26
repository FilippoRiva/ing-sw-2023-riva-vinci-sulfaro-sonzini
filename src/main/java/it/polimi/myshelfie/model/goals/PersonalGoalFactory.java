package it.polimi.myshelfie.model.goals;

import java.awt.*;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PersonalGoalFactory {
    public static ArrayList<PersonalGoal> getDistinctPersonalGoals(int numPersonalGoalsPerPlayer, Dimension bookshelfSize, int numPlayers, double personalGoalRatio){
        if(numPersonalGoalsPerPlayer == 0) throw new InvalidParameterException("invalid personal goals per players number");
        if(bookshelfSize.width == 5 && bookshelfSize.height == 6){
            return getDefaults(numPersonalGoalsPerPlayer,numPlayers);
        }
        return getCustoms(numPersonalGoalsPerPlayer,bookshelfSize,numPlayers,personalGoalRatio);
    }

    /**
     *
     * @param numPersonalGoals the num of personal goals per player
     * @param numPlayers the number of players
     * @return a List of (numPersonalGoals * numPlayers) personal goals
     * if numPersonalGoals == 1 the PersonalGoals are distinct.
     */
    private static ArrayList<PersonalGoal> getDefaults(int numPersonalGoals,int numPlayers){
        ArrayList<PersonalGoal> result = new ArrayList<>();
        List<Integer> randoms;
        int maxValue = PersonalGoalDefaults.values().length*numPersonalGoals;
        if (maxValue > numPersonalGoals*numPlayers) randoms = generateDistinctRandomIntegers(numPersonalGoals*numPlayers,maxValue);
        else randoms = generateDistinctRandomIntegers(numPersonalGoals*numPlayers,numPersonalGoals*numPlayers);
        for(Integer i : randoms){
            int index = i % PersonalGoalDefaults.values().length;
            result.add(new PersonalGoal(PersonalGoalDefaults.values()[index]));
        }
        return result;
    }

    private static ArrayList<PersonalGoal> getCustoms(int numPersonalGoals,Dimension bookshelfSize, int numPlayers,double personalGoalRatio){
        ArrayList<PersonalGoal> result = new ArrayList<>();
        for(int i = 0; i < numPersonalGoals*numPlayers; i++){
           result.add(new PersonalGoal(bookshelfSize.width,bookshelfSize.height,0,personalGoalRatio));
        }
        return result;
    }

    private static java.util.List<Integer> generateDistinctRandomIntegers(int resultSize, int maxValue) {
        List<Integer> result = new ArrayList<>();
        for(int i = 0; i<maxValue; i++) result.add(i);
        Collections.shuffle(result);
        result = result.subList(0,resultSize);
        return result;
    }
}
