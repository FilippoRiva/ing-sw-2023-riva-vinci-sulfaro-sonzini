package it.polimi.myshelfie.controller;

import it.polimi.myshelfie.controller.interfaces.Controller;
import it.polimi.myshelfie.controller.interfaces.LobbyController;
import it.polimi.myshelfie.model.ModelImpl;
import it.polimi.myshelfie.model.exceptions.InvalidParameterException;
import it.polimi.myshelfie.model.exceptions.LobbyExistanceException;
import it.polimi.myshelfie.model.exceptions.LobbyFullException;
import it.polimi.myshelfie.model.interfaces.ClientExceptionsNotifier;
import it.polimi.myshelfie.model.interfaces.Model;
import it.polimi.myshelfie.model.interfaces.LobbyModel;

import java.util.HashMap;

public class ControllerImpl implements Controller {
    private final HashMap<String, GameControllerImpl> gamesControllers;

    private final HashMap<String, LobbyControllerImpl> lobbiesControllers;

    private final HashMap<String, ClientExceptionsNotifier> clientExceptionsNotifiers;

    private final Model model;


    public ControllerImpl(){
        this.gamesControllers = new HashMap<>();
        this.lobbiesControllers = new HashMap<>();
        this.clientExceptionsNotifiers = new HashMap<>();
        this.model = new ModelImpl();
    }

    @Override
    public LobbyController createLobby(String playerName, String lobbyName){
        //creates new model implementation
        LobbyModel lobbyModel;
        try {
            lobbyModel = model.addLobby(lobbyName);
            lobbyModel.addPlayer(playerName);
        } catch (LobbyExistanceException | InvalidParameterException | LobbyFullException e) {
            clientExceptionsNotifiers.get(playerName).notifyException(e);
            return null;
        }
        GameControllerImpl gameController = new GameControllerImpl(lobbyModel, lobbyModel.getGameModel(), lobbyModel.getChatModel(), this);
        LobbyControllerImpl lobbyController = new LobbyControllerImpl(lobbyModel, lobbyModel.getChatModel(), this);
        gamesControllers.put(lobbyName,gameController);
        lobbiesControllers.put(lobbyName,lobbyController);
        clientExceptionsNotifiers.put(playerName, model.getNewClientExceptionNotifier(playerName));
        return lobbyController;
    }

    @Override
    public LobbyController joinLobby(String playerName, String roomName) {
        LobbyModel lobbyModel = getLobbyModel(roomName);
        if (lobbyModel != null) {
            try {
                gamesControllers.get(roomName).getLobbyModel().addPlayer(playerName);
            } catch (LobbyFullException | InvalidParameterException e) {
                clientExceptionsNotifiers.get(playerName).notifyException(e);
            }
        } else {
            clientExceptionsNotifiers.get(playerName).notifyException(new LobbyExistanceException(false, roomName));
        }
        return lobbiesControllers.get(roomName);
    }


    private LobbyModel getLobbyModel(String lobbyName) {
        return gamesControllers.get(lobbyName).getLobbyModel();
    }
    private GameControllerImpl getController(String lobbyName) {
        return gamesControllers.get(lobbyName);
    }

    public void deleteLobby(String roomName){
        gamesControllers.remove(roomName);
    }

    public GameControllerImpl getGameController(String roomName){
        return gamesControllers.get(roomName);
    }
}
