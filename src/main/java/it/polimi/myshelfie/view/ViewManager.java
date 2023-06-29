package it.polimi.myshelfie.view;

import it.polimi.myshelfie.ClientApp;
import it.polimi.myshelfie.GameConfig;
import it.polimi.myshelfie.model.*;
import it.polimi.myshelfie.model.chatting.Message;
import it.polimi.myshelfie.model.events.ExceptionEvent;
import it.polimi.myshelfie.model.exceptions.InvalidParameterException;
import it.polimi.myshelfie.model.modelviews.*;
import it.polimi.myshelfie.network.NetworkMessage;
import it.polimi.myshelfie.utils.Observable;
import it.polimi.myshelfie.utils.Observer;
import it.polimi.myshelfie.view.events.ViewEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * The ViewManager handles the View and all the requests that come to it.
 * It sends NetworkMessages with the ViewEvents that happen and the information associated with them.
 * It sets and makes updates to the modelViews based on the ViewEvents that occurred.
 * It manages the ViewManager Observers with the UINotifier.
 * It implements a Singleton Design Pattern.
 */
public class ViewManager extends View {

    public static String username;

    /**
     * Used to implement the Singleton Design Pattern.
     */
    private static ViewManager viewManager; //used to have only one ViewManager.
    private final Observable<ViewUpdate, Object> UINotifier;
    private final Object sync;
    private ModelView modelView;
    private ClientExceptionsNotifierModelView clientExceptionsNotifierView;
    private ClientExceptionsNotifierModelView disconnectedClientExceptionsNotifierView;
    private PlayerChatModelView playerChatModelView;
    private LobbyModelView lobbyModelView;
    private FullGameModelView fullGameModelView;
    private FullGameModelView gameDifferences;
    private FullGameModelView oldGameModelView;
    private ViewType viewType;


    /**
     * Constructor.
     */
    private ViewManager() {
        this.UINotifier = new Observable<>();
        this.sync = new Object();
    }


    /**
     * Returns the username.
     *
     * @return the username.
     */
    public static String getUsername() {
        return username;
    }


    /**
     * Sets the username.
     *
     * @param username the username to set.
     */
    public static void setUsername(String username) {
        ViewManager.username = username;
    }


    /**
     * Returns the instance of THE View manager by using a Singleton Design Pattern.
     *
     * @return the ViewManager.
     */
    public static ViewManager getInstance() {
        if (viewManager == null) {
            ViewManager.viewManager = new ViewManager();
        }
        return viewManager;
    }


    /**
     * Notifies the observers that a change has occurred and changes their attribute "changed" to true.
     *
     * @param networkMessage the NetworkMessage sent.
     */
    private synchronized void sendMessage(NetworkMessage networkMessage) {
        if (networkMessage == null) return;
        setChanged();
        notifyObservers((ViewEvent) networkMessage.getEvent(), networkMessage);
    }


    /**
     * Sends a NetworkMessage with the game settings when a ViewEvent.CONFIG_REQUEST occurs.
     *
     * @param config the game settings.
     */
    public void configRequest(GameConfig config) {
        synchronized (this) {
            try {
                viewManager.sendMessage(new NetworkMessage(ViewEvent.CONFIG_REQUEST, config));
            } catch (InvalidParameterException e) {
                throw new RuntimeException(e);
            }
        }
    }


    /**
     * Sends a NetworkMessage with the lobby name and player name when a ViewEvent.JOIN_REQUEST occurs.
     *
     * @param lobby      the lobby name.
     * @param playerName thn eplayer name.
     */
    public void joinRequest(String lobby, String playerName) {
        synchronized (this) {
            try {
                sendMessage(new NetworkMessage(ViewEvent.JOIN_REQUEST, playerName, lobby));
            } catch (InvalidParameterException e) {
                throw new RuntimeException(e);
            }
        }
    }


