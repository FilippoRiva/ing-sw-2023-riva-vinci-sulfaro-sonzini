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
    private GameModel gameModel;
    private ChatModel chatModel;

    public GameControllerImpl(GameModel gameModel, ChatModel chatModel){
        this.gameModel = gameModel;
        this.chatModel = chatModel;
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

    @Override
    public void addMessage(Message message){
        chatModel.addMessage(message);
    }

    private void endTurn(String playerName){
        gameModel.checkCommonGoal(playerName);

        gameModel.refillLivingRoom();
        gameModel.nextTurn();
    }

    public boolean roomFull() {
        return gameModel.getNumberOfPlayers() == gameModel.getGameConfig().getNumPlayers();
    }

    public void init() {
        gameModel.init();
        gameModel.setCommonGoals(CommonGoalFactory.getGoals(gameModel.getNumberOfPlayers()));
    }
}
