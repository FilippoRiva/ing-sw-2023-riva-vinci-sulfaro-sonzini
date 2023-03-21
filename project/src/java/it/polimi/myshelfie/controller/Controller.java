package it.polimi.myshelfie.controller;

import it.polimi.myshelfie.GameConfig;
import it.polimi.myshelfie.controller.interfaces.GameView;
import it.polimi.myshelfie.controller.interfaces.LobbyView;
import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.Position;

import java.util.ArrayList;
import java.util.List;

public class Controller implements GameView, LobbyView {
    GamesHandler gamesHandler = new GamesHandler();

    @Override
    public void createGame(String namePlayer, String roomName) throws Exception {
        if(!gamesHandler.isRoomNameValid(roomName)) throw new Exception("name of the room is not available");
        gamesHandler.addGame(roomName).addPlayer(namePlayer);
    }
    @Override
    public List<String> askGameList() {
        return new ArrayList<>(gamesHandler.getGameLobby().keySet());
    }
    @Override
    public void joinGame(String namePlayer, String roomName) throws Exception {
        if(!gamesHandler.isRoomNameValid(roomName)) throw new Exception("room does not exist");
        gamesHandler.getLobby(roomName).addPlayer(namePlayer);
        if(gamesHandler.getController(roomName).getNumberOfPlayers() == gamesHandler.getConfig(roomName).getNumPlayers())
            initializeGame(roomName);
    }
    @Override
    public void setGameConfig(String roomName, GameConfig configs) throws Exception {
        if(!gamesHandler.isRoomNameValid(roomName)) throw new Exception("room does not exist");
        gamesHandler.addGameConfig(roomName, configs);
    }
    @Override
    public void exitGame(String namePlayer, String roomName) throws Exception {
        if(!gamesHandler.isRoomNameValid(roomName)) throw new Exception("room does not exist");
        gamesHandler.getLobby(roomName).removePlayer(namePlayer);
    }
    private void initializeGame(String roomName) throws Exception {
        if(!gamesHandler.isRoomNameValid(roomName)) throw new Exception("room does not exist");
        gamesHandler.getController(roomName).init();
    }
    @Override
    public void deleteTileFromLivingRoom(List<Position> takenTiles, String playerName, String roomName) throws Exception {
        if(!gamesHandler.isRoomNameValid(roomName)) throw new Exception("room does not exist");
        gamesHandler.getController(roomName).takeTilesFromLivingRoom(takenTiles);
    }

    @Override
    public void insertTileInBookshelf(List<ItemTile> takenTiles, int column, String playerName, String roomName) throws Exception {
        if(!gamesHandler.isRoomNameValid(roomName)) throw new Exception("room does not exist");
        gamesHandler.getController(roomName).addTilesInBookshelf(playerName, takenTiles, column);
    }
}
