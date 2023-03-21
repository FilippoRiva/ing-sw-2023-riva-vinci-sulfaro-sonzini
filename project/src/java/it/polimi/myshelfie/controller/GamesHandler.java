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
    private HashMap<String, LobbyController> gameLobby;
    private List<GameFile> savedGames;

    public Game addGame(String roomName){
        Game game = new Game(roomName);
        gameControllers.put(roomName, game);
        return game;
    }

    public void addGameConfig(String roomName, GameConfig gameConfig){
        getLobby(roomName).setGameConfig(gameConfig);
    }

    public GameController getGame(String roomName){
        return gameControllers.get(roomName);
    }

    public boolean isRoomNameValid(String roomName){
        return !gameControllers.containsKey(roomName);
    }

    public GameController getController(String roomName) {
        return gameControllers.get(roomName);
    }
    public ChatController getGameChat(String roomName) {
        return gameChats.get(roomName);
    }

    public HashMap<String, LobbyController> getGameLobby() {
        return gameLobby;
    }

    public LobbyController getLobby(String roomName) {
        return gameLobby.get(roomName);
    }
    public GameConfig getConfig(String roomName) {
        return getLobby(roomName).getGameConfig();
    }
}
