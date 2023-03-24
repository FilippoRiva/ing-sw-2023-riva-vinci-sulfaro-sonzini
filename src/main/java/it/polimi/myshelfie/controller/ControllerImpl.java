package it.polimi.myshelfie.controller;

import it.polimi.myshelfie.GameConfig;
import it.polimi.myshelfie.controller.interfaces.Controller;
import it.polimi.myshelfie.model.ModelImpl;
import it.polimi.myshelfie.model.interfaces.Model;
import it.polimi.myshelfie.model.interfaces.LobbyModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ControllerImpl implements Controller {
    private final HashMap<String, GameControllerImpl> gamesControllers;
    private final HashMap<String, LobbyModel> lobbiesModels;
    private final Model model;
    private final List<GameFile> savedGames;

    public ControllerImpl(){
        this.gamesControllers = new HashMap<>();
        this.lobbiesModels = new HashMap<>();
        this.model = new ModelImpl();
        this.savedGames = new ArrayList<>();
    }

    @Override
    public void createGame(String playerName, String lobbyName) throws Exception {
        if(gamesControllers.containsKey(lobbyName)) throw new Exception("lobby name is not available");
        //creates new model implementation
        LobbyModel lobbyModel = model.addLobby(lobbyName);
        lobbiesModels.put(lobbyName, lobbyModel);

        gamesControllers.put(lobbyName, new GameControllerImpl(lobbyModel.getGameModel(), lobbyModel.getChatModel()));

        //modifies model
        lobbyModel.addPlayer(playerName);
    }
    @Override
    public void setGameConfig(String lobbyName, GameConfig configs) throws Exception {
        if(!gamesControllers.containsKey(lobbyName)) throw new Exception("lobby does not exist");
        getLobbyModel(lobbyName).setGameConfig(configs);
    }
    @Override
    public void askGameList() {
        model.askGameList();
    }
    @Override
    public void joinGame(String playerName, String lobbyName) throws Exception {
        if(!gamesControllers.containsKey(lobbyName)) throw new Exception("lobby does not exist");
        if(!getLobbyModel(lobbyName).isPlayerNameValid(playerName)) throw new Exception("player name already exists");

        if(getLobbyModel(lobbyName).isLobbyFull())
            throw new Exception("lobby full");
        else
            getLobbyModel(lobbyName).addPlayer(playerName);

        if(getLobbyModel(lobbyName).isLobbyFull())
            getController(lobbyName).initializeGame();
    }
    @Override
    public void exitGame(String playerName, String lobbyName) throws Exception {
        if(!gamesControllers.containsKey(lobbyName)) throw new Exception("lobby does not exist");
        getLobbyModel(lobbyName).removePlayer(playerName);
    }

    private LobbyModel getLobbyModel(String lobbyName) {
        return lobbiesModels.get(lobbyName);
    }
    private GameControllerImpl getController(String lobbyName) {
        return gamesControllers.get(lobbyName);
    }
}
