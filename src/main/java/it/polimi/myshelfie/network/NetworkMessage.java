package it.polimi.myshelfie.network;

import it.polimi.myshelfie.GameConfig;
import it.polimi.myshelfie.controller.interfaces.LobbyController;
import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.Position;
import it.polimi.myshelfie.model.chatting.Message;
import it.polimi.myshelfie.model.events.*;
import it.polimi.myshelfie.model.exceptions.InvalidParameterException;
import it.polimi.myshelfie.model.modelviews.*;
import it.polimi.myshelfie.utils.SerializableConsumer;
import it.polimi.myshelfie.utils.ServerLogger;
import it.polimi.myshelfie.view.View;
import it.polimi.myshelfie.view.ViewType;
import it.polimi.myshelfie.view.events.ViewEvent;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Represents the generic network message.
 * It has a specific messageType, a serialVersionUID, an event and 2 SerializableConsumer: clientFunction, used on the view, and serverFunction, used on the controllerGroup of the client.
 */
public class NetworkMessage implements Serializable {
    static final long serialVersionUID = 1L;
    MessageType messageType;
    Object event;
    private SerializableConsumer<View> clientFunction = (x -> {
        throw new RuntimeException("Not Implemented");
    });
    private SerializableConsumer<ControllerGroup> serverFunction = (x -> {
        throw new RuntimeException("Not Implemented");
    });

    //CLIENT TO SERVER MESSAGES

    /**
     * Constructor when Join or Create requests are called.
     *
     * @param evt        a view event.
     * @param playerName the player name.
     * @param lobby      the lobby name.
     * @throws InvalidParameterException if :
     *                                   <ul>
     *                                   <li>evt is not ViewEvent.JOIN_REQUEST or ViewEvent.CREATE_REQUEST</li>
     *                                   <li>playerName is null</li>
     *                                   <li>lobby is null</li>
     *                                   </ul>
     */
    public NetworkMessage(ViewEvent evt, String playerName, String lobby) throws InvalidParameterException {
        if ((evt != ViewEvent.JOIN_REQUEST && evt != ViewEvent.CREATE_REQUEST) || playerName == null || lobby == null)
            throw new InvalidParameterException("wrong event");
        this.messageType = MessageType.VIEW_MESSAGE;
        this.event = evt;
        this.serverFunction = ((controllers) -> new Thread(() -> {
            Client client = controllers.getClient();
            ServerConnectionHandlerImpl connectionHandler = controllers.getServerConnectionHandler();
            LobbyController lobbyController;
            connectionHandler.setPlayerName(playerName);
            if (evt == ViewEvent.JOIN_REQUEST) {
                lobbyController = controllers.getController().joinLobby(client, connectionHandler, playerName, lobby);
            } else {
                lobbyController = controllers.getController().createLobby(client, connectionHandler, playerName, lobby);
            }
            if (lobbyController == null) {
                connectionHandler.setPlayerName("");
                return;
            }
            lobbyController.addLobbyObserver(playerName, ((event, lobbyModelView) -> new Thread(() -> {
                try {
                    client.send(new NetworkMessage(event,lobbyModelView));
                } catch (RemoteException e) {
                    ServerLogger.logClientInfo(controllers.getServerConnectionHandler(),"Lobby Observer failed to send a Network Message");
                }
            }).start()));
            connectionHandler.setLobbyController(lobbyController);
        }).start());
    }

    /**
     * Constructor when a Config request is called.
     *
     * @param evt        a view event.
     * @param gameConfig the game config to set.
     * @throws InvalidParameterException if evt is not ViewEvent.CONFIG_ REQUEST.
     */
    public NetworkMessage(ViewEvent evt, GameConfig gameConfig) throws InvalidParameterException {
        if (evt != ViewEvent.CONFIG_REQUEST) throw new InvalidParameterException("wrong event");
        this.messageType = MessageType.VIEW_MESSAGE;
        this.event = evt;
        this.serverFunction = ((controllers) -> {
            if (controllers.getLobbyController() == null) return;
            new Thread(() -> controllers.getLobbyController().setConfig(controllers.getUsername(), gameConfig)).start();
        });
    }

