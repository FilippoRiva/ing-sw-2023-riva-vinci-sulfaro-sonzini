package it.polimi.myshelfie.controller;

import it.polimi.myshelfie.controller.interfaces.Controller;
import it.polimi.myshelfie.controller.interfaces.LobbyController;
import it.polimi.myshelfie.model.exceptions.InvalidParameterException;
import it.polimi.myshelfie.model.exceptions.LobbyExistanceException;
import it.polimi.myshelfie.model.exceptions.LobbyFullException;
import it.polimi.myshelfie.model.interfaces.ClientExceptionsNotifier;
import it.polimi.myshelfie.model.interfaces.Model;
import it.polimi.myshelfie.model.interfaces.LobbyModel;
import it.polimi.myshelfie.network.Client;
import it.polimi.myshelfie.network.observers.NetworkExceptionsObserver;
import it.polimi.myshelfie.network.observers.NetworkModelObserver;
import it.polimi.myshelfie.view.ViewType;

import java.rmi.RemoteException;
import java.util.HashMap;

public class ControllerImpl implements Controller {
    private final HashMap<String, GameControllerImpl> gamesControllers;

    private final HashMap<String, LobbyControllerImpl> lobbiesControllers;

    private final HashMap<Client, ClientExceptionsNotifier> clientExceptionsNotifiers;
    private final HashMap<Client,String> clientStringHashMap;

    private Model model;

    public ControllerImpl(){
        this.gamesControllers = new HashMap<>();
        this.lobbiesControllers = new HashMap<>();
        this.clientExceptionsNotifiers = new HashMap<>();
        this.clientStringHashMap = new HashMap<>();
    }

    public void setModel(Model model){
        this.model = model;
    }

    @Override
    public synchronized LobbyController createLobby(Client client, String playerName, String lobbyName){
        LobbyModel lobbyModel;
        try {
            lobbyModel = model.addLobby(lobbyName);
            lobbyModel.addPlayer(playerName);
        } catch (LobbyExistanceException | InvalidParameterException | LobbyFullException e) {
            clientExceptionsNotifiers.get(client).notifyException(e);
            return null;
        }
        GameControllerImpl gameController = new GameControllerImpl(lobbyModel.getGameModel(), lobbyModel.getChatModel(), this);
        LobbyControllerImpl lobbyController = new LobbyControllerImpl(lobbyModel, lobbyModel.getChatModel(), this);
        gamesControllers.put(lobbyName,gameController);
        lobbiesControllers.put(lobbyName,lobbyController);
        clientStringHashMap.put(client,playerName);
        new Thread(()->{
            try {
                client.setViewType(ViewType.LOBBY);
            } catch (RemoteException ignored) {
            }
        }).start();
        return lobbyController;
    }

    @Override
    public synchronized LobbyController joinLobby(Client client, String playerName, String lobbyName) {
        LobbyModel lobbyModel = getLobbyModel(lobbyName);
        if (lobbyModel != null) {
            try {
                lobbiesControllers.get(lobbyName).getLobbyModel().addPlayer(playerName);
            } catch (LobbyFullException | InvalidParameterException e) {
                clientExceptionsNotifiers.get(client).notifyException(e);
                return null;
            }
        } else {
            clientExceptionsNotifiers.get(client).notifyException(new LobbyExistanceException(false, lobbyName));
            return null;
        }
        new Thread(()-> {
            try {
                client.setViewType(ViewType.LOBBY);
            } catch (RemoteException ignored) {
            }
        }).start();
        return lobbiesControllers.get(lobbyName);
    }

    @Override
    public synchronized void addClient(Client client, NetworkModelObserver networkModelObserver, NetworkExceptionsObserver networkExceptionsObserver){

        networkModelObserver.setModel(model);
        model.addModelObserver(networkModelObserver);

        ClientExceptionsNotifier cen = model.getNewClientExceptionNotifier();
        networkExceptionsObserver.setExceptionsNotifier(cen);
        cen.addExceptionsObserver(networkExceptionsObserver);

        clientExceptionsNotifiers.put(client,cen);
        new Thread(()->{
            try {
                client.setViewType(ViewType.INIT);
            } catch (RemoteException ignored) {
            }
        }).start();
    }

    private LobbyModel getLobbyModel(String lobbyName) {
        LobbyControllerImpl lobbyController = lobbiesControllers.get(lobbyName);
        if(lobbyController==null) return null;
        return lobbyController.getLobbyModel();
    }

    /**
     *
     * @param lobbyName is the name of the lobby that must be deleted
     */
    public void deleteLobby(String lobbyName){
        gamesControllers.remove(lobbyName);
    }

    public GameControllerImpl getGameController(String lobbyName){
        return gamesControllers.get(lobbyName);
    }
}
