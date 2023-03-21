package it.polimi.myshelfie.model;

import it.polimi.myshelfie.GameConfig;
import it.polimi.myshelfie.model.chatting.ChatSystem;
import it.polimi.myshelfie.model.exceptions.GameFullException;
import it.polimi.myshelfie.model.exceptions.NameTakenException;
import it.polimi.myshelfie.model.goals.CommonGoal;
import it.polimi.myshelfie.model.goals.PersonalGoal;
import it.polimi.myshelfie.model.interfaces.ChatController;
import it.polimi.myshelfie.model.interfaces.GameController;
import it.polimi.myshelfie.model.interfaces.LobbyController;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

public class Game implements GameController, LobbyController {
    private GameConfig gameConfig;
    private String gameName;
    private String turn;
    private GameState currentState;
    private ChatSystem chatSystem;
    private CommonGoal[] commonGoals;
    private LivingRoom livingRoom;
    private ArrayList<Player> players;

    public Game(String gameName){
        this.gameName = gameName;
        currentState = GameState.LOBBY;
        chatSystem = new ChatSystem();
        players = new ArrayList<>();
    }

    public void addPlayer(Player player) throws GameFullException, NameTakenException {
        players.add(player);
    }


    @Override
    public String getName() {
        return gameName;
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
        turn = players.get(0).getName();
        commonGoals = new CommonGoal[gameConfig.getNumCommonGoal()];
        livingRoom = new LivingRoom(gameConfig.getNumTilesPerType(), gameConfig.getLivingRoomSize(), gameConfig.getNumPlayers());
    }

    @Override
    public void takeTilesFromLivingRoom(List<Position> takenTiles) {
        for(Position p : takenTiles){
            livingRoom.removeTile(p.getX(), p.getY());
        }
    }

    @Override
    public void addTilesInBookshelf(String playerName, List<ItemTile> takenTiles, int column) {
        //TODO check if full
        for(Player p : players){
            if(p.getName().equals(playerName)){
                ItemTile[][] bookshelf = p.getBookshelf().getBookshelfItems();
                for(int i = bookshelf.length-1; i>=0; i--){
                    if(bookshelf[i][column] == ItemTile.EMPTY){
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
                return;
            }
        }
    }

    @Override
    public void refillGame() throws Exception {
        livingRoom.refill();
    }

    @Override
    public void nextTurn() {
        int currentTurn;

        for(Player p : players){
            if(p.getName().equals(turn)){
                currentTurn = players.indexOf(p);
                if(currentTurn < players.size()-1)
                    turn = players.get(currentTurn+1).getName();
                else
                    turn = players.get(0).getName();
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
    public PersonalGoal getPersonalGoal(String playerName) {
        for(Player p : players){
            if(p.getName().equals(playerName)){
                return p.getPersonalGoal();
            }
        }
        return null;
    }

    @Override
    public int getNumberOfPlayers() {
        return players.size();
    }

    @Override
    public GameState getState() {
        return currentState;
    }

    @Override
    public String getCurrentPlayer() {
        return turn;
    }

    @Override
    public String getLastPlayer() {
        return players.get(players.size()-1).getName();
    }

    @Override
    public boolean isPlayerNameValid(String playerName) {
        for(Player p : players){
            if(p.getName().equals(playerName)){
                return false;
            }
        }
        return false;
    }

    @Override
    public void addPlayer(String name) {
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
    public ChatController getChatController() {
        return chatSystem;
    }

    @Override
    public GameController getGameController() {
        return this;
    }
}
