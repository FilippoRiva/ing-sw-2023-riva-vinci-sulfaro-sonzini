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
import it.polimi.myshelfie.network.ServerConnectionHandler;
import it.polimi.myshelfie.network.observers.NetworkChatObserver;
import it.polimi.myshelfie.network.observers.NetworkGameObserver;


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
        try {
            gameModel.drawTiles(positions, playerName);
        } catch (InvalidParameterException e) {
            exceptionsNotifiersMap.get(playerName).notifyException(e);
        }
    }

    @Override
    public synchronized void orderChosenTiles(List<ItemTile> orderedTiles, String playerName) {
        try {
            gameModel.orderChosenTiles(orderedTiles, playerName);
        } catch (InvalidParameterException e) {
            exceptionsNotifiersMap.get(playerName).notifyException(e);
        }
    }

    @Override
    public synchronized void insertTiles(String playerName, int column) {
        try {
            gameModel.insertTiles(playerName, column);
        } catch (InvalidParameterException | FullColumnException e) {
            exceptionsNotifiersMap.get(playerName).notifyException(e);
        }

        endTurn(playerName);
    }

    @Override
    public synchronized void addGameObserver(NetworkGameObserver networkGameObserver) {
        networkGameObserver.setGameModel(gameModel);
        gameModel.addGameObserver(networkGameObserver);
    }
    public void init(Map<String,Client> clientsMap, Map<String,ServerConnectionHandler> connectionHandlersMap,
                     Map<String, ClientExceptionsNotifier> notifiersMap){
        for(Client c : clientsMap.values()) {
            NetworkGameObserver ngo = new NetworkGameObserver(c);
            ngo.setGameModel(gameModel);
            addGameObserver(ngo);
        }
        this.clientsMap = clientsMap;
        this.connectionHandlersMap = connectionHandlersMap;
        this.exceptionsNotifiersMap = notifiersMap;
        for(ServerConnectionHandler sch : connectionHandlersMap.values()){
            try {
                sch.setGameController(this);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        gameModel.setState(GameState.DRAW);
    }

    /**
     *
     * @param playerName is the current player that has finished its turn
     */
    private void endTurn(String playerName){
        gameModel.checkCommonGoal(playerName);
        gameModel.refillLivingRoom();
        gameModel.nextTurn();
    }
    @Override
    public synchronized void addMessage(Message message){
        chatModel.addMessage(message);
    }

    @Override
    public synchronized void addChatObserver(NetworkChatObserver networkChatObserver) {
        networkChatObserver.setChatModel(chatModel);
        chatModel.addChatObserver(networkChatObserver);
    }
}
