package it.polimi.myshelfie.network;

import it.polimi.myshelfie.GameConfig;
import it.polimi.myshelfie.model.chatting.Message;
import it.polimi.myshelfie.model.events.*;
import it.polimi.myshelfie.model.exceptions.InvalidParameterException;
import it.polimi.myshelfie.model.modelviews.*;
import it.polimi.myshelfie.view.ViewType;
import it.polimi.myshelfie.view.events.ViewEvent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NetworkMessage implements Serializable {
    static final long serialVersionUID = 1L;
    MessageType messageType;
    Object event;
    List<Object> parameters;

    public NetworkMessage(ViewType viewType){
        this.messageType = MessageType.SELECT_VIEW_MESSAGE;
        this.event = null;
        this.parameters = new ArrayList<>();
        this.parameters.add(viewType);
    }
    public NetworkMessage(ViewEvent evt, GameConfig gameConfig) throws InvalidParameterException {
        if(evt != ViewEvent.CONFIG_REQUEST) throw new InvalidParameterException("wrong event");
        this.messageType = MessageType.VIEW_MESSAGE;
        this.event = evt;
        this.parameters = new ArrayList<>();
        this.parameters.add(gameConfig);
    }
    public NetworkMessage(ViewEvent evt, int column) throws InvalidParameterException {
        if(evt!=ViewEvent.INSERT_REQUEST) throw new InvalidParameterException("wrong event");
        this.messageType = MessageType.VIEW_MESSAGE;
        this.event = evt;
        this.parameters = new ArrayList<>();
        this.parameters.add(column);
    }
    public NetworkMessage(ViewEvent evt, List<?> positionsOrOrderedTiles) throws InvalidParameterException {
        if(evt!=ViewEvent.DRAW_REQUEST && evt!=ViewEvent.ORGANIZE_REQUEST) throw new InvalidParameterException("wrong event");
        this.messageType = MessageType.VIEW_MESSAGE;
        this.event = evt;
        this.parameters = new ArrayList<>();
        this.parameters.add(positionsOrOrderedTiles);
    }
    public NetworkMessage(ViewEvent evt) throws InvalidParameterException {
        if(evt!=ViewEvent.START_REQUEST && evt!= ViewEvent.EXIT_REQUEST) throw new InvalidParameterException("wrong event");
        this.messageType = MessageType.VIEW_MESSAGE;
        this.event = evt;
        this.parameters = new ArrayList<>();
    }
    public NetworkMessage(ViewEvent evt, String playerName, String lobby) throws InvalidParameterException {
        if((evt!=ViewEvent.JOIN_REQUEST && evt!=ViewEvent.CREATE_REQUEST)|| playerName==null || lobby == null ) throw new InvalidParameterException("wrong event");
        this.messageType = MessageType.VIEW_MESSAGE;
        this.event = evt;
        this.parameters = new ArrayList<>();
        this.parameters.add(playerName);
        this.parameters.add(lobby);
    }
    public NetworkMessage(ViewEvent evt, Message message) throws InvalidParameterException{
        if(evt!=ViewEvent.MESSAGE_SEND) throw new InvalidParameterException("wrong event");
        this.messageType = MessageType.VIEW_MESSAGE;
        this.event = evt;
        this.parameters = new ArrayList<>();
        this.parameters.add(message);
    }
    public NetworkMessage(LobbyEvent evt, LobbyModelView lobbyModelView){
        this.messageType = MessageType.LOBBY_MESSAGE;
        this.event = evt;
        this.parameters = new ArrayList<>();
        this.parameters.add(lobbyModelView);
    }
    public NetworkMessage(GameEvent evt, GameModelView gameModelView){
        this.messageType = MessageType.GAME_MESSAGE;
        this.event = evt;
        this.parameters = new ArrayList<>();
        this.parameters.add(gameModelView);
    }

    public NetworkMessage(ExceptionEvent evt, ClientExceptionsNotifierView exceptionsNotifierView){
        this.messageType = MessageType.EXCEPTION_MESSAGE;
        this.event = evt;
        this.parameters = new ArrayList<>();
        this.parameters.add(exceptionsNotifierView);
    }
    public NetworkMessage(ChatEvent evt, PlayerChatModelView chatModelView){
        this.messageType = MessageType.CHAT_MESSAGE;
        this.event = evt;
        this.parameters = new ArrayList<>();
        this.parameters.add(chatModelView);
    }

    public NetworkMessage(ModelEvent evt, ModelView modelView) {
        this.messageType = MessageType.MODEL_MESSAGE;
        this.event = evt;
        this.parameters = new ArrayList<>();
        this.parameters.add(modelView);
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public Object getEvent() {
        return event;
    }

    public List<Object> getParameters() {
        return parameters;
    }
}
