package it.polimi.myshelfie.model.modelviews;

import it.polimi.myshelfie.model.*;
import it.polimi.myshelfie.model.goals.CommonGoal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the End turn model view.
 */
public class EndTurnModelView implements Serializable, GameModelView {
    private final String lastPlayer;
    private final ArrayList<EarnedPoint> earnedPoints;
    private final List<Token> playerTokens;
    private final List<CommonGoalModelView> commonGoals;
    private final ItemTile[][] livingRoom;
    private final int remainingTiles;

    private final GameState currentState;
    private final String currentPlayer;


    /**
     * Constructor.
     *
     * @param game the game in process.
     */
    public EndTurnModelView(Game game) {
        this.lastPlayer = game.getLastPlayer();
        this.earnedPoints = game.getPlayer(lastPlayer).getEarnedPoints();
        this.playerTokens = game.getPlayer(lastPlayer).getObtainedTokens();
        List<CommonGoal> actualCommonGoals = game.getCommonGoals();
        this.commonGoals = new ArrayList<>();
        for (CommonGoal c : actualCommonGoals) {
            commonGoals.add(new CommonGoalModelView(c));
        }
        this.livingRoom = game.getLivingRoom();
        this.remainingTiles = game.getBagSize();
        this.currentState = game.getState();
        this.currentPlayer = game.getCurrentPlayer();

    }

    /**
     * Returns the last player.
     *
     * @return the last player.
     */
    public String getLastPlayer() {
        return lastPlayer;
    }

    /**
     * Returns the list of earned points.
     * @return the list of earned points.
     */
    public ArrayList<EarnedPoint> getEarnedPoints() {
        return earnedPoints;
    }

    /**
     * Returns the list of the player tokens.
     * @return the list of the player tokens.
     */
    public List<Token> getPlayerTokens() {
        return playerTokens;
    }

    /**
     * Returns the list of common goal.
     * @return the list of common goal.
     */
    public List<CommonGoalModelView> getCommonGoals() {
        return commonGoals;
    }

    /**
     * Returns the living room.
     * @return the living room.
     */
    public ItemTile[][] getLivingRoom() {
        return livingRoom;
    }

    /**
     * Returns how many tiles remained on the board.
     * @return how many tiles remained on the board.
     */
    public int getRemainingTiles() {
        return remainingTiles;
    }

    /**
     * Returns the current state.
     * @return the current state.
     */
    public GameState getCurrentState() {
        return currentState;
    }

    /**
     * Returns the current player.
     * @return the current player.
     */
    public String getCurrentPlayer() {
        return currentPlayer;
    }
}
