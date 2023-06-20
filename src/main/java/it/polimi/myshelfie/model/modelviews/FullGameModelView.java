package it.polimi.myshelfie.model.modelviews;

import it.polimi.myshelfie.GameConfig;
import it.polimi.myshelfie.model.*;
import it.polimi.myshelfie.model.goals.CommonGoal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FullGameModelView implements Serializable, GameModelView {
    private final List<PlayerModelView> players;
    private String currentPlayer;
    private GameState currentState;
    private List<CommonGoalModelView> commonGoals;
    private ItemTile[][] livingRoom;
    private int remainingTiles;
    private GameConfig gameConfig;
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

    public FullGameModelView(FullGameModelView toCopy){
        this.players = new ArrayList<>();
        for(PlayerModelView p : toCopy.getPlayers()){
            this.players.add(new PlayerModelView(p));
        }
        this.currentPlayer = toCopy.currentPlayer;
        this.currentState = toCopy.currentState;
        this.commonGoals = new ArrayList<>();
        for(CommonGoalModelView c : toCopy.getCommonGoals()){
            this.commonGoals.add(new CommonGoalModelView(c));
        }
        this.livingRoom = new ItemTile[toCopy.livingRoom.length][toCopy.livingRoom[0].length];
        for(int i = 0; i < toCopy.livingRoom.length; i++){
            System.arraycopy(toCopy.livingRoom[i], 0, this.livingRoom[i], 0, toCopy.livingRoom[0].length);
        }
        this.remainingTiles = toCopy.remainingTiles;
        this.gameConfig = toCopy.gameConfig;
        this.roomName = toCopy.roomName;
    }

    /**
     *
     * @return the list of player model views.
     */
    public List<PlayerModelView> getPlayers() {
        return new ArrayList<>(players);
    }

    /**
     * Returns the player model view of the player given his position.
     * The position can be requested with any offset, even negatives ones.
     * @param position the position of the player.
     * @return the player model view associated to that player.
     */
    public PlayerModelView getPlayer(int position){
        return players.get((int) (Math.sqrt(Math.pow(position,2)) % players.size()));
    }

    /**
     * Returns the player model view given the name of the player.
     * @param username the player name.
     * @return the player model view requested.
     */
    public PlayerModelView getPlayer(String username){
        return players.stream().filter(x->x.getName().equals(username)).findFirst().orElse(null);
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
    public String getCurrentPlayerName(){
        return currentPlayer;
    }
    public PlayerModelView getCurrentPlayer(){
        return players.stream().filter(x->x.getName().equals(currentPlayer)).findFirst().orElse(null);
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
        if(x > offset || x < -offset || y > offset || y < -offset) return null;
        return livingRoom[x+offset][offset-y];
    }

    protected void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    protected void setCurrentState(GameState currentState) {
        this.currentState = currentState;
    }

    protected void setCommonGoals(List<CommonGoalModelView> commonGoals) {
        this.commonGoals = commonGoals;
    }

    protected void setLivingRoom(ItemTile[][] livingRoom) {
        this.livingRoom = livingRoom;
    }

    protected void setRemainingTiles(int remainingTiles) {
        this.remainingTiles = remainingTiles;
    }

    public void diff(FullGameModelView theNewOne){
        for(PlayerModelView p : theNewOne.players){
            Optional<PlayerModelView> player = this.players.stream().filter(x->x.getName().equals(p.getName())).findFirst();
            player.ifPresent((x)->x.diff(p));
        }
        if(this.currentPlayer.equals(theNewOne.currentPlayer)) this.currentPlayer = null;
        else this.currentPlayer = theNewOne.currentPlayer;
        if(this.currentState.equals(theNewOne.currentState)) this.currentState = null;
        else this.currentState =  theNewOne.currentState;
        for(CommonGoalModelView c : theNewOne.commonGoals){
            Optional<CommonGoalModelView> commonGoal = this.commonGoals.stream().filter(x->x.getId()==c.getId()).findFirst();
            commonGoal.ifPresent((x)->x.diff(c));
        }
        if(theNewOne.livingRoom.length!=this.livingRoom.length ||
            theNewOne.livingRoom[0].length!=this.livingRoom[0].length) this.livingRoom = null;
        else {
            for(int i = 0; i < livingRoom.length; i++){
                for(int j = 0; j<livingRoom[0].length; j++){
                    if(theNewOne.livingRoom[i][j]!=null){
                        this.livingRoom[i][j] = theNewOne.livingRoom[i][j].equals(this.livingRoom[i][j]) ?
                                null : theNewOne.livingRoom[i][j];
                    } else {
                        this.livingRoom[i][j]=null;
                    }
                }
            }
        }
        if(this.remainingTiles == theNewOne.remainingTiles) this.remainingTiles = -1;
        else this.remainingTiles = theNewOne.remainingTiles;
        this.gameConfig = null;
    }

    public void applyChanges(DrawModelView drawModelView){
        this.setCurrentState(drawModelView.getCurrentState());
        this.setLivingRoom(drawModelView.getLivingRoom());
        this.getCurrentPlayer().setTakenTiles(drawModelView.getTakenTiles());
    }

    public void applyChanges(OrganizeModelView organizeModelView){
        this.setCurrentState(organizeModelView.getCurrentState());
        this.getCurrentPlayer().setTakenTiles(organizeModelView.getOrderedTiles());
    }

    public void applyChanges(InsertModelView insertModelView){
        this.getPlayer(insertModelView.getCurrentPlayer()).setBookshelf(insertModelView.getBookshelf());
        this.getPlayer(insertModelView.getCurrentPlayer()).setTakenTiles(new ArrayList<>());
        if(this.getCurrentState()!= GameState.DRAW) {
            this.setCurrentState(insertModelView.getCurrentState());
        }
    }

    public void applyChanges(EndTurnModelView endTurnModelView){
        this.setCurrentState(endTurnModelView.getCurrentState());
        this.getPlayer(endTurnModelView.getLastPlayer()).setEarnedPoints(endTurnModelView.getEarnedPoints());
        this.getPlayer(endTurnModelView.getLastPlayer()).setTokens(endTurnModelView.getPlayerTokens());
        this.setRemainingTiles(endTurnModelView.getRemainingTiles());
        this.setLivingRoom(endTurnModelView.getLivingRoom());
        this.setCommonGoals(endTurnModelView.getCommonGoals());
        this.setCurrentPlayer(endTurnModelView.getCurrentPlayer());
    }
}