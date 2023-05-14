package it.polimi.myshelfie.view;

import it.polimi.myshelfie.model.GameState;
import it.polimi.myshelfie.model.modelviews.*;
import it.polimi.myshelfie.network.NetworkMessage;
import it.polimi.myshelfie.view.events.ViewEvent;
import it.polimi.myshelfie.utils.Observable;
import it.polimi.myshelfie.utils.Observer;

import java.util.ArrayList;


public class ViewBuffer extends View {
    Observable<ViewUpdate, NetworkMessage> UINotifier;
    ModelView modelView;
    ClientExceptionsNotifierView clientExceptionsNotifierView;
    PlayerChatModelView playerChatModelView;
    LobbyModelView lobbyModelView;
    FullGameModelView fullGameModelView;
    ViewType viewType;
    public ViewBuffer(){
        this.UINotifier = new Observable<>();
    }
    public synchronized void sendMessage(NetworkMessage networkMessage){
        if(networkMessage == null) return;
        setChanged();
        notifyObservers((ViewEvent) networkMessage.getEvent(), networkMessage);
    }

    @Override
    public synchronized void setModelView(ModelView modelView) {
        this.modelView = modelView;
        UINotifier.setChanged();
        UINotifier.notifyObservers(ViewUpdate.MODEL_UPDATE, null);
    }

    @Override
    public synchronized void setExceptionView(ClientExceptionsNotifierView exceptionView) {
        this.clientExceptionsNotifierView = exceptionView;
        UINotifier.setChanged();
        UINotifier.notifyObservers(ViewUpdate.EXCEPTION_UPDATE, null);
    }

    @Override
    public synchronized void setChatView(PlayerChatModelView chatView) {
        this.playerChatModelView = chatView;
        UINotifier.setChanged();
        UINotifier.notifyObservers(ViewUpdate.CHAT_UPDATE, null);
    }

    @Override
    public synchronized void setLobbyModelView(LobbyModelView lobbyModelView) {
        this.lobbyModelView = lobbyModelView;
        UINotifier.setChanged();
        UINotifier.notifyObservers(ViewUpdate.LOBBY_UPDATE, null);
    }

    @Override
    public synchronized void setGameModelView(FullGameModelView fullGameModelView) {
        this.fullGameModelView = fullGameModelView;
        UINotifier.setChanged();
        UINotifier.notifyObservers(ViewUpdate.GAME_UPDATE, null);
    }

    @Override
    public void updateDrawView(DrawModelView drawModelView) {
        fullGameModelView.setCurrentState(drawModelView.getCurrentState());
        fullGameModelView.setLivingRoom(drawModelView.getLivingRoom());
        fullGameModelView.getCurrentPlayer().setTakenTiles(drawModelView.getTakenTiles());

        UINotifier.setChanged();
        UINotifier.notifyObservers(ViewUpdate.GAME_UPDATE, null);
    }

    @Override
    public void updateOrganizeView(OrganizeModelView organizeModelView) {
        fullGameModelView.setCurrentState(organizeModelView.getCurrentState());
        fullGameModelView.getCurrentPlayer().setTakenTiles(organizeModelView.getOrderedTiles());

        UINotifier.setChanged();
        UINotifier.notifyObservers(ViewUpdate.GAME_UPDATE, null);
    }

    @Override
    public void updateInsertView(InsertModelView insertModelView) {
        fullGameModelView.getPlayer(insertModelView.getCurrentPlayer()).setBookshelf(insertModelView.getBookshelf());
        fullGameModelView.getPlayer(insertModelView.getCurrentPlayer()).setTakenTiles(new ArrayList<>());
        if(fullGameModelView.getCurrentState()!= GameState.DRAW) {
            fullGameModelView.setCurrentState(insertModelView.getCurrentState());
        }
        UINotifier.setChanged();
        UINotifier.notifyObservers(ViewUpdate.GAME_UPDATE, null);
    }

    @Override
    public void updateTurnView(EndTurnModelView endTurnModelView) {
        fullGameModelView.setCurrentState(endTurnModelView.getCurrentState());
        fullGameModelView.getPlayer(endTurnModelView.getLastPlayer()).setPoints(endTurnModelView.getPoints());
        fullGameModelView.getPlayer(endTurnModelView.getLastPlayer()).setTokens(endTurnModelView.getPlayerTokens());
        fullGameModelView.setRemainingTiles(endTurnModelView.getRemainingTiles());
        fullGameModelView.setLivingRoom(endTurnModelView.getLivingRoom());
        fullGameModelView.setCommonGoals(endTurnModelView.getCommonGoals());
        fullGameModelView.setCurrentPlayer(endTurnModelView.getCurrentPlayer());

        UINotifier.setChanged();
        UINotifier.notifyObservers(ViewUpdate.GAME_UPDATE, null);
    }

    @Override
    public synchronized void setView(ViewType viewType) {
        this.viewType = viewType;
        UINotifier.setChanged();
        UINotifier.notifyObservers(ViewUpdate.VIEW_TYPE_UPDATE, null);
    }

    public synchronized void addUI(Observer<ViewUpdate, NetworkMessage> o) {
        UINotifier.addObserver(o);
    }

    public synchronized void removeUI(Observer<ViewUpdate, NetworkMessage> o ){
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

    public synchronized FullGameModelView getGameModelView() {
        return fullGameModelView;
    }

    public synchronized ViewType getViewType() {
        return viewType;
    }
}
