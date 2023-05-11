package it.polimi.myshelfie.controller;

import it.polimi.myshelfie.controller.interfaces.Controller;
import it.polimi.myshelfie.controller.interfaces.LobbyController;
import it.polimi.myshelfie.model.events.ExceptionEvent;
import it.polimi.myshelfie.model.events.ModelEvent;
import it.polimi.myshelfie.model.exceptions.InvalidParameterException;
import it.polimi.myshelfie.model.exceptions.LobbyExistenceException;
import it.polimi.myshelfie.model.exceptions.LobbyFullException;
import it.polimi.myshelfie.model.interfaces.ClientExceptionsNotifier;
import it.polimi.myshelfie.model.interfaces.Model;
import it.polimi.myshelfie.model.interfaces.LobbyModel;
import it.polimi.myshelfie.model.modelviews.ClientExceptionsNotifierView;
import it.polimi.myshelfie.model.modelviews.ModelView;
import it.polimi.myshelfie.network.Client;
import it.polimi.myshelfie.network.NetworkMessage;
import it.polimi.myshelfie.network.ServerConnectionHandler;
import it.polimi.myshelfie.utils.Observer;
import it.polimi.myshelfie.view.ViewType;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

public class ControllerImpl implements Controller {
    private final Map<String, GameControllerImpl> gamesControllers;

    private final Map<String, LobbyControllerImpl> lobbiesControllers;

    private final Map<Client, ClientExceptionsNotifier> clientExceptionsNotifiers;

    private Model model;

    public ControllerImpl(){
        this.gamesControllers = new HashMap<>();
        this.lobbiesControllers = new HashMap<>();
        this.clientExceptionsNotifiers = new HashMap<>();
    }

    public void setModel(Model model){
        this.model = model;
    }

    @Override
    public synchronized LobbyController createLobby(Client client,ServerConnectionHandler connectionHandler, String playerName, String lobbyName){
        LobbyModel lobbyModel;
        try {
            lobbyModel = model.addLobby(lobbyName);
        } catch (LobbyExistenceException e) {
            clientExceptionsNotifiers.get(client).notifyException(e);
            return null;
        }
        GameControllerImpl gameController = new GameControllerImpl(lobbyModel.getGameModel(), lobbyModel.getChatModel(), this);
        LobbyControllerImpl lobbyController = new LobbyControllerImpl(lobbyModel, lobbyModel.getChatModel(), this);
        try {
            lobbyController.addPlayer(client,connectionHandler,clientExceptionsNotifiers.get(client),playerName);
        } catch (InvalidParameterException | LobbyFullException e) {
            clientExceptionsNotifiers.get(client).notifyException(e);
            try {
                client.send(new NetworkMessage(ViewType.INIT));
            } catch (RemoteException ignored) {
            }
            return null;
        }
        gamesControllers.put(lobbyName,gameController);
        lobbiesControllers.put(lobbyName,lobbyController);
        new Thread(()->{
            try {
                client.send(new NetworkMessage(ViewType.LOBBY));
                connectionHandler.setChatController(lobbyController);
            } catch (RemoteException ignored) {
            }
        }).start();
        return lobbyController;
    }

    @Override
    public synchronized LobbyController joinLobby(Client client, ServerConnectionHandler connectionHandler, String playerName, String lobbyName) {
        LobbyModel lobbyModel = getLobbyModel(lobbyName);
        LobbyControllerImpl lobbyController = lobbiesControllers.get(lobbyName);
        if (lobbyModel != null) {
            try {
                lobbiesControllers.get(lobbyName).addPlayer(client,connectionHandler,clientExceptionsNotifiers.get(client),playerName);
            } catch (InvalidParameterException | LobbyFullException e) {
                clientExceptionsNotifiers.get(client).notifyException(e);
                try {
                    client.send(new NetworkMessage(ViewType.INIT));
                } catch (RemoteException ignored) {
                }
                return null;
            }
        } else {
            clientExceptionsNotifiers.get(client).notifyException(new LobbyExistenceException(false, lobbyName));
            return null;
        }
        new Thread(()-> {
            try {
                client.send(new NetworkMessage(ViewType.LOBBY));
                connectionHandler.setChatController(lobbyController);
            } catch (RemoteException ignored) {
            }
        }).start();
        return lobbiesControllers.get(lobbyName);
    }

    @Override
    public synchronized void addClient(Client client,Observer<ModelEvent, ModelView> networkModelObserver,
                                       Observer<ExceptionEvent, ClientExceptionsNotifierView> networkExceptionsObserver){

        model.addModelObserver(networkModelObserver);

        ClientExceptionsNotifier cen = model.getNewClientExceptionNotifier();
        cen.addExceptionsObserver(networkExceptionsObserver);

        clientExceptionsNotifiers.put(client,cen);
        new Thread(()->{
            try {
                client.send(new NetworkMessage(ViewType.INIT));
            } catch (RemoteException ignored) {

            }
        }).start();
    }

    @Override
    public void quit(Client client) {
        clientExceptionsNotifiers.remove(client);
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
        try {
            model.removeLobby(lobbyName);
        } catch (LobbyExistenceException e) {
            throw new RuntimeException(e);
        }
    }

    public GameControllerImpl getGameController(String lobbyName){
        return gamesControllers.get(lobbyName);
    }
}
