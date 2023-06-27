package it.polimi.myshelfie.network;

import it.polimi.myshelfie.controller.interfaces.ChatController;
import it.polimi.myshelfie.controller.interfaces.Controller;
import it.polimi.myshelfie.controller.interfaces.GameController;
import it.polimi.myshelfie.controller.interfaces.LobbyController;
import it.polimi.myshelfie.model.exceptions.InvalidParameterException;
import it.polimi.myshelfie.utils.ServerLogger;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Timer;
import java.util.TimerTask;

public class ServerConnectionHandlerImpl extends UnicastRemoteObject implements ServerConnectionHandler {
    private static int id = 0;
    public final String hostName;
    public final int clientID;
    private final Client client;
    private final Controller controller;
    private final Object lifelineSync = new Object();
    private String playerName;
    private LobbyController lobbyController;
    private GameController gameController;
    private ChatController chatController;
    private Timer timeOutTimer;
    private boolean connected;

    public ServerConnectionHandlerImpl(Client client, Controller controller) throws RemoteException {
        this.client = client;
        this.controller = controller;
        this.timeOutTimer = new Timer();
        this.hostName = client.getHostName();
        this.clientID = id;
        id++;
    }

    public void start() throws RemoteException {
        client.handshake(this);
        ServerLogger.logClientInfo(this, "Connected");
        //RMI disconnection detection
        new Thread(() -> {
            try {
                client.lifeline();
            } catch (RemoteException e) {
                try {
                    if (!connected) return;
                    ServerLogger.logClientInfo(this, "RMI Lifeline failed. Closing connection...");
                    close();
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }).start();
        try {
            controller.addClient(client, ((evt, modelView) -> new Thread(() -> {
                try { //this is called every 10 seconds
                    client.send(new NetworkMessage(evt, modelView));
                } catch (RemoteException ignored) {
                }
            }).start()), ((evt, clientExceptionsNotifierView) -> {
                if (evt == null) return;
                new Thread(() -> {
                    try {
                        client.send(new NetworkMessage(evt, clientExceptionsNotifierView));
                    } catch (RemoteException ignored) {
                    }
                }).start();
            }));
        } catch (InvalidParameterException e) {
            throw new RuntimeException(e); //controller does not support multiple instances of the same client connected
        }

        connected = true;
    }

    @Override
    public synchronized void setGameController(GameController gameController) throws RemoteException {
        if (!connected) throw new RemoteException();
        this.gameController = gameController;
        if (gameController == null) return;
        gameController.addGameObserver(playerName, (evt, gameModelView) -> {
            if (evt == null) return;
            new Thread(() -> {
                try {
                    client.send(new NetworkMessage(evt, gameModelView));
                } catch (RemoteException ignored) {
                }
            }).start();
        });
    }

    @Override
    public synchronized void setChatController(ChatController chatController) throws RemoteException {
        if (!connected) throw new RemoteException();
        this.chatController = chatController;
        if (chatController == null) return;
        chatController.addChatObserver(playerName, ((evt, playerChatModelView) -> {
            if (evt == null) return;
            new Thread(() -> {
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
        synchronized (lifelineSync) {
            try {
                lifelineSync.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public synchronized void close() throws RemoteException {
        if (!connected) return;
        controller.quit(client);
        if (gameController != null) gameController.disconnectFromGame(playerName);
        else if (lobbyController != null) lobbyController.exitLobby(playerName);
        connected = false;
        ServerLogger.logClientInfo(this, "Disconnected");
    }

    @Override
    public void ping() throws RemoteException {
        if (!connected) throw new RemoteException();
        this.timeOutTimer.cancel();
        this.timeOutTimer = new Timer();
        ServerConnectionHandlerImpl connectionHandler = this;
        this.timeOutTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    if (!connected) return;
                    ServerLogger.logClientInfo(connectionHandler, "Timed Out, Closing Connection...");
                    close();
                } catch (RemoteException ignored) {
                }
            }
        }, 10000);
    }

    @Override
    public synchronized void send(NetworkMessage networkMessage) throws RemoteException {
        if (!connected) throw new RemoteException();
        MessageType messageType = networkMessage.getMessageType();
        if (messageType != MessageType.VIEW_MESSAGE && messageType != MessageType.PING) return;
        networkMessage.serverExecute(new ControllerGroup(client, this,
                playerName, controller, lobbyController, gameController, chatController));
    }

    public void setLobbyController(LobbyController lobbyController) {
        this.lobbyController = lobbyController;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
