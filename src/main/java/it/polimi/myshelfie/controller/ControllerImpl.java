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

    /**
     * Constructor for the ControllerImpl class.
     */
    public ControllerImpl(){
        this.gamesControllers = new HashMap<>();
        this.lobbiesControllers = new HashMap<>();
        this.clientExceptionsNotifiers = new HashMap<>();
    }

    /**
     * Sets the model.
     * @param model is the model to set.
     */
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
        GameControllerImpl gameController = new GameControllerImpl(lobbyName, lobbyModel.getGameModel(), lobbyModel.getChatModel(), this);
        LobbyControllerImpl lobbyController = new LobbyControllerImpl(lobbyName, lobbyModel, lobbyModel.getChatModel(), this);
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
        try {
            connectionHandler.setChatController(lobbyController);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        new Thread(()->{
            try {
                client.send(new NetworkMessage(ViewType.LOBBY));
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
        try{
            connectionHandler.setChatController(lobbyController);
        } catch(RemoteException e){
            throw new RuntimeException(e);
        }
        new Thread(()-> {
            try {
                client.send(new NetworkMessage(ViewType.LOBBY));
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
    public synchronized void quit(Client client) {
        clientExceptionsNotifiers.remove(client);
    }

    /**
     * Returns the lobby model associated to that specific lobby.
     * @param lobbyName the lobby whose model I want.
     * @return the associated lobby model.
     */
    private LobbyModel getLobbyModel(String lobbyName) {
        LobbyControllerImpl lobbyController = lobbiesControllers.get(lobbyName);
        if(lobbyController==null) return null;
        return lobbyController.getLobbyModel();
    }

    /**
     * Removes a lobby from the lobby list.
     * @param lobbyName is the name of the lobby that must be deleted.
     */
    protected void deleteLobby(String lobbyName){
        if(!lobbiesControllers.containsKey(lobbyName)) return;
        lobbiesControllers.remove(lobbyName);
        try {
            model.removeLobby(lobbyName);
        } catch (LobbyExistenceException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the game controller associated to that specific game.
     * @param lobbyName is the lobby/game whose controller I want.
     * @return the associated game controller.
     */
    protected GameControllerImpl getGameController(String lobbyName){
        return gamesControllers.get(lobbyName);
    }

    /**
     * Removes a game from the game list.
     * @param gameName is the name of the game that must be deleted.
     */
    protected void deleteGame(String gameName) {
        gamesControllers.remove(gameName);
    }
}
