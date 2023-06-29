package it.polimi.myshelfie.model;

import it.polimi.myshelfie.GameConfig;
import it.polimi.myshelfie.model.chatting.ChatSystem;
import it.polimi.myshelfie.model.events.GameEvent;
import it.polimi.myshelfie.model.events.LobbyEvent;
import it.polimi.myshelfie.model.exceptions.FullColumnException;
import it.polimi.myshelfie.model.exceptions.InvalidParameterException;
import it.polimi.myshelfie.model.exceptions.LobbyFullException;
import it.polimi.myshelfie.model.goals.CommonGoal;
import it.polimi.myshelfie.model.goals.CommonGoalDefaultsFactory;
import it.polimi.myshelfie.model.goals.PersonalGoal;
import it.polimi.myshelfie.model.goals.PersonalGoalFactory;
import it.polimi.myshelfie.model.interfaces.ChatModel;
import it.polimi.myshelfie.model.interfaces.GameModel;
import it.polimi.myshelfie.model.interfaces.LobbyModel;
import it.polimi.myshelfie.model.modelviews.*;
import it.polimi.myshelfie.utils.Observer;
import it.polimi.myshelfie.utils.ServerLogger;


import java.util.*;
import java.util.logging.Level;

/**
 * Represents and entire game: it has all the settings, a name, the players, the living room, the common goals and the chat system.
 */
public class Game implements GameModel, LobbyModel {
    private final it.polimi.myshelfie.utils.Observable<LobbyEvent, LobbyModelView> lobbyObservable;
    private final it.polimi.myshelfie.utils.Observable<GameEvent, GameModelView> gameObservable;
    private final ChatSystem chatSystem;
    private final String lobbyName;
    private final ArrayList<Player> players;
    private GameConfig gameConfig;
    private Player currentPlayer;
    private GameState currentState;
    private List<CommonGoal> commonGoals;
    private LivingRoom livingRoom;
    private String roomOwner;


    /**
     * Constructor.
     *
     * @param roomName the name of the game.
     */
    public Game(String roomName) {
        this.lobbyObservable = new it.polimi.myshelfie.utils.Observable<>();
        this.gameObservable = new it.polimi.myshelfie.utils.Observable<>();
        try {
            this.gameConfig = new GameConfig(2);
        } catch (InvalidParameterException e) {
            ServerLogger.log(Level.SEVERE, "Critical Failure in Game Initialization. Unable to create Default Game Config. Shutting Down");
            e.printStackTrace();
            System.exit(-1);
        }
        this.currentState = GameState.LOBBY;
        this.chatSystem = new ChatSystem();
        this.players = new ArrayList<>();
        this.lobbyName = roomName;
    }

    /**
     * {@inheritDoc}
     *
     * @param playerName is the name of the new player that has to be added to the game.
     * @throws LobbyFullException        if the lobby is full
     * @throws InvalidParameterException if the playerName is not available
     */
    @Override
    public synchronized void addPlayer(String playerName) throws LobbyFullException, InvalidParameterException {
        if (isLobbyFull()) throw new LobbyFullException(true);
        if (!isPlayerNameValid(playerName)) throw new InvalidParameterException("Player name is not valid");
        if (players.size() == 0) roomOwner = playerName;
        players.add(new Player(playerName));
        lobbyObservable.setChanged();
        lobbyObservable.notifyObservers(LobbyEvent.PLAYER_ADD, getLobbyModelView());
        chatSystem.addPlayerToChat(playerName);
    }

    /**
     * Checks if the lobby is full.
     *
     * @return true when the number of the players that wants to play is grater than the param NumPlayers set in the game config, false otherwise.
     */
    private boolean isLobbyFull() {
        return players.size() == gameConfig.getNumPlayers();
    }

    /**
     * Checks if the player name is valid.
     *
     * @param playerName is the username the player choose to play with.
     * @return true if the chosen username is already taken by another player, false otherwise.
     */
    private boolean isPlayerNameValid(String playerName) {
        for (Player p : players) {
            if (p.getName().equals(playerName)) {
                return false;
            }
        }
        return true;
    }

