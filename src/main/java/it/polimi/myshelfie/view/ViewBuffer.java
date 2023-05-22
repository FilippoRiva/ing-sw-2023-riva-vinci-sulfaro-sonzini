package it.polimi.myshelfie.view;

import it.polimi.myshelfie.GameConfig;
import it.polimi.myshelfie.model.*;
import it.polimi.myshelfie.model.chatting.Message;
import it.polimi.myshelfie.model.exceptions.InvalidParameterException;
import it.polimi.myshelfie.model.modelviews.*;
import it.polimi.myshelfie.network.NetworkMessage;
import it.polimi.myshelfie.view.events.ViewEvent;
import it.polimi.myshelfie.utils.Observable;
import it.polimi.myshelfie.utils.Observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ViewBuffer extends View {
    Observable<ViewUpdate, Object> UINotifier;
    private static ViewBuffer viewBuffer;
    ModelView modelView;
    ClientExceptionsNotifierView clientExceptionsNotifierView;
    PlayerChatModelView playerChatModelView;
    LobbyModelView lobbyModelView;
    FullGameModelView fullGameModelView;
    FullGameModelView gameDifferences;
    FullGameModelView oldGameModelView;
    ViewType viewType;
    public ViewBuffer(){
        this.UINotifier = new Observable<>();
    }
    public synchronized void sendMessage(NetworkMessage networkMessage){
        if(networkMessage == null) return;
        setChanged();
        notifyObservers((ViewEvent) networkMessage.getEvent(), networkMessage);
    }

    public static ViewBuffer getInstance(){
        if(viewBuffer==null){
            ViewBuffer.viewBuffer = new ViewBuffer();
        }
        return viewBuffer;
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
        LobbyModelView lobbyModelView = viewBuffer.getLobbyModelView();
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
        LobbyModelView lobbyModelView = viewBuffer.getLobbyModelView();
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
    public synchronized void setModelView(ModelView modelView) {
        this.modelView = modelView;
        UINotifier.setChanged();
        UINotifier.notifyObservers(ViewUpdate.MODEL_UPDATE, null);
    }

    @Override
    public synchronized void setExceptionView(ClientExceptionsNotifierView exceptionView) {
        this.clientExceptionsNotifierView = exceptionView;
        UINotifier.setChanged();
        UINotifier.notifyObservers(ViewUpdate.EXCEPTION_UPDATE, null);
    }

    @Override
    public synchronized void setChatView(PlayerChatModelView chatView) {
        this.playerChatModelView = chatView;
        UINotifier.setChanged();
        UINotifier.notifyObservers(ViewUpdate.CHAT_UPDATE, null);
    }

    @Override
    public synchronized void setLobbyModelView(LobbyModelView lobbyModelView) {
        this.lobbyModelView = lobbyModelView;
        UINotifier.setChanged();
        UINotifier.notifyObservers(ViewUpdate.LOBBY_UPDATE, null);
    }

    @Override
    public synchronized void setGameModelView(FullGameModelView theNewOne) {
        if(this.fullGameModelView != null) {
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
        //debugDiff();
    }

    @Override
    public synchronized void updateDrawView(DrawModelView drawModelView) {
        this.oldGameModelView = new FullGameModelView(fullGameModelView);
        FullGameModelView oldModelView = new FullGameModelView(fullGameModelView);

        fullGameModelView.applyChanges(drawModelView);

        oldModelView.diff(this.fullGameModelView);
        this.gameDifferences= oldModelView;

        UINotifier.setChanged();
        UINotifier.notifyObservers(ViewUpdate.GAME_UPDATE, null);
        //debugDiff();
    }

    @Override
    public synchronized void updateOrganizeView(OrganizeModelView organizeModelView) {
        this.oldGameModelView = new FullGameModelView(fullGameModelView);
        FullGameModelView oldModelView = new FullGameModelView(fullGameModelView);

        fullGameModelView.applyChanges(organizeModelView);

        oldModelView.diff(this.fullGameModelView);
        this.gameDifferences= oldModelView;

        UINotifier.setChanged();
        UINotifier.notifyObservers(ViewUpdate.GAME_UPDATE, null);
        //debugDiff();
    }

    @Override
    public synchronized void updateInsertView(InsertModelView insertModelView) {
        this.oldGameModelView = new FullGameModelView(fullGameModelView);
        FullGameModelView oldModelView = new FullGameModelView(fullGameModelView);

        fullGameModelView.applyChanges(insertModelView);

        oldModelView.diff(this.fullGameModelView);
        this.gameDifferences= oldModelView;

        UINotifier.setChanged();
        UINotifier.notifyObservers(ViewUpdate.GAME_UPDATE, null);

        //debugDiff();
    }

    @Override
    public synchronized void updateTurnView(EndTurnModelView endTurnModelView) {
        this.oldGameModelView = new FullGameModelView(fullGameModelView);
        FullGameModelView oldModelView = new FullGameModelView(fullGameModelView);

        fullGameModelView.applyChanges(endTurnModelView);

        oldModelView.diff(this.fullGameModelView);
        this.gameDifferences= oldModelView;

        UINotifier.setChanged();
        UINotifier.notifyObservers(ViewUpdate.GAME_UPDATE, null);

        //debugDiff();
    }

    @Override
    public synchronized void setView(ViewType viewType) {
        this.viewType = viewType;
        if(viewType==ViewType.INIT) {
            fullGameModelView = null;
            gameDifferences = null;
            oldGameModelView = null;
            lobbyModelView = null;
            playerChatModelView = null;
        }
        UINotifier.setChanged();
        UINotifier.notifyObservers(ViewUpdate.VIEW_TYPE_UPDATE, null);
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

    public synchronized void debugDiff(){
        if(this.gameDifferences==null) return;
        boolean changedPlayer = isCurrentPlayerDiff();
        String newPlayer = getCurrentPlayerDiff();
        String lastPlayer = getLastPlayer();
        boolean changedState = isCurrentStateDiff();
        GameState newState = getCurrentStateDiff();
        boolean remainingTilesChanged = isRemainingTilesDiff();
        int newRemainingTiles = getRemainingTilesDiff();
        HashMap<Position,ItemTile> livingRoomDiff = getLivingRoomDiff();
        List<CommonGoalModelView> commonGoalDiff = getCommonGoalDiff();
        int lastPlayerPointDiff = getLastPlayerPointsDiff();
        List<Token> lastPlayerTokenDiff = getLastPlayerTokenDiff();
        List<ItemTile> lastPlayerTakenTileDiff = getLastPlayerTakenTilesDiff();
        boolean hasInserted = hasLastPlayerInsertedTiles();
        int lastColumnUsed = getLastPlayerInsertedColumnPosition();
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
}
