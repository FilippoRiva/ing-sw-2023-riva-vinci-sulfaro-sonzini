package it.polimi.myshelfie.model;

import it.polimi.myshelfie.GameConfig;
import it.polimi.myshelfie.model.chatting.ChatSystem;
import it.polimi.myshelfie.model.exceptions.FullColumnException;
import it.polimi.myshelfie.model.goals.CommonGoal;
import it.polimi.myshelfie.model.goals.CommonGoalFactory;
import it.polimi.myshelfie.model.goals.PersonalGoal;
import it.polimi.myshelfie.model.goals.PersonalGoalDefaults;
import it.polimi.myshelfie.model.interfaces.ChatModel;
import it.polimi.myshelfie.model.interfaces.GameModel;
import it.polimi.myshelfie.model.interfaces.LobbyModel;

import java.security.InvalidParameterException;
import java.util.*;

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
    public void initialize() {
        turn = players.get(0);
        commonGoals = CommonGoalFactory.getGoals(gameConfig.getNumPlayers(), gameConfig.getNumCommonGoal());
        livingRoom = new LivingRoom(gameConfig.getNumTilesPerType(), gameConfig.getLivingRoomSize(), gameConfig.getNumPlayers());

        List<PersonalGoal> goals = generatePersonalGoals(gameConfig.getNumPlayers(), gameConfig.getNumPersonalGoals());
        Iterator<PersonalGoal> itr = goals.iterator();
        for(Player p : players){
            p.setBookshelf(new Bookshelf(gameConfig.getBookshelfDimension()));
            for(int i = 0 ; i < gameConfig.getNumPersonalGoals(); i++){
                p.setPersonalGoal(itr.next());
            }
        }

    }

    private List<PersonalGoal> generatePersonalGoals(int numOfPlayers, int numOfPersonalGoal){
        ArrayList<Integer> numbersList = new ArrayList<>();
        Random rand = new Random();
        for(int i = 0; i < numOfPlayers*numOfPersonalGoal; ){
            int randomInt = rand.nextInt(PersonalGoalDefaults.values().length);
            if(!numbersList.contains(randomInt)){
                numbersList.add(randomInt);
                i++;
            }
        }
        ArrayList<PersonalGoal> result = new ArrayList<>();
        for(Integer i : numbersList){
            result.add(new PersonalGoal(PersonalGoalDefaults.values()[i]));
        }
        return result;
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
        if(!positionsAreValid(positions)) throw new InvalidParameterException("positions not valid");
        if(!playerName.equals(turn.getName())) throw new InvalidParameterException("player playing in the wrong turn");

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

    private boolean positionsAreValid(List<Position> positions){
        for(Position p : positions){
            if(!livingRoom.isValid(p)) return false;
            if(!livingRoom.atLeastOneSideFree(p)) return false;
        }

        return aligned(positions);
    }

    private boolean aligned(List<Position> positions){
        return positions.stream().map(Position::getX).distinct().limit(2).count()==1 ||
                positions.stream().map(Position::getY).distinct().limit(2).count()==1;
    }

    @Override
    public void orderChosenTiles(List<ItemTile> orderedTiles, String playerName) throws InvalidParameterException {
        if(!playerName.equals(turn.getName())) throw new InvalidParameterException("player playing in the wrong turn");

        for(Player player : players) {
            if (player.getName().equals(playerName)) {
                // for ArrayLists the complexity of containsAll is O(n*m) where n are the items in the list and m are
                // the items of the argument. Creating a HashSet is O(n) and the containsAll is O(1) so it's faster
                if (!new HashSet<>(player.getTilesTaken()).containsAll(orderedTiles) || player.getTilesTaken().size() != orderedTiles.size())
                    throw new InvalidParameterException("tiles different from the taken in the draw move");
                player.setTilesTaken(orderedTiles);
                return;
            }
        }
    }

    @Override
    public void insertTiles(String playerName, int column) throws InvalidParameterException, FullColumnException {
        if(!playerName.equals(turn.getName())) throw new InvalidParameterException("player playing in the wrong turn");
        if(column < 0 || column > gameConfig.getBookshelfDimension().width-1) throw new InvalidParameterException("column not available");

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
    public ChatModel getChatModel() {
        return chatSystem;

    }
    @Override
    public GameModel getGameModel() {
        return this;
    }

    //for test:
    public String getTurn() {
        return turn.getName();
    }

    public Player getPlayer(String playerName) {
        for(Player p : players){
            if(p.getName().equals(playerName)){
                return p;
            }
        }
        return null;
    }
}
