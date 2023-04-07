package it.polimi.myshelfie.controller;

import it.polimi.myshelfie.GameConfig;
import it.polimi.myshelfie.controller.interfaces.ChatController;
import it.polimi.myshelfie.controller.interfaces.GameController;
import it.polimi.myshelfie.controller.interfaces.LobbyController;
import it.polimi.myshelfie.model.chatting.Message;
import it.polimi.myshelfie.model.interfaces.ChatModel;
import it.polimi.myshelfie.model.interfaces.LobbyModel;
import it.polimi.myshelfie.network.observers.NetworkChatObserver;
import it.polimi.myshelfie.network.observers.NetworkLobbyObserver;

public class LobbyControllerImpl implements LobbyController, ChatController{

    private final LobbyModel lobbyModel;
    private final ChatModel chatModel;
    private final ControllerImpl controllerImpl;

    public LobbyControllerImpl(LobbyModel lobbyModel, ChatModel chatModel, ControllerImpl controllerImpl){
        this.lobbyModel = lobbyModel;
        this.chatModel = chatModel;
        this.controllerImpl=controllerImpl;
    }

    @Override
    public void exitLobby(String playerName) throws Exception {
        lobbyModel.removePlayer(playerName);
        if(lobbyModel.isLobbyEmpty()){
            controllerImpl.deleteLobby(lobbyModel.getLobbyName());
        }
    }

    @Override
    public void setConfig(GameConfig config) throws Exception{
        lobbyModel.setGameConfig(config);
    }

    @Override
    public GameController startGame(String playerName){
        lobbyModel.initialize();
        return controllerImpl.getGameController(lobbyModel.getLobbyName());
    }

    @Override
    public void addLobbyObserver(NetworkLobbyObserver networkLobbyObserver) {
        networkLobbyObserver.setLobby(lobbyModel);
        lobbyModel.addLobbyObserver(networkLobbyObserver);
    }

    @Override
    public void addMessage(Message message){
        chatModel.addMessage(message);
    }

    @Override
    public void addChatObserver(NetworkChatObserver networkChatObserver) {
        networkChatObserver.setChatModel(chatModel);
        chatModel.addChatObserver(networkChatObserver);
    }

    public LobbyModel getLobbyModel(){
        return lobbyModel;
    }
}
