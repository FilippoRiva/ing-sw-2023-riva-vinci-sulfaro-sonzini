package it.polimi.myshelfie.controller;

import it.polimi.myshelfie.GameConfig;
import it.polimi.myshelfie.controller.interfaces.LobbiesController;
import it.polimi.myshelfie.model.Game;
import it.polimi.myshelfie.model.interfaces.LobbyModel;

import java.util.HashMap;
import java.util.List;

public class LobbiesControllerImpl implements LobbiesController {
    private HashMap<String, GameControllerImpl> gamesModel = new HashMap<>();
    private HashMap<String, LobbyModel> lobbiesModel = new HashMap<>();
    private List<GameFile> savedGames;

    @Override
    public void createGame(String playerName, String lobbyName) throws Exception {
        if(gamesModel.containsKey(lobbyName)) throw new Exception("room name is not available");

        LobbyModel gameLobby = new Game();

        lobbiesModel.put(lobbyName, gameLobby);
        gamesModel.put(lobbyName, new GameControllerImpl(gameLobby.getGameModel(), gameLobby.getChatModel()));

        gameLobby.addPlayer(playerName);
    }
    @Override
    public void setGameConfig(String lobbyName, GameConfig configs) throws Exception {
        if(!gamesModel.containsKey(lobbyName)) throw new Exception("room does not exist");
        getLobby(lobbyName).setGameConfig(configs);
    }
    @Override
    public void askGameList() {
        //TODO implementation
    }
    @Override
    public void joinGame(String playerName, String lobbyName) throws Exception {
        if(!gamesModel.containsKey(lobbyName)) throw new Exception("room does not exist");
        if(!getLobby(lobbyName).isPlayerNameValid(playerName)) throw new Exception("player name already exists");

        if(getLobby(lobbyName).isLobbyFull())
            throw new Exception("room full");
        else
            getLobby(lobbyName).addPlayer(playerName);

        if(getLobby(lobbyName).isLobbyFull())
            getController(lobbyName).initializeGame();
    }
    @Override
    public void exitGame(String playerName, String lobbyName) throws Exception {
        if(!gamesModel.containsKey(lobbyName)) throw new Exception("room does not exist");
        getLobby(lobbyName).removePlayer(playerName);
    }

    private LobbyModel getLobby(String lobbyName) {
        return lobbiesModel.get(lobbyName);
    }
    private GameControllerImpl getController(String lobbyName) {
        return gamesModel.get(lobbyName);
    }
}
