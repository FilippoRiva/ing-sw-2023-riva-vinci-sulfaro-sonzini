package it.polimi.myshelfie.network;

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
        switch (type){
            case MODELMESSAGE ->
                    new Thread(()->view.setModelView((ModelView) networkMessage.getParameters().get(0))).start();
            case CHATMESSAGE ->
                    new Thread(()-> view.setChatView((PlayerChatModelView) networkMessage.getParameters().get(0))).start();
            case LOBBYMESSAGE ->
                    new Thread(()-> view.setLobbyModelView((LobbyModelView) networkMessage.getParameters().get(0))).start();
            case GAMEMESSAGE ->
                    new Thread(()-> view.setGameModelView((FullGameModelView) networkMessage.getParameters().get(0))).start();
            case EXCEPTIONMESSAGE ->
                    new Thread(()-> view.setExceptionView((ClientExceptionsNotifierView) networkMessage.getParameters().get(0))).start();
            case SELECTVIEWMESSAGE ->
                    new Thread(()-> view.setView((ViewType) networkMessage.getParameters().get(0))).start();
        }
    }
}
