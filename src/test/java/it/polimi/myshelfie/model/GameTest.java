package it.polimi.myshelfie.model;

import it.polimi.myshelfie.GameConfig;
import it.polimi.myshelfie.model.exceptions.FullColumnException;
import it.polimi.myshelfie.model.exceptions.InvalidParameterException;
import it.polimi.myshelfie.model.exceptions.LobbyFullException;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    @Test
    void createGame() throws InvalidParameterException, LobbyFullException {
        Game game = new Game();
        game.setGameConfig(new GameConfig(4));

        assertFalse(game.isLobbyFull());

        game.addPlayer("pippo");

        assertFalse(game.isLobbyFull());

        game.addPlayer("pluto");
        game.addPlayer("jack");
        game.removePlayer("pluto");

        game.addPlayer("john");

        assertFalse(game.isLobbyFull());

        game.addPlayer("lillo");

        assertTrue(game.isLobbyFull());
    }

    @Test
    void play() throws InvalidParameterException, LobbyFullException {
        Game game = new Game();
        game.setGameConfig(new GameConfig(2));
        game.addPlayer("pippo");
        game.addPlayer("pluto");
        game.initialize();
        game.refillLivingRoom();
        //pippo playing
        assertEquals("pippo", game.getTurn());
        List<Position> positions = new ArrayList<>();
        positions.add(new Position(0, 0));
        try {
            game.drawTiles(positions, "pippo");
            fail();
        }catch (InvalidParameterException ignored){}
        positions = new ArrayList<>();
        positions.add(new Position(1, 2));
        positions.add(new Position(2, 1));
        try {
            game.drawTiles(positions, "pippo");
            fail();
        }catch (InvalidParameterException ignored){}

        positions = new ArrayList<>();
        positions.add(new Position(3, 1));
        positions.add(new Position(3, 0));
        try {
            game.drawTiles(positions, "pippo");
        }catch (InvalidParameterException ignored){
            fail();
        }

        try {
            game.orderChosenTiles(game.getPlayer("pippo").getTakenTiles(), "pippo");
        } catch (InvalidParameterException e) {
            throw new RuntimeException(e);
        }
        try {
            game.insertTiles("pippo", 10);
            fail();
        }catch (InvalidParameterException | FullColumnException ignored){}
        try {
            game.insertTiles("pluto", 3);
            fail();
        }catch (InvalidParameterException | FullColumnException ignored){}
        game.checkCommonGoal("pippo");
        assertEquals(0, game.getPlayer("pippo").getPoints());

        game.nextTurn();
        assertEquals("pluto", game.getTurn());
        game.nextTurn();
        assertEquals("pippo", game.getTurn());
    }
}
