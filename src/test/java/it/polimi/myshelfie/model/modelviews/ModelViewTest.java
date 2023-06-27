package it.polimi.myshelfie.model.modelviews;

import it.polimi.myshelfie.model.*;
import it.polimi.myshelfie.model.events.GameEvent;
import it.polimi.myshelfie.model.exceptions.FullColumnException;
import it.polimi.myshelfie.model.exceptions.InvalidParameterException;
import it.polimi.myshelfie.model.exceptions.LobbyExistenceException;
import it.polimi.myshelfie.model.exceptions.LobbyFullException;
import it.polimi.myshelfie.model.goals.PersonalGoalDefaultsTest;
import it.polimi.myshelfie.model.interfaces.GameModel;
import it.polimi.myshelfie.model.interfaces.LobbyModel;
import it.polimi.myshelfie.utils.Observer;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ModelViewTest {

    @Test
    void modelViewsTest() throws LobbyExistenceException, InvalidParameterException, LobbyFullException {
        //Model View
        ModelImpl model = new ModelImpl();
        ModelView modelView = new ModelView(model);
        assertEquals(0, modelView.getLobbies().size());
        LobbyModel lobby = model.addLobby("nuova lobby");
        lobby.addPlayer("gigi");
        lobby.addPlayer("franco");
        modelView = model.getModelView();
        assertEquals(1, modelView.getLobbies().size());
        assertEquals(2, modelView.getCurrentSize("nuova lobby"));
        model.addLobby("altra lobby");
        modelView = new ModelView(model);
        assertEquals(2, modelView.getLobbies().size());
        assertEquals(2, modelView.getMaxSize("nuova lobby"));
        Map<String, Integer> maxSizeMap = modelView.getMaxSizeMap();
        assertEquals(2, maxSizeMap.get("nuova lobby"));
        assertEquals(2, maxSizeMap.get("altra lobby"));
        Map<String, Integer> currentSizeMap = modelView.getCurrentSizeMap();
        assertEquals(2, currentSizeMap.get("nuova lobby"));
        assertEquals(0, currentSizeMap.get("altra lobby"));
        assertEquals(2, modelView.getCurrentSize("nuova lobby"));

        //LobbyModelView
        LobbyModelView lobbyModelView = lobby.getLobbyModelView();
        assertEquals(2, lobbyModelView.getGameConfig().getNumPlayers());
        assertEquals("nuova lobby", lobbyModelView.getName());
        assertEquals("gigi", lobbyModelView.getRoomOwner());
        assertEquals(2, lobbyModelView.getPlayers().size());
        assertEquals("gigi", lobbyModelView.getPlayers().get(0));
        assertEquals("franco", lobbyModelView.getPlayers().get(1));
        assertTrue(lobbyModelView.isRoomOwner("gigi"));
    }

    @Test
    void gameModelViewsTest() throws LobbyExistenceException, InvalidParameterException, LobbyFullException, FullColumnException {
        FullGameModelView[] fullGameModelViews = new FullGameModelView[1];
        DrawModelView[] drawModelViews = new DrawModelView[1];
        OrganizeModelView[] organizeModelViews = new OrganizeModelView[1];
        InsertModelView[] insertModelViews = new InsertModelView[1];
        EndTurnModelView[] endTurnModelViews = new EndTurnModelView[1];
        GameEvent[] events = new GameEvent[1];
        ModelImpl model = new ModelImpl();
        LobbyModel lobby = model.addLobby("lobby");
        lobby.addPlayer("gigi");
        lobby.addPlayer("franco");
        lobby.initialize();
        GameModel game = lobby.getGameModel();
        Observer<GameEvent, GameModelView> obs = (evt, gameModelView) -> {
            events[0] = evt;
            if (evt == null) {
                fullGameModelViews[0] = (FullGameModelView) gameModelView;
                return;
            }
            switch (evt) {
                case GAME_START, WIN -> fullGameModelViews[0] = (FullGameModelView) gameModelView;
                case DRAW -> drawModelViews[0] = (DrawModelView) gameModelView;
                case ORGANIZE -> organizeModelViews[0] = (OrganizeModelView) gameModelView;
                case INSERT -> insertModelViews[0] = (InsertModelView) gameModelView;
                case COUNTING_POINTS -> endTurnModelViews[0] = (EndTurnModelView) gameModelView;
            }
        };
        game.addGameObserver(obs);
        game.setState(GameState.DRAW);
        FullGameModelView gameModelView = game.getGameModelView();
        gameModelView = new FullGameModelView(gameModelView);
        List<String> players = gameModelView.getPlayers().stream().map(PlayerModelView::getName).toList();
        assertEquals(gameModelView.getCurrentState(), GameState.DRAW);
        assertEquals(gameModelView.getPlayer(0).getName(), players.get(0));
        assertEquals(gameModelView.getCurrentPlayer().getName(), players.get(0));
        assertEquals(gameModelView.getFirstPlayer(), gameModelView.getCurrentPlayer());
        assertEquals("lobby", gameModelView.getRoomName());
        ItemTile[][] livingRoom = gameModelView.getLivingRoom();
        for (int i = 0; i < livingRoom.length; i++) {
            for (int j = 0; j < livingRoom[0].length; j++) {
                Position relPos = new Position(i, j).centered(gameModelView.getGameConfig().getLivingRoomSize());
                if (livingRoom[i][j] == null) {
                    assertNull(gameModelView.getTile(relPos.getX(), relPos.getY()));
                } else {
                    assertEquals(livingRoom[i][j], gameModelView.getTile(relPos.getX(), relPos.getY()));
                }
            }
        }
        assertEquals(gameModelView.getPlayer(players.get(0)), gameModelView.getCurrentPlayer());
        assertEquals(103, gameModelView.getRemainingTiles());
        assertNotNull(gameModelView.getCurrentPlayer().getPersonalGoals().get(0).getGoal());
        assertNotEquals(-1, gameModelView.getCurrentPlayer().getPersonalGoals().get(0).getId());
        assertEquals(2, gameModelView.getCommonGoals().get(0).getTokens().size());
        assertNotNull(gameModelView.getCommonGoals().get(0).getDescription());
        assertEquals(2, gameModelView.getCommonGoals().get(1).getTokens().size());
        assertNotNull(gameModelView.getCommonGoals().get(1).getDescription());

        List<PlayerModelView> playerModelViews = fullGameModelViews[0].getPlayers();
        for (PlayerModelView p : playerModelViews) {
            assertEquals(0, p.getPoints());
            assertEquals(0, p.getTakenTiles().size());
            assertEquals(0, p.getTokens().size());
            ItemTile[][] bookshelf = p.getBookshelf();
            for (ItemTile[] itemTiles : bookshelf) {
                for (int j = 0; j < bookshelf[0].length; j++) {
                    assertEquals(itemTiles[j].getType(), ItemTileType.EMPTY);
                }
            }
        }

        FullGameModelView copy = new FullGameModelView(fullGameModelViews[0]);


        //draw
        String currentPlayer = gameModelView.getCurrentPlayer().getName();
        ArrayList<Position> positions = new ArrayList<>();
        positions.add(new Position(-1, 3));
        positions.add(new Position(0, 3));
        game.drawTiles(positions, currentPlayer);
        game.setState(GameState.ORGANIZE);
        assertNotNull(drawModelViews[0]);

        fullGameModelViews[0].applyChanges(drawModelViews[0]);

        assertEquals(GameState.ORGANIZE, drawModelViews[0].getCurrentState());
        assertEquals(2, drawModelViews[0].getTakenTiles().size());
        assertEquals(currentPlayer, drawModelViews[0].getCurrentPlayer());
        assertEquals(fullGameModelViews[0].getTile(-1, 3).getType(), ItemTileType.EMPTY);
        assertEquals(fullGameModelViews[0].getTile(0, 3).getType(), ItemTileType.EMPTY);

        copy.diff(fullGameModelViews[0]);

        assertEquals(2, copy.getPlayer(currentPlayer).getTakenTiles().size());

        //organize
        List<ItemTile> tiles = fullGameModelViews[0].getCurrentPlayer().getTakenTiles();
        Collections.shuffle(tiles);
        game.orderChosenTiles(tiles, currentPlayer);
        game.setState(GameState.INSERT);
        assertNotNull(organizeModelViews[0]);
        assertEquals(currentPlayer, organizeModelViews[0].getCurrentPlayer());
        assertEquals(tiles, organizeModelViews[0].getOrderedTiles());
        assertEquals(GameState.INSERT, organizeModelViews[0].getCurrentState());

        copy = new FullGameModelView(fullGameModelViews[0]);

        fullGameModelViews[0].applyChanges(organizeModelViews[0]);

        assertEquals(fullGameModelViews[0].getCurrentPlayer().getTakenTiles(), tiles);

        copy.diff(fullGameModelViews[0]);

        assertEquals(0, copy.getPlayer(currentPlayer).getTakenTiles().size());

        //insert
        game.insertTiles(currentPlayer, 1);
        game.setState(GameState.COUNTING_POINTS);
        assertNotNull(insertModelViews[0]);

        InsertModelView i = insertModelViews[0];
        assertEquals(currentPlayer, i.getCurrentPlayer());
        assertEquals(GameState.COUNTING_POINTS, i.getCurrentState());
        ItemTile[][] bookshelf = i.getBookshelf();
        assertEquals(tiles.get(0), bookshelf[5][1]);
        assertEquals(tiles.get(1), bookshelf[4][1]);
        for (ItemTile[] itemTiles : bookshelf) {
            for (int j = 0; j < bookshelf[0].length; j++) {
                ItemTile tile = itemTiles[j];
                assertTrue(tile.getType() == ItemTileType.EMPTY || tile.equals(tiles.get(0)) || tile.equals(tiles.get(1)));
            }
        }

        copy = new FullGameModelView(fullGameModelViews[0]);

        fullGameModelViews[0].applyChanges(insertModelViews[0]);

        copy.diff(fullGameModelViews[0]);

        game.checkCommonGoal(currentPlayer);
        game.refillLivingRoom();
        game.nextTurn();
        game.setState(GameState.DRAW);

        assertNotNull(endTurnModelViews[0]);
        EndTurnModelView e = endTurnModelViews[0];
        assertEquals(currentPlayer, e.getLastPlayer());
        assertEquals(players.get(1), e.getCurrentPlayer());
        assertEquals(103, e.getRemainingTiles());
        livingRoom = fullGameModelViews[0].getLivingRoom();
        assertTrue(PersonalGoalDefaultsTest.matrixEqual(livingRoom, e.getLivingRoom()));
        assertEquals(e.getCommonGoals().get(0).getId(), fullGameModelViews[0].getCommonGoals().get(0).getId());
        assertEquals(e.getCommonGoals().get(1).getId(), fullGameModelViews[0].getCommonGoals().get(1).getId());
        assertEquals(GameState.DRAW, e.getCurrentState());
        assertEquals(0, e.getPlayerTokens().size());

        copy = new FullGameModelView(fullGameModelViews[0]);

        fullGameModelViews[0].applyChanges(e);

        copy.diff(fullGameModelViews[0]);

    }

}