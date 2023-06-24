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
import it.polimi.myshelfie.utils.ServerLogger;
import it.polimi.myshelfie.view.ViewType;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class ControllerImpl implements Controller {
    private final Map<String, LobbyControllerImpl> lobbiesControllers;
    private final Map<Client, ClientExceptionsNotifier> clientExceptionsNotifiers;
    private final Map<Client, Observer<ModelEvent, ModelView>> clientToModelObserversMap;
    private final Map<Client,Boolean> clientHasJoinedMap;
    private Model model;

    /**
     * Constructor for the ControllerImpl class.
     */
    public ControllerImpl(){
        this.lobbiesControllers = new HashMap<>();
        this.clientExceptionsNotifiers = new HashMap<>();
        this.clientToModelObserversMap = new HashMap<>();
        this.clientHasJoinedMap = new HashMap<>();
    }

    /**
     * Sets the model.
     * @param model is the model to set.
     */
    public void setModel(Model model){
        this.model = model;
    }

    /**
     * Adds a client to this controller implementation.
     * @param client is the client representing the player who's connecting.
     * @param networkModelObserver is the network model observer (??)
     * @param networkExceptionsObserver (??)
     */
    @Override
    public synchronized void addClient(Client client,Observer<ModelEvent, ModelView> networkModelObserver,
                                       Observer<ExceptionEvent, ClientExceptionsNotifierView> networkExceptionsObserver) throws InvalidParameterException{
        if(clientExceptionsNotifiers.containsKey(client)) {
            throw new InvalidParameterException("Client already exists");
        }

        //connects the client to the model
        model.addModelObserver(networkModelObserver);
        clientToModelObserversMap.put(client,networkModelObserver);

        ClientExceptionsNotifier cen = model.getNewClientExceptionNotifier();
        cen.addExceptionsObserver(networkExceptionsObserver);

        clientExceptionsNotifiers.put(client,cen);

        clientHasJoinedMap.put(client,false);

        //going to INIT view
        new Thread(()->{
            try {
                client.send(new NetworkMessage(ViewType.INIT));
            } catch (RemoteException ignored) {
            }
        }).start();
    }

    /**
     * Creates a lobby and connects the creator to it.
     * It checks if the username and the lobby name are valid names.
     * Notifies the Client's ClientExceptionNotifier if the lobby has already been created
     *
     * @param client is the client representing the player who's connecting.
     * @param connectionHandler is the ServerConnectionHandler used for the player's connection.
     * @param playerName is the name of this first player which is now the room owner and created this lobby.
     * @param lobbyName is the name of the created lobby.
     * @return null if the creation fails, the lobbyController of the created lobby otherwise.
     */
    @Override
    public synchronized LobbyController createLobby(Client client,ServerConnectionHandler connectionHandler, String playerName, String lobbyName){
        if(!clientExceptionsNotifiers.containsKey(client)) return null; //checking connection
        if(clientHasJoinedMap.get(client)) return null; //client is already in a game
        //checking player name and lobby name validity
        if(playerName.contains("#")){
            clientExceptionsNotifiers.get(client).notifyException(new InvalidParameterException("# is not a valid character"));
            return null;
        } else if (playerName.contains(" ")){
            clientExceptionsNotifiers.get(client).notifyException(new InvalidParameterException("Empty spaces not allowed"));
            return null;
        } else if (playerName.isEmpty()){
            clientExceptionsNotifiers.get(client).notifyException(new InvalidParameterException("No username"));
            return null;
        } else if(playerName.length()>20){
            clientExceptionsNotifiers.get(client).notifyException(new InvalidParameterException("Usernames must not exceed 20 characters"));
            return null;
        } else if(lobbyName.replace(" ","").isEmpty()) {
            clientExceptionsNotifiers.get(client).notifyException(new InvalidParameterException("No lobby name"));
            return null;
        } else if(lobbyName.length()>20){
            clientExceptionsNotifiers.get(client).notifyException(new InvalidParameterException("Lobby names must no exceed 20 characters"));
            return null;
        }

        // starting lobby model
        LobbyModel lobbyModel;
        try {
            lobbyModel = model.addLobby(lobbyName);
        } catch (LobbyExistenceException e) {
            clientExceptionsNotifiers.get(client).notifyException(e);
            ServerLogger.log(Level.FINEST,"Failed lobby creation. Lobby name not available : "+lobbyName);
            return null;
        }

        //starting lobby controller
        LobbyControllerImpl lobbyController = new LobbyControllerImpl(lobbyName, lobbyModel, lobbyModel.getChatModel(), this);
        try {
            lobbyController.addPlayer(client,connectionHandler,clientExceptionsNotifiers.get(client),playerName);
        } catch (InvalidParameterException | LobbyFullException e) {
            ServerLogger.log(Level.FINEST,"Failed lobby creation. Player name not accepted : "+playerName);
            try {
                model.removeLobby(lobbyName);
            } catch (LobbyExistenceException ex) {
                throw new RuntimeException(ex);
            }
            clientExceptionsNotifiers.get(client).notifyException(e);
            return null;
        }
        lobbiesControllers.put(lobbyName,lobbyController);

        //going to LOBBY view
        new Thread(()->{
            try {
                client.send(new NetworkMessage(ViewType.LOBBY));
            } catch (RemoteException ignored) {
            }
        }).start();

        clientHasJoinedMap.replace(client,true);

        ServerLogger.log(Level.FINE,"Lobby creation. Lobby name : "+lobbyName+" | Room owner player name : "+playerName);
        return lobbyController;
    }

    /**
     * Connects the client to a pre-existing lobby.
     * If the lobby doesn't exist it notifies the Client's ClientExceptionNotifier.
     *
     * @param client is the client representing the player who's connecting.
     * @param connectionHandler is the ServerConnectionHandler used for the player's connection.
     * @param playerName is the name of the player that wants to join this lobby.
     * @param lobbyName is the name of the joined lobby.
     * @return null if connection fails, the lobbyController otherwise
     */
    @Override
    public synchronized LobbyController joinLobby(Client client, ServerConnectionHandler connectionHandler, String playerName, String lobbyName) {
        if(!clientExceptionsNotifiers.containsKey(client)) return null;
        if(clientHasJoinedMap.get(client)) return null; //client is already in a game
        //checking player name validity
        if(playerName.contains("#")){
            clientExceptionsNotifiers.get(client).notifyException(new InvalidParameterException("# is not a valid character"));
            return null;
        } else if (playerName.contains(" ")){
            clientExceptionsNotifiers.get(client).notifyException(new InvalidParameterException("Empty spaces not allowed"));
            return null;
        } else if (playerName.isEmpty()){
            clientExceptionsNotifiers.get(client).notifyException(new InvalidParameterException("No username"));
            return null;
        } else if(playerName.length()>20){
            clientExceptionsNotifiers.get(client).notifyException(new InvalidParameterException("Usernames must not exceed 20 characters"));
            return null;
        }

        //joining lobby using lobby controller if lobby exists
        LobbyControllerImpl lobbyController = lobbiesControllers.get(lobbyName);
        if (lobbyController == null) {
            clientExceptionsNotifiers.get(client).notifyException(new LobbyExistenceException(false, lobbyName));
            ServerLogger.log(Level.FINEST,"Player join failed since the requested lobby doesn't exists. Lobby name : "+lobbyName+" | Player name : "+playerName);
            return null;
        }
        try {
            lobbyController.addPlayer(client,connectionHandler,clientExceptionsNotifiers.get(client),playerName);
        } catch (InvalidParameterException e) {
            ServerLogger.log(Level.FINEST,"Player join failed: Player name not valid. Lobby name : "+lobbyName+" | Requeste player name : "+playerName);
            clientExceptionsNotifiers.get(client).notifyException(e);
            return null;
        } catch (LobbyFullException e){
            ServerLogger.log(Level.FINEST,"Player join failed since the requested lobby is full. Lobby name : "+lobbyName);
            clientExceptionsNotifiers.get(client).notifyException(e);
            return null;
        }

        //going to LOBBY view
        new Thread(()-> {
            try {
                client.send(new NetworkMessage(ViewType.LOBBY));
            } catch (RemoteException ignored) {
            }
        }).start();

        clientHasJoinedMap.replace(client,true);

        ServerLogger.log(Level.FINE,"Player joined lobby. Lobby name : "+lobbyName+" | Player joining : "+playerName);
        return lobbyController;
    }
    @Override
    public synchronized void quit(Client client) {
        if(!clientExceptionsNotifiers.containsKey(client)) { //ignoring disconnected clients
            return;
        }
        model.removeModelObserver(clientToModelObserversMap.get(client));
        if(clientExceptionsNotifiers.get(client)!=null) clientExceptionsNotifiers.get(client).removeExceptionsObservers();
        clientExceptionsNotifiers.remove(client);
        clientHasJoinedMap.remove(client);
    }

    /**
     * Removes a lobby from the lobby list.
     * @param lobbyName is the name of the lobby that must be deleted.
     */
    protected synchronized void deleteLobby(String lobbyName){
        if(!lobbiesControllers.containsKey(lobbyName)) return;
        lobbiesControllers.remove(lobbyName);
        try {
            model.removeLobby(lobbyName);
        } catch (LobbyExistenceException e) {
            throw new RuntimeException(e);
        }
        ServerLogger.log(Level.FINE,"Empty lobby deletion. Lobby name : "+lobbyName);
    }

    public void clientHasLeft(Client client) {
        clientHasJoinedMap.replace(client, false);
    }
}
