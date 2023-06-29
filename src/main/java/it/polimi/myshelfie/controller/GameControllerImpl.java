package it.polimi.myshelfie.controller;

import it.polimi.myshelfie.controller.interfaces.ChatController;
import it.polimi.myshelfie.controller.interfaces.GameController;
import it.polimi.myshelfie.model.GameState;
import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.Position;
import it.polimi.myshelfie.model.chatting.Message;
import it.polimi.myshelfie.model.events.ChatEvent;
import it.polimi.myshelfie.model.events.GameEvent;
import it.polimi.myshelfie.model.exceptions.DisconnectionException;
import it.polimi.myshelfie.model.exceptions.FullColumnException;
import it.polimi.myshelfie.model.exceptions.InvalidParameterException;
import it.polimi.myshelfie.model.interfaces.ChatModel;
import it.polimi.myshelfie.model.interfaces.ClientExceptionsNotifier;
import it.polimi.myshelfie.model.interfaces.GameModel;
import it.polimi.myshelfie.model.modelviews.GameModelView;
import it.polimi.myshelfie.model.modelviews.PlayerChatModelView;
import it.polimi.myshelfie.network.Client;
import it.polimi.myshelfie.network.NetworkMessage;
import it.polimi.myshelfie.network.ServerConnectionHandler;
import it.polimi.myshelfie.utils.Observer;
import it.polimi.myshelfie.utils.ServerLogger;
import it.polimi.myshelfie.view.ViewType;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * It allows to do the basic moves of the game, disconnect from a game, add the observers and is responsible for signaling the end turn and end game.
 */
public class GameControllerImpl implements GameController, ChatController {

    private final GameModel gameModel;
    private final ChatModel chatModel;
    private final ControllerImpl controller;
    private final Map<String, Observer<GameEvent, GameModelView>> gameObserverMap;
    private final String gameName;
    private Map<String, Client> clientsMap;
    private Map<String, ClientExceptionsNotifier> exceptionsNotifiersMap;
    private Map<String, ServerConnectionHandler> connectionHandlersMap;
    private Map<String, Observer<ChatEvent, PlayerChatModelView>> chatObserverMap;
    private boolean closed;

    /**
     * Constructor for the GameControllerImpl class.
     *
     * @param gameModel the model associated to the game.
     * @param chatModel the model associated to the game chat.
     * @param gameName  the name of the game.
     */
    public GameControllerImpl(ControllerImpl controller, GameModel gameModel, ChatModel chatModel, String gameName) {
        this.controller = controller;
        this.gameModel = gameModel;
        this.chatModel = chatModel;
        this.gameName = gameName;
        this.clientsMap = new HashMap<>();
        this.exceptionsNotifiersMap = new HashMap<>();
        this.connectionHandlersMap = new HashMap<>();
        this.gameObserverMap = new HashMap<>();
        this.chatObserverMap = new HashMap<>();
    }

    /**
     * Initializes the attributes of the GameControllerImpl class.
     *
     * @param clientsMap            the list of clients and their players.
     * @param connectionHandlersMap the list of connection handlers.
     * @param notifiersMap          the list of the exceptions notifiers.
     */
    protected void init(Map<String, Client> clientsMap, Map<String, ServerConnectionHandler> connectionHandlersMap,
                        Map<String, ClientExceptionsNotifier> notifiersMap) {
        this.clientsMap = clientsMap;
        this.exceptionsNotifiersMap = notifiersMap;
        this.connectionHandlersMap = connectionHandlersMap;
        this.chatObserverMap = new HashMap<>();
        for (String player : connectionHandlersMap.keySet()) {
            ServerConnectionHandler sch = connectionHandlersMap.get(player);
            try {
                sch.setGameController(this);
                sch.setChatController(this);
            } catch (RemoteException e) { //disconnection during init
                closed = false;
                gameModel.setState(GameState.FIRST_DRAW);
                disconnectFromGame(player);
            }
        }
        closed = false;
        gameModel.setState(GameState.FIRST_DRAW);
    }

