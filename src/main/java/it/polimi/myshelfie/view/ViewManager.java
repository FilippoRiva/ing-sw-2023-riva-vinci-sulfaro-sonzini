package it.polimi.myshelfie.view;

import it.polimi.myshelfie.GameConfig;
import it.polimi.myshelfie.model.*;
import it.polimi.myshelfie.model.chatting.Message;
import it.polimi.myshelfie.model.events.ExceptionEvent;
import it.polimi.myshelfie.model.exceptions.InvalidParameterException;
import it.polimi.myshelfie.model.modelviews.*;
import it.polimi.myshelfie.network.NetworkMessage;
import it.polimi.myshelfie.view.events.ViewEvent;
import it.polimi.myshelfie.utils.Observable;
import it.polimi.myshelfie.utils.Observer;

import java.util.HashMap;
import java.util.List;


public class ViewManager extends View {
    private final Observable<ViewUpdate, Object> UINotifier;
    private static ViewManager viewManager;
    private ModelView modelView;
    private ClientExceptionsNotifierView clientExceptionsNotifierView;
    private ClientExceptionsNotifierView disconnectedClientExceptionsNotifierView;
    private PlayerChatModelView playerChatModelView;
    private LobbyModelView lobbyModelView;
    private FullGameModelView fullGameModelView;
    private FullGameModelView gameDifferences;
    private FullGameModelView oldGameModelView;
    private final Object sync;
    private ViewType viewType;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        ViewManager.username = username;
    }

    public static String username;
    public ViewManager(){
        this.UINotifier = new Observable<>();
        this.sync = new Object();
    }

    public static ViewManager getInstance(){
        if(viewManager ==null){
            ViewManager.viewManager = new ViewManager();
        }
        return viewManager;
    }

    private synchronized void sendMessage(NetworkMessage networkMessage){
        if(networkMessage == null) return;
        setChanged();
        notifyObservers((ViewEvent) networkMessage.getEvent(), networkMessage);
    }

    public void configRequest(GameConfig config){
        synchronized (this) {
            try {
                viewManager.sendMessage(new NetworkMessage(ViewEvent.CONFIG_REQUEST,config));
            } catch (InvalidParameterException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void joinRequest(String lobby, String playerName){
        synchronized (this) {
            try {
                sendMessage(new NetworkMessage(ViewEvent.JOIN_REQUEST,playerName,lobby));
            } catch (InvalidParameterException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void createRequest(String lobby, String playerName){
        synchronized (this) {
            try {
                sendMessage(new NetworkMessage(ViewEvent.CREATE_REQUEST, playerName, lobby));
            } catch (InvalidParameterException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void exitRequest() {
        try {
            sendMessage(new NetworkMessage(ViewEvent.EXIT_REQUEST));
        } catch (InvalidParameterException e) {
            throw new RuntimeException(e);
        }
    }

    public void startRequest() {
        synchronized (this){
            try {
                sendMessage(new NetworkMessage(ViewEvent.START_REQUEST));
            } catch (InvalidParameterException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void drawRequest(List<Position> positions) {
        synchronized (this){
            try {
                sendMessage(new NetworkMessage(ViewEvent.DRAW_REQUEST,positions));
            } catch (InvalidParameterException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void organizeRequest(List<ItemTile> orderedTiles){
        synchronized (this){
            try{
                sendMessage(new NetworkMessage(ViewEvent.ORGANIZE_REQUEST,orderedTiles));
            } catch (InvalidParameterException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void insertRequest(int column){
        synchronized (this){
            try{
                sendMessage(new NetworkMessage(ViewEvent.INSERT_REQUEST,column));
            } catch (InvalidParameterException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void sendChatMessage(Message message){
        synchronized (this){
            try{
                sendMessage(new NetworkMessage(ViewEvent.MESSAGE_SEND,message));
            } catch (InvalidParameterException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean removePlayer() {
        LobbyModelView lobbyModelView = viewManager.getLobbyModelView();
        int currentSize =  lobbyModelView.getGameConfig().getNumPlayers();
        if(currentSize==2) return false;
        GameConfig config;
        try {
            config = new GameConfig(currentSize-1);
        } catch (InvalidParameterException ignored) {
            return false;
        }
        synchronized (this) {
            try {
                sendMessage(new NetworkMessage(ViewEvent.CONFIG_REQUEST,config));
            } catch (InvalidParameterException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }

    public boolean addPlayer() {
        LobbyModelView lobbyModelView = viewManager.getLobbyModelView();
        int currentSize =  lobbyModelView.getGameConfig().getNumPlayers();
        if(currentSize==4) return false;
        GameConfig config;
        try {
            config = new GameConfig(currentSize+1);
        } catch (InvalidParameterException e) {
            return false;
        }
        synchronized (this) {
            try {
                sendMessage(new NetworkMessage(ViewEvent.CONFIG_REQUEST,config));
            } catch (InvalidParameterException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }

    @Override
    public void setModelView(ModelView modelView) {
        synchronized (sync) {
            this.modelView = modelView;
            UINotifier.setChanged();
            UINotifier.notifyObservers(ViewUpdate.MODEL_UPDATE, null);
            sync.notifyAll();
        }
    }

    @Override
    public void setExceptionView(ClientExceptionsNotifierView exceptionView, ExceptionEvent evt) {
        synchronized (sync){
            if(evt == ExceptionEvent.NEW_EXCEPTION) {
                this.clientExceptionsNotifierView = exceptionView;
                UINotifier.setChanged();
                UINotifier.notifyObservers(ViewUpdate.EXCEPTION_UPDATE, null);
            }
            else this.disconnectedClientExceptionsNotifierView = exceptionView;
            sync.notifyAll();
        }
    }

    @Override
    public void setChatView(PlayerChatModelView chatView) {
        synchronized (sync){
            this.playerChatModelView = chatView;
            UINotifier.setChanged();
            UINotifier.notifyObservers(ViewUpdate.CHAT_UPDATE, null);
            sync.notifyAll();
        }
    }

    @Override
    public void setLobbyModelView(LobbyModelView lobbyModelView) {
        synchronized (sync){
            this.lobbyModelView = lobbyModelView;
            UINotifier.setChanged();
            UINotifier.notifyObservers(ViewUpdate.LOBBY_UPDATE, null);
            sync.notifyAll();
        }
    }

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

    @Override
    public void updateDrawView(DrawModelView drawModelView) {
        synchronized (sync){
            GameState currentState = fullGameModelView.getCurrentState();
            while(currentState != GameState.DRAW && currentState != GameState.LAST_DRAW){
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
            this.gameDifferences= diffCopy;

            UINotifier.setChanged();
            UINotifier.notifyObservers(ViewUpdate.GAME_UPDATE, null);
            sync.notifyAll();
        }
    }

    @Override
    public void updateOrganizeView(OrganizeModelView changes) {
        synchronized (sync){
            GameState currentState = fullGameModelView.getCurrentState();
            while(currentState != GameState.ORGANIZE && currentState != GameState.LAST_ORGANIZE){
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
            this.gameDifferences= diffCopy;

            UINotifier.setChanged();
            UINotifier.notifyObservers(ViewUpdate.GAME_UPDATE, null);
            sync.notifyAll();
        }
    }

    @Override
    public void updateInsertView(InsertModelView changes) {
        synchronized (sync) {
            GameState currentState = fullGameModelView.getCurrentState();
            while(currentState != GameState.INSERT && currentState != GameState.LAST_INSERT){
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

    @Override
    public void updateTurnView(EndTurnModelView endTurnModelView) {
        synchronized (sync){
            GameState currentState = fullGameModelView.getCurrentState();
            while(currentState != GameState.COUNTING_POINTS && currentState != GameState.LAST_COUNTING_POINTS){
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
                    fullGameModelView = null;
                    gameDifferences = null;
                    oldGameModelView = null;
                    lobbyModelView = null;
                    playerChatModelView = null;
                    disconnectedClientExceptionsNotifierView = null;
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
                    while(disconnectedClientExceptionsNotifierView == null){
                        try {
                            sync.wait();
                        } catch (InterruptedException ignored) {
                        }
                    }
                }
            }
            this.viewType = viewType;
            UINotifier.setChanged();
            UINotifier.notifyObservers(ViewUpdate.VIEW_TYPE_UPDATE, null);
            sync.notifyAll();
        }
    }

    public synchronized void addUI(Observer<ViewUpdate, Object> o) {
        UINotifier.addObserver(o);
    }

    public synchronized void removeUI(Observer<ViewUpdate, Object> o ){
        UINotifier.deleteObserver(o);
    }

    public synchronized ModelView getModelView() {
        return modelView;
    }

    public synchronized ClientExceptionsNotifierView getClientExceptionsNotifierView() {
        return clientExceptionsNotifierView;
    }

    public synchronized ClientExceptionsNotifierView getDisconnectedClientExceptionsNotifierView(){
        return disconnectedClientExceptionsNotifierView;
    }

    public synchronized PlayerChatModelView getPlayerChatModelView() {
        return playerChatModelView;
    }

    public synchronized LobbyModelView getLobbyModelView() {
        return lobbyModelView;
    }

    public synchronized FullGameModelView getGameModelView() {
        return fullGameModelView;
    }

    public synchronized ViewType getViewType() {
        return viewType;
    }

    /**
     *
     * @return if the current player/turn has changed in the last game update
     */
    public synchronized boolean isCurrentPlayerDiff(){
        return gameDifferences.getCurrentPlayerName()!=null;
    }
    /**
     *
     * @return the username of the player that is currently the new current player
     */
    public synchronized String getCurrentPlayerDiff(){
        return gameDifferences.getCurrentPlayerName();
    }

    /**
     *
     * @return the username of the player that was playing in the last game update
     */
    public synchronized String getLastPlayer(){
        return oldGameModelView.getCurrentPlayerName();
    }

    /**
     * @return if the state have changed since the last game update
     */
    public synchronized boolean isCurrentStateDiff(){
        return gameDifferences.getCurrentState()!=null;
    }

    /**
     * @return the new state if the state has changed, otherwise it returns null
     */
    public synchronized GameState getCurrentStateDiff(){
        return gameDifferences.getCurrentState();
    }

    /**
     * @return the state previous to the one received in the last game update
     */
    public synchronized GameState getLastState(){
        return oldGameModelView.getCurrentState();
    }

    /**
     * @return if remaining tiles have changed since the last game update
     */
    public synchronized boolean isRemainingTilesDiff(){
        return gameDifferences.getRemainingTiles()!=-1;
    }
    public synchronized int getRemainingTilesDiff(){
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
    public synchronized HashMap<Position,ItemTile> getLivingRoomDiff(){
        HashMap<Position,ItemTile> result = new HashMap<>();
        ItemTile[][] diffLivingRoom = gameDifferences.getLivingRoom();
        for(int i = 0; i<diffLivingRoom.length; i++){
            for(int j = 0; j<diffLivingRoom[0].length; j++){
                if(diffLivingRoom[i][j]!=null){
                    result.put(new Position(j,i),diffLivingRoom[i][j]);
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
    public synchronized List<CommonGoalModelView> getCommonGoalDiff(){
        return gameDifferences.getCommonGoals().stream().filter(x->x.getTokens().size()!=0).toList();
    }

    /**
     * The returned PlayerModelView's points are the new points of those players.
     * @return the list of the players that have changed their points compared to the last game update.
     */
    public synchronized List<PlayerModelView> getPlayersPointsDiff(){
        return gameDifferences.getPlayers().stream().filter(x->x.getPoints()!=-1).toList();
    }

    /**
     *
     * @return the new points of the player if they have changed, otherwise returns -1
     */
    public synchronized int getLastPlayerPointsDiff(){
        return gameDifferences.getPlayer(oldGameModelView.getCurrentPlayerName()).getPoints();
    }

    /**
     *
     * @return the tokens that the last player has earned in the last game update
     */
    public synchronized List<Token> getLastPlayerTokenDiff(){
        return gameDifferences.getPlayer(oldGameModelView.getCurrentPlayerName()).getTokens();
    }

    /**
     * @return the last player taken tiles that have been added in the last game update
     */
    public synchronized List<ItemTile> getLastPlayerTakenTilesDiff(){
        return gameDifferences.getPlayer(oldGameModelView.getCurrentPlayerName()).getTakenTiles();
    }

    public synchronized boolean hasLastPlayerInsertedTiles(){
        return fullGameModelView.getCurrentPlayer().getTakenTiles().size()< oldGameModelView.getCurrentPlayer().getTakenTiles().size();
    }

    public synchronized int getLastPlayerInsertedColumnPosition(){
        if(!hasLastPlayerInsertedTiles()) return -1;
        ItemTile[][] bookshelf = gameDifferences.getPlayer(oldGameModelView.getCurrentPlayerName()).getBookshelf();
        for (ItemTile[] itemTiles : bookshelf) {
            for (int j = 0; j < bookshelf[0].length; j++) {
                if (itemTiles[j] != null) return j;
            }
        }
        return -1;
    }

    public void startAutoPlayer(int roomSize) {
        AutoPlayer a = new AutoPlayer(viewManager, null);
        try {
            a.preGameStart(roomSize);
        } catch (InvalidParameterException ignored) {
        }
    }
    public void startAutoPlayer(){
        AutoPlayer a = new AutoPlayer(viewManager, null);
        if(fullGameModelView!=null){
            a.postGameStart(username);
        } else {
            try {
                a.preGameStart(2);
            } catch (InvalidParameterException ignored) {
            }
        }
    }
}
