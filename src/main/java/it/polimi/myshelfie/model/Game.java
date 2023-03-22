package it.polimi.myshelfie.model;

import it.polimi.myshelfie.GameConfig;
import it.polimi.myshelfie.model.chatting.ChatSystem;
import it.polimi.myshelfie.model.exceptions.FullColumnException;
import it.polimi.myshelfie.model.goals.CommonGoal;
import it.polimi.myshelfie.model.interfaces.ChatModel;
import it.polimi.myshelfie.model.interfaces.GameModel;
import it.polimi.myshelfie.model.interfaces.LobbyModel;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class Game implements GameModel, LobbyModel {
    private GameConfig gameConfig;
    private Player turn;
    private GameState currentState;
    private final ChatSystem chatSystem;
    private List<CommonGoal> commonGoals;
    private LivingRoom livingRoom;
    private final ArrayList<Player> players;

    public Game(){
        currentState = GameState.LOBBY;
        chatSystem = new ChatSystem();
        players = new ArrayList<>();
    }

    @Override
    public void setGameConfig(GameConfig gameConfig) {
        this.gameConfig = gameConfig;
    }

    @Override
    public void initializeGame() {
        turn = players.get(0);
        commonGoals = CommonGoalFactory.getGoals(gameConfig.getNumPlayers(), gameConfig.getNumCommonGoal());
        livingRoom = new LivingRoom(gameConfig.getNumTilesPerType(), gameConfig.getLivingRoomSize(), gameConfig.getNumPlayers());

        for(Player p : players){
            p.setBookshelf(new Bookshelf(gameConfig.getBookshelfDimension()));
            //TODO initialize personal goal
        }
    }

    @Override
    public void nextTurn() {
        int currentTurn;

        for(Player p : players){
            if(p.equals(turn)){
                currentTurn = players.indexOf(p);
                if(currentTurn < players.size()-1)
                    turn = players.get(currentTurn+1);
                else
                    turn = players.get(0);
                return;
            }
        }
    }

    @Override
    public void calculateWinResults() {
        for(Player player : players) {
            player.calculatePoints();
        }
    }

    @Override
    public void setState(GameState state) {
        this.currentState = state;
    }

    @Override
    public GameState getState() {
        return currentState;
    }

    @Override
    public void drawTiles(List<Position> positions, String playerName) throws InvalidParameterException {
        List<ItemTile> items = new ArrayList<>();
        for(Position p : positions){
            items.add(livingRoom.removeTile(p.getX(), p.getY()));
        }
        for(Player player : players) {
            if (player.getName().equals(playerName)) {
                player.setTilesTaken(items);
            }
            return;
        }
    }

    @Override
    public void orderChosenTiles(List<ItemTile> orderedTiles, String playerName) throws InvalidParameterException {
        for(Player player : players) {
            if (player.getName().equals(playerName)) {
                player.setTilesTaken(orderedTiles);
            }
            return;
        }
    }

    @Override
    public void insertTiles(String playerName, int column) throws InvalidParameterException, FullColumnException {
        for (Player player : players) {
            if (player.getName().equals(playerName)) {
                for(ItemTile t: player.getTilesTaken()) {
                    player.getBookshelf().insertTile(column, t);
                }
                return;
            }
        }
    }

    @Override
    public void checkCommonGoal(String playerName) {
        for(CommonGoal cm : commonGoals){
            if(cm.isVerified(turn.getBookshelf().getBookshelfItems())){
                addToken(playerName, cm.popToken());
            }
        }
    }

    private void addToken(String playerName, Token token) {
        for(Player p : players){
            if(p.getName().equals(playerName)){
                p.addObtainedToken(token);
                p.addPoints(token.getValue());
                return;
            }
        }
    }

    @Override
    public void refillLivingRoom(){
        livingRoom.refill();
    }

    @Override
    public boolean isPlayerNameValid(String playerName) {
        for(Player p : players){
            if(p.getName().equals(playerName)){
                return false;
            }
        }
        return true;
    }

    @Override
    public void addPlayer(String name){
        players.add(new Player(name));
    }

    @Override
    public void removePlayer(String playerName) {
        for(Player p : players){
            if(p.getName().equals(playerName)){
                players.remove(p);
                return;
            }
        }
    }

    @Override
    public boolean isLobbyFull() {
        return players.size() == gameConfig.getNumPlayers();
    }

    @Override
    public ChatModel getChatModel() {
        return chatSystem;

    }

    @Override
    public GameModel getGameModel() {
        return this;
    }


}
