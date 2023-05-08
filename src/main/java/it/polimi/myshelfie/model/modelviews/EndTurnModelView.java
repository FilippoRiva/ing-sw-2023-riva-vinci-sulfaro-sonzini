package it.polimi.myshelfie.model.modelviews;

import it.polimi.myshelfie.model.*;
import it.polimi.myshelfie.model.goals.CommonGoal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EndTurnModelView implements Serializable, GameModelView {
    private final String lastPlayer;
    private final int points;
    private final List<Token> playerTokens;
    private final List<CommonGoalModelView> commonGoals;
    private final ItemTile[][] livingRoom;
    private final int remainingTiles;

    private final GameState currentState;
    private final String currentPlayer;


    public EndTurnModelView(Game game) {
        this.lastPlayer = game.getLastPlayer();
        this.points = game.getPlayer(lastPlayer).getPoints();
        this.playerTokens = game.getPlayer(lastPlayer).getObtainedTokens();
        List<CommonGoal> actualCommonGoals = game.getCommonGoals();
        this.commonGoals = new ArrayList<>();
        for(CommonGoal c : actualCommonGoals) {
            commonGoals.add(new CommonGoalModelView(c));
        }
        this.livingRoom = game.getLivingRoom();
        this.remainingTiles = game.getBagSize();
        this.currentState = game.getState();
        this.currentPlayer = game.getCurrentPlayer();

    }

    public String getLastPlayer() { return lastPlayer; }

    public int getPoints() { return points; }

    public List<Token> getPlayerTokens() { return playerTokens; }

    public List<CommonGoalModelView> getCommonGoals() { return commonGoals; }

    public ItemTile[][] getLivingRoom() { return livingRoom; }

    public int getRemainingTiles() { return remainingTiles; }

    public GameState getCurrentState() { return currentState; }

    public String getCurrentPlayer() { return currentPlayer; }
}