    /**
     * Constructor when a message is sent in the chat.
     *
     * @param evt     a view event.
     * @param message the message sent.
     * @throws InvalidParameterException if evt is not ViewEvent.MESSAGE_SEND.
     */
    public NetworkMessage(ViewEvent evt, Message message) throws InvalidParameterException {
        if (evt != ViewEvent.MESSAGE_SEND) throw new InvalidParameterException("wrong event");
        this.messageType = MessageType.VIEW_MESSAGE;
        this.event = evt;
        this.serverFunction = ((controllers) -> {
            if (controllers.getChatController() == null) return;
            new Thread(() -> controllers.getChatController().addMessage(
                    controllers.getUsername(), message)).start();
        });
    }

    /**
     * Constructor when a Start or Exit request is called.
     *
     * @param evt a view event.
     * @throws InvalidParameterException if evt is not ViewEvent.START_REQUEST or ViewEvent.EXIT_REQUEST.
     */
    public NetworkMessage(ViewEvent evt) throws InvalidParameterException {
        if (evt != ViewEvent.START_REQUEST && evt != ViewEvent.EXIT_REQUEST)
            throw new InvalidParameterException("wrong event");
        this.messageType = MessageType.VIEW_MESSAGE;
        this.event = evt;
        this.serverFunction = ((controllers) -> new Thread(() -> {
            if (evt == ViewEvent.START_REQUEST) { //starting lobby
                if (controllers.getLobbyController() == null) return;
                controllers.getLobbyController().startGame(controllers.getUsername());
            } else {
                if (controllers.getGameController() != null) { //leaving game
                    controllers.getGameController().leaveEndGame(controllers.getUsername(), false);
                    try {
                        controllers.getServerConnectionHandler().setGameController(null);
                        controllers.getServerConnectionHandler().setChatController(null);
                    } catch (RemoteException ignored) {
                    }
                } else if (controllers.getLobbyController() != null) { //leaving lobby
                    controllers.getLobbyController().exitLobby(controllers.getUsername());
                    controllers.getServerConnectionHandler().setPlayerName("");
                    controllers.getServerConnectionHandler().setLobbyController(null);
                    try {
                        controllers.getServerConnectionHandler().setChatController(null);
                    } catch (RemoteException ignored) {
                    }
                }
            }
        }).start());
    }

    /**
     * Constructor when a Draw or Organize request is called.
     *
     * @param evt a view event.
     * @param positionsOrOrderedTiles list of positions of the ordered tiles (drawn or organized).
     * @throws InvalidParameterException if evt is not ViewEvent.DRAW_REQUEST or ViewEvent.ORGANIZE_REQUEST.
     */
    public NetworkMessage(ViewEvent evt, List<?> positionsOrOrderedTiles) throws InvalidParameterException {
        if (evt != ViewEvent.DRAW_REQUEST && evt != ViewEvent.ORGANIZE_REQUEST)
            throw new InvalidParameterException("wrong event");
        this.messageType = MessageType.VIEW_MESSAGE;
        this.event = evt;
        this.serverFunction = ((controllers) -> {
            if (controllers.getGameController() == null) return;
            new Thread(() -> {
                if (evt == ViewEvent.DRAW_REQUEST)
                    controllers.getGameController().drawTiles((List<Position>) positionsOrOrderedTiles,
                            controllers.getUsername());
                else
                    controllers.getGameController().orderChosenTiles((List<ItemTile>) positionsOrOrderedTiles,
                            controllers.getUsername());
            }).start();
        });
    }


    /**
     * Constructor when an Insert request is called.
     *
     * @param evt a view event.
     * @param column the column where to insert the taken tiles.
     * @throws InvalidParameterException if evt is not ViewEvent.INSERT_REQUEST.
     */
    public NetworkMessage(ViewEvent evt, int column) throws InvalidParameterException {
        if (evt != ViewEvent.INSERT_REQUEST) throw new InvalidParameterException("wrong event");
        this.messageType = MessageType.VIEW_MESSAGE;
        this.event = evt;
        this.serverFunction = ((controllers) -> {
            if (controllers.getGameController() == null) return;
            new Thread(() -> controllers.getGameController().insertTiles(controllers.getUsername(), column)).start();
        });
    }


