package it.polimi.myshelfie.model;

import it.polimi.myshelfie.GameConfig;
import it.polimi.myshelfie.model.chatting.ChatSystem;
import it.polimi.myshelfie.model.goals.CommonGoal;
import it.polimi.myshelfie.model.interfaces.ChatModel;
import it.polimi.myshelfie.model.interfaces.GameModel;
import it.polimi.myshelfie.model.interfaces.LobbyModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

public class Game implements GameModel, LobbyModel {
    private GameConfig gameConfig;
    private final String gameName;
    private Player turn;
    private GameState currentState;
    private final ChatSystem chatSystem;
    private CommonGoal[] commonGoals;
    private LivingRoom livingRoom;
    private final ArrayList<Player> players;

    public Game(String gameName){
        this.gameName = gameName;
        currentState = GameState.LOBBY;
        chatSystem = new ChatSystem();
        players = new ArrayList<>();
    }

    @Override
    public void setGameConfig(GameConfig config) {
        this.gameConfig = config;
    }

    @Override
    public GameConfig getGameConfig() {
        return gameConfig;
    }

    @Override
    public void init() {
        turn = players.get(0);
        commonGoals = new CommonGoal[gameConfig.getNumCommonGoal()];
        livingRoom = new LivingRoom(gameConfig.getNumTilesPerType(), gameConfig.getLivingRoomSize(), gameConfig.getNumPlayers());
    }

    @Override
    public void setCommonGoals(CommonGoal[] commonGoals) {
        this.commonGoals = commonGoals;
    }

    @Override
    public void deleteTilesFromLivingRoom(List<Position> takenTiles) {
        for(Position p : takenTiles){
            livingRoom.removeTile(p.getX(), p.getY());
        }
    }

    @Override
    public void addTilesInBookshelf(String playerName, List<ItemTile> takenTiles, int column) {
        for(Player p : players){
            if(p.getName().equals(playerName)){
                ItemTile[][] bookshelf = p.getBookshelf().getBookshelfItems();
                for(int i = bookshelf.length-1; i>=0; i--){
                    if(bookshelf[i][column].getType() == ItemTileType.EMPTY){
                        for (ItemTile takenTile : takenTiles) {
                            bookshelf[i][column] = takenTile;
                            i--;
                        }
                        return;
                    }
                }
            }
        }
    }

    @Override
    public void addToken(String playerName, Token token) {
        for(Player p : players){
            if(p.getName().equals(playerName)){
                p.addObtainedToken(token);
                p.addPoints(token.getValue());
                return;
            }
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
    public void setState(GameState state) {
        this.currentState = state;
    }

    @Override
    public void addPoints(String playerName, int points) {
        for(Player p : players){
            if(p.getName().equals(playerName)){
                p.addPoints(points);
                return;
            }
        }
    }

    @Override
    public int getNumberOfPlayers() {
        return players.size();
    }

    @Override
    public void checkCommonGoal(String playerName) {
        for(CommonGoal cm : commonGoals){
            if(cm.isVerified(turn.getBookshelf().getBookshelfItems())){
                addToken(playerName, cm.popToken());
            }
        }
    }

    @Override
    public void refillLivingRoom() {
        try {
            livingRoom.refill();
        }catch(Exception e){

        }
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
    public void addObserver(Observer observer, String playerName) {
        //TODO implementation
    }

    @Override
    public ChatModel getChatController() {
        return chatSystem;
    }

    @Override
    public GameModel getGameController() {
        return this;
    }


}
