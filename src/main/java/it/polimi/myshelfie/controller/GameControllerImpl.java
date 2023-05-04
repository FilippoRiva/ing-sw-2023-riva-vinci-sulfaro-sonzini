package it.polimi.myshelfie.controller;

import it.polimi.myshelfie.controller.interfaces.ChatController;
import it.polimi.myshelfie.controller.interfaces.GameController;
import it.polimi.myshelfie.model.GameState;
import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.Position;
import it.polimi.myshelfie.model.chatting.Message;
import it.polimi.myshelfie.model.exceptions.FullColumnException;
import it.polimi.myshelfie.model.exceptions.InvalidParameterException;
import it.polimi.myshelfie.model.interfaces.ChatModel;
import it.polimi.myshelfie.model.interfaces.ClientExceptionsNotifier;
import it.polimi.myshelfie.model.interfaces.GameModel;
import it.polimi.myshelfie.network.Client;
import it.polimi.myshelfie.network.NetworkMessage;
import it.polimi.myshelfie.network.ServerConnectionHandler;
import it.polimi.myshelfie.network.observers.NetworkChatObserver;
import it.polimi.myshelfie.network.observers.NetworkGameObserver;
import it.polimi.myshelfie.network.observers.NetworkLobbyObserver;
import it.polimi.myshelfie.view.ViewType;


import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameControllerImpl implements GameController, ChatController{

    private final GameModel gameModel;
    private final ChatModel chatModel;
    private final ControllerImpl controllerImpl;
    private Map<String,Client> clientsMap;
    private Map<String,ServerConnectionHandler> connectionHandlersMap;
    private Map<String,ClientExceptionsNotifier> exceptionsNotifiersMap;
    private Map<String, NetworkLobbyObserver> lobbyObserverMap;
    private Map<String, NetworkChatObserver> chatObserverMap;
    private Map<String, NetworkGameObserver> gameObserverMap;

    public GameControllerImpl(GameModel gameModel, ChatModel chatModel, ControllerImpl controllerImpl){
        this.gameModel = gameModel;
        this.chatModel = chatModel;
        this.controllerImpl=controllerImpl;
        this.clientsMap = new HashMap<>();
        this.connectionHandlersMap = new HashMap<>();
        this.exceptionsNotifiersMap = new HashMap<>();
    }
    @Override
    public synchronized void drawTiles(List<Position> positions, String playerName) {
        GameState currentGameState = gameModel.getState();
        if(currentGameState != GameState.DRAW && currentGameState != GameState.LASTDRAW) return;
        try {
            gameModel.drawTiles(positions, playerName);
            if(gameModel.getState()==GameState.DRAW) gameModel.setState(GameState.ORGANIZE);
            else gameModel.setState(GameState.LASTORGANIZE);
        } catch (InvalidParameterException e) {
            exceptionsNotifiersMap.get(playerName).notifyException(e);
        }
    }

    @Override
    public synchronized void orderChosenTiles(List<ItemTile> orderedTiles, String playerName) {
        GameState currentGameState = gameModel.getState();
        if(currentGameState != GameState.ORGANIZE && currentGameState != GameState.LASTORGANIZE) return;
        try {
            gameModel.orderChosenTiles(orderedTiles, playerName);
            if(currentGameState==GameState.ORGANIZE) gameModel.setState(GameState.INSERT);
            else gameModel.setState(GameState.LASTINSERT);
        } catch (InvalidParameterException e) {
            exceptionsNotifiersMap.get(playerName).notifyException(e);
        }
    }

    @Override
    public synchronized void insertTiles(String playerName, int column) {
        GameState currentGameState = gameModel.getState();
        if(currentGameState != GameState.INSERT && currentGameState != GameState.LASTINSERT) return;
        try {
            gameModel.insertTiles(playerName, column);
            if(currentGameState == GameState.INSERT) gameModel.setState(GameState.COUNTINGPOINTS);
            else gameModel.setState(GameState.LASTCOUNTINGPOINTS);
        } catch (InvalidParameterException | FullColumnException e) {
            exceptionsNotifiersMap.get(playerName).notifyException(e);
            return;
        }
        if(gameModel.isBookshelfFull(playerName) || gameModel.getState() == GameState.LASTCOUNTINGPOINTS){
            if(gameModel.isLastPlayer(playerName)){
                gameModel.calculateWinResults();
                gameModel.setState(GameState.WIN);
                for(Client c : clientsMap.values()){
                    try {
                        c.send(new NetworkMessage(ViewType.WIN));
                    } catch (RemoteException ignored) {
                    }
                }
            } else {
                endTurn(playerName);
            }
        } else {
            endTurn(playerName);
        }
    }

    /**
     *
     * @param playerName is the current player that has finished its turn
     */
    private void endTurn(String playerName){
        gameModel.checkCommonGoal(playerName);
        gameModel.refillLivingRoom();
        gameModel.nextTurn();
        if(gameModel.getState()==GameState.COUNTINGPOINTS) gameModel.setState(GameState.DRAW);
        else gameModel.setState(GameState.LASTDRAW);
    }

    @Override
    public synchronized void addGameObserver(NetworkGameObserver networkGameObserver) {
        networkGameObserver.setGameModel(gameModel);
        gameModel.addGameObserver(networkGameObserver);
    }
    public void init(Map<String,Client> clientsMap, Map<String,ServerConnectionHandler> connectionHandlersMap,
                     Map<String, ClientExceptionsNotifier> notifiersMap,
                     Map<String, NetworkLobbyObserver> lobbyObserverMap,
                     Map<String, NetworkChatObserver> chatObserverMap){
        this.clientsMap = clientsMap;
        this.connectionHandlersMap = connectionHandlersMap;
        this.exceptionsNotifiersMap = notifiersMap;
        this.lobbyObserverMap = lobbyObserverMap;
        this.chatObserverMap = chatObserverMap;
        for(ServerConnectionHandler sch : connectionHandlersMap.values()){
            try {
                sch.setGameController(this);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        gameModel.setState(GameState.DRAW);
    }

    @Override
    public synchronized void addMessage(Message message){
        chatModel.addMessage(message);
    }

    @Override
    public synchronized void addChatObserver(String playername, NetworkChatObserver networkChatObserver) {
        networkChatObserver.setChatModel(chatModel);
        chatModel.addChatObserver(networkChatObserver);
    }
}
