package it.polimi.myshelfie.view;

import it.polimi.myshelfie.model.modelviews.*;
import it.polimi.myshelfie.network.NetworkMessage;
import it.polimi.myshelfie.network.events.ViewEvent;
import it.polimi.myshelfie.utils.Observable;
import it.polimi.myshelfie.utils.Observer;

import java.util.ArrayDeque;
import java.util.Queue;

public class ViewBuffer extends View {
    Observable<ViewUpdate> UINotifier;
    Queue<NetworkMessage> networkMessages;
    ModelView modelView;
    ClientExceptionsNotifierView clientExceptionsNotifierView;
    PlayerChatModelView playerChatModelView;
    LobbyModelView lobbyModelView;
    GameModelView gameModelView;
    ViewType viewType;
    public ViewBuffer(){
        this.networkMessages = new ArrayDeque<>();
        this.UINotifier = new Observable<>();
    }
    public synchronized void sendMessage(NetworkMessage networkMessage){
        if(networkMessage ==null) return;
        networkMessages.add(networkMessage);
        setChanged();
        notifyObservers((ViewEvent) networkMessage.getEvent());
    }

    @Override
    public synchronized void setModelView(ModelView modelView) {
        this.modelView = modelView;
        UINotifier.setChanged();
        UINotifier.notifyObservers(ViewUpdate.MODEL_UPDATE);
    }

    @Override
    public synchronized void setExceptionView(ClientExceptionsNotifierView exceptionView) {
        this.clientExceptionsNotifierView = exceptionView;
        UINotifier.setChanged();
        UINotifier.notifyObservers(ViewUpdate.EXCEPTION_UPDATE);
    }

    @Override
    public synchronized void setChatView(PlayerChatModelView chatView) {
        this.playerChatModelView = chatView;
        UINotifier.setChanged();
        UINotifier.notifyObservers(ViewUpdate.CHAT_UPDATE);
    }

    @Override
    public synchronized void setLobbyModelView(LobbyModelView lobbyModelView) {
        this.lobbyModelView = lobbyModelView;
        UINotifier.setChanged();
        UINotifier.notifyObservers(ViewUpdate.LOBBY_UPDATE);
    }

    @Override
    public synchronized void setGameModelView(GameModelView gameModelView) {
        this.gameModelView = gameModelView;
        UINotifier.setChanged();
        UINotifier.notifyObservers(ViewUpdate.GAME_UPDATE);
    }

    @Override
    public synchronized void setView(ViewType viewType) {
        this.viewType = viewType;
        UINotifier.setChanged();
        UINotifier.notifyObservers(ViewUpdate.VIEWTYPE_UPDATE);
    }

    @Override
    public NetworkMessage getMessage() {
        return networkMessages.poll();
    }

    public synchronized void addUI(Observer<ViewUpdate> o) {
        UINotifier.addObserver(o);
    }

    public synchronized void removeUI(Observer<ViewUpdate> o ){
        UINotifier.deleteObserver(o);
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