    /**
     * Sends a NetworkMessage with the lobby name and player name when a ViewEvent.CREATE_REQUEST occurs.
     *
     * @param lobby      the lobby name.
     * @param playerName the player name.
     */
    public void createRequest(String lobby, String playerName) {
        synchronized (this) {
            try {
                sendMessage(new NetworkMessage(ViewEvent.CREATE_REQUEST, playerName, lobby));
            } catch (InvalidParameterException e) {
                throw new RuntimeException(e);
            }
        }
    }


    /**
     * Sends a NetworkMessage when a ViewEvent.EXIT_REQUEST occurs.
     */
    public void exitRequest() {
        try {
            sendMessage(new NetworkMessage(ViewEvent.EXIT_REQUEST));
        } catch (InvalidParameterException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Sends a NetworkMessage when a ViewEvent.START_REQUEST occurs.
     */
    public void startRequest() {
        synchronized (this) {
            try {
                sendMessage(new NetworkMessage(ViewEvent.START_REQUEST));
            } catch (InvalidParameterException e) {
                throw new RuntimeException(e);
            }
        }
    }


    /**
     * Sends a NetworkMessage with the chosen positions when a ViewEvent.DRAW_REQUEST occurs.
     *
     * @param positions the position selected in the draw move.
     */
    public void drawRequest(List<Position> positions) {
        synchronized (this) {
            try {
                sendMessage(new NetworkMessage(ViewEvent.DRAW_REQUEST, positions));
            } catch (InvalidParameterException e) {
                throw new RuntimeException(e);
            }
        }
    }


    /**
     * Sends a NetworkMessage with the ordered tiles when a ViewEvent.ORGANIZE_REQUEST occurs.
     *
     * @param orderedTiles the tiles ordered.
     */
    public void organizeRequest(List<ItemTile> orderedTiles) {
        synchronized (this) {
            try {
                sendMessage(new NetworkMessage(ViewEvent.ORGANIZE_REQUEST, orderedTiles));
            } catch (InvalidParameterException e) {
                throw new RuntimeException(e);
            }
        }
    }


    /**
     * Sends a NetworkMessage with the selected column when a ViewEvent.INSERT_REQUEST occurs.
     *
     * @param column the column where to insert the taken tiles.
     */
    public void insertRequest(int column) {
        synchronized (this) {
            try {
                sendMessage(new NetworkMessage(ViewEvent.INSERT_REQUEST, column));
            } catch (InvalidParameterException e) {
                throw new RuntimeException(e);
            }
        }
    }


    /**
     * Sends a NetworkMessage with a message when a ViewEvent.MESSAGE_SEND occurs.
     *
     * @param message the message sent.
     */
    public void sendChatMessage(Message message) {
        synchronized (this) {
            try {
                sendMessage(new NetworkMessage(ViewEvent.MESSAGE_SEND, message));
            } catch (InvalidParameterException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Returns true if removePlayer fails.
     * @return true if removePlayer fails.
     */
    public boolean removePlayer() {
        LobbyModelView lobbyModelView = viewManager.getLobbyModelView();
        int currentSize = lobbyModelView.getGameConfig().getNumPlayers();
        if (currentSize == 2) return true;
        GameConfig config;
        try {
            config = new GameConfig(currentSize - 1);
        } catch (InvalidParameterException ignored) {
            return true;
        }
        synchronized (this) {
            try {
                sendMessage(new NetworkMessage(ViewEvent.CONFIG_REQUEST, config));
            } catch (InvalidParameterException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    /**
     * Returns true if addPlayer fails.
     * @return true if addPlayer fails.
     */
    public boolean addPlayer() {
        LobbyModelView lobbyModelView = viewManager.getLobbyModelView();
        int currentSize = lobbyModelView.getGameConfig().getNumPlayers();
        if (currentSize == 4) return true;
        GameConfig config;
        try {
            config = new GameConfig(currentSize + 1);
        } catch (InvalidParameterException e) {
            return true;
        }
        synchronized (this) {
            try {
                sendMessage(new NetworkMessage(ViewEvent.CONFIG_REQUEST, config));
            } catch (InvalidParameterException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }


    /**
     * {@inheritDoc}
     *
     * @param exceptionView the exception view to check.
     * @param evt           the exception event that has occurred.
     */
    @Override
    public void setExceptionView(ClientExceptionsNotifierModelView exceptionView, ExceptionEvent evt) {
        synchronized (sync) {
            if (evt == ExceptionEvent.NEW_EXCEPTION) {
                this.clientExceptionsNotifierView = exceptionView;
                UINotifier.setChanged();
                UINotifier.notifyObservers(ViewUpdate.EXCEPTION_UPDATE, null);
            } else this.disconnectedClientExceptionsNotifierView = exceptionView;
            sync.notifyAll();
        }
    }


    /**
     * {@inheritDoc}
     *
     * @param chatView the chat view to set.
     */
    @Override
    public void setChatView(PlayerChatModelView chatView) {
        synchronized (sync) {
            this.playerChatModelView = chatView;
            UINotifier.setChanged();
            UINotifier.notifyObservers(ViewUpdate.CHAT_UPDATE, null);
            sync.notifyAll();
        }
    }


    /**
     * {@inheritDoc}
     *
     * @param drawModelView the model view to update.
     */
    @Override
    public void updateDrawView(DrawModelView drawModelView) {
        synchronized (sync) {
            GameState currentState = fullGameModelView.getCurrentState();
            while (currentState != GameState.DRAW && currentState != GameState.LAST_DRAW) {
                try {
                    sync.wait();
                } catch (InterruptedException ignored) {
                }
                currentState = fullGameModelView.getCurrentState();
            }
            this.oldGameModelView = fullGameModelView;
            FullGameModelView diffCopy = new FullGameModelView(fullGameModelView);
            FullGameModelView updateCopy = new FullGameModelView(fullGameModelView);

            updateCopy.applyChanges(drawModelView);

            fullGameModelView = updateCopy;
            diffCopy.diff(this.fullGameModelView);
            this.gameDifferences = diffCopy;

            UINotifier.setChanged();
            UINotifier.notifyObservers(ViewUpdate.GAME_UPDATE, null);
            sync.notifyAll();
        }
    }


    /**
     * {@inheritDoc}
     *
     * @param changes th emodel view to update.
     */
    @Override
    public void updateOrganizeView(OrganizeModelView changes) {
        synchronized (sync) {
            GameState currentState = fullGameModelView.getCurrentState();
            while (currentState != GameState.ORGANIZE && currentState != GameState.LAST_ORGANIZE) {
                try {
                    sync.wait();
                } catch (InterruptedException ignored) {
                }
                currentState = fullGameModelView.getCurrentState();
            }
            this.oldGameModelView = fullGameModelView;
            FullGameModelView diffCopy = new FullGameModelView(fullGameModelView);
            FullGameModelView updateCopy = new FullGameModelView(fullGameModelView);

            updateCopy.applyChanges(changes);

            fullGameModelView = updateCopy;
            diffCopy.diff(this.fullGameModelView);
            this.gameDifferences = diffCopy;

            UINotifier.setChanged();
            UINotifier.notifyObservers(ViewUpdate.GAME_UPDATE, null);
            sync.notifyAll();
        }
    }


    /**
     * {@inheritDoc}
     *
     * @param changes the model view to update.
     */
    @Override
    public void updateInsertView(InsertModelView changes) {
        synchronized (sync) {
            GameState currentState = fullGameModelView.getCurrentState();
            while (currentState != GameState.INSERT && currentState != GameState.LAST_INSERT) {
                try {
                    sync.wait();
                } catch (InterruptedException ignored) {
                }
                currentState = fullGameModelView.getCurrentState();
            }
            this.oldGameModelView = fullGameModelView;
            FullGameModelView diffCopy = new FullGameModelView(fullGameModelView);
            FullGameModelView updateCopy = new FullGameModelView(fullGameModelView);

            updateCopy.applyChanges(changes);

            fullGameModelView = updateCopy;
            diffCopy.diff(this.fullGameModelView);
            this.gameDifferences = diffCopy;

            UINotifier.setChanged();
            UINotifier.notifyObservers(ViewUpdate.GAME_UPDATE, null);
            sync.notifyAll();
        }
    }


    /**
     * {@inheritDoc}
     *
     * @param endTurnModelView the modelview to update.
     */
    @Override
    public void updateTurnView(EndTurnModelView endTurnModelView) {
        synchronized (sync) {
            GameState currentState = fullGameModelView.getCurrentState();
            while (currentState != GameState.COUNTING_POINTS && currentState != GameState.LAST_COUNTING_POINTS) {
                try {
                    sync.wait();
                } catch (InterruptedException ignored) {
                }
                currentState = fullGameModelView.getCurrentState();
            }
            this.oldGameModelView = fullGameModelView;
            FullGameModelView diffCopy = new FullGameModelView(fullGameModelView);
            FullGameModelView updateCopy = new FullGameModelView(fullGameModelView);

            updateCopy.applyChanges(endTurnModelView);

            fullGameModelView = updateCopy;
            diffCopy.diff(this.fullGameModelView);
            this.gameDifferences = diffCopy;

            UINotifier.setChanged();
            UINotifier.notifyObservers(ViewUpdate.GAME_UPDATE, null);
            sync.notifyAll();
        }
    }


    /**
     * Resets the ModelViews.
     */
    private void resetBuffer() {
        this.oldGameModelView = fullGameModelView;
        this.fullGameModelView = null;
        this.gameDifferences = null;
        this.lobbyModelView = null;
        this.playerChatModelView = null;
    }


    /**
     * {@inheritDoc}
     *
     * @param viewType the view type.
     */
    @Override
    public void setView(ViewType viewType) {
        synchronized (sync) {
            switch (viewType) {
                case INIT -> {
                    while (modelView == null) { //waits until modelView != null
                        try {
                            sync.wait();
                        } catch (InterruptedException ignored) {
                        }
                    }
                    this.resetBuffer();
                }
                case LOBBY -> {
                    while (lobbyModelView == null) { //waits until lobbyModelView != null
                        try {
                            sync.wait();
                        } catch (InterruptedException ignored) {
                        }
                    }
                }
                case GAME -> {
                    while (fullGameModelView == null) { //waits until fullGameModelView != null
                        try {
                            sync.wait();
                        } catch (InterruptedException ignored) {
                        }
                    }
                }
                case WIN -> {
                    while (fullGameModelView == null || fullGameModelView.getCurrentState() != GameState.WIN) {
                        try {
                            sync.wait();
                        } catch (InterruptedException ignored) {
                        }
                    }
                }
                case DISCONNECTION -> {
                    while (disconnectedClientExceptionsNotifierView == null) {
                        try {
                            sync.wait();
                        } catch (InterruptedException ignored) {
                        }
                    }
                    this.resetBuffer();
                }
            }
            this.viewType = viewType;
            UINotifier.setChanged();
            UINotifier.notifyObservers(ViewUpdate.VIEW_TYPE_UPDATE, null);
            sync.notifyAll();
        }
    }


    /**
     * Adds an observer to the UINotifier.
     *
     * @param o the observer to add.
     */
    public synchronized void addUI(Observer<ViewUpdate, Object> o) {
        UINotifier.addObserver(o);
    }


    /**
     * Removes an observer to the UINotifier.
     *
     * @param o the observer to remove.
     */
    public synchronized void removeUI(Observer<ViewUpdate, Object> o) {
        UINotifier.deleteObserver(o);
    }


    /**
     * Returns the ModelView.
     *
     * @return the ModelView.
     */
    public synchronized ModelView getModelView() {
        return modelView;
    }


    /**
     * {@inheritDoc}
     *
     * @param modelView the model view to set.
     */
    @Override
    public void setModelView(ModelView modelView) {
        synchronized (sync) {
            this.modelView = modelView;
            UINotifier.setChanged();
            UINotifier.notifyObservers(ViewUpdate.MODEL_UPDATE, null);
            sync.notifyAll();
        }
    }


    /**
     * Returns the ClientExceptionsNotifierModelView.
     *
     * @return the ClientExceptionsNotifierModelView.
     */
    public synchronized ClientExceptionsNotifierModelView getClientExceptionsNotifierView() {
        return clientExceptionsNotifierView;
    }


    /**
     * Returns the ClientExceptionsNotifierModelView that disconnected.
     *
     * @return the ClientExceptionsNotifierModelView that disconnected.
     */
    public synchronized ClientExceptionsNotifierModelView getDisconnectedClientExceptionsNotifierView() {
        return disconnectedClientExceptionsNotifierView;
    }


    /**
     * Return the PlayerChatModelView.
     *
     * @return the PlayerChatModelView.
     */
    public synchronized PlayerChatModelView getPlayerChatModelView() {
        return playerChatModelView;
    }


    /**
     * Returns the LobbyModelView.
     *
     * @return the LobbyModelView.
     */
    public synchronized LobbyModelView getLobbyModelView() {
        return lobbyModelView;
    }


    /**
     * {@inheritDoc}
     *
     * @param lobbyModelView the lobby model view to set.
     */
    @Override
    public void setLobbyModelView(LobbyModelView lobbyModelView) {
        synchronized (sync) {
            this.lobbyModelView = lobbyModelView;
            UINotifier.setChanged();
            UINotifier.notifyObservers(ViewUpdate.LOBBY_UPDATE, null);
            sync.notifyAll();
        }
    }


    /**
     * Returns the FullGameModelView.
     *
     * @return the FullGameModelView.
     */
    public synchronized FullGameModelView getGameModelView() {
        return fullGameModelView;
    }


    /**
     * {@inheritDoc}
     *
     * @param theNewOne the game model view to set.
     */
    @Override
    public void setGameModelView(FullGameModelView theNewOne) {
        synchronized (sync) {
            if (this.fullGameModelView != null) {
                this.oldGameModelView = new FullGameModelView(fullGameModelView);
                FullGameModelView copy = new FullGameModelView(fullGameModelView);
                this.fullGameModelView = theNewOne;
                copy.diff(this.fullGameModelView);
                this.gameDifferences = copy;
            } else {
                this.fullGameModelView = theNewOne;
            }
            UINotifier.setChanged();
            UINotifier.notifyObservers(ViewUpdate.GAME_UPDATE, null);
            sync.notifyAll();
        }
    }


    /**
     * Returns the ViewType.
     *
     * @return the ViewType.
     */
    public synchronized ViewType getViewType() {
        return viewType;
    }

    /**
     * Returns if the current player/turn has changed in the last game update
     * @return if the current player/turn has changed in the last game update
     */
    public synchronized boolean isCurrentPlayerDiff() {
        return gameDifferences.getCurrentPlayerName() != null;
    }

    /**
     * Returns the username of the player that is currently the new current player
     * @return the username of the player that is currently the new current player
     */
    public synchronized String getCurrentPlayerDiff() {
        return gameDifferences.getCurrentPlayerName();
    }

    /**
     * Returns the username of the player that was playing in the last game update
     * @return the username of the player that was playing in the last game update
     */
    public synchronized String getPreviousPlayer() {
        if (oldGameModelView == null) return "";
        return oldGameModelView.getCurrentPlayerName();
    }

    /**
     * Returns if the state have changed since the last game update
     * @return if the state have changed since the last game update
     */
    public synchronized boolean isCurrentStateDiff() {
        return gameDifferences.getCurrentState() != null;
    }

    /**
     * Returns the new state if the state has changed, otherwise it returns null
     * @return the new state if the state has changed, otherwise it returns null
     */
    public synchronized GameState getCurrentStateDiff() {
        if (gameDifferences == null) return GameState.DRAW; //first turn
        return gameDifferences.getCurrentState();
    }

    /**
     * Returns the state previous to the one received in the last game update
     * @return the state previous to the one received in the last game update
     */
    public synchronized GameState getLastState() {
        return oldGameModelView.getCurrentState();
    }

    /**
     * Returns if remaining tiles have changed since the last game update
     * @return if remaining tiles have changed since the last game update
     */
    public synchronized boolean isRemainingTilesDiff() {
        if (gameDifferences == null) return false;
        return gameDifferences.getRemainingTiles() != -1;
    }

    public synchronized int getRemainingTilesDiff() {
        return gameDifferences.getRemainingTiles();
    }

    /**
     * Gives an HashMap from Positions to ItemTiles. The Positions are the positions that have changed
     * in the last game update and the ItemTiles are the new ItemTiles in that position.
     * The Positions are not center relative but are relative to the top left
     * corner of a usual matrix.
     *
     * @return a map from Positions to ItemTiles that have changed from the previous game update
     */
    public synchronized HashMap<Position, ItemTile> getLivingRoomDiff() {
        if (gameDifferences == null) return null;
        HashMap<Position, ItemTile> result = new HashMap<>();
        ItemTile[][] diffLivingRoom = gameDifferences.getLivingRoom();
        for (int i = 0; i < diffLivingRoom.length; i++) {
            for (int j = 0; j < diffLivingRoom[0].length; j++) {
                if (diffLivingRoom[i][j] != null) {
                    result.put(new Position(j, i), diffLivingRoom[i][j]);
                }
            }
        }
        return result;
    }

    /**
     * The Common Goals returned are the CommonGoals who's Tokens lists have changed compared to the
     * last game update.
     * The Tokens in the CommonGoals returned are the tokens that have been removed.
     *
     * @return a list of the commonGoals that have changed from the previous game update
     */
    public synchronized List<CommonGoalModelView> getCommonGoalDiff() {
        return gameDifferences.getCommonGoals().stream().filter(x -> x.getTokens().size() != 0).toList();
    }

    /**
     * The returned PlayerModelView's points are the new points of those players.
     *
     * @return the list of the players that have changed their points compared to the last game update.
     */
    public synchronized List<PlayerModelView> getPlayersPointsDiff() {
        if (gameDifferences != null)
            return gameDifferences.getPlayers().stream().filter(x -> x.getPoints() != -1).toList();
        return new ArrayList<>();
    }

    /**
     * Returns the new points of the player if they have changed, otherwise returns -1.
     *
     * @return an int.
     */
    public synchronized int getLastPlayerPointsDiff() {
        return gameDifferences.getPlayer(oldGameModelView.getCurrentPlayerName()).getPoints();
    }

    /**
     * Returns the tokens that the last player has earned in the last game update.
     *
     * @return a list of tokens.
     */
    public synchronized List<Token> getLastPlayerTokenDiff() {
        if (oldGameModelView != null && gameDifferences != null)
            return gameDifferences.getPlayer(oldGameModelView.getCurrentPlayerName()).getTokens();
        return new ArrayList<>();
    }

    /**
     * Returns the last player taken tiles that have been added in the last game update.
     *
     * @return a list of item tiles.
     */
    public synchronized List<ItemTile> getCurrentPlayerTakenTilesDiff() {
        return gameDifferences.getPlayer(oldGameModelView.getCurrentPlayerName()).getTakenTiles();
    }


    /**
     * Return the list of item tiles inserted by the current player.
     *
     * @return the list of item tiles inserted by the current player.
     */
    public synchronized List<ItemTile> getCurrentPlayerInsertedTiles() {
        if (oldGameModelView != null) return oldGameModelView.getCurrentPlayer().getTakenTiles();
        return new ArrayList<>();
    }


    /**
     * Checks if the current player has inserted the tile taken.
     *
     * @return true if the player inserted, false otherwise.
     */
    public synchronized boolean hasCurrentPlayerInsertedTiles() {
        if (fullGameModelView != null && oldGameModelView != null)
            return fullGameModelView.getCurrentPlayer().getTakenTiles().size() < oldGameModelView.getCurrentPlayer().getTakenTiles().size();
        return false;
    }


    /**
     * Checks if it's the turn of the player set in the ViewManager.username.
     *
     * @return true if it's the turn of the player set in the ViewManager.username. false otherwise.
     */
    public synchronized boolean isMyTurn() {
        if (fullGameModelView != null) return fullGameModelView.getCurrentPlayerName().equals(ViewManager.username);
        else if (oldGameModelView != null) return oldGameModelView.getCurrentPlayerName().equals(ViewManager.username);
        else return false;
    }


    /**
     * Checks if the tiles taken are at least one and has at least one side free.
     *
     * @param chosenTiles the tiles taken.
     * @return true if the chosen tiles are not valid, false otherwise.
     */
    public synchronized boolean areChosenTilesInvalid(List<Position> chosenTiles) {
        if (fullGameModelView == null) return false;
        if (chosenTiles.size() == 0) return true;
        for (Position p : chosenTiles) {
            if (!fullGameModelView.atLeastOneSideFree(p)) return true;
        }
        return false;
    }


    /**
     * Checks if there's enough space in the bookshelf to insert tiles.
     *
     * @param tryChosenTiles the tiles to insert.
     * @return true if there's enough space in the bookshelf, false otherwise.
     */
    public boolean isThereEnoughBookshelfSpace(List<Position> tryChosenTiles) {
        if (fullGameModelView == null) return false;
        ItemTile[][] bookshelf = fullGameModelView.getCurrentPlayer().getBookshelf();
        int width = bookshelf[0].length;
        for (int i = 0; i < width; i++) {
            if (bookshelf[tryChosenTiles.size() - 1][i].getType() == ItemTileType.EMPTY) return true;
        }
        return false;
    }


    /**
     * Returns the position in the column where the current player has inserted the last tile taken.
     *
     * @return an int.
     */
    public synchronized int getCurrentPlayerInsertedColumnPosition() {
        if (!hasCurrentPlayerInsertedTiles()) return -1;
        ItemTile[][] bookshelf = gameDifferences.getPlayer(oldGameModelView.getCurrentPlayerName()).getBookshelf();
        for (ItemTile[] itemTiles : bookshelf) {
            for (int j = 0; j < bookshelf[0].length; j++) {
                if (itemTiles[j] != null) return j;
            }
        }
        return -1;
    }


    /**
     * Returns the list of players ordered by scores.
     *
     * @return a list of PlayerModelViews.
     */
    public synchronized List<PlayerModelView> getPlayersOrderedByScores() {
        if (fullGameModelView == null) return new ArrayList<>();
        return fullGameModelView.getPlayers().stream().sorted((x, y) -> {
            if (x.getPoints() == y.getPoints()) return 0;
            else return x.getPoints() >= y.getPoints() ? -1 : 1;
        }).toList();
    }


    /**
     * Returns the winner.
     *
     * @return the player who won and has the highest score.
     */
    public synchronized PlayerModelView getWinner() {
        return getPlayersOrderedByScores().get(0);
    }

    public void startAutoPlayer(int roomSize) {
        AutoPlayer a = new AutoPlayer();
        try {
            a.preGameStart(roomSize);
        } catch (InvalidParameterException ignored) {
        }
    }

    public void startAutoPlayer() {
        AutoPlayer a = new AutoPlayer();
        if (fullGameModelView != null) {
            a.postGameStart();
        } else {
            try {
                a.preGameStart(2);
            } catch (InvalidParameterException ignored) {
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stopUI() {
        for (Observer<ViewUpdate, Object> o : UINotifier.getObservers()) {
            ((UI) o).stopUI();
        }
        UINotifier.deleteObservers();
        this.resetBuffer();
        System.out.println("Disconnected from server...\nTry to reconnect\n");
        ClientApp.initialUserChoice();
    }

}
