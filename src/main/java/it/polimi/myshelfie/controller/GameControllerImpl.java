package it.polimi.myshelfie.controller;

import it.polimi.myshelfie.controller.interfaces.ChatController;
import it.polimi.myshelfie.controller.interfaces.GameController;
import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.Position;
import it.polimi.myshelfie.model.chatting.Message;
import it.polimi.myshelfie.model.exceptions.FullColumnException;
import it.polimi.myshelfie.model.interfaces.ChatModel;
import it.polimi.myshelfie.model.interfaces.ClientExceptionsNotifier;
import it.polimi.myshelfie.model.interfaces.GameModel;


import java.util.HashMap;
import java.util.List;

public class GameControllerImpl implements GameController, ChatController {
    private final GameModel gameModel;
    private final ChatModel chatModel;

    public GameControllerImpl(GameModel gameModel, ChatModel chatModel){
        this.gameModel = gameModel;
        this.chatModel = chatModel;
    }

    public void initializeGame() {
        gameModel.initialize();
        gameModel.refillLivingRoom();
    }

    @Override
    public void drawTiles(List<Position> positions, String playerName) {
        try {
            gameModel.drawTiles(positions, playerName);
        } catch (Exception e) {
            //TODO notify exception to view
        }
    }

    @Override
    public void orderChosenTiles(List<ItemTile> orderedTiles, String playerName) {
        try {
            gameModel.orderChosenTiles(orderedTiles, playerName);
        } catch (Exception e) {
            //TODO notify exception to view
        }
    }

    @Override
    public void insertTiles(String playerName, int column) {
        try {
            gameModel.insertTiles(playerName, column);
        } catch (Exception e) {
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
}
