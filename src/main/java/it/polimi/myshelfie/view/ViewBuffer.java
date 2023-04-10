package it.polimi.myshelfie.view;

import it.polimi.myshelfie.model.modelviews.*;
import it.polimi.myshelfie.utils.Observable;
import it.polimi.myshelfie.utils.Observer;

import java.util.ArrayDeque;
import java.util.Queue;

public class ViewBuffer extends View{
    Observable<ViewUpdate> observable;
    Queue<Object[]> parameters;
    ModelView modelView;
    ClientExceptionsNotifierView clientExceptionsNotifierView;
    PlayerChatModelView playerChatModelView;
    LobbyModelView lobbyModelView;
    GameModelView gameModelView;
    ViewType viewType;
    public ViewBuffer(){
        this.parameters = new ArrayDeque<>();
        this.observable = new Observable<>();
    }
    public synchronized void setUpdateParameters(Object[] updateParameters){
        if(updateParameters==null) parameters.add(new Object[0]);
        else parameters.add(updateParameters);
    }
    @Override
    public synchronized Object[] getUpdateParameters() {
        return parameters.poll();
    }

    @Override
    public synchronized void setModelView(ModelView modelView) {
        this.modelView = modelView;
        observable.setChanged();
        observable.notifyObservers(ViewUpdate.MODEL_UPDATE);
    }

    @Override
    public synchronized void setExceptionView(ClientExceptionsNotifierView exceptionView) {
        this.clientExceptionsNotifierView = exceptionView;
        observable.setChanged();
        observable.notifyObservers(ViewUpdate.EXCEPTION_UPDATE);
    }

    @Override
    public synchronized void setChatView(PlayerChatModelView chatView) {
        this.playerChatModelView = chatView;
        observable.setChanged();
        observable.notifyObservers(ViewUpdate.CHAT_UPDATE);
    }

    @Override
    public synchronized void setLobbyModelView(LobbyModelView lobbyModelView) {
        this.lobbyModelView = lobbyModelView;
        observable.setChanged();
        observable.notifyObservers(ViewUpdate.LOBBY_UPDATE);
    }

    @Override
    public synchronized void setGameModelView(GameModelView gameModelView) {
        this.gameModelView = gameModelView;
        observable.setChanged();
        observable.notifyObservers(ViewUpdate.GAME_UPDATE);
    }

    @Override
    public synchronized void setView(ViewType viewType) {
        this.viewType = viewType;
        observable.setChanged();
        observable.notifyObservers(ViewUpdate.VIEWTYPE_UPDATE);
    }

    public synchronized void addUpdateObserver(Observer<ViewUpdate> o) {
        observable.addObserver(o);
    }

    public synchronized void removeUpdateObserver(Observer<ViewUpdate> o ){
        observable.deleteObserver(o);
    }

    public synchronized ModelView getModelView() {
        return modelView;
    }

    public synchronized ClientExceptionsNotifierView getClientExceptionsNotifierView() {
        return clientExceptionsNotifierView;
    }

    public synchronized PlayerChatModelView getPlayerChatModelView() {
        return playerChatModelView;
    }

    public synchronized LobbyModelView getLobbyModelView() {
        return lobbyModelView;
    }

    public synchronized GameModelView getGameModelView() {
        return gameModelView;
    }

    public synchronized ViewType getViewType() {
        return viewType;
    }
}
