package it.polimi.myshelfie.controller;

import it.polimi.myshelfie.GameConfig;
import it.polimi.myshelfie.model.Game;
import it.polimi.myshelfie.model.interfaces.ChatController;
import it.polimi.myshelfie.model.interfaces.GameController;
import it.polimi.myshelfie.model.interfaces.LobbyController;

import java.util.HashMap;
import java.util.List;

public class GamesHandler {
    private HashMap<String, GameController> gameControllers;
    private HashMap<String, ChatController> gameChats;
    private HashMap<String, LobbyController> gameLobbies;
    private List<GameFile> savedGames;

    public LobbyController addGame(String roomName){
        LobbyController gameLobby = new Game(roomName);
        gameLobbies.put(roomName,gameLobby);
        gameChats.put(roomName,gameLobby.getChatController());
        gameControllers.put(roomName,gameLobby.getGameController());
        return gameLobby;
    }

    public void addGameConfig(String roomName, GameConfig gameConfig){
        getLobby(roomName).setGameConfig(gameConfig);
    }

    public boolean isRoomNameValid(String roomName){
        return !gameControllers.containsKey(roomName);
    }

    public GameController getController(String roomName) {
        return gameControllers.get(roomName);
    }
    public ChatController getChat(String roomName) {
        return gameChats.get(roomName);
    }

    public HashMap<String, LobbyController> getLobbies() {
        return gameLobbies;
    }

    public LobbyController getLobby(String roomName) {
        return gameLobbies.get(roomName);
    }
    public GameConfig getConfig(String roomName) {
        return getLobby(roomName).getGameConfig();
    }
}
