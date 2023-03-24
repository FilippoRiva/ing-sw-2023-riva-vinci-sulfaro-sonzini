package it.polimi.myshelfie.controller;

import it.polimi.myshelfie.controller.interfaces.ChatController;
import it.polimi.myshelfie.controller.interfaces.GameController;
import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.Position;
import it.polimi.myshelfie.model.chatting.Message;
import it.polimi.myshelfie.model.exceptions.FullColumnException;
import it.polimi.myshelfie.model.interfaces.ChatModel;
import it.polimi.myshelfie.model.interfaces.GameModel;

import java.security.InvalidParameterException;
import java.util.List;

public class GameControllerImpl implements GameController, ChatController {
    private final GameModel gameModel;
    private final ChatModel chatModel;

    public GameControllerImpl(GameModel gameModel, ChatModel chatModel){
        this.gameModel = gameModel;
        this.chatModel = chatModel;
    }

    public void initializeGame() {
        gameModel.initializeGame();
    }

    @Override
    public void drawTiles(List<Position> positions, String playerName) throws InvalidParameterException {
        gameModel.drawTiles(positions, playerName);
    }

    @Override
    public void orderChosenTiles(List<ItemTile> orderedTiles, String playerName) throws InvalidParameterException {
        gameModel.orderChosenTiles(orderedTiles, playerName);
    }

    @Override
    public void insertTiles(String playerName, int column) throws InvalidParameterException, FullColumnException {
        gameModel.insertTiles(playerName, column);

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
}
