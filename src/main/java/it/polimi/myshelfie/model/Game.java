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
import it.polimi.myshelfie.model.modelviews.*;
import it.polimi.myshelfie.model.events.GameEvent;
import it.polimi.myshelfie.model.events.LobbyEvent;
import it.polimi.myshelfie.utils.Observer;


import java.util.*;

public class Game implements GameModel, LobbyModel {
    private GameConfig gameConfig;
    private final it.polimi.myshelfie.utils.Observable<LobbyEvent, LobbyModelView> lobbyObservable;
    private final it.polimi.myshelfie.utils.Observable<GameEvent, GameModelView> gameObservable;
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
        lobbyObservable.notifyObservers(LobbyEvent.NEW_CONFIG, getLobbyModelView());
    }

    @Override
    public void addPlayer(String playerName) throws LobbyFullException, InvalidParameterException {
        if(isLobbyFull()) throw new LobbyFullException(true);
        if(!isPlayerNameValid(playerName)) throw new InvalidParameterException("Player name is not valid");
        if(players.size()==0) roomOwner = playerName;
        players.add(new Player(playerName));
        lobbyObservable.setChanged();
        lobbyObservable.notifyObservers(LobbyEvent.PLAYER_ADD, getLobbyModelView());
        chatSystem.addPlayerToChat(playerName);
    }


    /**
     * Returns true when the number of the players that wants to play is grater than the param NumPlayers set in the game config.
     * @return true or false.
     */
    public boolean isLobbyFull() {
        return players.size() == gameConfig.getNumPlayers();
    }

    /**
     * Returns true if the chosen username is already taken by another player.
     * @param playerName is the username the player choose to play with.
     * @return true or false.
     */
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
                if(p.getName().equals(roomOwner) && players.size()!=0) roomOwner = players.get(0).getName();
                lobbyObservable.setChanged();
                lobbyObservable.notifyObservers(LobbyEvent.PLAYER_REMOVE, getLobbyModelView());
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
    public void initialize() throws LobbyFullException{
        if(!isLobbyFull()) throw new LobbyFullException(false);
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
            List<PersonalGoal> playerGoals = new ArrayList<>(goals.subList(0,gameConfig.getNumPersonalGoals()));
            p.setPersonalGoals(playerGoals);
            for(PersonalGoal pp : playerGoals){
                goals.remove(pp);
            }
        }
    }

    /**
     * Return the list of personal goals that have to be assigned to every player.
     * @param numOfPlayers is the number of the players in the game.
     * @param numOfPersonalGoal is the number of the personal goals assigned to every player.
     * @return the list of personal goals that have to be assigned to every player in the game.
     * @throws Exception ??
     */
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
        if(state==GameState.FIRST_DRAW) {
            this.currentState = GameState.DRAW;
            gameObservable.setChanged();
            gameObservable.notifyObservers(GameEvent.GAME_START, new FullGameModelView(this));
            return;
        }
        this.currentState = state;
        if(state==GameState.LOBBY) {
            lobbyObservable.setChanged();
            lobbyObservable.notifyObservers(new LobbyModelView(this));
        }
        else {
            gameObservable.setChanged();
            switch (state){
                case DRAW, LAST_DRAW -> gameObservable.notifyObservers(GameEvent.COUNTING_POINTS, new EndTurnModelView(this));
                case ORGANIZE, LAST_ORGANIZE -> gameObservable.notifyObservers(GameEvent.DRAW, new DrawModelView(this));
                case INSERT, LAST_INSERT -> gameObservable.notifyObservers(GameEvent.ORGANIZE, new OrganizeModelView(this));
                case COUNTING_POINTS, LAST_COUNTING_POINTS -> gameObservable.notifyObservers(GameEvent.INSERT, new InsertModelView(this));
                case WIN -> gameObservable.notifyObservers(GameEvent.WIN,new FullGameModelView(this));
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
        if(positions.size() > 3) throw new InvalidParameterException("player has taken more tiles than allowed");
        if(positions.size() > currentPlayer.getBookshelf().columnFreeSpace() || positions.size() > 3) throw new InvalidParameterException("player has taken more tiles than allowed");



        List<ItemTile> items = new ArrayList<>();
        for(Position p : positions){
            items.add(livingRoom.removeTile(p.getX(), p.getY()));
        }

        currentPlayer.setTakenTiles(items);
    }

    /**
     * Returns true if the selected tiles from the board can be taken.
     * @param positions are the positions of the items selected from the board.
     * @return true or false.
     */
    private boolean positionsAreValid(List<Position> positions){
        for(Position p : positions){
            if(!livingRoom.isValid(p)) return false;
            if(livingRoom.emptyPosition(p)) return false;
            if(!livingRoom.atLeastOneSideFree(p)) return false;
        }

        return aligned(positions);
    }

    private boolean aligned(List<Position> positions){
        if(positions.stream().map(Position::getX).distinct().limit(2).count()==1){
            positions.sort(Comparator.comparingInt(Position::getY));
            for(int i=0; i< positions.size()-1; i++){
                if(!(positions.get(i).getY() == positions.get(i+1).getY()+1)) return false;
            }
            return true;
        }else if(positions.stream().map(Position::getY).distinct().limit(2).count()==1){
            positions.sort(Comparator.comparingInt(Position::getX));
            for(int i=0; i< positions.size()-1; i++){
                if(!(positions.get(i).getX()+1 == positions.get(i+1).getX())) return false;
            }
            return true;
        }else{
            return false;
        }
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
                if(numTakenTiles == 0) throw new InvalidParameterException("player has not yet drawn any tiles");
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
            for(Player p : players){
                if(p.getName().equals(playerName)){
                    if(cm.getTokens().size()!=0 && !p.getObtainedTokens().stream().map(Token::getRomanNumeral).toList().contains(cm.peekToken().getRomanNumeral())) {
                        if (cm.isVerified(currentPlayer.getBookshelf().getBookshelfItems())) {
                            addToken(playerName, cm.popToken());
                        }
                    }
                    break;
                }
            }
        }
    }

    /**
     * When a player completes a common goal, he obtained the token on the top of its stack, and
     * the points on the token are added to the points of the player.
     * @param playerName is the name of the player that obtained the token.
     * @param token is the token obtained.
     */
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

    /**
     * @return the name of the player that created the lobby.
     */
    public String getRoomOwner(){
        return roomOwner;
    }

    //for test:

    /**
     * @return the name of the player playing in that turn.
     */
    public String getCurrentPlayer() {
        return currentPlayer.getName();
    }
    public String getLastPlayer() {
        int index = players.indexOf(currentPlayer);
        if (index==0) {
            return players.get(players.size()-1).getName();
        }
        return players.get(index-1).getName();
    }

    public Player getPlayer(String playerName) {
        for(Player p : players){
            if(p.getName().equals(playerName)){
                return p;
            }
        }
        return null;
    }


    /**
     * Return true if the bookshelf of the selected player is full.
     * @param playerName is the name of the player whose bookshelf has to be controlled.
     * @return true of false.
     */
    @Override
    public boolean isBookshelfFull(String playerName){
        return this.getPlayer(playerName).getBookshelf().isFull();
    }

    /**
     * Return true if the player playing is the last one of that turn.
     * @param playerName is the name of the player that has to be controlled.
     * @return true or false.
     */
    public boolean isLastPlayer(String playerName){
        return players.get(players.size() - 1).getName().equals(playerName);
    }

    @Override
    public String getLobbyName(){
        return lobbyName;
    }
    @Override
    public void addGameObserver(Observer<GameEvent, GameModelView> obs) {
        gameObservable.addObserver(obs);
        gameObservable.setChanged();
        gameObservable.notifyObservers(new FullGameModelView(this));
    }

    @Override
    public void removeGameObserver(Observer<GameEvent, GameModelView> obs) {
        gameObservable.deleteObserver(obs);
    }

    @Override
    public synchronized FullGameModelView getGameModelView() {
        return new FullGameModelView(this);
    }

    @Override
    public void addLobbyObserver(Observer<LobbyEvent, LobbyModelView> obs) {
        lobbyObservable.addObserver(obs);
        lobbyObservable.setChanged();
        lobbyObservable.notifyObservers(new LobbyModelView(this));
    }

    @Override
    public void removeLobbyObserver(Observer<LobbyEvent, LobbyModelView> obs) {
        lobbyObservable.deleteObserver(obs);
    }

    @Override
    public synchronized LobbyModelView getLobbyModelView() {
        return new LobbyModelView(this);
    }

    /**
     *
     * @return the list of the players in the game.
     */
    public List<Player> getPlayers(){
        return new ArrayList<>(players);
    }

    /**
     * Returns the number of the players of the game in a specific moment.
     * @return the size of the list of players.
     */
    public int getCurrentSize() {
        return players.size();
    }

    /**
     * Return the max number of players admitted in the game.
     * @return a number.
     */
    public int getMaxSize(){
        return gameConfig.getNumPlayers();
    }

    /**
     *
     * @return the game config.
     */
    public GameConfig getGameConfig() {
        return gameConfig;
    }

    /**
     * Return the list with all the player names.
     * @return a list of strings.
     */
    public List<String> getPlayersNames() {
        return players.stream().map(Player::getName).toList();
    }

    /**
     * Return the list of the common goals of the game.
     * @return a list of common goals.
     */
    public List<CommonGoal> getCommonGoals() {
        return new ArrayList<>(commonGoals);
    }

    /**
     * Return a copy of the living room.
     * @return a matrix of the item tiles in the living room.
     */
    public ItemTile[][] getLivingRoom() {
        ItemTile[][] livingRoom = this.livingRoom.getTable();
        ItemTile[][] result = new ItemTile[gameConfig.getLivingRoomSize()][gameConfig.getLivingRoomSize()];
        for(int i = 0; i<gameConfig.getLivingRoomSize(); i++){
            if (gameConfig.getLivingRoomSize() >= 0)
                System.arraycopy(livingRoom[i], 0, result[i], 0, gameConfig.getLivingRoomSize());
        }
        return result;
    }

    /**
     *
     * Return the number of item tiles remained in the bag.
     * @return the dimension of the bag stack.
     */
    public int getBagSize(){
        return livingRoom.getBag().size();
    }

    /**
     * Return the name of the game.
     * @return a string with the name of the game.
     */
    public String getName() {
        return lobbyName;
    }

    /**
     * Initialize the name of the game.
     * @param name is the name that has to be assigned to the game.
     */
    public void setName(String name) {
        if(this.lobbyName == null) {
            this.lobbyName = name;
        }
    }

    @Override
    public boolean isRoomOwner(String username){
        if(username==null) return false;
        return roomOwner.equals(username);
    }

    @Override
    public boolean hasStarted(){
        return commonGoals!=null;
    }
}
