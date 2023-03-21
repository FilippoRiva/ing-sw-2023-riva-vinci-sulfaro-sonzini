package it.polimi.myshelfie.controller;

import it.polimi.myshelfie.controller.interfaces.GameView;
import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.Position;

import java.util.List;

public class GameController implements GameView {
    GamesHandler gamesHandler = new GamesHandler();
    @Override
    public void deleteTileFromLivingRoom(List<Position> takenTiles, String roomName) throws Exception {
        if(!gamesHandler.isRoomNameValid(roomName)) throw new Exception("room does not exist");
        gamesHandler.getController(roomName).takeTilesFromLivingRoom(takenTiles);
    }

    @Override
    public void insertTileInBookshelf(List<ItemTile> takenTiles, int column, String playerName, String roomName) throws Exception {
        if(!gamesHandler.isRoomNameValid(roomName)) throw new Exception("room does not exist");
        gamesHandler.getController(roomName).addTilesInBookshelf(playerName, takenTiles, column);
    }
}
