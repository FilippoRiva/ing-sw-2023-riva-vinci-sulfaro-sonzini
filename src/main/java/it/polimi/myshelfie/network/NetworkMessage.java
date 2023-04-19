package it.polimi.myshelfie.network;

import it.polimi.myshelfie.GameConfig;
import it.polimi.myshelfie.model.exceptions.InvalidParameterException;
import it.polimi.myshelfie.model.modelviews.*;
import it.polimi.myshelfie.network.events.*;
import it.polimi.myshelfie.view.ViewType;

import java.io.Serializable;
import java.util.List;

public class NetworkMessage implements Serializable {
    MessageType messageType;
    Object event;
    Object[] parameters;

    public NetworkMessage(ViewType viewType){
        this.messageType = MessageType.SELECTVIEWMESSAGE;
        this.event = null;
        this.parameters = new Object[1];
        this.parameters[0] = viewType;
    }
    public NetworkMessage(ViewEvent evt, GameConfig gameConfig) throws InvalidParameterException {
        if(evt != ViewEvent.CONFIG_REQUEST) throw new InvalidParameterException("wrong event");
        this.messageType = MessageType.VIEWMESSAGE;
        this.event = evt;
        this.parameters = new Object[1];
        this.parameters[0] = gameConfig;
    }
    public NetworkMessage(ViewEvent evt, int column) throws InvalidParameterException {
        if(evt!=ViewEvent.INSERT_REQUEST) throw new InvalidParameterException("wrong event");
        this.messageType = MessageType.VIEWMESSAGE;
        this.event = evt;
        this.parameters = new Object[1];
        this.parameters[0] = column;
    }
    public NetworkMessage(ViewEvent evt, List<?> positionsOrOrderedTiles) throws InvalidParameterException {
        if(evt!=ViewEvent.DRAW_REQUEST && evt!=ViewEvent.ORGANIZE_REQUEST) throw new InvalidParameterException("wrong event");
        this.messageType = MessageType.VIEWMESSAGE;
        this.event = evt;
        this.parameters = new Object[1];
        this.parameters[0] = positionsOrOrderedTiles;
    }
    public NetworkMessage(ViewEvent evt) throws InvalidParameterException {
        if(evt!=ViewEvent.START_REQUEST) throw new InvalidParameterException("wrong event");
        this.messageType = MessageType.VIEWMESSAGE;
        this.event = evt;
        this.parameters = new Object[0];
    }
    public NetworkMessage(ViewEvent evt, String playerName, String lobby) throws InvalidParameterException {
        if((evt!=ViewEvent.JOIN_REQUEST && evt!=ViewEvent.CREATE_REQUEST)|| playerName==null || lobby == null ) throw new InvalidParameterException("wrong event");
        this.messageType = MessageType.VIEWMESSAGE;
        this.event = evt;
        this.parameters = new Object[2];
        this.parameters[0] = playerName;
        this.parameters[1] = lobby;
    }
    public NetworkMessage(LobbyEvent evt, LobbyModelView lobbyModelView){
        this.messageType = MessageType.LOBBYMESSAGE;
        this.event = evt;
        this.parameters = new Object[1];
        this.parameters[0] = lobbyModelView;
    }
    public NetworkMessage(GameEvent evt, GameModelView gameModelView){
        this.messageType = MessageType.GAMEMESSAGE;
        this.event = evt;
        this.parameters = new Object[1];
        this.parameters[0] = gameModelView;
    }
    public NetworkMessage(ExceptionEvent evt, ClientExceptionsNotifierView exceptionsNotifierView){
        this.messageType = MessageType.EXCEPTIONMESSAGE;
        this.event = evt;
        this.parameters = new Object[1];
        this.parameters[0] = exceptionsNotifierView;
    }
    public NetworkMessage(ChatEvent evt, PlayerChatModelView chatModelView){
        this.messageType = MessageType.CHATMESSAGE;
        this.event = evt;
        this.parameters = new Object[1];
        this.parameters[0] = chatModelView;
    }
    public NetworkMessage(ModelEvent evt, ModelView modelView) {
        this.messageType = MessageType.MODELMESSAGE;
        this.event = evt;
        this.parameters = new Object[1];
        this.parameters[0] = modelView;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public Object getEvent() {
        return event;
    }

    public Object[] getParameters() {
        return parameters;
    }
}
