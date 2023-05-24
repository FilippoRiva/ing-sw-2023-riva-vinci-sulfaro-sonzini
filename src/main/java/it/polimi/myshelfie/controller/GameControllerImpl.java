package it.polimi.myshelfie.controller;

import it.polimi.myshelfie.controller.interfaces.ChatController;
import it.polimi.myshelfie.controller.interfaces.GameController;
import it.polimi.myshelfie.model.GameState;
import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.Position;
import it.polimi.myshelfie.model.Token;
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
import it.polimi.myshelfie.view.ViewType;


import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameControllerImpl implements GameController, ChatController{

    private final String gameName;
    private final GameModel gameModel;
    private final ChatModel chatModel;
    private final ControllerImpl controllerImpl;
    private Map<String,Client> clientsMap;
    private Map<String,ClientExceptionsNotifier> exceptionsNotifiersMap;
    private Map<String, Observer<ChatEvent, PlayerChatModelView>> chatObserverMap;
    private final Map<String, Observer<GameEvent, GameModelView>> gameObserverMap;

    /**
     * Constructor for the GameControllerImpl class.
     * @param gameName the name of the game.
     * @param gameModel the model associated to the game.
     * @param chatModel the model associated to the game chat.
     * @param controllerImpl the controller of the game.
     */
    public GameControllerImpl(String gameName, GameModel gameModel, ChatModel chatModel, ControllerImpl controllerImpl){
        this.gameName = gameName;
        this.gameModel = gameModel;
        this.chatModel = chatModel;
        this.controllerImpl = controllerImpl;
        this.clientsMap = new HashMap<>();
        this.exceptionsNotifiersMap = new HashMap<>();
        this.gameObserverMap = new HashMap<>();
        this.chatObserverMap = new HashMap<>();
    }
    @Override
    public synchronized void drawTiles(List<Position> positions, String playerName) {
        GameState currentGameState = gameModel.getState();
        if(currentGameState != GameState.DRAW && currentGameState != GameState.LAST_DRAW) return;
        try {
            gameModel.drawTiles(positions, playerName);
            if(gameModel.getState()==GameState.DRAW) gameModel.setState(GameState.ORGANIZE);
            else gameModel.setState(GameState.LAST_ORGANIZE);
        } catch (InvalidParameterException e) {
            exceptionsNotifiersMap.get(playerName).notifyException(e);
        }
    }

    @Override
    public synchronized void orderChosenTiles(List<ItemTile> orderedTiles, String playerName) {
        GameState currentGameState = gameModel.getState();
        if(currentGameState != GameState.ORGANIZE && currentGameState != GameState.LAST_ORGANIZE) return;
        try {
            gameModel.orderChosenTiles(orderedTiles, playerName);
            if(currentGameState==GameState.ORGANIZE) gameModel.setState(GameState.INSERT);
            else gameModel.setState(GameState.LAST_INSERT);
        } catch (InvalidParameterException e) {
            exceptionsNotifiersMap.get(playerName).notifyException(e);
        }
    }

    @Override
    public synchronized void insertTiles(String playerName, int column) {
        GameState currentGameState = gameModel.getState();
        if(currentGameState != GameState.INSERT && currentGameState != GameState.LAST_INSERT) return;
        try {
            gameModel.insertTiles(playerName, column);
            if(currentGameState == GameState.INSERT) gameModel.setState(GameState.COUNTING_POINTS);
            else gameModel.setState(GameState.LAST_COUNTING_POINTS);
        } catch (InvalidParameterException | FullColumnException e) {
            exceptionsNotifiersMap.get(playerName).notifyException(e);
            return;
        }
        endTurn(playerName);

    }

    /**
     * This method process the end turn: it checks if the player achieved a common goal; checks if the living room is refillable;
     * sets the next turn.
     * @param playerName is the current player that has finished its turn.
     */
    private void endTurn(String playerName){
        gameModel.checkCommonGoal(playerName);
        if(gameModel.isBookshelfFull(playerName)) {
            if (gameModel.getState() == GameState.LAST_COUNTING_POINTS) {
                if (gameModel.isLastPlayer(playerName)) {
                    gameModel.calculateWinResults();
                    gameModel.setState(GameState.WIN);
                    for (Client c : clientsMap.values()) {
                        try {
                            c.send(new NetworkMessage(ViewType.WIN));
                        } catch (RemoteException ignored) {}
                    }
                }
            } else {
                gameModel.getGameModelView().getPlayer(playerName).getTokens().add(Token.ENDGAME);
                gameModel.refillLivingRoom();
                gameModel.nextTurn();
                gameModel.setState(GameState.LAST_DRAW);
            }
        } else {
            gameModel.refillLivingRoom();
            gameModel.nextTurn();
            if(gameModel.getState()==GameState.COUNTING_POINTS) gameModel.setState(GameState.DRAW);
            else if (gameModel.isLastPlayer(playerName)) {
                gameModel.calculateWinResults();
                gameModel.setState(GameState.WIN);
                for (Client c : clientsMap.values()) {
                    try {
                        c.send(new NetworkMessage(ViewType.WIN));
                    } catch (RemoteException ignored) {}
                }
            }
            else gameModel.setState(GameState.LAST_DRAW);
        }
    }

    @Override
    public synchronized void addGameObserver(String playerName, Observer<GameEvent, GameModelView>networkGameObserver) {
        gameModel.addGameObserver(networkGameObserver);
        gameObserverMap.put(playerName, networkGameObserver);
    }

    @Override
    public void exitGame(String disconnectedPlayerName) {
        clientsMap.remove(disconnectedPlayerName);
        //notifying non disconnected clients
        for(String player : clientsMap.keySet()){
            new Thread(()->{
                try {
                    clientsMap.get(player).send(new NetworkMessage(ViewType.INIT));
                    exceptionsNotifiersMap.get(player).notifyException(new DisconnectionException(disconnectedPlayerName));
                } catch (RemoteException ignored) {
                }
            }).start();
        }
        //removing observers
        for(Observer<GameEvent, GameModelView> o : gameObserverMap.values()){
            gameModel.removeGameObserver(o);
        }
        for(String player : chatObserverMap.keySet()){
            chatModel.removeChatObserver(player,chatObserverMap.get(player));
        }
        gameModel.setState(GameState.WIN);
        controllerImpl.deleteGame(gameName);
    }

    /**
     * This method initializes the attributes of the GameControllerImpl class.
     * @param clientsMap the list of clients and their players.
     * @param connectionHandlersMap the list of connection handlers.
     * @param notifiersMap the list of the exceptions notifiers.
     * @param chatObserverMap the list of the chat observers.
     */
    public void init(Map<String,Client> clientsMap, Map<String,ServerConnectionHandler> connectionHandlersMap,
                     Map<String, ClientExceptionsNotifier> notifiersMap,
                     Map<String, Observer<ChatEvent, PlayerChatModelView>> chatObserverMap){
        this.clientsMap = clientsMap;
        this.exceptionsNotifiersMap = notifiersMap;
        this.chatObserverMap = chatObserverMap;
        for(ServerConnectionHandler sch : connectionHandlersMap.values()){
            try {
                sch.setGameController(this);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        gameModel.setState(GameState.FIRST_DRAW);
    }

    @Override
    public synchronized void addMessage(Message message){
        chatModel.addMessage(message);
    }

    @Override
    public synchronized void addChatObserver(String playername, Observer<ChatEvent, PlayerChatModelView> networkChatObserver) {
        chatModel.addChatObserver(playername,networkChatObserver);
    }
}
