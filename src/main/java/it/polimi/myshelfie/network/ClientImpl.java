package it.polimi.myshelfie.network;

import it.polimi.myshelfie.model.events.GameEvent;
import it.polimi.myshelfie.model.modelviews.*;
import it.polimi.myshelfie.network.observers.NetworkViewObserver;
import it.polimi.myshelfie.view.View;
import it.polimi.myshelfie.view.ViewType;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientImpl extends UnicastRemoteObject implements Client {
    View view;

    public ClientImpl(View view) throws RemoteException {
        this.view = view;
    }

    @Override
    public void handshake(ServerConnectionHandler server) throws RemoteException {
        new Thread(()-> view.addObserver(new NetworkViewObserver(server, view))).start();
    }

    @Override
    public void send(NetworkMessage networkMessage){
        MessageType type = networkMessage.getMessageType();

        new Thread(() -> {
            switch (type) {
                case MODEL_MESSAGE -> view.setModelView((ModelView) networkMessage.getParameters().get(0));
                case CHAT_MESSAGE ->
                        view.setChatView((PlayerChatModelView) networkMessage.getParameters().get(0));
                case LOBBY_MESSAGE ->
                        view.setLobbyModelView((LobbyModelView) networkMessage.getParameters().get(0));
                case GAME_MESSAGE -> {
                    switch ((GameEvent) networkMessage.getEvent()) {
                        case GAME_START ->
                                view.setGameModelView((FullGameModelView) networkMessage.getParameters().get(0));
                        case DRAW ->
                                view.updateDrawView((DrawModelView) networkMessage.getParameters().get(0));
                        case ORGANIZE -> {
                            //TODO
                        }
                        case INSERT ->
                                view.updateBookshelfView((InsertModelView) networkMessage.getParameters().get(0));
                        case COUNTING_POINTS ->
                                view.updateTurnView((EndTurnModelView) networkMessage.getParameters().get(0));
                        case WIN -> {
                            //TODO
                        }
                    }
                }
                case EXCEPTION_MESSAGE ->
                        view.setExceptionView((ClientExceptionsNotifierView) networkMessage.getParameters().get(0));
                case SELECT_VIEW_MESSAGE ->
                        view.setView((ViewType) networkMessage.getParameters().get(0));
            }
        }).start();
    }
}
