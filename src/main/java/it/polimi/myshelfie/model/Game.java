package it.polimi.myshelfie.model;

import it.polimi.myshelfie.GameConfig;
import it.polimi.myshelfie.model.chatting.ChatSystem;
import it.polimi.myshelfie.model.exceptions.FullColumnException;
import it.polimi.myshelfie.model.exceptions.InvalidParameterException;
import it.polimi.myshelfie.model.exceptions.LobbyFullException;
import it.polimi.myshelfie.model.goals.*;
import it.polimi.myshelfie.model.interfaces.ChatModel;
import it.polimi.myshelfie.model.interfaces.GameModel;
import it.polimi.myshelfie.model.interfaces.LobbyModel;
import it.polimi.myshelfie.model.modelviews.GameModelView;
import it.polimi.myshelfie.model.modelviews.LobbyModelView;
import it.polimi.myshelfie.network.events.GameEvent;
import it.polimi.myshelfie.network.events.LobbyEvent;
import it.polimi.myshelfie.utils.Observer;


import java.util.*;

public class Game implements GameModel, LobbyModel {
    private GameConfig gameConfig;
    private final it.polimi.myshelfie.utils.Observable<LobbyEvent> lobbyObservable;
    private final it.polimi.myshelfie.utils.Observable<GameEvent> gameObservable;
    private Player currentPlayer;
    private GameState currentState;
    private final ChatSystem chatSystem;
    private List<CommonGoal> commonGoals;
    private LivingRoom livingRoom;
    private String lobbyName;
    private final ArrayList<Player> players;
    private String roomOwner;


    public Game(String roomName){
        this.lobbyObservable = new it.polimi.myshelfie.utils.Observable<>();
        this.gameObservable = new it.polimi.myshelfie.utils.Observable<>();
        try {
            this.gameConfig = new GameConfig(2);
        } catch (InvalidParameterException ignored) {
        }
        this.currentState = GameState.LOBBY;
        this.chatSystem = new ChatSystem();
        this.players = new ArrayList<>();
        this.lobbyName =roomName;
    }

    @Override
    public void setGameConfig(GameConfig gameConfig) throws InvalidParameterException{
        //if(isLobbyFull()) throw new Exception("lobby is full");
        if(gameConfig.getNumPlayers()< players.size()) throw new InvalidParameterException("too few players");
        this.gameConfig = gameConfig;
        lobbyObservable.setChanged();
        lobbyObservable.notifyObservers(LobbyEvent.NEW_CONFIG);
    }

    @Override
    public void addPlayer(String playerName) throws LobbyFullException, InvalidParameterException {
        if(isLobbyFull()) throw new LobbyFullException();
        if(!isPlayerNameValid(playerName)) throw new InvalidParameterException("player name is not valid");
        if(players.size()==0) roomOwner = playerName;
        players.add(new Player(playerName));
        lobbyObservable.setChanged();
        lobbyObservable.notifyObservers(LobbyEvent.PLAYER_ADD);
    }

    public boolean isLobbyFull() {
        return players.size() == gameConfig.getNumPlayers();
    }

    public boolean isPlayerNameValid(String playerName) {
        //TODO define rules for available characters in playerName
        for(Player p : players){
            if(p.getName().equals(playerName)){
                return false;
            }
        }
        return true;
    }

    @Override
    public void removePlayer(String playerName) throws InvalidParameterException{
        for(Player p : players){
            if(p.getName().equals(playerName)){
                players.remove(p);
                if(p.getName().equals(roomOwner)) roomOwner = players.get(0).getName();
                lobbyObservable.setChanged();
                lobbyObservable.notifyObservers(LobbyEvent.PLAYER_REMOVE);
                return;
            }
        }
        throw new InvalidParameterException("player is not in this lobby");
    }

    @Override
    public boolean isLobbyEmpty(){
        return players.size() == 0;
    }


