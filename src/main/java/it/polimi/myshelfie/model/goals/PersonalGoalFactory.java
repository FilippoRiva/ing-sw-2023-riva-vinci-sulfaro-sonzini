package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.exceptions.InvalidParameterException;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Used to generate personal goals.
 */
public class PersonalGoalFactory {

    /**
     * Generates a random list of random custom personal goals.
     *
     * @param numPersonalGoalsPerPlayer the number of personal goals are assigned to a player.
     * @param bookshelfSize             the dimension of the bookshelf.
     * @param numPlayers                the number of players playing.
     * @param personalGoalRatio         it's the filling percentage of the personal goal.
     * @return a list of personal goals.
     * @throws Exception if the number of personal goals per players is not valid (equals to 0).
     */
    public static ArrayList<PersonalGoal> getDistinctPersonalGoals(int numPersonalGoalsPerPlayer, Dimension bookshelfSize, int numPlayers, double personalGoalRatio)
            throws Exception {
        if (numPersonalGoalsPerPlayer == 0)
            throw new InvalidParameterException("invalid personal goals per players number");
        if (bookshelfSize.width == 5 && bookshelfSize.height == 6) {
            return getDefaults(numPersonalGoalsPerPlayer, numPlayers);
        }
        return getCustoms(numPersonalGoalsPerPlayer, bookshelfSize, numPlayers, personalGoalRatio);
    }

    /**
     * Generates a random list of default personal goals.
     *
     * @param numPersonalGoals the num of personal goals per player.
     * @param numPlayers       the number of players.
     * @return a List of (numPersonalGoals * numPlayers) personal goals
     * if numPersonalGoals == 1 the PersonalGoals are distinct.
     */
    private static ArrayList<PersonalGoal> getDefaults(int numPersonalGoals, int numPlayers) {
        ArrayList<PersonalGoal> result = new ArrayList<>();
        List<Integer> randoms;
        int maxValue = PersonalGoalDefaults.values().length * numPersonalGoals;
        if (maxValue > numPersonalGoals * numPlayers)
            randoms = generateDistinctRandomIntegers(numPersonalGoals * numPlayers, maxValue);
        else randoms = generateDistinctRandomIntegers(numPersonalGoals * numPlayers, numPersonalGoals * numPlayers);
        for (Integer i : randoms) {
            int index = i % PersonalGoalDefaults.values().length;
            result.add(new PersonalGoal(PersonalGoalDefaults.values()[index]));
        }
        return result;
    }

    /**
     * Generates a list of custom personal goals, based on the bookshelf dimension, number of player and filling percentage.
     *
     * @param numPersonalGoals  the number of personal goals to generate.
     * @param bookshelfSize     the bookshelf dimension.
     * @param numPlayers        the number of players.
     * @param personalGoalRatio the filling percentage of the personal goal.
     * @return a list of custom personal goals.
     * @throws Exception if the number of personal goals per player is not valid (equals to 0).
     */
    private static ArrayList<PersonalGoal> getCustoms(int numPersonalGoals, Dimension bookshelfSize, int numPlayers, double personalGoalRatio)
            throws Exception {
        ArrayList<PersonalGoal> result = new ArrayList<>();
        for (int i = 0; i < numPersonalGoals * numPlayers; i++) {
            result.add(new PersonalGoal(bookshelfSize.width, bookshelfSize.height, 0, personalGoalRatio));
        }
        return result;
    }

    /**
     * Generates a list of random integers.
     *
     * @param resultSize how many integers are required.
     * @param maxValue   the higher value in the list of integers.
     * @return a list of random integers.
     */
    private static java.util.List<Integer> generateDistinctRandomIntegers(int resultSize, int maxValue) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < maxValue; i++) result.add(i);
        Collections.shuffle(result);
        result = result.subList(0, resultSize);
        return result;
    }
}
