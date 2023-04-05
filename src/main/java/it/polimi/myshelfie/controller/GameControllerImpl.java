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


import java.util.List;

public class GameControllerImpl implements GameController, ChatController{

    private final LobbyModel lobbyModel;
    private final GameModel gameModel;
    private final ChatModel chatModel;
    private final ControllerImpl controllerImpl;


    public GameControllerImpl(LobbyModel lobbyModel, GameModel gameModel, ChatModel chatModel, ControllerImpl controllerImpl){
        this.lobbyModel = lobbyModel;
        this.gameModel = gameModel;
        this.chatModel = chatModel;
        this.controllerImpl=controllerImpl;

    }
    @Override
    public void drawTiles(List<Position> positions, String playerName) {
        try {
            gameModel.drawTiles(positions, playerName);
        } catch (InvalidParameterException e) {
            //TODO notify exception to view
        }
    }

    @Override
    public void orderChosenTiles(List<ItemTile> orderedTiles, String playerName) {
        try {
            gameModel.orderChosenTiles(orderedTiles, playerName);
        } catch (InvalidParameterException e) {
            //TODO notify exception to view
        }
    }

    @Override
    public void insertTiles(String playerName, int column) {
        try {
            gameModel.insertTiles(playerName, column);
        } catch (InvalidParameterException | FullColumnException e) {
            //TODO notify exception to view
        }

        endTurn(playerName);
    }
    private void endTurn(String playerName){
        gameModel.checkCommonGoal(playerName);
        gameModel.refillLivingRoom();
        gameModel.nextTurn();
    }
    @Override
    public void addMessage(Message message){
        chatModel.addMessage(message);
    }

    public LobbyModel getLobbyModel(){
        return lobbyModel;
    }
}
