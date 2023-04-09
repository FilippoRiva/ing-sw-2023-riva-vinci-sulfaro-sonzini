package it.polimi.myshelfie.controller;

import it.polimi.myshelfie.GameConfig;
import it.polimi.myshelfie.controller.interfaces.ChatController;
import it.polimi.myshelfie.controller.interfaces.GameController;
import it.polimi.myshelfie.controller.interfaces.LobbyController;
import it.polimi.myshelfie.model.chatting.Message;
import it.polimi.myshelfie.model.exceptions.InvalidParameterException;
import it.polimi.myshelfie.model.interfaces.ChatModel;
import it.polimi.myshelfie.model.interfaces.LobbyModel;
import it.polimi.myshelfie.network.Client;
import it.polimi.myshelfie.network.observers.NetworkChatObserver;
import it.polimi.myshelfie.network.observers.NetworkGameObserver;
import it.polimi.myshelfie.network.observers.NetworkLobbyObserver;
import it.polimi.myshelfie.view.ViewType;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class LobbyControllerImpl implements LobbyController, ChatController{

    private final LobbyModel lobbyModel;
    private final ChatModel chatModel;
    private final ControllerImpl controllerImpl;
    private final List<Client> clients;

    public LobbyControllerImpl(LobbyModel lobbyModel, ChatModel chatModel, ControllerImpl controllerImpl){
        this.lobbyModel = lobbyModel;
        this.chatModel = chatModel;
        this.controllerImpl = controllerImpl;
        this.clients = new ArrayList<>();
    }

    @Override
    public synchronized void exitLobby(String playerName){
        try {
            lobbyModel.removePlayer(playerName);
        } catch (InvalidParameterException e) {
            //TODO implement
            return;
        }
        if(lobbyModel.isLobbyEmpty()){
            controllerImpl.deleteLobby(lobbyModel.getLobbyName());
        }
    }

    @Override
    public synchronized void setConfig(GameConfig config){
        try {
            lobbyModel.setGameConfig(config);
        } catch (InvalidParameterException e) {
            //TODO notify client
        }
    }

    @Override
    public synchronized GameController startGame(String playerName){
        lobbyModel.initialize();
        GameController result = controllerImpl.getGameController(lobbyModel.getLobbyName());
        if(result!=null) { //sets the correct view type for every client and adds observer to the game
            new Thread(()-> {
                try {
                    for(Client c : clients){
                        c.setViewType(ViewType.GAME);
                        NetworkGameObserver ngo = new NetworkGameObserver(c);
                        ngo.setGameModel(lobbyModel.getGameModel());
                        result.addGameObserver(ngo);
                    }
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }).start();
            result.setClients(clients);
        }
        return result;
    }

    @Override
    public synchronized void addLobbyObserver(NetworkLobbyObserver networkLobbyObserver) {
        networkLobbyObserver.setLobby(lobbyModel);
        lobbyModel.addLobbyObserver(networkLobbyObserver);
    }

    @Override
    public synchronized void addMessage(Message message){
        chatModel.addMessage(message);
    }

    @Override
    public synchronized void addChatObserver(NetworkChatObserver networkChatObserver) {
        networkChatObserver.setChatModel(chatModel);
        chatModel.addChatObserver(networkChatObserver);
    }

    public LobbyModel getLobbyModel(){
        return lobbyModel;
    }

    public void addClient(Client client) {
        clients.add(client);
    }
}
