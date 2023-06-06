package it.polimi.myshelfie.network;

import it.polimi.myshelfie.controller.GameControllerImpl;
import it.polimi.myshelfie.controller.interfaces.ChatController;
import it.polimi.myshelfie.controller.interfaces.Controller;
import it.polimi.myshelfie.controller.interfaces.GameController;
import it.polimi.myshelfie.controller.interfaces.LobbyController;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerConnectionHandlerImpl extends UnicastRemoteObject implements ServerConnectionHandler {
    private final Client client;
    private String playerName;
    private final Controller controller;
    private LobbyController lobbyController;
    private GameController gameController;
    private ChatController chatController;
    public ServerConnectionHandlerImpl(Client client, Controller controller) throws RemoteException {
        this.client = client;
        this.controller = controller;
    }
    public void start() throws RemoteException {
        client.handshake(this);
        System.out.println("Client connected");
        //RMI disconnection detection
        new Thread(()->{
            try {
                client.lifeline();
            } catch (RemoteException e) {
                try {
                    System.err.println("Cannot receive from client. Closing connection...");
                    close();
                    System.out.println("Client Disconnected");
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }).start();
        controller.addClient(client, ((evt, modelView)-> new Thread(()->{
            try {
                client.send(new NetworkMessage(evt,modelView));
            } catch (RemoteException ignored) {
            }
        }).start()), ((evt, clientExceptionsNotifierView)->{
            if(evt==null) return;
            new Thread(()->{
                try {
                    client.send(new NetworkMessage(evt,clientExceptionsNotifierView));
                } catch (RemoteException ignored) {
                }
            }).start();
        }));
    }

    @Override
    public synchronized void setGameController(GameControllerImpl gameController) throws RemoteException{
        this.gameController = gameController;
        gameController.addGameObserver(playerName, (evt, gameModelView)->{
            if(evt==null) return;
            new Thread(()->{
                try {
                    client.send(new NetworkMessage(evt,gameModelView));
                } catch (RemoteException ignored) {
                }
            }).start();
        });
    }

    @Override
    public synchronized void setChatController(ChatController chatController) throws RemoteException{
        this.chatController = chatController;
        chatController.addChatObserver(playerName, ((evt,playerChatModelView)->{
            if(evt==null) return;
            new Thread(()->{
                try {
                    client.send(new NetworkMessage(evt, playerChatModelView));
                } catch (RemoteException ignored) {
                }
            }).start();
        }));
    }

    @Override
    public void lifeline() throws RemoteException {
        //this method blocks forever
        Object obj = new Object();
        synchronized (obj){
            try {
                obj.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public synchronized void close() throws RemoteException {
        controller.quit(client);
        if(gameController!=null) gameController.exitGame(playerName);
        else if(lobbyController!=null) lobbyController.exitLobby(playerName);
    }

    @Override
    public synchronized void send(NetworkMessage networkMessage) throws RemoteException {
        MessageType messageType = networkMessage.getMessageType();
        if(messageType!=MessageType.VIEW_MESSAGE) return;
        networkMessage.serverExecute(new ControllerGroup(client,this,
                playerName,controller,lobbyController,gameController,chatController));
    }

    public void setLobbyController(LobbyController lobbyController) {
        this.lobbyController = lobbyController;
    }

    public void setPlayerName(String playerName){
        this.playerName = playerName;
    }
}
