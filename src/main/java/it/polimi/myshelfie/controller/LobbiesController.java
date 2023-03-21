package it.polimi.myshelfie.controller;

import it.polimi.myshelfie.GameConfig;
import it.polimi.myshelfie.controller.interfaces.LobbyController;
import it.polimi.myshelfie.model.Game;
import it.polimi.myshelfie.model.interfaces.ChatModel;
import it.polimi.myshelfie.model.interfaces.GameModel;
import it.polimi.myshelfie.model.interfaces.LobbyModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LobbiesController implements LobbyController {
    private HashMap<String, GameModel> games;
    private HashMap<String, ChatModel> chats;
    private HashMap<String, LobbyModel> lobbies;
    private List<GameFile> savedGames;

    @Override
    public void createGame(String playerName, String roomName) throws Exception {
        if(!games.containsKey(roomName)) throw new Exception("room name is not available");

        LobbyModel gameLobby = new Game(roomName);

        lobbies.put(roomName, gameLobby);
        chats.put(roomName, gameLobby.getChatController());
        games.put(roomName, gameLobby.getGameController());

        gameLobby.addPlayer(playerName);
    }
    @Override
    public void setGameConfig(String roomName, GameConfig configs) throws Exception {
        if(!games.containsKey(roomName)) throw new Exception("room does not exist");
        getLobby(roomName).setGameConfig(configs);
    }
    @Override
    public List<String> askGameList() {
        return new ArrayList<>(lobbies.keySet());
    }
    @Override
    public void joinGame(String playerName, String roomName) throws Exception {
        if(!games.containsKey(roomName)) throw new Exception("room does not exist");
        if(getLobby(roomName).isPlayerNameValid(playerName)) throw new Exception("player name already exists");

        getLobby(roomName).addPlayer(playerName);
        if(getController(roomName).getNumberOfPlayers() == getConfig(roomName).getNumPlayers()) {
            getController(roomName).init();

        }
    }
    @Override
    public void exitGame(String playerName, String roomName) throws Exception {
        if(!games.containsKey(roomName)) throw new Exception("room does not exist");
        getLobby(roomName).removePlayer(playerName);
    }

    private GameModel getController(String roomName) {
        return games.get(roomName);
    }
    private ChatModel getChat(String roomName) {
        return chats.get(roomName);
    }
    private LobbyModel getLobby(String roomName) {
        return lobbies.get(roomName);
    }
    private GameConfig getConfig(String roomName) {
        return getLobby(roomName).getGameConfig();
    }
}