    /**
     * {@inheritDoc}
     *
     * @param playerName is the name of the player that has to be removed from the game.
     * @throws InvalidParameterException if the requested player is not in this lobby.
     */
    @Override
    public synchronized void removePlayer(String playerName) throws InvalidParameterException {
        for (Player p : players) {
            if (p.getName().equals(playerName)) {
                players.remove(p);
                if (p.getName().equals(roomOwner) && players.size() != 0) roomOwner = players.get(0).getName();
                lobbyObservable.setChanged();
                lobbyObservable.notifyObservers(LobbyEvent.PLAYER_REMOVE, getLobbyModelView());
                return;
            }
        }
        throw new InvalidParameterException("player is not in this lobby");
    }

    /**
     * {@inheritDoc}
     *
     * @return true if the lobby is empty
     */
    @Override
    public boolean isLobbyEmpty() {
        return players.size() == 0;
    }

    /**
     * {@inheritDoc}
     *
     * @throws LobbyFullException if the lobby is not full.
     */
    @Override
    public synchronized void initialize() throws LobbyFullException {
        if (!isLobbyFull()) throw new LobbyFullException(false);
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

        for (Player p : players) {
            p.setBookshelf(new Bookshelf(gameConfig.getBookshelfDimension()));
            List<PersonalGoal> playerGoals = new ArrayList<>(goals.subList(0, gameConfig.getNumPersonalGoals()));
            p.setPersonalGoals(playerGoals);
            for (PersonalGoal pp : playerGoals) {
                goals.remove(pp);
            }
        }
    }

