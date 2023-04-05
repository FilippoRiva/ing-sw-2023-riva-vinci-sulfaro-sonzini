package it.polimi.myshelfie.model.modelviews;

import it.polimi.myshelfie.GameConfig;
import it.polimi.myshelfie.model.Game;
import it.polimi.myshelfie.model.GameState;
import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.Player;
import it.polimi.myshelfie.model.goals.CommonGoal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameModelView implements Serializable {
    private final List<PlayerModelView> players;
    private final String currentPlayer;
    private final GameState currentState;
    private final List<CommonGoalModelView> commonGoals;
    private final ItemTile[][] livingRoom;
    private final int remainingTiles;
    private final GameConfig gameConfig;
    private final String roomName;
    public GameModelView(Game game){
        List<Player> actualPlayers = game.getPlayers();
        players = new ArrayList<>();
        for(Player p : actualPlayers){
            players.add(new PlayerModelView(p));
        }
        this.currentPlayer = game.getCurrentPlayer();
        this.currentState = game.getState();
        List<CommonGoal> actualCommonGoals = game.getCommonGoals();
        this.commonGoals = new ArrayList<>();
        for(CommonGoal c : actualCommonGoals){
            commonGoals.add(new CommonGoalModelView(c));
        }
        this.livingRoom = game.getLivingRoom();
        this.remainingTiles = game.getBagSize();
        this.gameConfig = game.getGameConfig();
        this.roomName = game.getName();
    }

    public List<PlayerModelView> getPlayers() {
        return players;
    }
    public PlayerModelView getPlayer(int position){
        return players.get(position);
    }
    public PlayerModelView getPlayer(String username){
        for(PlayerModelView p : players){
            if(p.getName().equals(username)) return p;
        }
        return null;
    }
    public PlayerModelView getFirstPlayer(){
        return getPlayer(0);
    }
    public String getCurrentPlayer(){
        return currentPlayer;
    }
    public GameState getCurrentState(){
        return currentState;
    }
    public List<CommonGoalModelView> getCommonGoals() {
        return commonGoals;
    }
    public ItemTile[][] getLivingRoom() {
        return livingRoom;
    }
    public int getRemainingTiles() {
        return remainingTiles;
    }
    public GameConfig getGameConfig() {
        return gameConfig;
    }
    public String getRoomName() {
        return roomName;
    }
    public ItemTile getTile(int x, int y){
        int offset = (livingRoom.length-1)/2;
        return livingRoom[x+offset][offset-y];
    }
}