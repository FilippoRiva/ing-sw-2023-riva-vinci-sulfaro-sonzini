package it.polimi.myshelfie.controller;

import it.polimi.myshelfie.GameConfig;
import it.polimi.myshelfie.model.Game;
import it.polimi.myshelfie.model.interfaces.ChatModel;
import it.polimi.myshelfie.model.interfaces.GameModel;
import it.polimi.myshelfie.model.interfaces.LobbyModel;

import java.util.HashMap;
import java.util.List;

public class GamesHandler {
    private HashMap<String, GameModel> gameControllers;
    private HashMap<String, ChatModel> gameChats;
    private HashMap<String, LobbyModel> gameLobbies;
    private List<GameFile> savedGames;

    public LobbyModel addGame(String roomName){
        LobbyModel gameLobby = new Game(roomName);
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

    public GameModel getController(String roomName) {
        return gameControllers.get(roomName);
    }
    public ChatModel getChat(String roomName) {
        return gameChats.get(roomName);
    }

    public HashMap<String, LobbyModel> getLobbies() {
        return gameLobbies;
    }

    public LobbyModel getLobby(String roomName) {
        return gameLobbies.get(roomName);
    }
    public GameConfig getConfig(String roomName) {
        return getLobby(roomName).getGameConfig();
    }
}
