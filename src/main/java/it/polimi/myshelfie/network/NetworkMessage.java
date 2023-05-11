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
import it.polimi.myshelfie.view.View;
import it.polimi.myshelfie.view.ViewType;
import it.polimi.myshelfie.view.events.ViewEvent;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;

public class NetworkMessage implements Serializable {
    static final long serialVersionUID = 1L;
    MessageType messageType;
    private SerializableConsumer<View> clientFunction = (x -> {
        throw new RuntimeException("Not Implemented");
    });
    private SerializableConsumer<ControllerGroup> serverFunction = (x -> {
        throw new RuntimeException("Not Implemented");
    });
    Object event;

    //CLIENT TO SERVER MESSAGES
    public void clientExecute(View view){
        clientFunction.accept(view);
    }

    public NetworkMessage(ViewEvent evt, String playerName, String lobby) throws InvalidParameterException {
        if((evt!=ViewEvent.JOIN_REQUEST && evt!=ViewEvent.CREATE_REQUEST)|| playerName==null || lobby == null ) throw new InvalidParameterException("wrong event");
        this.messageType = MessageType.VIEW_MESSAGE;
        this.event = evt;
        this.serverFunction = ((controllers)-> new Thread(()->{
            Client client = controllers.getClient();
            ServerConnectionHandlerImpl connectionHandler = controllers.getServerConnectionHandler();
            LobbyController lobbyController;
            if(evt==ViewEvent.JOIN_REQUEST){
                lobbyController = controllers.getController().joinLobby(client, connectionHandler, playerName, lobby);
            } else {
                lobbyController = controllers.getController().createLobby(client, connectionHandler, playerName, lobby);
            }
            if(lobbyController==null) return;
            lobbyController.addLobbyObserver(playerName,((event,lobbyModelView)-> new Thread(()->{
                try {
                    client.send(new NetworkMessage(event,lobbyModelView));
                } catch (RemoteException ignored) {
                }
            }).start()));
            connectionHandler.setLobbyController(lobbyController);
            connectionHandler.setPlayerName(playerName);
        }).start());
    }

    public NetworkMessage(ViewEvent evt, GameConfig gameConfig) throws InvalidParameterException {
        if(evt != ViewEvent.CONFIG_REQUEST) throw new InvalidParameterException("wrong event");
        this.messageType = MessageType.VIEW_MESSAGE;
        this.event = evt;
        this.serverFunction = ((controllers)->{
            if(controllers.getLobbyController()==null) return;
            new Thread(()->controllers.getLobbyController().setConfig(controllers.getUsername(), gameConfig)).start();
        });
    }

    public NetworkMessage(ViewEvent evt, Message message) throws InvalidParameterException{
        if(evt!=ViewEvent.MESSAGE_SEND) throw new InvalidParameterException("wrong event");
        this.messageType = MessageType.VIEW_MESSAGE;
        this.event = evt;
        this.serverFunction = ((controllers)->{
            if(controllers.getChatController()==null) return;
            new Thread(()->controllers.getChatController().addMessage(message)).start();
        });
    }

    public NetworkMessage(ViewEvent evt) throws InvalidParameterException {
        if(evt!=ViewEvent.START_REQUEST && evt!= ViewEvent.EXIT_REQUEST) throw new InvalidParameterException("wrong event");
        this.messageType = MessageType.VIEW_MESSAGE;
        this.event = evt;
        this.serverFunction = ((controllers)->{
            if(controllers.getLobbyController()==null) return;
            new Thread(()->{
                if(evt == ViewEvent.START_REQUEST) controllers.getLobbyController().startGame(controllers.getUsername());
                else controllers.getLobbyController().exitLobby(controllers.getUsername());
            }).start();
        });
    }

    public NetworkMessage(ViewEvent evt, List<?> positionsOrOrderedTiles) throws InvalidParameterException {
        if(evt!=ViewEvent.DRAW_REQUEST && evt!=ViewEvent.ORGANIZE_REQUEST) throw new InvalidParameterException("wrong event");
        this.messageType = MessageType.VIEW_MESSAGE;
        this.event = evt;
        this.serverFunction = ((controllers)->{
            if(controllers.getGameController()==null) return;
            new Thread(()->{
                if(evt == ViewEvent.DRAW_REQUEST)
                    controllers.getGameController().drawTiles((List<Position>) positionsOrOrderedTiles,
                            controllers.getUsername());
                else
                    controllers.getGameController().orderChosenTiles((List<ItemTile>) positionsOrOrderedTiles,
                            controllers.getUsername());
            }).start();
        });
    }

    public NetworkMessage(ViewEvent evt, int column) throws InvalidParameterException {
        if(evt!=ViewEvent.INSERT_REQUEST) throw new InvalidParameterException("wrong event");
        this.messageType = MessageType.VIEW_MESSAGE;
        this.event = evt;
        this.serverFunction = ((controllers)->{
            if(controllers.getGameController()==null) return;
            new Thread(()->controllers.getGameController().insertTiles(controllers.getUsername(),column)).start();
        });
    }

    //SERVER TO CLIENT MESSAGES
    public void serverExecute(ControllerGroup controllers){
        serverFunction.accept(controllers);
    }

    public NetworkMessage(ViewType viewType){
        this.messageType = MessageType.SELECT_VIEW_MESSAGE;
        this.event = null;
        this.clientFunction = ((view)-> new Thread(()->view.setView(viewType)).start());
    }
    public NetworkMessage(LobbyEvent evt, LobbyModelView lobbyModelView){
        this.messageType = MessageType.LOBBY_MESSAGE;
        this.event = evt;
        this.clientFunction = ((view)-> new Thread(()->view.setLobbyModelView(lobbyModelView)).start());
    }
    public NetworkMessage(GameEvent evt, GameModelView gameModelView){
        this.messageType = MessageType.GAME_MESSAGE;
        this.event = evt;
        this.clientFunction = ((view)-> new Thread(()->{
            switch (evt){
                case GAME_START, WIN -> view.setGameModelView((FullGameModelView) gameModelView);
                case DRAW -> view.updateDrawView((DrawModelView) gameModelView);
                case ORGANIZE -> view.updateOrganizeView((OrganizeModelView) gameModelView);
                case INSERT -> view.updateInsertView((InsertModelView) gameModelView);
                case COUNTING_POINTS -> view.updateTurnView((EndTurnModelView) gameModelView);
            }
        }).start());
    }

    public NetworkMessage(ExceptionEvent evt, ClientExceptionsNotifierView exceptionsNotifierView){
        this.messageType = MessageType.EXCEPTION_MESSAGE;
        this.event = evt;
        this.clientFunction = ((view)-> new Thread(()->view.setExceptionView(exceptionsNotifierView)).start());
    }
    public NetworkMessage(ChatEvent evt, PlayerChatModelView chatModelView){
        this.messageType = MessageType.CHAT_MESSAGE;
        this.event = evt;
        this.clientFunction = ((view)-> new Thread(()->view.setChatView(chatModelView)).start());
    }

    public NetworkMessage(ModelEvent evt, ModelView modelView) {
        this.messageType = MessageType.MODEL_MESSAGE;
        this.event = evt;
        this.clientFunction = ((view)-> new Thread(()->view.setModelView(modelView)).start());
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public Object getEvent() {
        return event;
    }
}
