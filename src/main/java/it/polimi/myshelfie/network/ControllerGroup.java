package it.polimi.myshelfie.network;

import it.polimi.myshelfie.controller.interfaces.ChatController;
import it.polimi.myshelfie.controller.interfaces.Controller;
import it.polimi.myshelfie.controller.interfaces.GameController;
import it.polimi.myshelfie.controller.interfaces.LobbyController;

/**
 * Contains a Client, his Server Connection Handler and all of his controllers.
 * This class is instantiated each time a Network message from the Client to the Server has to be executed.
 */
public class ControllerGroup {
    private final Client client;
    private final ServerConnectionHandlerImpl serverConnectionHandler;
    private final String username;
    private final Controller controller;
    private final LobbyController lobbyController;
    private final GameController gameController;
    private final ChatController chatController;

    /**
     * Constructor.
     *
     * @param client                  the client.
     * @param serverConnectionHandler the server connection handler.
     * @param username                the name of the player.
     * @param controller              the controller.
     * @param lobbyController         the lobby controller.
     * @param gameController          the game controller.
     * @param chatController          the chat controller.
     */
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

    /**
     * @return the client.
     */
    public Client getClient() {
        return client;
    }

    /**
     * @return the server connection handler.
     */
    public ServerConnectionHandlerImpl getServerConnectionHandler() {
        return serverConnectionHandler;
    }

    /**
     * @return the name of the player.
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return the controller.
     */
    public Controller getController() {
        return controller;
    }

    /**
     * @return the lobby controller.
     */
    public LobbyController getLobbyController() {
        return lobbyController;
    }

    /**
     * @return the game controller.
     */
    public GameController getGameController() {
        return gameController;
    }

    /**
     * @return the chat controller.
     */
    public ChatController getChatController() {
        return chatController;
    }
}