    @Override
    public void initialize() {
        //TODO check that who's starting the game is the lobby creator
        if(!isLobbyFull()) return;
        Collections.shuffle(players);
        currentPlayer = players.get(0);
        try {
            commonGoals = CommonGoalDefaultsFactory.getGoals(gameConfig.getNumPlayers(), gameConfig.getNumCommonGoal());
            livingRoom = new LivingRoom(gameConfig.getNumTilesPerType(), gameConfig.getLivingRoomSize(), gameConfig.getNumPlayers());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        List<PersonalGoal> goals;
        try {
            goals = generatePersonalGoals(gameConfig.getNumPlayers(), gameConfig.getNumPersonalGoals());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        for(Player p : players){
            p.setBookshelf(new Bookshelf(gameConfig.getBookshelfDimension()));
            List<PersonalGoal> playerGoals = goals.subList(0,gameConfig.getNumPersonalGoals());
            p.setPersonalGoals(playerGoals);
            for(PersonalGoal pp : playerGoals){
                goals.remove(pp);
            }
        }
    }

    private List<PersonalGoal> generatePersonalGoals(int numOfPlayers, int numOfPersonalGoal) throws Exception {
        return PersonalGoalFactory.getDistinctPersonalGoals(numOfPersonalGoal,gameConfig.getBookshelfDimension(),numOfPlayers,gameConfig.getPersonalGoalRatio());
    }


    @Override
    public void nextTurn() {
        int currentTurn;

        for(Player p : players){
            if(p.equals(currentPlayer)){
                currentTurn = players.indexOf(p);
                currentPlayer = players.get((currentTurn+1)% players.size());
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
        if(state==GameState.LOBBY) {
            lobbyObservable.setChanged();
            lobbyObservable.notifyObservers();
        }
        else {
            gameObservable.setChanged();
            switch (state){
                case DRAW, LASTDRAW -> gameObservable.notifyObservers(GameEvent.REFILL);
                case ORGANIZE, LASTORGANIZE -> gameObservable.notifyObservers(GameEvent.DRAW);
                case INSERT, LASTINSERT -> gameObservable.notifyObservers(GameEvent.ORGANIZE);
                case COUNTINGPOINTS, LASTCOUNTINGPOINTS -> gameObservable.notifyObservers(GameEvent.INSERT);
                case REFILL -> gameObservable.notifyObservers(GameEvent.COUNTINGPOINTS);
                case WIN -> gameObservable.notifyObservers(GameEvent.WIN);
            }
        }
    }

    @Override
    public GameState getState() {
        return currentState;
    }

    @Override
    public void drawTiles(List<Position> positions, String playerName) throws InvalidParameterException {
        if(!playerName.equals(currentPlayer.getName())) throw new InvalidParameterException("player playing in the wrong turn");
        if(!positionsAreValid(positions)) throw new InvalidParameterException("positions are not valid");

        List<ItemTile> items = new ArrayList<>();
        for(Position p : positions){
            items.add(livingRoom.removeTile(p.getX(), p.getY()));
        }
        for(Player player : players) {
            if (player.getName().equals(playerName)) {
                player.setTakenTiles(items);
                break;
            }
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
        if(!playerName.equals(currentPlayer.getName())) throw new InvalidParameterException("player playing in the wrong turn");
        if(orderedTiles.size()==0) { //keeping the order
            return;
        }
        for(Player player : players) {
            if (player.getName().equals(playerName)) {
                if (!player.getTakenTiles().containsAll(orderedTiles) || player.getTakenTiles().size() != orderedTiles.size())
                    throw new InvalidParameterException("tiles different from the taken ones in the draw move");
                player.setTakenTiles(orderedTiles);
                return;
            }
        }
    }

    @Override
    public void insertTiles(String playerName, int column) throws InvalidParameterException, FullColumnException {
        if(!playerName.equals(currentPlayer.getName())) throw new InvalidParameterException("player playing in the wrong turn");
        if(column < 0 || column > gameConfig.getBookshelfDimension().width-1) throw new InvalidParameterException("column not available");

        for (Player p : players) {
            if (p.getName().equals(playerName)) {
                int numTakenTiles = p.getTakenTiles().size();
                if (!p.getBookshelf().isColumnFillable(numTakenTiles, column))
                    throw new FullColumnException(column);
                for(ItemTile t: p.getTakenTiles()) {
                    p.getBookshelf().insertTile(column, t);
                }
                p.clearTakenTiles();
                return;
            }
        }
    }

    @Override
    public void checkCommonGoal(String playerName) {
        for(CommonGoal cm : commonGoals){
            if(cm.isVerified(currentPlayer.getBookshelf().getBookshelfItems())){
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

    @Override
    public void setRoomOwner(String owner) {
        this.roomOwner = owner;
    }

    public String getRoomOwner(){
        return roomOwner;
    }

    //for test:
    public String getCurrentPlayer() {
        return currentPlayer.getName();
    }

    public Player getPlayer(String playerName) {
        for(Player p : players){
            if(p.getName().equals(playerName)){
                return p;
            }
        }
        return null;
    }

    public boolean isBookshelfFull(String playerName){
        return this.getPlayer(playerName).getBookshelf().isFull();
    }

    public boolean isLastPlayer(String playerName){
        return players.get(players.size() - 1).getName().equals(playerName);
    }

    @Override
    public String getLobbyName(){
        return lobbyName;
    }
    @Override
    public void addGameObserver(Observer<GameEvent> obs) {
        gameObservable.addObserver(obs);
        gameObservable.setChanged();
        gameObservable.notifyObservers();
    }

    @Override
    public synchronized GameModelView getGameModelView() {
        return new GameModelView(this);
    }

    @Override
    public void addLobbyObserver(Observer<LobbyEvent> obs) {
        lobbyObservable.addObserver(obs);
        lobbyObservable.setChanged();
        lobbyObservable.notifyObservers();
    }

    @Override
    public synchronized LobbyModelView getLobbyModelView() {
        return new LobbyModelView(this);
    }

    public List<Player> getPlayers(){
        return new ArrayList<>(players);
    }

    public int getCurrentSize() {
        return players.size();
    }
    public int getMaxSize(){
        return gameConfig.getNumPlayers();
    }

    public GameConfig getGameConfig() {
        return gameConfig;
    }

    public List<String> getPlayersNames() {
        return players.stream().map(Player::getName).toList();
    }

    public List<CommonGoal> getCommonGoals() {
        return new ArrayList<>(commonGoals);
    }

    public ItemTile[][] getLivingRoom() {
        ItemTile[][] livingRoom = this.livingRoom.getTable();
        ItemTile[][] result = new ItemTile[gameConfig.getLivingRoomSize()][gameConfig.getLivingRoomSize()];
        for(int i = 0; i<gameConfig.getLivingRoomSize(); i++){
            if (gameConfig.getLivingRoomSize() >= 0)
                System.arraycopy(livingRoom[i], 0, result[i], 0, gameConfig.getLivingRoomSize());
        }
        return result;
    }
    public int getBagSize(){
        return livingRoom.getBag().size();
    }
    public String getName() {
        return lobbyName;
    }

    public void setName(String name) {
        if(this.lobbyName == null) {
            this.lobbyName = name;
        }
    }
}
