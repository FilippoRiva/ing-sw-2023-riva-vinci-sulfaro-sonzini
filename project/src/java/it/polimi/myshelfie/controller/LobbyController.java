package it.polimi.myshelfie.controller;

import it.polimi.myshelfie.GameConfig;
import it.polimi.myshelfie.model.interfaces.LobbyModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LobbyController implements it.polimi.myshelfie.controller.interfaces.LobbyController {
    GamesHandler gamesHandler = new GamesHandler();
    HashMap<String,GameControllerImpl> gameControllers = new HashMap<>();
    @Override
    public void createGame(String playerName, String roomName) throws Exception {
        if(!gamesHandler.isRoomNameValid(roomName)) throw new Exception("room name is not available");
        LobbyModel newLobby = gamesHandler.addGame(roomName);
        newLobby.addPlayer(playerName);
       gameControllers.put(roomName,new GameControllerImpl(newLobby.getGameController(),newLobby.getChatController()));
    }
    @Override
    public List<String> askGameList() {
        return new ArrayList<>(gamesHandler.getLobbies().keySet());
    }
    @Override
    public void joinGame(String playerName, String roomName) throws Exception {
        if(!gamesHandler.isRoomNameValid(roomName)) throw new Exception("room does not exist");
        gamesHandler.getLobby(roomName).addPlayer(playerName);
        if(gamesHandler.getController(roomName).getNumberOfPlayers() == gamesHandler.getConfig(roomName).getNumPlayers())
            gamesHandler.getController(roomName).init();
    }
    @Override
    public void setGameConfig(String roomName, GameConfig configs) throws Exception {
        if(!gamesHandler.isRoomNameValid(roomName)) throw new Exception("room does not exist");
        gamesHandler.addGameConfig(roomName, configs);
    }
    @Override
    public void exitGame(String playerName, String roomName) throws Exception {
        if(!gamesHandler.isRoomNameValid(roomName)) throw new Exception("room does not exist");
        gamesHandler.getLobby(roomName).removePlayer(playerName);
    }
}
