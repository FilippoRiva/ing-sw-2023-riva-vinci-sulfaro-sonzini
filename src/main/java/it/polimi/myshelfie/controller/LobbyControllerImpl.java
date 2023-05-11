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
import it.polimi.myshelfie.model.interfaces.LobbyModel;
import it.polimi.myshelfie.model.modelviews.LobbyModelView;
import it.polimi.myshelfie.model.modelviews.PlayerChatModelView;
import it.polimi.myshelfie.network.Client;
import it.polimi.myshelfie.network.NetworkMessage;
import it.polimi.myshelfie.network.ServerConnectionHandler;
import it.polimi.myshelfie.utils.Observer;
import it.polimi.myshelfie.view.ViewType;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

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
        try {
            lobbyModel.removePlayer(playerName);
            lobbyModel.removeLobbyObserver(lobbyObserverMap.get(playerName));
            chatModel.removeChatObserver(playerName, chatObserverMap.get(playerName));
            new Thread(()->{
                try {
                    clientsMap.get(playerName).send(new NetworkMessage(ViewType.INIT));
                } catch (RemoteException e) {
                    clientsMap.remove(playerName); //this means that the client crashed or lost connection
                }
            }).start();
        } catch (InvalidParameterException e) {
            exceptionsNotifiersMap.get(playerName).notifyException(e);
            return;
        }
        if(lobbyModel.isLobbyEmpty()){
            controllerImpl.deleteLobby(lobbyModel.getLobbyName());
        }
    }

    @Override
    public synchronized void setConfig(String playerName, GameConfig config){
        if(!lobbyModel.isRoomOwner(playerName)){
            exceptionsNotifiersMap.get(playerName).notifyException(new Exception("You're not the room owner"));
            return;
        }
        try {
            lobbyModel.setGameConfig(config);
        } catch (InvalidParameterException e) {
            exceptionsNotifiersMap.get(playerName).notifyException(e);
        }
    }

    @Override
    public synchronized void startGame(String playerName){
        if(!lobbyModel.isRoomOwner(playerName)){
            exceptionsNotifiersMap.get(playerName).notifyException(new Exception("You're not the room owner"));
            return;
        }

        if(lobbyModel.hasStarted()) return;
        try {
            lobbyModel.initialize();
        }catch(LobbyFullException e){
            exceptionsNotifiersMap.get(playerName).notifyException(e);
        }
        GameControllerImpl result = controllerImpl.getGameController(lobbyModel.getLobbyName());
        if(result!=null) { //sets the correct view type for every client and adds observer to the game
            new Thread(()-> {
                try {
                    for(Client c : clientsMap.values()){
                        c.send(new NetworkMessage(ViewType.GAME));
                    }
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }).start();
            result.init(clientsMap, connectionHandlersMap, exceptionsNotifiersMap,chatObserverMap);
        }
        controllerImpl.deleteLobby(lobbyName);
    }

    @Override
    public synchronized void addLobbyObserver(String playerName,Observer<LobbyEvent, LobbyModelView> networkLobbyObserver) {
        lobbyModel.addLobbyObserver(networkLobbyObserver);
        lobbyObserverMap.put(playerName,networkLobbyObserver);
    }

    @Override
    public synchronized void addMessage(Message message){
        chatModel.addMessage(message);
    }

    @Override
    public synchronized void addChatObserver(String playerName, Observer<ChatEvent, PlayerChatModelView> networkChatObserver) {
        chatModel.addChatObserver(playerName,networkChatObserver);
        chatObserverMap.put(playerName,networkChatObserver);
    }

    public LobbyModel getLobbyModel(){
        return lobbyModel;
    }

    public void addPlayer(Client client, ServerConnectionHandler connectionHandler, ClientExceptionsNotifier exceptionsNotifier, String playerName) throws InvalidParameterException, LobbyFullException {
        lobbyModel.addPlayer(playerName);
        clientsMap.put(playerName,client);
        connectionHandlersMap.put(playerName,connectionHandler);
        exceptionsNotifiersMap.put(playerName,exceptionsNotifier);
    }
}