    /**
     * Return the list of personal goals that have to be assigned to every player.
     *
     * @param numOfPlayers      the number of the players in the game.
     * @param numOfPersonalGoal the number of the personal goals assigned to every player.
     * @return the list of personal goals that have to be assigned to every player in the game.
     * @throws Exception if the number of personal goals per player is not valid.
     */
    private List<PersonalGoal> generatePersonalGoals(int numOfPlayers, int numOfPersonalGoal) throws Exception {
        return PersonalGoalFactory.getDistinctPersonalGoals(numOfPersonalGoal, gameConfig.getBookshelfDimension(), numOfPlayers, gameConfig.getPersonalGoalRatio());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void nextTurn() {
        int currentTurn;

        for (Player p : players) {
            if (p.equals(currentPlayer)) {
                currentTurn = players.indexOf(p);
                currentPlayer = players.get((currentTurn + 1) % players.size());
                return;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void calculateWinResults() {
        for (Player player : players) {
            player.calculatePoints();
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param playerName the name of the player who obtains the token.
     */
    @Override
    public void addEndToken(String playerName) {
        for (Player p : players) {
            if (p.getName().equals(playerName)) {
                p.addObtainedToken(Token.ENDGAME);
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * @return the current GameState.
     */
    @Override
    public GameState getState() {
        return currentState;
    }

    /**
     * Sets a {@link GameState}
     *
     * @param state the new state.
     */
    @Override
    public synchronized void setState(GameState state) {
        if (state == GameState.FIRST_DRAW) {
            this.currentState = GameState.DRAW;
            gameObservable.setChanged();
            gameObservable.notifyObservers(GameEvent.GAME_START, new FullGameModelView(this));
            return;
        }
        this.currentState = state;
        if (state == GameState.LOBBY) {
            lobbyObservable.setChanged();
            lobbyObservable.notifyObservers(new LobbyModelView(this));
        } else {
            gameObservable.setChanged();
            switch (state) {
                case DRAW, LAST_DRAW ->
                        gameObservable.notifyObservers(GameEvent.COUNTING_POINTS, new EndTurnModelView(this));
                case ORGANIZE, LAST_ORGANIZE -> gameObservable.notifyObservers(GameEvent.DRAW, new DrawModelView(this));
                case INSERT, LAST_INSERT ->
                        gameObservable.notifyObservers(GameEvent.ORGANIZE, new OrganizeModelView(this));
                case COUNTING_POINTS, LAST_COUNTING_POINTS ->
                        gameObservable.notifyObservers(GameEvent.INSERT, new InsertModelView(this));
                case WIN -> gameObservable.notifyObservers(GameEvent.WIN, new FullGameModelView(this));
            }
        }
    }

    /**
     * @param positions  the positions of the tiles that have to be removed from the board, where the first in the list is the first one to be
     *                   inserted into the player's bookshelf, and the last one is the last one to be inserted.
     * @param playerName the name of the player drawing the tiles.
     * @throws InvalidParameterException if the player is :
     *                                   <ul>
     *                                       <li>playing in the wrong turn</li>
     *                                       <li>drawing and empty list of Position</li>
     *                                       <li>drawing non valid positions</li>
     *                                       <li>drawing more tiles then allowed</li>
     *                                   </ul>
     */
    @Override
    public synchronized void drawTiles(List<Position> positions, String playerName) throws InvalidParameterException {
        if (!playerName.equals(currentPlayer.getName()))
            throw new InvalidParameterException("player playing in the wrong turn");
        if (positions.size() == 0) throw new InvalidParameterException("no tiles selected");
        if (!positionsAreValid(positions)) throw new InvalidParameterException("positions are not valid");
        if (positions.size() > currentPlayer.getBookshelf().columnFreeSpace() || positions.size() > 3)
            throw new InvalidParameterException("player has taken more tiles than allowed");

        List<ItemTile> items = new ArrayList<>();
        for (Position p : positions) {
            items.add(livingRoom.removeTile(p.getX(), p.getY()));
        }

        currentPlayer.setTakenTiles(items);
    }

    /**
     * Returns true if the selected tiles from the board can be taken.
     *
     * @param positions are the positions of the items selected from the board.
     * @return true or false.
     */
    private boolean positionsAreValid(List<Position> positions) {
        for (Position p : positions) {
            if (!livingRoom.isValid(p)) return false;
            if (livingRoom.emptyPosition(p)) return false;
            if (!livingRoom.atLeastOneSideFree(p)) return false;
        }

        return !Position.arePositionsMisaligned(positions);
    }

    /**
     * {@inheritDoc}
     *
     * @param orderedTiles the tiles taken form the board.
     * @param playerName   the player playing.
     * @throws InvalidParameterException if the player is playing in the wrong turn or the ordered tiles are different from the taken ones in the draw move.
     */
    @Override
    public synchronized void orderChosenTiles(List<ItemTile> orderedTiles, String playerName) throws InvalidParameterException {
        if (!playerName.equals(currentPlayer.getName()))
            throw new InvalidParameterException("player playing in the wrong turn");
        if (orderedTiles == null || orderedTiles.size() == 0) { //keeping the order
            return;
        }
        for (Player player : players) {
            if (player.getName().equals(playerName)) {
                if (!player.getTakenTiles().containsAll(orderedTiles) || player.getTakenTiles().size() != orderedTiles.size())
                    throw new InvalidParameterException("tiles different from the taken ones in the draw move");
                player.setTakenTiles(orderedTiles);
                return;
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param playerName the player playing.
     * @param column     the chose column to insert the tiles in.
     * @throws InvalidParameterException if the player is playing in the wrong turn or if the column is not available or has not yet drawn any tile.
     * @throws FullColumnException       if the requested column is full.
     */
    @Override
    public synchronized void insertTiles(String playerName, int column) throws InvalidParameterException, FullColumnException {
        if (!playerName.equals(currentPlayer.getName()))
            throw new InvalidParameterException("player playing in the wrong turn");
        if (column < 0 || column > gameConfig.getBookshelfDimension().width - 1)
            throw new InvalidParameterException("column not available");

        for (Player p : players) {
            if (p.getName().equals(playerName)) {
                int numTakenTiles = p.getTakenTiles().size();
                if (numTakenTiles == 0) throw new InvalidParameterException("player has not yet drawn any tiles");
                if (!p.getBookshelf().isColumnFillable(numTakenTiles, column))
                    throw new FullColumnException(column);
                for (ItemTile t : p.getTakenTiles()) {
                    p.getBookshelf().insertTile(column, t);
                }
                p.clearTakenTiles();
                return;
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param playerName the player playing
     */
    @Override
    public synchronized void checkCommonGoal(String playerName) {
        for (CommonGoal cm : commonGoals) {
            for (Player p : players) {
                if (p.getName().equals(playerName)) {
                    if (cm.getTokens().size() != 0 && !p.getObtainedTokens().stream().map(Token::getRomanNumeral).toList().contains(cm.peekToken().getRomanNumeral())) {
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
     * Adds a token when a player completes a common goal.
     * The one added is on the top of the stack, and the points on the token are added to the points of the player.
     *
     * @param playerName is the name of the player that obtained the token.
     * @param token      is the token obtained.
     */
    private void addToken(String playerName, Token token) {
        for (Player p : players) {
            if (p.getName().equals(playerName)) {
                p.addObtainedToken(token);
                return;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void refillLivingRoom() {
        livingRoom.refill();
    }

    /**
     * {@inheritDoc}
     *
     * @return the Chat Model of this game
     */
    @Override
    public ChatModel getChatModel() {
        return chatSystem;

    }

    /**
     * {@inheritDoc}.
     * If the lobby has not been initialized it returns null.
     *
     * @return the GameModel
     */
    @Override
    public GameModel getGameModel() {
        if (!hasStarted()) return null; //lobby has not been initialized
        return this;
    }

    /**
     * Returns the name of the player that created the lobby.
     * @return the name of the player that created the lobby.
     */
    public String getRoomOwner() {
        return roomOwner;
    }

    /**
     * Returns the name of the player playing in that turn.
     *
     * @return the name of the player playing in that turn.
     */
    public String getCurrentPlayer() {
        return currentPlayer.getName();
    }

    /**
     * Returns the name of the last player who played.
     *
     * @return the name of the last player who played.
     */
    public String getLastPlayer() {
        int index = players.indexOf(currentPlayer);
        if (index == 0) {
            return players.get(players.size() - 1).getName();
        }
        return players.get(index - 1).getName();
    }

    /**
     * Returns the Player associated with a certain playerName.
     * @param playerName the requested playerName.
     * @return the {@link Player} associated whit the playerName.
     */
    public Player getPlayer(String playerName) {
        for (Player p : players) {
            if (p.getName().equals(playerName)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Checks whether the bookshelf is full.
     *
     * @param playerName is the name of the player whose bookshelf has to be controlled.
     * @return true if the bookshelf is full, false otherwise.
     */
    @Override
    public synchronized boolean isBookshelfFull(String playerName) {
        return this.getPlayer(playerName).getBookshelf().isFull();
    }

    /**
     * Checks whether the player playing is the last one of that turn.
     *
     * @param playerName is the name of the player that has to be controlled.
     * @return true if the player playing is the last one of that turn, false otherwise.
     */
    @Override
    public synchronized boolean isLastPlayer(String playerName) {
        return players.get(players.size() - 1).getName().equals(playerName);
    }

    /**
     * {@inheritDoc}
     *
     * @return the lobby name
     */
    @Override
    public String getLobbyName() {
        return lobbyName;
    }

    /**
     * {@inheritDoc}
     *
     * @param obs the observer to add.
     */
    @Override
    public synchronized void addGameObserver(Observer<GameEvent, GameModelView> obs) {
        gameObservable.addObserver(obs);
        gameObservable.setChanged();
        gameObservable.notifyObservers(new FullGameModelView(this));
    }

    /**
     * {@inheritDoc}
     *
     * @param obs the game observer to remove.
     */
    @Override
    public synchronized void removeGameObserver(Observer<GameEvent, GameModelView> obs) {
        gameObservable.deleteObserver(obs);
    }

    /**
     * {@inheritDoc}
     *
     * @return the {@link FullGameModelView} of this game
     */
    @Override
    public synchronized FullGameModelView getGameModelView() {
        return new FullGameModelView(this);
    }

    /**
     * {@inheritDoc}
     *
     * @param obs the observer to add.
     */
    @Override
    public synchronized void addLobbyObserver(Observer<LobbyEvent, LobbyModelView> obs) {
        lobbyObservable.addObserver(obs);
        lobbyObservable.setChanged();
        lobbyObservable.notifyObservers(new LobbyModelView(this));
    }

    /**
     * {@inheritDoc}
     *
     * @param obs the observer to remove.
     */
    @Override
    public synchronized void removeLobbyObserver(Observer<LobbyEvent, LobbyModelView> obs) {
        lobbyObservable.deleteObserver(obs);
    }

    /**
     * {@inheritDoc}
     * @return the {@link LobbyModelView}
     */
    @Override
    public synchronized LobbyModelView getLobbyModelView() {
        return new LobbyModelView(this);
    }

    /**
     * Returns the list of the players in the game.
     * @return the list of the players in the game.
     */
    public List<Player> getPlayers() {
        return new ArrayList<>(players);
    }

    /**
     * Returns the number of the players of the game in a specific moment.
     *
     * @return the size of the list of players.
     */
    protected int getCurrentSize() {
        return players.size();
    }

    /**
     * Return the max number of players admitted in the game.
     *
     * @return a number.
     */
    public int getMaxSize() {
        return gameConfig.getNumPlayers();
    }

    /**
     * Returns the {@link GameConfig} previously set in this Game.
     *
     * @return the {@link GameConfig}.
     */
    public GameConfig getGameConfig() {
        return gameConfig;
    }

    /**
     * {@inheritDoc}
     *
     * @param gameConfig are the configurations for the game.
     * @throws InvalidParameterException if the players in the game config are less then the current players in this lobby
     */
    @Override
    public synchronized void setGameConfig(GameConfig gameConfig) throws InvalidParameterException {
        if (gameConfig.getNumPlayers() < players.size()) throw new InvalidParameterException("too few players");
        this.gameConfig = gameConfig;
        lobbyObservable.setChanged();
        lobbyObservable.notifyObservers(LobbyEvent.NEW_CONFIG, getLobbyModelView());
    }

    /**
     * Return the list with all the player names.
     *
     * @return a list of strings.
     */
    public List<String> getPlayersNames() {
        return new ArrayList<>(players.stream().map(Player::getName).toList());
    }

    /**
     * Return the list of the common goals of the game.
     *
     * @return a list of common goals.
     */
    public List<CommonGoal> getCommonGoals() {
        return new ArrayList<>(commonGoals);
    }

    /**
     * Return a copy of the living room.
     *
     * @return a matrix of the item tiles in the living room.
     */
    public ItemTile[][] getLivingRoom() {
        ItemTile[][] livingRoom = this.livingRoom.getTable();
        ItemTile[][] result = new ItemTile[gameConfig.getLivingRoomSize()][gameConfig.getLivingRoomSize()];
        for (int i = 0; i < gameConfig.getLivingRoomSize(); i++) {
            if (gameConfig.getLivingRoomSize() >= 0)
                System.arraycopy(livingRoom[i], 0, result[i], 0, gameConfig.getLivingRoomSize());
        }
        return result;
    }

    /**
     * Return the number of item tiles remained in the bag.
     *
     * @return the dimension of the bag stack.
     */
    public int getBagSize() {
        return livingRoom.getBag().size();
    }

    /**
     * Return the name of the game.
     *
     * @return a string with the name of the game.
     */
    public String getName() {
        return lobbyName;
    }

    /**
     * {@inheritDoc}
     *
     * @param username the name of the player to check.
     * @return true if the player is the room owner
     */
    @Override
    public boolean isRoomOwner(String username) {
        if (username == null) return false;
        return roomOwner.equals(username);
    }

    /**
     * {@inheritDoc}
     *
     * @return true if the game has started
     */
    @Override
    public boolean hasStarted() {
        return commonGoals != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeLobbyObservers() {
        lobbyObservable.deleteObservers();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeGameObservers() {
        gameObservable.deleteObservers();
    }
}
