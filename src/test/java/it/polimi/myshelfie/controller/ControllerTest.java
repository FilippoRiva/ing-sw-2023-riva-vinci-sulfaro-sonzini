package it.polimi.myshelfie.controller;


import it.polimi.myshelfie.GameConfig;
import it.polimi.myshelfie.controller.interfaces.ChatController;
import it.polimi.myshelfie.controller.interfaces.GameController;
import it.polimi.myshelfie.controller.interfaces.LobbyController;
import it.polimi.myshelfie.model.*;
import it.polimi.myshelfie.model.chatting.Message;
import it.polimi.myshelfie.model.events.ExceptionEvent;
import it.polimi.myshelfie.model.events.GameEvent;
import it.polimi.myshelfie.model.exceptions.InvalidParameterException;
import it.polimi.myshelfie.model.interfaces.Model;
import it.polimi.myshelfie.model.modelviews.*;
import it.polimi.myshelfie.network.Client;
import it.polimi.myshelfie.network.NetworkMessage;
import it.polimi.myshelfie.network.ServerConnectionHandler;
import it.polimi.myshelfie.view.View;
import it.polimi.myshelfie.view.ViewType;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class ControllerTest {


    public Client getClient(View view) {
        return new Client() {
            @Override
            public String getHostName() throws RemoteException {
                return null;
            }

            @Override
            public void handshake(ServerConnectionHandler server) throws RemoteException {
            }

            @Override
            public void send(NetworkMessage networkMessage) throws RemoteException {
                networkMessage.clientExecute(view);
            }

            @Override
            public void lifeline() throws RemoteException {
            }
        };
    }

    public View getView() {
        return new View() {
            public ModelView modelView;
            public LobbyModelView lobbyView;
            public GameModelView gameView;
            public ClientExceptionsNotifierModelView exceptionView;
            public PlayerChatModelView chatView;
            public DrawModelView drawView;
            public InsertModelView insertView;
            public OrganizeModelView organizeView;
            public EndTurnModelView endView;

            public ViewType viewType;

            @Override
            public void setModelView(ModelView modelView) {
                this.modelView = modelView;
            }

            @Override
            public void setExceptionView(ClientExceptionsNotifierModelView exceptionView, ExceptionEvent evt) {
                this.exceptionView = exceptionView;
            }

            @Override
            public void setChatView(PlayerChatModelView chatView) {
                this.chatView = chatView;
            }

            @Override
            public void setLobbyModelView(LobbyModelView lobbyModelView) {
                this.lobbyView = lobbyModelView;
            }

            @Override
            public void setGameModelView(FullGameModelView fullGameModelView) {
                this.gameView = fullGameModelView;
            }

            @Override
            public void updateDrawView(DrawModelView drawModelView) {
                this.drawView = drawModelView;
            }

            @Override
            public void updateOrganizeView(OrganizeModelView organizeModelView) {
                this.organizeView = organizeModelView;
            }

            @Override
            public void updateInsertView(InsertModelView insertModelView) {
                this.insertView = insertModelView;
            }

            @Override
            public void updateTurnView(EndTurnModelView endTurnModelView) {
                this.endView = endTurnModelView;
            }

            @Override
            public void setView(ViewType viewType) {
                this.viewType = viewType;
            }

            @Override
            public void stopUI() {

            }
        };
    }

    public ServerConnectionHandler getConnHand() {
        return new ServerConnectionHandler() {
            GameController gameController = null;

            @Override
            public void send(NetworkMessage networkMessage) throws RemoteException {
            }

            @Override
            public void setChatController(ChatController chatController) throws RemoteException {
            }

            @Override
            public void setLobbyController(LobbyController lobbyController) {
            }

            @Override
            public void lifeline() throws RemoteException {
            }

            @Override
            public void close() throws RemoteException {
            }

            @Override
            public void ping() throws RemoteException {
            }

            @Override
            public GameController getGameController() {
                return this.gameController;
            }

            @Override
            public void setGameController(GameController gameController) throws RemoteException {
                this.gameController = gameController;
            }
        };
    }

    @Test
    void fullControllerTest() {
    }

    @Test
    void notAssignedModelTest() {
        ControllerImpl controller = new ControllerImpl();
        ServerConnectionHandler sch = getConnHand();
        View view1 = getView();
        Client client1 = getClient(view1);
        assertNull(controller.createLobby(client1, sch, "P1", "lobby1"));
    }

    @Test
    void notRecognizedClientTest() {
        ControllerImpl controller = new ControllerImpl();
        Model model = new ModelImpl();
        controller.setModel(model);
        ServerConnectionHandler sch = getConnHand();
        View view1 = getView();
        Client client1 = getClient(view1);
        assertNull(controller.createLobby(client1, sch, "P1", "lobby1"));
        assertNull(controller.joinLobby(client1, sch, "P1", "lobby1"));

    }

    @Test
    void lobbyControllerTest() throws InvalidParameterException, RemoteException {
        ControllerImpl controller = new ControllerImpl();
        LobbyControllerImpl[] lobbyControllers = new LobbyControllerImpl[4];
        Model model = new ModelImpl();
        controller.setModel(model);
        ServerConnectionHandler sch = getConnHand();
        ClientExceptionsNotifierModelView[] exceptionNotifierView = new ClientExceptionsNotifierModelView[4];
        ModelView[] modelViews = new ModelView[4];
        View[] views = new View[4];
        Client[] clients = new Client[4];
        for (int i = 0; i < views.length; i++) {
            views[i] = getView();
            clients[i] = getClient(views[i]);
        }
        controller.addClient(clients[0], (evt, modelView) -> modelViews[0] = modelView, (evt, exceptionView) -> exceptionNotifierView[0] = exceptionView);
        try {
            controller.addClient(clients[0], (x, y) -> {
            }, (x, y) -> {
            });
            fail("Client already added");
        } catch (InvalidParameterException ignored) {
        }
        controller.addClient(clients[1], (evt, modelView) -> modelViews[1] = modelView, (evt, exceptionView) -> exceptionNotifierView[1] = exceptionView);
        controller.addClient(clients[2], (evt, modelView) -> modelViews[2] = modelView, (evt, exceptionView) -> exceptionNotifierView[2] = exceptionView);
        controller.addClient(clients[3], (evt, modelView) -> modelViews[3] = modelView, (evt, exceptionView) -> exceptionNotifierView[3] = exceptionView);


        //createLobby
        lobbyControllers[0] = (LobbyControllerImpl) controller.createLobby(clients[0], sch, "Player1", "lobby1");
        lobbyControllers[0].addLobbyObserver("Player1", ((evt, lobbyModelView) -> {
        }));
        assertNotNull(lobbyControllers[0]); //create a lobby
        assertNull(controller.createLobby(clients[0], sch, "Player", "lobby"));
        assertNull(controller.createLobby(clients[1], sch, "#player", "lobby2"));
        assertNull(controller.createLobby(clients[1], sch, "my player", "lobby2"));
        assertNull(controller.createLobby(clients[1], sch, "my player", "lobby2"));
        assertNull(controller.createLobby(clients[1], sch, "", "lobby2"));
        assertNull(controller.createLobby(clients[1], sch, "themostbeatifulnameever", "lobby2"));
        assertNull(controller.createLobby(clients[1], sch, "myplayer", ""));
        assertNull(controller.createLobby(clients[1], sch, "Player1", "lobby1"));
        lobbyControllers[2] = (LobbyControllerImpl) controller.createLobby(clients[2], sch, "Player3", "lobby2");
        assertNotNull(lobbyControllers[2]);
        //joinlobby
        assertNull(controller.joinLobby(clients[0], sch, "Player1", "lobby2"));
        assertNull(controller.joinLobby(clients[1], sch, "#player", "lobby2"));
        assertNull(controller.joinLobby(clients[1], sch, "my player", "lobby2"));
        assertNull(controller.joinLobby(clients[1], sch, "my player", "lobby2"));
        assertNull(controller.joinLobby(clients[1], sch, "", "lobby2"));
        assertNull(controller.joinLobby(clients[1], sch, "themostbeatifulnameever", "lobby2"));
        assertNull(controller.joinLobby(clients[1], sch, "Player2", "lobby7"));
        lobbyControllers[1] = (LobbyControllerImpl) controller.joinLobby(clients[1], sch, "Player2", "lobby1");
        assertNotNull(lobbyControllers[1]);
        lobbyControllers[1].setConfig("Player2", new GameConfig(4));
        assertEquals("You're not the room owner", exceptionNotifierView[1].getLastException().getMessage());
        assertNull(controller.joinLobby(clients[3], sch, "Player4", "lobby1"));
        lobbyControllers[3] = (LobbyControllerImpl) controller.joinLobby(clients[3], sch, "Player4", "lobby2");
        assertNotNull(lobbyControllers[3]);
        //exitLobby
        lobbyControllers[1].exitLobby("Player2");
        lobbyControllers[3].exitLobby("Player4");
        GameConfig gameconfig = new GameConfig(3);
        lobbyControllers[0].setConfig("Player1", gameconfig);
        lobbyControllers[1] = (LobbyControllerImpl) controller.joinLobby(clients[1], sch, "Player2", "lobby1");
        lobbyControllers[3] = (LobbyControllerImpl) controller.joinLobby(clients[3], sch, "Player4", "lobby1");
        assertNotNull(lobbyControllers[1]);
        assertNotNull(lobbyControllers[3]);
        lobbyControllers[0].setConfig("Player1", new GameConfig(2));
        assertEquals("too few players", exceptionNotifierView[0].getLastException().getMessage());
        lobbyControllers[3].exitLobby("Player4");
        lobbyControllers[0].startGame("Player1");
        assertNull(sch.getGameController());
        gameconfig = new GameConfig(2);
        lobbyControllers[0].setConfig("Player1", gameconfig);
        lobbyControllers[1].startGame("Player2");
        assertNull(sch.getGameController());
        lobbyControllers[0].startGame("Player1");
        lobbyControllers[0].startGame("Player1");
        assertEquals("lobby closed", exceptionNotifierView[0].getLastException().getMessage());
        lobbyControllers[0].addLobbyObserver("Player1", ((evt, lobbyModelView) -> {
        }));
        assertEquals("lobby closed", exceptionNotifierView[0].getLastException().getMessage());
        GameControllerImpl gameController = (GameControllerImpl) sch.getGameController();
        assertNotNull(gameController);
        lobbyControllers[0].setConfig("Player1", new GameConfig(3));
        assertEquals("Lobby closed", exceptionNotifierView[0].getLastException().getMessage());
        lobbyControllers[2].exitLobby("Player3");
        lobbyControllers[2] = (LobbyControllerImpl) controller.createLobby(clients[2], sch, "Player3", "newLobby");
        lobbyControllers[3] = (LobbyControllerImpl) controller.joinLobby(clients[3], sch, "Player4", "newLobby");
        lobbyControllers[3].exitLobby("Player4");
        lobbyControllers[2].exitLobby("Player3");
    }

    @Test
    void gameControllerTest() throws InvalidParameterException, RemoteException {
        ControllerImpl controller = new ControllerImpl();
        LobbyControllerImpl[] lobbyControllers = new LobbyControllerImpl[4];
        ModelImpl model = new ModelImpl();
        controller.setModel(model);
        ServerConnectionHandler sch = getConnHand();
        View[] views = new View[2];
        Client[] clients = new Client[2];
        for (int i = 0; i < views.length; i++) {
            views[i] = getView();
            clients[i] = getClient(views[i]);
        }
        ClientExceptionsNotifierModelView[] exceptionNotifierView = new ClientExceptionsNotifierModelView[2];
        ModelView[] modelViews = new ModelView[2];
        controller.addClient(clients[0], (evt, modelView) -> modelViews[0] = modelView, (evt, exceptionView) -> exceptionNotifierView[0] = exceptionView);
        controller.addClient(clients[1], (evt, modelView) -> modelViews[1] = modelView, (evt, exceptionView) -> exceptionNotifierView[1] = exceptionView);

        lobbyControllers[0] = (LobbyControllerImpl) controller.createLobby(clients[0], sch, "Player1", "lobby1");
        lobbyControllers[1] = (LobbyControllerImpl) controller.joinLobby(clients[1], sch, "Player2", "lobby1");
        FullGameModelView[] fullGameModelView = new FullGameModelView[1];
        lobbyControllers[0].startGame("Player1");
        lobbyControllers[0].exitLobby("Player1");
        assertEquals("lobby closed", exceptionNotifierView[0].getLastException().getMessage());
        GameControllerImpl gameController = (GameControllerImpl) sch.getGameController();
        gameController.addGameObserver("Player1", ((evt, gameModelView) -> {
            if (evt == null) evt = GameEvent.GAME_START;
            switch (evt) {
                case GAME_START, WIN -> fullGameModelView[0] = (FullGameModelView) gameModelView;
                case DRAW -> fullGameModelView[0].applyChanges((DrawModelView) gameModelView);
                case ORGANIZE -> fullGameModelView[0].applyChanges((OrganizeModelView) gameModelView);
                case INSERT -> fullGameModelView[0].applyChanges((InsertModelView) gameModelView);
                case COUNTING_POINTS -> fullGameModelView[0].applyChanges((EndTurnModelView) gameModelView);
            }
        }));
        assertNotNull(gameController);
        assertEquals("lobby closed", exceptionNotifierView[0].getLastException().getMessage());
        List<Position> pos = new ArrayList<>();
        List<ItemTile> tiles;
        if (fullGameModelView[0].getCurrentPlayerName().equals("Player1")) {
            pos.add(new Position(0, 4));
            gameController.drawTiles(pos, "Player1");
            assertEquals("positions are not valid", exceptionNotifierView[0].getLastException().getMessage());
            pos = new ArrayList<>();
            pos.add(new Position(0, 3));
            gameController.drawTiles(pos, "Player2");
            assertEquals("player playing in the wrong turn", exceptionNotifierView[1].getLastException().getMessage());
            pos = new ArrayList<>();
            pos.add(new Position(0, 3));
            gameController.drawTiles(pos, "Player1");
            assertEquals("positions are not valid", exceptionNotifierView[0].getLastException().getMessage());

            tiles = new ArrayList<>();
            tiles.add(new ItemTile(ItemTileType.CAT, 0));
            tiles.add(new ItemTile(ItemTileType.CAT, 0));
            gameController.orderChosenTiles(tiles, "Player2");
            assertEquals("player playing in the wrong turn", exceptionNotifierView[1].getLastException().getMessage());
            tiles = new ArrayList<>(fullGameModelView[0].getPlayer(0).getTakenTiles());
            gameController.orderChosenTiles(tiles, "Player1");
            assertEquals("positions are not valid", exceptionNotifierView[0].getLastException().getMessage());
            gameController.insertTiles("Player2", 3);
            assertEquals("player playing in the wrong turn", exceptionNotifierView[1].getLastException().getMessage());
            gameController.insertTiles("Player1", 0);
            assertEquals("positions are not valid", exceptionNotifierView[0].getLastException().getMessage());
        } else {
            pos.add(new Position(0, 4));
            gameController.drawTiles(pos, "Player2");
            assertEquals("positions are not valid", exceptionNotifierView[1].getLastException().getMessage());
            pos = new ArrayList<>();
            pos.add(new Position(0, 3));
            gameController.drawTiles(pos, "Player1");
            assertEquals("player playing in the wrong turn", exceptionNotifierView[0].getLastException().getMessage());
            pos = new ArrayList<>();
            pos.add(new Position(0, 3));
            gameController.drawTiles(pos, "Player2");
            assertEquals("positions are not valid", exceptionNotifierView[1].getLastException().getMessage());

            tiles = new ArrayList<>();
            tiles.add(new ItemTile(ItemTileType.CAT, 0));
            tiles.add(new ItemTile(ItemTileType.CAT, 0));
            gameController.orderChosenTiles(tiles, "Player1");
            assertEquals("player playing in the wrong turn", exceptionNotifierView[0].getLastException().getMessage());
            tiles = new ArrayList<>(fullGameModelView[0].getCurrentPlayer().getTakenTiles());
            gameController.orderChosenTiles(tiles, "Player2");
            assertEquals("positions are not valid", exceptionNotifierView[1].getLastException().getMessage());

            gameController.insertTiles("Player1", 3);
            assertEquals("player playing in the wrong turn", exceptionNotifierView[0].getLastException().getMessage());
            gameController.insertTiles("Player2", 0);
            assertEquals("positions are not valid", exceptionNotifierView[1].getLastException().getMessage());
        }
        fillBookshelf(fullGameModelView[0], gameController);
        assertEquals(GameState.WIN, fullGameModelView[0].getCurrentState());
        gameController.leaveEndGame("Player1", true);
        gameController.disconnectFromGame("Player7");
        gameController.leaveEndGame("Player2", false);

        lobbyControllers[0] = (LobbyControllerImpl) controller.createLobby(clients[0], sch, "Player1", "lobby");
        lobbyControllers[1] = (LobbyControllerImpl) controller.joinLobby(clients[1], sch, "Player2", "lobby");
        lobbyControllers[0].startGame("Player1");
        gameController = (GameControllerImpl) sch.getGameController();
        gameController.disconnectFromGame("Player1");
    }

    private void fillBookshelf(FullGameModelView gmv, GameController gc) {
        List<Position> pos = new ArrayList<>();
        List<ItemTile> tiles;

        pos.add(new Position(-1, 3));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        gc.insertTiles(gmv.getCurrentPlayerName(), 0);

        pos = new ArrayList<>();
        pos.add(new Position(-1, 2));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        gc.insertTiles(gmv.getCurrentPlayerName(), 0);

        pos = new ArrayList<>();
        pos.add(new Position(0, 2));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        gc.insertTiles(gmv.getCurrentPlayerName(), 0);

        pos = new ArrayList<>();
        pos.add(new Position(1, 2));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        gc.insertTiles(gmv.getCurrentPlayerName(), 0);

        pos = new ArrayList<>();
        pos.add(new Position(-2, 1));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        gc.insertTiles(gmv.getCurrentPlayerName(), 0);

        pos = new ArrayList<>();
        pos.add(new Position(-1, 1));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        gc.insertTiles(gmv.getCurrentPlayerName(), 0);

        pos = new ArrayList<>();
        pos.add(new Position(0, 1));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        gc.insertTiles(gmv.getCurrentPlayerName(), 0);

        pos = new ArrayList<>();
        pos.add(new Position(1, 1));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        gc.insertTiles(gmv.getCurrentPlayerName(), 0);

        pos = new ArrayList<>();
        pos.add(new Position(2, 1));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        gc.insertTiles(gmv.getCurrentPlayerName(), 0);

        pos = new ArrayList<>();
        pos.add(new Position(3, 1));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        gc.insertTiles(gmv.getCurrentPlayerName(), 0);

        pos = new ArrayList<>();
        pos.add(new Position(-3, 0));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        gc.insertTiles(gmv.getCurrentPlayerName(), 0);

        pos = new ArrayList<>();
        pos.add(new Position(-2, 0));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        gc.insertTiles(gmv.getCurrentPlayerName(), 1);

        pos = new ArrayList<>();
        pos.add(new Position(-1, 0));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        gc.insertTiles(gmv.getCurrentPlayerName(), 1);

        pos = new ArrayList<>();
        pos.add(new Position(0, 0));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        gc.insertTiles(gmv.getCurrentPlayerName(), 1);

        pos = new ArrayList<>();
        pos.add(new Position(1, 0));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        gc.insertTiles(gmv.getCurrentPlayerName(), 1);

        pos = new ArrayList<>();
        pos.add(new Position(2, 0));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        gc.insertTiles(gmv.getCurrentPlayerName(), 1);

        pos = new ArrayList<>();
        pos.add(new Position(3, 0));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        gc.insertTiles(gmv.getCurrentPlayerName(), 1);

        pos = new ArrayList<>();
        pos.add(new Position(-3, -1));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        gc.insertTiles(gmv.getCurrentPlayerName(), 1);

        pos = new ArrayList<>();
        pos.add(new Position(-2, -1));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        gc.insertTiles(gmv.getCurrentPlayerName(), 1);

        pos = new ArrayList<>();
        pos.add(new Position(-1, -1));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        gc.insertTiles(gmv.getCurrentPlayerName(), 1);

        pos = new ArrayList<>();
        pos.add(new Position(0, -1));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        gc.insertTiles(gmv.getCurrentPlayerName(), 1);

        pos = new ArrayList<>();
        pos.add(new Position(1, -1));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        gc.insertTiles(gmv.getCurrentPlayerName(), 1);

        pos = new ArrayList<>();
        pos.add(new Position(2, -1));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        gc.insertTiles(gmv.getCurrentPlayerName(), 1);

        pos = new ArrayList<>();
        pos.add(new Position(-1, -2));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        gc.insertTiles(gmv.getCurrentPlayerName(), 2);

        pos = new ArrayList<>();
        pos.add(new Position(0, -2));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        gc.insertTiles(gmv.getCurrentPlayerName(), 2);

        pos = new ArrayList<>();
        pos.add(new Position(1, -2));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        gc.insertTiles(gmv.getCurrentPlayerName(), 2);

        pos = new ArrayList<>();
        pos.add(new Position(0, -3));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        gc.insertTiles(gmv.getCurrentPlayerName(), 2);


        pos = new ArrayList<>();
        pos.add(new Position(0, 3));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        gc.insertTiles(gmv.getCurrentPlayerName(), 2);

        pos = new ArrayList<>();
        pos.add(new Position(-1, 3));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        gc.insertTiles(gmv.getCurrentPlayerName(), 2);

        pos = new ArrayList<>();
        pos.add(new Position(-1, 2));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        gc.insertTiles(gmv.getCurrentPlayerName(), 2);

        pos = new ArrayList<>();
        pos.add(new Position(0, 2));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        gc.insertTiles(gmv.getCurrentPlayerName(), 2);

        pos = new ArrayList<>();
        pos.add(new Position(1, 2));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        gc.insertTiles(gmv.getCurrentPlayerName(), 2);

        pos = new ArrayList<>();
        pos.add(new Position(-2, 1));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        gc.insertTiles(gmv.getCurrentPlayerName(), 2);

        pos = new ArrayList<>();
        pos.add(new Position(-1, 1));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        gc.insertTiles(gmv.getCurrentPlayerName(), 2);

        pos = new ArrayList<>();
        pos.add(new Position(0, 1));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        gc.insertTiles(gmv.getCurrentPlayerName(), 2);

        pos = new ArrayList<>();
        pos.add(new Position(1, 1));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        gc.insertTiles(gmv.getCurrentPlayerName(), 3);

        pos = new ArrayList<>();
        pos.add(new Position(2, 1));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        gc.insertTiles(gmv.getCurrentPlayerName(), 3);

        pos = new ArrayList<>();
        pos.add(new Position(3, 1));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        gc.insertTiles(gmv.getCurrentPlayerName(), 3);

        pos = new ArrayList<>();
        pos.add(new Position(-3, 0));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        gc.insertTiles(gmv.getCurrentPlayerName(), 3);

        pos = new ArrayList<>();
        pos.add(new Position(-2, 0));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        gc.insertTiles(gmv.getCurrentPlayerName(), 3);

        pos = new ArrayList<>();
        pos.add(new Position(-1, 0));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        gc.insertTiles(gmv.getCurrentPlayerName(), 3);

        pos = new ArrayList<>();
        pos.add(new Position(0, 0));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        gc.insertTiles(gmv.getCurrentPlayerName(), 3);

        pos = new ArrayList<>();
        pos.add(new Position(1, 0));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        gc.insertTiles(gmv.getCurrentPlayerName(), 3);

        pos = new ArrayList<>();
        pos.add(new Position(2, 0));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        gc.insertTiles(gmv.getCurrentPlayerName(), 3);

        pos = new ArrayList<>();
        pos.add(new Position(3, 0));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        gc.insertTiles(gmv.getCurrentPlayerName(), 3);

        pos = new ArrayList<>();
        pos.add(new Position(-3, -1));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        gc.insertTiles(gmv.getCurrentPlayerName(), 3);

        pos = new ArrayList<>();
        pos.add(new Position(-2, -1));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        gc.insertTiles(gmv.getCurrentPlayerName(), 3);

        pos = new ArrayList<>();
        pos.add(new Position(-1, -1));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        gc.insertTiles(gmv.getCurrentPlayerName(), 4);

        pos = new ArrayList<>();
        pos.add(new Position(0, -1));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        gc.insertTiles(gmv.getCurrentPlayerName(), 4);

        pos = new ArrayList<>();
        pos.add(new Position(1, -1));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        gc.insertTiles(gmv.getCurrentPlayerName(), 4);

        pos = new ArrayList<>();
        pos.add(new Position(2, -1));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        gc.insertTiles(gmv.getCurrentPlayerName(), 4);

        pos = new ArrayList<>();
        pos.add(new Position(-1, -2));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        gc.insertTiles(gmv.getCurrentPlayerName(), 4);

        pos = new ArrayList<>();
        pos.add(new Position(0, -2));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        gc.insertTiles(gmv.getCurrentPlayerName(), 4);

        pos = new ArrayList<>();
        pos.add(new Position(1, -2));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        gc.insertTiles(gmv.getCurrentPlayerName(), 4);

        pos = new ArrayList<>();
        pos.add(new Position(0, -3));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        gc.insertTiles(gmv.getCurrentPlayerName(), 4);


        pos = new ArrayList<>();
        pos.add(new Position(0, 3));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        gc.insertTiles(gmv.getCurrentPlayerName(), 4);

        pos = new ArrayList<>();
        pos.add(new Position(-1, 3));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        gc.insertTiles(gmv.getCurrentPlayerName(), 4);

        pos = new ArrayList<>();
        pos.add(new Position(-1, 3));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        gc.insertTiles(gmv.getCurrentPlayerName(), 4);

        pos = new ArrayList<>();
        pos.add(new Position(-1, 2));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        gc.insertTiles(gmv.getCurrentPlayerName(), 4);
        assertEquals(GameState.LAST_DRAW, gmv.getCurrentState());


        pos = new ArrayList<>();
        pos.add(new Position(0, 2));
        gc.drawTiles(pos, gmv.getCurrentPlayerName());
        assertEquals(GameState.LAST_ORGANIZE, gmv.getCurrentState());
        tiles = new ArrayList<>(gmv.getCurrentPlayer().getTakenTiles());
        gc.orderChosenTiles(tiles, gmv.getCurrentPlayerName());
        assertEquals(GameState.LAST_INSERT, gmv.getCurrentState());
        gc.insertTiles(gmv.getCurrentPlayerName(), 4);
        assertEquals(GameState.LAST_COUNTING_POINTS, gmv.getCurrentState());
    }

    @Test
    void chatControllerTest() throws InvalidParameterException, RemoteException {
        ControllerImpl controller = new ControllerImpl();
        LobbyControllerImpl[] lobbyControllers = new LobbyControllerImpl[4];
        Model model = new ModelImpl();
        controller.setModel(model);
        ServerConnectionHandler sch = getConnHand();
        ClientExceptionsNotifierModelView[] exceptionNotifierView = new ClientExceptionsNotifierModelView[4];
        ModelView[] modelViews = new ModelView[4];
        View[] views = new View[4];
        Client[] clients = new Client[4];
        for (int i = 0; i < views.length; i++) {
            views[i] = getView();
            clients[i] = getClient(views[i]);
        }
        controller.addClient(clients[0], (evt, modelView) -> modelViews[0] = modelView, (evt, exceptionView) -> exceptionNotifierView[0] = exceptionView);
        controller.addClient(clients[1], (evt, modelView) -> modelViews[1] = modelView, (evt, exceptionView) -> exceptionNotifierView[1] = exceptionView);
        controller.addClient(clients[2], (evt, modelView) -> modelViews[2] = modelView, (evt, exceptionView) -> exceptionNotifierView[2] = exceptionView);
        controller.addClient(clients[3], (evt, modelView) -> modelViews[3] = modelView, (evt, exceptionView) -> exceptionNotifierView[3] = exceptionView);


        lobbyControllers[0] = (LobbyControllerImpl) controller.createLobby(clients[0], sch, "p1", "lobby");
        lobbyControllers[0].setConfig("p1", new GameConfig(4));
        lobbyControllers[1] = (LobbyControllerImpl) controller.joinLobby(clients[1], sch, "p2", "lobby");
        lobbyControllers[2] = (LobbyControllerImpl) controller.joinLobby(clients[2], sch, "p3", "lobby");
        lobbyControllers[3] = (LobbyControllerImpl) controller.joinLobby(clients[3], sch, "p4", "lobby");

        lobbyControllers[0].addChatObserver("p1", (evt, playerChatModelView) -> {
        });
        lobbyControllers[1].addChatObserver("p2", (evt, playerChatModelView) -> {
        });
        lobbyControllers[2].addChatObserver("p3", (evt, playerChatModelView) -> {
        });
        lobbyControllers[3].addChatObserver("p4", (evt, playerChatModelView) -> {
        });

        lobbyControllers[1].addMessage("p2", new Message("p2", "p3", "ciao"));

        lobbyControllers[0].startGame("p1");
        lobbyControllers[0].addChatObserver("p1", (evt, playerChatModelView) -> {
        });
        assertEquals("lobby closed", exceptionNotifierView[0].getLastException().getMessage());
        GameControllerImpl gameController = (GameControllerImpl) sch.getGameController();
        gameController.addChatObserver("p1", (evt, playerChatModelView) -> {
        });
        gameController.addMessage("p1", new Message("p1", "everyone", "ciao a tutti"));
        controller.quit(clients[1]);
    }
}