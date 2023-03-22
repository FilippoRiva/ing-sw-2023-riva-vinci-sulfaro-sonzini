package it.polimi.myshelfie.controller;

import it.polimi.myshelfie.GameConfig;
import it.polimi.myshelfie.controller.interfaces.LobbyController;
import it.polimi.myshelfie.model.Game;
import it.polimi.myshelfie.model.interfaces.LobbyModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LobbiesController implements LobbyController {
    private HashMap<String, GameControllerImpl> games = new HashMap<>();
    private HashMap<String, LobbyModel> lobbiesModel = new HashMap<>();
    private List<GameFile> savedGames;

    @Override
    public void createGame(String playerName, String roomName) throws Exception {
        if(games.containsKey(roomName)) throw new Exception("room name is not available");

        LobbyModel gameLobby = new Game(roomName);

        lobbiesModel.put(roomName, gameLobby);
        games.put(roomName, new GameControllerImpl(gameLobby.getGameController(), gameLobby.getChatController()));

        gameLobby.addPlayer(playerName);
    }
    @Override
    public void setGameConfig(String roomName, GameConfig configs) throws Exception {
        if(!games.containsKey(roomName)) throw new Exception("room does not exist");
        getLobby(roomName).setGameConfig(configs);
    }
    @Override
    public List<String> askGameList() {
        return new ArrayList<>(lobbiesModel.keySet());
    }
    @Override
    public void joinGame(String playerName, String roomName) throws Exception {
        if(!games.containsKey(roomName)) throw new Exception("room does not exist");
        if(!getLobby(roomName).isPlayerNameValid(playerName)) throw new Exception("player name already exists");

        getLobby(roomName).addPlayer(playerName);

        if(getController(roomName).roomFull())
            getController(roomName).init();
    }
    @Override
    public void exitGame(String playerName, String roomName) throws Exception {
        if(!games.containsKey(roomName)) throw new Exception("room does not exist");
        getLobby(roomName).removePlayer(playerName);
    }

    private LobbyModel getLobby(String roomName) {
        return lobbiesModel.get(roomName);
    }
    private GameControllerImpl getController(String roomName) {
        return games.get(roomName);
    }
}
