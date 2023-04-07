package it.polimi.myshelfie.controller;

import it.polimi.myshelfie.controller.interfaces.ChatController;
import it.polimi.myshelfie.controller.interfaces.GameController;
import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.Position;
import it.polimi.myshelfie.model.chatting.Message;
import it.polimi.myshelfie.model.exceptions.FullColumnException;
import it.polimi.myshelfie.model.exceptions.InvalidParameterException;
import it.polimi.myshelfie.model.interfaces.ChatModel;
import it.polimi.myshelfie.model.interfaces.GameModel;
import it.polimi.myshelfie.model.interfaces.LobbyModel;
import it.polimi.myshelfie.network.observers.NetworkChatObserver;
import it.polimi.myshelfie.network.observers.NetworkGameObserver;


import java.util.List;

public class GameControllerImpl implements GameController, ChatController{

    private final GameModel gameModel;
    private final ChatModel chatModel;
    private final ControllerImpl controllerImpl;


    public GameControllerImpl(GameModel gameModel, ChatModel chatModel, ControllerImpl controllerImpl){
        this.gameModel = gameModel;
        this.chatModel = chatModel;
        this.controllerImpl=controllerImpl;

    }
    @Override
    public synchronized void drawTiles(List<Position> positions, String playerName) {
        try {
            gameModel.drawTiles(positions, playerName);
        } catch (InvalidParameterException e) {
            //TODO notify exception to view
        }
    }

    @Override
    public synchronized void orderChosenTiles(List<ItemTile> orderedTiles, String playerName) {
        try {
            gameModel.orderChosenTiles(orderedTiles, playerName);
        } catch (InvalidParameterException e) {
            //TODO notify exception to view
        }
    }

    @Override
    public synchronized void insertTiles(String playerName, int column) {
        try {
            gameModel.insertTiles(playerName, column);
        } catch (InvalidParameterException | FullColumnException e) {
            //TODO notify exception to view
        }

        endTurn(playerName);
    }

    @Override
    public synchronized void addGameObserver(NetworkGameObserver networkGameObserver) {
        networkGameObserver.setGameModel(gameModel);
        gameModel.addGameObserver(networkGameObserver);
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