    /**
     * {@inheritDoc}
     *
     * @param positions  the positions of the tiles the player wants to select from the board.
     * @param playerName the player playing.
     */
    @Override
    public synchronized void drawTiles(List<Position> positions, String playerName) {
        if (closed) {
            ServerLogger.log(Level.FINEST, "Player draw fail. Game Closed. Game name : " + gameName + " | Player drawing : " + playerName);
            exceptionsNotifiersMap.get(playerName).notifyException(new Exception("Game Closed"));
            return;
        }
        GameState currentGameState = gameModel.getState();
        if (currentGameState != GameState.DRAW && currentGameState != GameState.LAST_DRAW) return;
        try {
            gameModel.drawTiles(positions, playerName);
            ServerLogger.log(Level.FINEST, "Player draws. Game name : " + gameName + " | Player drawing : " + playerName + " | Drawn positions : " + positions);
            if (gameModel.getState() == GameState.DRAW) gameModel.setState(GameState.ORGANIZE);
            else gameModel.setState(GameState.LAST_ORGANIZE);
        } catch (InvalidParameterException e) {
            ServerLogger.log(Level.FINEST, "Player draw fail. Game name : " + gameName + " | Player drawing : " + playerName + " | Drawn positions : " + positions);
            exceptionsNotifiersMap.get(playerName).notifyException(e);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param orderedTiles the tiles selected from the board.
     * @param playerName   the player playing.
     */
    @Override
    public synchronized void orderChosenTiles(List<ItemTile> orderedTiles, String playerName) {
        if (closed) {
            ServerLogger.log(Level.FINEST, "Player organize fail. Game Closed. Game name : " + gameName + " | Player drawing : " + playerName);
            exceptionsNotifiersMap.get(playerName).notifyException(new Exception("Game Closed"));
            return;
        }
        GameState currentGameState = gameModel.getState();
        if (currentGameState != GameState.ORGANIZE && currentGameState != GameState.LAST_ORGANIZE) return;
        try {
            gameModel.orderChosenTiles(orderedTiles, playerName);
            if (currentGameState == GameState.ORGANIZE) gameModel.setState(GameState.INSERT);
            else gameModel.setState(GameState.LAST_INSERT);
            ServerLogger.log(Level.FINEST, "Player organizes. Game name : " + gameName + " | Player organizing : " + playerName + " | Organized tiles : " + orderedTiles);
        } catch (InvalidParameterException e) {
            ServerLogger.log(Level.FINEST, "Player organize fail. Game name : " + gameName + " | Player organizing : " + playerName + " | Organized tiles : " + orderedTiles);
            exceptionsNotifiersMap.get(playerName).notifyException(e);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param playerName the player playing.
     * @param column     the column where to insert the chosen tiles.
     */
    @Override
    public synchronized void insertTiles(String playerName, int column) {
        if (closed) {
            ServerLogger.log(Level.FINEST, "Player insert fail. Game Closed. Game name : " + gameName + " | Player drawing : " + playerName);
            exceptionsNotifiersMap.get(playerName).notifyException(new Exception("Game Closed"));
            return;
        }
        GameState currentGameState = gameModel.getState();
        if (currentGameState != GameState.INSERT && currentGameState != GameState.LAST_INSERT) return;
        try {
            gameModel.insertTiles(playerName, column);
            if (currentGameState == GameState.INSERT) gameModel.setState(GameState.COUNTING_POINTS);
            else gameModel.setState(GameState.LAST_COUNTING_POINTS);
            ServerLogger.log(Level.FINEST, "Player inserts. Game name : " + gameName + " | Player organizing : " + playerName + " | Chosen column : " + column);
        } catch (InvalidParameterException | FullColumnException e) {
            ServerLogger.log(Level.FINEST, "Player insert fail. Game name : " + gameName + " | Player organizing : " + playerName + " | Chosen column : " + column);
            exceptionsNotifiersMap.get(playerName).notifyException(e);
            return;
        }
        endTurn(playerName);
    }

    /**
     * Processes the end turn: it checks if the player achieved a common goal; checks if the living room is refillable;
     * sets the next turn.
     *
     * @param playerName the current player that has finished its turn.
     */
    private void endTurn(String playerName) {
        gameModel.checkCommonGoal(playerName);
        gameModel.nextTurn();
        gameModel.refillLivingRoom();

        //we are last round yet
        if (gameModel.getState() == GameState.LAST_COUNTING_POINTS) {
            if (gameModel.isLastPlayer(playerName)) {
                endGame();
                return;
            }
            gameModel.setState(GameState.LAST_DRAW);
        } else {
            if (gameModel.isBookshelfFull(playerName)) {
                gameModel.addEndToken(playerName);
                if (gameModel.isLastPlayer(playerName)) {
                    endGame();
                    return;
                }
                gameModel.setState(GameState.LAST_DRAW);
            } else {
                gameModel.setState(GameState.DRAW);
            }
        }
    }

    /**
     * Calculates the end game results such as end game tokens or personal goals points. Sets the game state to WIN.
     */
    private void endGame() {
        gameModel.calculateWinResults();
        gameModel.setState(GameState.WIN);
        for (Client c : clientsMap.values()) {
            try {
                c.send(new NetworkMessage(ViewType.WIN));
            } catch (RemoteException e) {
                ServerLogger.log(Level.FINE, "Sending a WIN Network Message to a Disconnected Client");
            }
        }
        closed = true;
        ServerLogger.log(Level.FINE, "Game ended. Game name : " + gameName);
    }

    /**
     * {@inheritDoc}
     *
     * @param playerName          the name of the player.
     * @param networkGameObserver the observer to add.
     */
    @Override
    public synchronized void addGameObserver(String playerName, Observer<GameEvent, GameModelView> networkGameObserver) {
        gameModel.addGameObserver(networkGameObserver);
        gameObserverMap.put(playerName, networkGameObserver);
    }

    /**
     * {@inheritDoc}
     *
     * @param disconnectedPlayerName the name of the player quitting.
     */
    @Override
    public synchronized void disconnectFromGame(String disconnectedPlayerName) {
        if (gameModel.getState() == GameState.WIN) {
            leaveEndGame(disconnectedPlayerName, true);
            return;
        }
        clientsMap.remove(disconnectedPlayerName);
        connectionHandlersMap.remove(disconnectedPlayerName);
        //notifying non disconnected clients
        for (String player : clientsMap.keySet()) {
            new Thread(() -> {
                try {
                    exceptionsNotifiersMap.get(player).notifyException(new DisconnectionException(disconnectedPlayerName));
                    connectionHandlersMap.get(player).setGameController(null);
                    connectionHandlersMap.get(player).setChatController(null);
                    clientsMap.get(player).send(new NetworkMessage(ViewType.DISCONNECTION));
                } catch (RemoteException e) {
                    ServerLogger.log(Level.FINE, "Sending a DISCONNECTION Network Message to a Disconnected Client");
                }
                controller.clientHasLeft(clientsMap.get(player));
            }).start();
        }
        //removing observers
        gameModel.removeGameObservers();
        chatModel.removeChatObservers();
        closed = true;
        ServerLogger.log(Level.FINE, "Game shutdown after disconnection. Game name : " + gameName + " | Player disconnected : " + disconnectedPlayerName);
    }

    /**
     * {@inheritDoc}
     *
     * @param username          the name of the player leaving.
     * @param fromDisconnection indicates the disconnection.
     */
    @Override
    public synchronized void leaveEndGame(String username, boolean fromDisconnection) {
        if (gameModel.getState() != GameState.WIN) {
            ServerLogger.log(Level.FINEST, "Player leave end game fail. Not in end game. Game name : " + gameName + " | Player name : " + username);
            exceptionsNotifiersMap.get(username).notifyException(new Exception("Not in end game"));
            return;
        }
        if (!fromDisconnection) {
            try {
                connectionHandlersMap.get(username).setGameController(null);
                connectionHandlersMap.get(username).setChatController(null);
            } catch (RemoteException e) {
                ServerLogger.log(Level.FINE, "Setting Controllers to a Disconnected Client");
            }
            Client client = clientsMap.get(username);
            new Thread(() -> {
                try {
                    client.send(new NetworkMessage(ViewType.INIT));
                } catch (RemoteException e) {
                    ServerLogger.log(Level.FINE, "Sending a INIT Network Message to a disconnected client while leaving game");
                }
            }).start();
        }
        gameModel.removeGameObserver(gameObserverMap.get(username));
        chatModel.removeChatObserver(username, chatObserverMap.get(username));
        gameObserverMap.remove(gameObserverMap.get(username));
        chatObserverMap.remove(chatObserverMap.get(username));
        controller.clientHasLeft(clientsMap.get(username));
        clientsMap.remove(username);
        if (!fromDisconnection)
            ServerLogger.log(Level.FINE, "Player left end game. Game name : " + gameName + " | Player disconnected : " + username);
        else
            ServerLogger.log(Level.FINE, "Player disconnected from end game. Game name : " + gameName + " | Player disconnected : " + username);
    }

    /**
     * {@inheritDoc}
     *
     * @param playerName
     * @param message    the message written by a player and sent to one or more players.
     */
    @Override
    public synchronized void addMessage(String playerName, Message message) {
        ServerLogger.log(Level.FINEST, "Player sent game chat message. Game name : " + gameName + " | Player chatting : " + playerName +
                " | Message : " + message.getSender() + "->" + message.getReceiver() + " :: " + message.getMessage());
        chatModel.addMessage(message);
    }

    /**
     * {@inheritDoc}
     *
     * @param playerName          the name of the player.
     * @param networkChatObserver the observer to add.
     */
    @Override
    public synchronized void addChatObserver(String playerName, Observer<ChatEvent, PlayerChatModelView> networkChatObserver) {
        chatModel.addChatObserver(playerName, networkChatObserver);
        chatObserverMap.put(playerName, networkChatObserver);
    }
}
