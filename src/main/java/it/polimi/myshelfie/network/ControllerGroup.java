package it.polimi.myshelfie.network;

import it.polimi.myshelfie.controller.interfaces.ChatController;
import it.polimi.myshelfie.controller.interfaces.Controller;
import it.polimi.myshelfie.controller.interfaces.GameController;
import it.polimi.myshelfie.controller.interfaces.LobbyController;

public class ControllerGroup {
    private final Client client;
    private final ServerConnectionHandlerImpl serverConnectionHandler;
    private final String username;
    private final Controller controller;
    private final LobbyController lobbyController;
    private final GameController gameController;
    private final ChatController chatController;

    public ControllerGroup(Client client, ServerConnectionHandlerImpl serverConnectionHandler,
                           String username, Controller controller,
                           LobbyController lobbyController, GameController gameController,
                           ChatController chatController) {
        this.client = client;
        this.serverConnectionHandler = serverConnectionHandler;
        this.username = username;
        this.controller = controller;
        this.lobbyController = lobbyController;
        this.gameController = gameController;
        this.chatController = chatController;
    }

    public Client getClient() {
        return client;
    }

    public ServerConnectionHandlerImpl getServerConnectionHandler() {
        return serverConnectionHandler;
    }

    public String getUsername() {
        return username;
    }

    public Controller getController() {
        return controller;
    }

    public LobbyController getLobbyController() {
        return lobbyController;
    }

    public GameController getGameController() {
        return gameController;
    }

    public ChatController getChatController() {
        return chatController;
    }
}
