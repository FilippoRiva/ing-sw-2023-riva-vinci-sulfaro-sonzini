package it.polimi.myshelfie.controller;

import it.polimi.myshelfie.GameConfig;
import it.polimi.myshelfie.controller.interfaces.ChatController;
import it.polimi.myshelfie.controller.interfaces.LobbyController;
import it.polimi.myshelfie.model.chatting.Message;
import it.polimi.myshelfie.model.events.ChatEvent;
import it.polimi.myshelfie.model.events.LobbyEvent;
import it.polimi.myshelfie.model.exceptions.InvalidParameterException;
import it.polimi.myshelfie.model.exceptions.LobbyFullException;
import it.polimi.myshelfie.model.interfaces.ChatModel;
import it.polimi.myshelfie.model.interfaces.ClientExceptionsNotifier;
import it.polimi.myshelfie.model.interfaces.GameModel;
import it.polimi.myshelfie.model.interfaces.LobbyModel;
import it.polimi.myshelfie.model.modelviews.LobbyModelView;
import it.polimi.myshelfie.model.modelviews.PlayerChatModelView;
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

/**
 * It allows to exit a lobby, add players to it, start the game if the player is the room owner and add observers.
 */
public class LobbyControllerImpl implements LobbyController, ChatController{

    private final String lobbyName;
    private final LobbyModel lobbyModel;
    private final ChatModel chatModel;
    private final ControllerImpl controllerImpl;
    private final Map<String,Client> clientsMap;
    private final Map<String,ServerConnectionHandler> connectionHandlersMap;
    private final Map<String,ClientExceptionsNotifier> exceptionsNotifiersMap;
    private final Map<String,Observer<LobbyEvent, LobbyModelView>> lobbyObserverMap;
    private final Map<String,Observer<ChatEvent, PlayerChatModelView>> chatObserverMap;

    /**
     * Constructor for the LobbyControllerImpl class
     * @param lobbyName the name of the lobby.
     * @param lobbyModel the model associated to the lobby.
     * @param chatModel the model associated to the game chat.
     * @param controllerImpl the controller of the game.
     */
    public LobbyControllerImpl(String lobbyName, LobbyModel lobbyModel, ChatModel chatModel, ControllerImpl controllerImpl){
        this.lobbyName = lobbyName;
        this.lobbyModel = lobbyModel;
        this.chatModel = chatModel;
        this.controllerImpl = controllerImpl;
        this.clientsMap = new HashMap<>();
        this.connectionHandlersMap = new HashMap<>();
        this.exceptionsNotifiersMap = new HashMap<>();
        this.lobbyObserverMap = new HashMap<>();
        this.chatObserverMap = new HashMap<>();
    }

    @Override
    public synchronized void exitLobby(String playerName){
        if(lobbyModel.hasStarted()) {
            exceptionsNotifiersMap.get(playerName).notifyException(new Exception("lobby closed"));
            ServerLogger.log(Level.FINEST,"Exit lobby fail. Lobby has already started. Lobby name : "+lobbyName+" | Player name : "+playerName);
            return;
        }

        //removes from model
        try {
            lobbyModel.removePlayer(playerName);
        } catch (InvalidParameterException e) {
            ServerLogger.log(Level.FINEST,"Exit lobby fail. Requested user is not in this lobby. Lobby name : "+lobbyName+" | Requested player name : "+playerName);
            exceptionsNotifiersMap.get(playerName).notifyException(e);
            return;
        }

        //removes from controller
        lobbyModel.removeLobbyObserver(lobbyObserverMap.get(playerName));
        chatModel.removeChatObserver(playerName, chatObserverMap.get(playerName));
        controllerImpl.clientHasLeft(clientsMap.get(playerName));

        //going to INIT view
        Client client = clientsMap.get(playerName);
        new Thread(()->{
            try {
                client.send(new NetworkMessage(ViewType.INIT));
            } catch (RemoteException ignored) {
            }
        }).start();
        clientsMap.remove(playerName);
        ServerLogger.log(Level.FINE,"Player left lobby. Lobby name : "+lobbyName+" | Player leaving : "+playerName);

        //deleting if empty
        if(lobbyModel.isLobbyEmpty()){
            controllerImpl.deleteLobby(lobbyModel.getLobbyName());
        }
    }

    @Override
    public synchronized void setConfig(String playerName, GameConfig config){
        if(!lobbyModel.isRoomOwner(playerName)){
            exceptionsNotifiersMap.get(playerName).notifyException(new Exception("You're not the room owner"));
            ServerLogger.log(Level.FINEST, "Set Config fail. Not Room Owner. Lobby name : "+lobbyName+" | Player setting config : "+playerName);
            return;
        }
        if(lobbyModel.hasStarted()) {
            exceptionsNotifiersMap.get(playerName).notifyException(new Exception("Lobby closed"));
            ServerLogger.log(Level.FINEST, "Set Config fail. Lobby Closed. Lobby name : "+lobbyName+" | Player setting config : "+playerName);
            return;
        }
        try {
            lobbyModel.setGameConfig(config);
        } catch (InvalidParameterException e) {
            ServerLogger.log(Level.FINEST, "Set Config fail. Config are not valid. Lobby name : "+lobbyName+" | Player setting config : "+playerName);
            exceptionsNotifiersMap.get(playerName).notifyException(e);
        }
        ServerLogger.log(Level.FINEST, "Player set config. Lobby name : "+lobbyName+" | Config : \n" +
                "    Player Num : "+ config.getNumPlayers()+"\n");
    }

