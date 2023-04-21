package it.polimi.myshelfie.network;

import it.polimi.myshelfie.GameConfig;
import it.polimi.myshelfie.controller.GameControllerImpl;
import it.polimi.myshelfie.controller.interfaces.ChatController;
import it.polimi.myshelfie.controller.interfaces.Controller;
import it.polimi.myshelfie.controller.interfaces.GameController;
import it.polimi.myshelfie.controller.interfaces.LobbyController;
import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.Position;
import it.polimi.myshelfie.model.chatting.Message;
import it.polimi.myshelfie.network.events.*;
import it.polimi.myshelfie.network.observers.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ServerConnectionHandlerRMI extends UnicastRemoteObject implements ServerConnectionHandler {
    private final Client client;
    private String playerName;
    private final Controller controller;
    private LobbyController lobbyController;
    private GameController gameController;
    private ChatController chatController;
    public ServerConnectionHandlerRMI(Client client, Controller controller) throws RemoteException {
        this.client = client;
        this.controller = controller;
    }
    public void start() throws RemoteException {
        client.handshake(this);
        controller.addClient(client,new NetworkModelObserver(client),new NetworkExceptionsObserver(client));
    }

    @Override
    public synchronized void setGameController(GameControllerImpl gameController) throws RemoteException{
        this.gameController = gameController;
        gameController.addGameObserver(new NetworkGameObserver(client));
    }

    @Override
    public synchronized void setChatController(ChatController chatController) throws RemoteException{
        this.chatController = chatController;
        chatController.addChatObserver(new NetworkChatObserver(client,playerName));
    }

    @Override
    public void send(NetworkMessage networkMessage) throws RemoteException {
        MessageType messageType = networkMessage.getMessageType();
        if(messageType!=MessageType.VIEWMESSAGE) return;
        ViewEvent viewEvent = (ViewEvent) networkMessage.getEvent();
        Object[] args = networkMessage.getParameters();
        new Thread(()->{
            switch (viewEvent){
                case CREATE_REQUEST -> {
                    createRequest(args);
                }
                case JOIN_REQUEST -> {
                    joinRequest(args);
                }
                case CONFIG_REQUEST -> {
                    if(lobbyController==null) return;
                    lobbyController.setConfig(playerName,(GameConfig) args[0]);
                }
                case START_REQUEST -> {
                    if(lobbyController==null) return;
                    GameController gc = lobbyController.startGame(playerName);
                    if(gc!=null) gameController = gc;
                }
                case DRAW_REQUEST -> {
                    gameController.drawTiles((List<Position>) args[0], playerName);
                }
                case ORGANIZE_REQUEST -> {
                    if (args == null || args.length==0) gameController.orderChosenTiles(null, playerName);
                    else gameController.orderChosenTiles((List<ItemTile>) args[0], playerName);
                }
                case INSERT_REQUEST -> {
                    gameController.insertTiles(playerName, (int) args[0]);
                }
                case MESSAGE_SEND -> {
                    chatController.addMessage((Message) args[0]);
                }
            }
        }).start();
    }

    private synchronized void createRequest(Object[] args) {
        String playerName = (String) args[0];
        String roomName = (String) args[1];
        lobbyController = controller.createLobby(client,this,playerName,roomName);
        if(lobbyController != null)  {
            lobbyController.addLobbyObserver(playerName,new NetworkLobbyObserver(client));
            this.playerName = playerName;
        }
    }

    private synchronized void joinRequest(Object[] args) {
        String playerName = (String) args[0];
        String roomName = (String) args[1];
        lobbyController = controller.joinLobby(client,this,playerName,roomName);
        if(lobbyController != null)  {
            lobbyController.addLobbyObserver(playerName,new NetworkLobbyObserver(client));
            this.playerName = playerName;
        }
    }
}
