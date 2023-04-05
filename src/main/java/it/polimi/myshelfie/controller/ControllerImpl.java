package it.polimi.myshelfie.controller;

import it.polimi.myshelfie.GameConfig;
import it.polimi.myshelfie.controller.interfaces.Controller;
import it.polimi.myshelfie.model.ClientsExceptionsNotifierImpl;
import it.polimi.myshelfie.model.ModelImpl;
import it.polimi.myshelfie.model.exceptions.InvalidParameterException;
import it.polimi.myshelfie.model.exceptions.LobbyExistanceException;
import it.polimi.myshelfie.model.exceptions.LobbyFullException;
import it.polimi.myshelfie.model.interfaces.ClientExceptionsNotifier;
import it.polimi.myshelfie.model.interfaces.Model;
import it.polimi.myshelfie.model.interfaces.LobbyModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ControllerImpl implements Controller {
    private final HashMap<String, GameControllerImpl> gamesControllers;
    private final HashMap<String, LobbyModel> lobbiesModels;
    private final HashMap<String, ClientExceptionsNotifier> clientExceptionsNotifiers;
    private final Model model;
    private final List<GameFile> savedGames;

    public ControllerImpl(){
        this.gamesControllers = new HashMap<>();
        this.lobbiesModels = new HashMap<>();
        this.clientExceptionsNotifiers = new HashMap<>();
        this.model = new ModelImpl();
        this.savedGames = new ArrayList<>();
    }

    @Override
    public void createLobby(String playerName, String lobbyName){
        //creates new model implementation
        LobbyModel lobbyModel;
        try {
            lobbyModel = model.addLobby(lobbyName);
            lobbyModel.addPlayer(playerName);
        } catch (LobbyExistanceException | InvalidParameterException | LobbyFullException e) {
            clientExceptionsNotifiers.get(playerName).notifyException(e);
            return;
        }

        lobbiesModels.put(lobbyName, lobbyModel);
        gamesControllers.put(lobbyName, new GameControllerImpl(lobbyModel.getGameModel(), lobbyModel.getChatModel()));
        clientExceptionsNotifiers.put(playerName, model.getNewClientExceptionNotifier(playerName));
    }
    @Override
    public void setGameConfig(String playerName, String lobbyName, GameConfig configs) {
        LobbyModel lobbyModel = getLobbyModel(lobbyName);
        if (lobbyModel != null) {
            getLobbyModel(lobbyName).setGameConfig(configs);
        } else {
            clientExceptionsNotifiers.get(playerName).notifyException(new Exception("lobby doesn't exists"));
        }
    }
    @Override
    public void joinLobby(String playerName, String lobbyName) {
        LobbyModel lobbyModel = getLobbyModel(lobbyName);
        if (lobbyModel != null) {
            try {
                lobbyModel.addPlayer(playerName);
            } catch (LobbyFullException | InvalidParameterException e) {
                clientExceptionsNotifiers.get(playerName).notifyException(e);
            }
        } else {
            clientExceptionsNotifiers.get(playerName).notifyException(new LobbyExistanceException(false, lobbyName));
        }

        getController(lobbyName).initializeGame();
    }
    @Override
    public void exitLobby(String playerName, String lobbyName) {
        LobbyModel lobbyModel = getLobbyModel(lobbyName);
        if (lobbyModel != null) {
            try {
                getLobbyModel(lobbyName).removePlayer(playerName);
            } catch (InvalidParameterException e) {
                clientExceptionsNotifiers.get(playerName).notifyException(e);
            }
        } else {
            clientExceptionsNotifiers.get(playerName).notifyException(new LobbyExistanceException(false, lobbyName));
        }
    }

    private LobbyModel getLobbyModel(String lobbyName) {
        return lobbiesModels.get(lobbyName);
    }
    private GameControllerImpl getController(String lobbyName) {
        return gamesControllers.get(lobbyName);
    }
}