    /**
     * Constructor to set a specific ViewType.
     *
     * @param viewType the type of view to set
     */
    public NetworkMessage(ViewType viewType) {
        this.messageType = MessageType.SELECT_VIEW_MESSAGE;
        this.event = null;
        this.clientFunction = ((view) -> new Thread(() -> view.setView(viewType)).start());
    }


    /**
     * Constructor when a LobbyEvent occurs.
     *
     * @param evt the lobby event.
     * @param lobbyModelView the LobbyModelView to set.
     */
    public NetworkMessage(LobbyEvent evt, LobbyModelView lobbyModelView) {
        this.messageType = MessageType.LOBBY_MESSAGE;
        this.event = evt;
        this.clientFunction = ((view) -> new Thread(() -> view.setLobbyModelView(lobbyModelView)).start());
    }


    /**
     * Constructor when a GameEvent occurs. It updates the ModelViews based on the event.
     *
     * @param evt the game event.
     * @param gameModelView the GameModelView to set.
     */
    public NetworkMessage(GameEvent evt, GameModelView gameModelView) {
        this.messageType = MessageType.GAME_MESSAGE;
        this.event = evt;
        this.clientFunction = ((view) -> new Thread(() -> {
            switch (evt) {
                case GAME_START, WIN -> view.setGameModelView((FullGameModelView) gameModelView);
                case DRAW -> view.updateDrawView((DrawModelView) gameModelView);
                case ORGANIZE -> view.updateOrganizeView((OrganizeModelView) gameModelView);
                case INSERT -> view.updateInsertView((InsertModelView) gameModelView);
                case COUNTING_POINTS -> view.updateTurnView((EndTurnModelView) gameModelView);
            }
        }).start());
    }


    /**
     * Constructor when an Exception Event occurs.
     * @param evt the exception event.
     * @param exceptionsNotifierModelView the ClientExceptionNotifierModelView to set.
     */
    public NetworkMessage(ExceptionEvent evt, ClientExceptionsNotifierModelView exceptionsNotifierModelView) {
        this.messageType = MessageType.EXCEPTION_MESSAGE;
        this.event = evt;
        this.clientFunction = ((view) -> new Thread(() -> view.setExceptionView(exceptionsNotifierModelView, evt)).start());
    }

    /**
     * Constructor when a Chat Event occurs.
     * @param evt the chat event.
     * @param chatModelView the ChatModelVIew to set.
     */
    public NetworkMessage(ChatEvent evt, PlayerChatModelView chatModelView) {
        this.messageType = MessageType.CHAT_MESSAGE;
        this.event = evt;
        this.clientFunction = ((view) -> new Thread(() -> view.setChatView(chatModelView)).start());
    }


    /**
     * Constructor when a Model Event occurs.
     * @param evt the model event.
     * @param modelView the ModelView to set.
     */
    public NetworkMessage(ModelEvent evt, ModelView modelView) {
        this.messageType = MessageType.MODEL_MESSAGE;
        this.event = evt;
        this.clientFunction = ((view) -> new Thread(() -> view.setModelView(modelView)).start());
    }


    /**
     * Constructor whwen the message type is a Ping.
     */
    public NetworkMessage() {
        this.messageType = MessageType.PING;
        this.clientFunction = ((view) -> {
        });
        this.serverFunction = ((controllersGroup) -> new Thread(() -> {
            try {
                controllersGroup.getServerConnectionHandler().ping();
            } catch (RemoteException ignored){
                ServerLogger.logClientInfo(controllersGroup.getServerConnectionHandler(),"Unable to Ping");
            }
        }).start());
    }

    /**
     * Executes the method invoked on the view from the client.
     *
     * @param view the view of the client.
     */
    public void clientExecute(View view) {
        clientFunction.accept(view);
    }

    //SERVER TO CLIENT MESSAGES
    public void serverExecute(ControllerGroup controllers) {
        serverFunction.accept(controllers);
    }

    /**
     * @return the message type.
     */
    public MessageType getMessageType() {
        return messageType;
    }

    /**
     * @return the event.
     */
    public Object getEvent() {
        return event;
    }
}
