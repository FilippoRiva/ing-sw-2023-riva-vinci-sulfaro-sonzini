package it.polimi.myshelfie.network;

import it.polimi.myshelfie.controller.interfaces.ChatController;
import it.polimi.myshelfie.controller.interfaces.Controller;
import it.polimi.myshelfie.controller.interfaces.GameController;
import it.polimi.myshelfie.controller.interfaces.LobbyController;
import it.polimi.myshelfie.network.events.*;
import it.polimi.myshelfie.network.observers.NetworkExceptionsObserver;
import it.polimi.myshelfie.network.observers.NetworkLobbyObserver;
import it.polimi.myshelfie.network.observers.NetworkModelObserver;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerConnectionHandlerRMI extends UnicastRemoteObject implements ServerConnectionHandler {
    private final Client client;
    private final Controller controller;
    private LobbyController lobbyController;
    private GameController gameController;
    private ChatController chatController;
    public ServerConnectionHandlerRMI(Client client, Controller controller) throws RemoteException {
        this.client = client;
        this.controller = controller;
    }
    public void start() throws RemoteException {
        client.handshake(this);
        controller.addClient(client,new NetworkModelObserver(client),new NetworkExceptionsObserver(client));
    }

    @Override
    public void send(Object[] args, ViewEvent evt) throws RemoteException {
        new Thread(()->{
            if(evt == ViewEvent.JOIN_REQUEST){
                String playerName = (String) args[0];
                String roomName = (String) args[1];
                lobbyController = controller.joinLobby(client,playerName,roomName);
                if(lobbyController != null)  lobbyController.addLobbyObserver(new NetworkLobbyObserver(client));
            } else if (evt == ViewEvent.CREATE_REQUEST) {
                String playerName = (String) args[0];
                String roomName = (String) args[1];
                lobbyController = controller.createLobby(client,playerName,roomName);
                if(lobbyController != null)  lobbyController.addLobbyObserver(new NetworkLobbyObserver(client));
            }
        }).start();
    }
}