    @Override
    public synchronized void startGame(String playerName){
        if(!lobbyModel.isRoomOwner(playerName)){
            exceptionsNotifiersMap.get(playerName).notifyException(new Exception("You're not the room owner"));
            ServerLogger.log(Level.FINEST, "Start game fail. Not Room Owner. Lobby name : "+lobbyName+" | Player starting game : "+playerName);
            return;
        }
        if(lobbyModel.hasStarted()) {
            exceptionsNotifiersMap.get(playerName).notifyException(new Exception("lobby closed"));
            ServerLogger.log(Level.FINEST, "Start game fail. Lobby Closed. Lobby name : "+lobbyName+" | Player starting game : "+playerName);
            return;
        }

        //initializes the model
        try {
            lobbyModel.initialize();
        }catch(LobbyFullException e){
            exceptionsNotifiersMap.get(playerName).notifyException(e);
            ServerLogger.log(Level.FINEST, "Start game fail. Lobby is not full. Lobby name : "+lobbyName+" | Player starting game : "+playerName);
            return;
        }

        //removes lobby controllers
        for(ServerConnectionHandler sch : connectionHandlersMap.values()){
            try {
            sch.setLobbyController(null);
            sch.setChatController(null);
            } catch (RemoteException ignored) {
            }
        }

        //removing observers
        lobbyModel.removeLobbyObservers();
        chatModel.removeChatObservers();
        controllerImpl.deleteLobby(lobbyName);

        //creating game controller and starting the game
        GameModel gameModel = lobbyModel.getGameModel();
        GameControllerImpl gameController = new GameControllerImpl(controllerImpl, gameModel,gameModel.getChatModel(),lobbyName);
        gameController.init(new HashMap<>(clientsMap), new HashMap<>(connectionHandlersMap), new HashMap<>(exceptionsNotifiersMap));

        //setting each client's views to GAME
        for(Client client : clientsMap.values()){
            new Thread(()->{
                try {
                    client.send(new NetworkMessage(ViewType.GAME));
                } catch (RemoteException ignored){
                }
            }).start();
        }
        ServerLogger.log(Level.FINE,"Game start. Lobby name : "+lobbyName+" | Players : "+clientsMap.keySet());
    }

    @Override
    public synchronized void addLobbyObserver(String playerName,Observer<LobbyEvent, LobbyModelView> networkLobbyObserver) {
        if(lobbyModel.hasStarted()) {
            exceptionsNotifiersMap.get(playerName).notifyException(new Exception("lobby closed"));
        }
        lobbyModel.addLobbyObserver(networkLobbyObserver);
        lobbyObserverMap.put(playerName,networkLobbyObserver);
    }

    @Override
    public synchronized void addMessage(String playerName, Message message){
        if(lobbyModel.hasStarted()) {
            exceptionsNotifiersMap.get(playerName).notifyException(new Exception("lobby closed"));
            ServerLogger.log(Level.FINEST, "Add message fail. Lobby Closed. Lobby name : "+lobbyName+" | Player chatting : "+playerName);
        }
        if(message.getMessage().isEmpty()) return;
        chatModel.addMessage(message);
        ServerLogger.log(Level.FINEST,"Player sent chat message. Lobby name : "+lobbyName+" | Player chatting : " +playerName +
                " | Message : "+message.getSender()+"->"+message.getReceiver()+" :: "+message.getMessage());
    }

    @Override
    public synchronized void addChatObserver(String playerName, Observer<ChatEvent, PlayerChatModelView> networkChatObserver) {
        if(lobbyModel.hasStarted()) {
            exceptionsNotifiersMap.get(playerName).notifyException(new Exception("lobby closed"));
        }
        chatModel.addChatObserver(playerName,networkChatObserver);
        chatObserverMap.put(playerName,networkChatObserver);
    }


    /**
     * Adds a player to the game.
     * @param client represents the player.
     * @param connectionHandler the ServerConnectionHandler used for the player's connection.
     * @param exceptionsNotifier the client exception notifier of this client.
     * @param playerName the name of the player that must be added to the lobby.
     * @throws InvalidParameterException if the playerName is not available.
     * @throws LobbyFullException if the lobby is full.
     */
    protected void addPlayer(Client client, ServerConnectionHandler connectionHandler, ClientExceptionsNotifier exceptionsNotifier, String playerName) throws InvalidParameterException, LobbyFullException {
        lobbyModel.addPlayer(playerName);
        clientsMap.put(playerName,client);
        connectionHandlersMap.put(playerName,connectionHandler);
        exceptionsNotifiersMap.put(playerName,exceptionsNotifier);
        try{
            connectionHandler.setChatController(this);
        } catch(RemoteException e){
            throw new RuntimeException(e);
        }
    }
}
