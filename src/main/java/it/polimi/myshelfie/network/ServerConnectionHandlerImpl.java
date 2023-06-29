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

/**
 * Represents the Server Connection Handler Implementation.
 */
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

    /**
     * Constructor.
     *
     * @param client     the client.
     * @param controller the controller.
     * @throws RemoteException if a communication-related exceptions has occurred.
     */
    public ServerConnectionHandlerImpl(Client client, Controller controller) throws RemoteException {
        this.client = client;
        this.controller = controller;
        this.timeOutTimer = new Timer();
        this.hostName = client.getHostName();
        this.clientID = id;
        id++;
    }

    /**
     * Creates the RMI connection between client and server.
     * @throws RemoteException if a communication-related exceptions has occurred.
     */
    public void start() throws RemoteException {
        client.handshake(this);
        ServerLogger.logClientInfo(this, "Connected");
        //RMI disconnection detection
        new Thread(() -> {
            try {
                client.lifeline();
            } catch (RemoteException e) {
                if (!connected) return;
                ServerLogger.logClientInfo(this, "RMI Lifeline failed. Closing connection...");
                close();
            }
        }).start();
        try {
            controller.addClient(client, ((evt, modelView) -> new Thread(() -> {
                try { //this is called every 10 seconds
                    client.send(new NetworkMessage(evt, modelView));
                } catch (RemoteException e) {
                    ServerLogger.logClientInfo(this, "Model Observer failed to send a Network Message");
                }
            }).start()), ((evt, clientExceptionsNotifierView) -> {
                if (evt == null) return;
                new Thread(() -> {
                    try {
                        client.send(new NetworkMessage(evt, clientExceptionsNotifierView));
                    } catch (RemoteException e) {
                        ServerLogger.logClientInfo(this, "Model Observer failed to send a Network Message");
                    }
                }).start();
            }));
        } catch (InvalidParameterException e) {
            throw new RuntimeException(e); //controller does not support multiple instances of the same client connected
        }

        connected = true;
    }

    /**
     * {@inheritDoc}
     * @param gameController the controller to set.
     * @throws RemoteException if a communication-related exceptions has occurred.
     */
    @Override
    public synchronized void setGameController(GameController gameController) throws RemoteException {
        if (!connected) throw new RemoteException();
        this.gameController = gameController;
        if (gameController == null) return;
        gameController.addGameObserver(playerName, (evt, gameModelView) -> {
            if (evt == null) return;
            new Thread(() -> {
                try {
                    client.send(new NetworkMessage(evt,gameModelView));
                } catch (RemoteException e) {
                    ServerLogger.logClientInfo(this, "Game Observer failed to send a Network Message");
                }
            }).start();
        });
    }

    /**
     * {@inheritDoc}
     * @param chatController the controller to set.
     * @throws RemoteException if a communication-related exceptions has occurred.
     */
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
                } catch (RemoteException e) {
                    ServerLogger.logClientInfo(this, "Chat Controller failed to send a Network Message");
                }
            }).start();
        }));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void lifeline() {
        //this method blocks forever
        synchronized (lifelineSync) {
            try {
                lifelineSync.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void close() {
        if (!connected) return;
        controller.quit(client);
        if (gameController != null) gameController.disconnectFromGame(playerName);
        else if (lobbyController != null) lobbyController.exitLobby(playerName);
        connected = false;
        ServerLogger.logClientInfo(this, "Disconnected");
    }

    /**
     * {@inheritDoc}
     * @throws RemoteException if a communication-related exceptions has occurred.
     */
    @Override
    public void ping() throws RemoteException {
        if (!connected) throw new RemoteException();
        this.timeOutTimer.cancel();
        this.timeOutTimer = new Timer();
        ServerConnectionHandlerImpl connectionHandler = this;
        this.timeOutTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!connected) return;
                ServerLogger.logClientInfo(connectionHandler, "Timed Out, Closing Connection...");
                close();
            }
        }, 10000);
    }

    /**
     * {@inheritDoc}
     * @throws RemoteException if a communication-related exceptions has occurred.
     */
    @Override
    public GameController getGameController() {
        return gameController;
    }

    /**
     * {@inheritDoc}
     * @param networkMessage the message to send.
     * @throws RemoteException if a communication-related exceptions has occurred.
     */
    @Override
    public synchronized void send(NetworkMessage networkMessage) throws RemoteException {
        if (!connected) throw new RemoteException();
        MessageType messageType = networkMessage.getMessageType();
        if (messageType != MessageType.VIEW_MESSAGE && messageType != MessageType.PING) return;
        networkMessage.serverExecute(new ControllerGroup(client, this,
                playerName, controller, lobbyController, gameController, chatController));
    }

    /**
     * {@inheritDoc}
     * @param lobbyController the controller to set.
     */
    @Override
    public void setLobbyController(LobbyController lobbyController) {
        this.lobbyController = lobbyController;
    }

    /**
     * Associates the player name.
     *
     * @param playerName the name to set.
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
