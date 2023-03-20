package it.polimi.myshelfie.controller;

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

    public Game addGame(String name){
        Game game = new Game(name);
        gameControllers.put(name, game);
        return game;
    }

    public GameController getGame(String roomName){
        return gameControllers.get(roomName);
    }

    public boolean isRoomNameValid(String roomName){
        return !gameControllers.containsKey(roomName);
    }

    public HashMap<String, GameController> getGameControllers() {
        return gameControllers;
    }

    public HashMap<String, ChatController> getGameChats() {
        return gameChats;
    }

    public HashMap<String, LobbyController> getGameLobby() {
        return gameLobby;
    }
}
