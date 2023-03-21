package it.polimi.myshelfie.controller;

import it.polimi.myshelfie.controller.interfaces.ChatController;
import it.polimi.myshelfie.controller.interfaces.GameController;
import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.Position;
import it.polimi.myshelfie.model.chatting.Message;
import it.polimi.myshelfie.model.interfaces.ChatModel;
import it.polimi.myshelfie.model.interfaces.GameModel;

import java.util.List;

public class GameControllerImpl implements GameController, ChatController {
    GameModel gameModel;
    ChatModel chatModel;

    public GameControllerImpl(GameModel gameModel, ChatModel chatModel){
        this.gameModel=gameModel;
        this.chatModel=chatModel;
    }
    @Override
    public void deleteTileFromLivingRoom(List<Position> takenTiles){
        gameModel.deleteTilesFromLivingRoom(takenTiles);
    }

    @Override
    public void insertTileInBookshelf(List<ItemTile> takenTiles, int column, String playerName) {
        gameModel.addTilesInBookshelf(playerName, takenTiles, column);

        endTurn(playerName);
    }

    private void endTurn(String playerName){
        gameModel.checkCommonGoal(playerName);

        gameModel.refillLivingRoom();
    }

    public void addMessage(Message message){
        chatModel.addMessage(message);
    }
}
