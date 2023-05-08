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

public class FullGameModelView implements Serializable, GameModelView {
    private final List<PlayerModelView> players;
    private String currentPlayer;
    private GameState currentState;
    private List<CommonGoalModelView> commonGoals;
    private ItemTile[][] livingRoom;
    private int remainingTiles;
    private final GameConfig gameConfig;
    private final String roomName;
    public FullGameModelView(Game game){
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

    /**
     *
     * @return the list of player model views.
     */
    public List<PlayerModelView> getPlayers() {
        return players;
    }

    /**
     * Returns the player model view of the player given his position.
     * @param position the position of the player.
     * @return the player model view associated to that player.
     */
    public PlayerModelView getPlayer(int position){
        return players.get(position);
    }

    /**
     * Returns the player model view given the name of the player.
     * @param username the player name.
     * @return the player model view requested.
     */
    public PlayerModelView getPlayer(String username){
        for(PlayerModelView p : players){
            if(p.getName().equals(username)) return p;
        }
        return null;
    }

    /**
     *
     * @return the player model view of the first player.
     */
    public PlayerModelView getFirstPlayer(){
        return getPlayer(0);
    }

    /**
     *
     * @return the name of the player playing.
     */
    public String getCurrentPlayer(){
        return currentPlayer;
    }

    /**
     *
     * @return the current state of the game.
     */
    public GameState getCurrentState(){
        return currentState;
    }

    /**
     *
     * @return the list of common goal views of the common goals of the game.
     */
    public List<CommonGoalModelView> getCommonGoals() {
        return commonGoals;
    }

    /**
     *
     * @return a copy of the living room.
     *
     */
    public ItemTile[][] getLivingRoom() {
        return livingRoom;
    }

    /**
     *
     * @return how many remaining tiles are on the board.
     */
    public int getRemainingTiles() {
        return remainingTiles;
    }

    /**
     *
     * @return the game config.
     */
    public GameConfig getGameConfig() {
        return gameConfig;
    }

    /**
     *
     * @return the lobby name.
     */
    public String getRoomName() {
        return roomName;
    }

    /**
     * Returns the tile with coordinates (x, y).
     * @param x is the x coordinate.
     * @param y is the y coordinate.
     * @return the requested item tile.
     */
    public ItemTile getTile(int x, int y){
        int offset = (livingRoom.length-1)/2;
        return livingRoom[x+offset][offset-y];
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setCurrentState(GameState currentState) {
        this.currentState = currentState;
    }

    public void setCommonGoals(List<CommonGoalModelView> commonGoals) {
        this.commonGoals = commonGoals;
    }

    public void setLivingRoom(ItemTile[][] livingRoom) {
        this.livingRoom = livingRoom;
    }

    public void setRemainingTiles(int remainingTiles) {
        this.remainingTiles = remainingTiles;
    }
}