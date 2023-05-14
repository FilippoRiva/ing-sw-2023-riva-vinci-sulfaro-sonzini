package it.polimi.myshelfie.model;

import it.polimi.myshelfie.GameConfig;
import it.polimi.myshelfie.model.exceptions.FullColumnException;
import it.polimi.myshelfie.model.exceptions.InvalidParameterException;
import it.polimi.myshelfie.model.exceptions.LobbyFullException;
import it.polimi.myshelfie.model.interfaces.GameModel;
import it.polimi.myshelfie.model.interfaces.LobbyModel;
import it.polimi.myshelfie.model.interfaces.Model;
import it.polimi.myshelfie.model.modelviews.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FullModelTest {
    Model model = new ModelImpl();
    ModelView modelView1 = null;
    ModelView modelView2 = null;
    private void setModelView1(ModelView mv1){
        modelView1 = mv1;
    }
    private void setModelView2(ModelView mv2){
        modelView2 = mv2;
    }
    @Test
    public void fullModelTest() throws InterruptedException {
        LobbyModel lobby1 = null;
        LobbyModel lobby2 = null;
        try{
            model.addModelObserver((evt,x)-> setModelView1(model.getModelView()));
            lobby1 = model.addLobby("Lobby1");

        }catch (Exception e){
            fail();
        }
        try{
            model.addLobby("Lobby1");
            fail();
        }catch (Exception ignored){
        }
        try{
            model.addModelObserver((evt,x)->setModelView2(model.getModelView()));
            lobby2 = model.addLobby("Lobby2");
        }catch (Exception e){
            fail();
        }

        assertEquals("Lobby1",modelView1.getLobbies().get(0));
        assertEquals("Lobby2",modelView1.getLobbies().get(1));
        assertEquals("Lobby1",modelView2.getLobbies().get(0));
        assertEquals("Lobby2",modelView2.getLobbies().get(1));

        try{
            model.addLobby("Lobby3");
        } catch (Exception e) {
            fail();
        }

        assertEquals("Lobby1",modelView1.getLobbies().get(0));
        assertEquals("Lobby2",modelView1.getLobbies().get(1));
        assertEquals("Lobby3",modelView1.getLobbies().get(2));
        assertEquals("Lobby1",modelView2.getLobbies().get(0));
        assertEquals("Lobby2",modelView2.getLobbies().get(1));
        assertEquals("Lobby3",modelView2.getLobbies().get(2));

        try{
            model.removeLobby("Lobby3");
        } catch (Exception e){
           fail();
        }

        assertEquals("Lobby1",modelView1.getLobbies().get(0));
        assertEquals("Lobby2",modelView1.getLobbies().get(1));
        assertEquals(2,modelView1.getLobbies().size());
        assertEquals("Lobby1",modelView2.getLobbies().get(0));
        assertEquals("Lobby2",modelView2.getLobbies().get(1));
        assertEquals(2,modelView2.getLobbies().size());

        LobbyModel finalLobby1 = lobby1;
        Thread t1 = new Thread(()-> playLobby(finalLobby1));
        t1.start();
        LobbyModel finalLobby2 = lobby2;
        Thread t2 = new Thread(()-> playLobby(finalLobby2));
        t2.start();
        t1.join();
        t2.join();

        try {
            finalLobby1.setGameConfig(new GameConfig(4));
        }catch(Exception e){
            throw new RuntimeException(e);
        }

        try{
            finalLobby1.initialize();
            fail();
        }catch(LobbyFullException ignored){}

        try {
            finalLobby1.addPlayer("Pippo");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            finalLobby1.initialize();
        }catch(LobbyFullException ignored){
            fail();
        }
        try {
            finalLobby2.initialize();
        }catch(LobbyFullException ignored){
            fail();
        }
        GameModel game1 = finalLobby1.getGameModel();
        GameModel game2 = finalLobby2.getGameModel();
        t1 = new Thread(() -> playGame1(game1));
        t1.start();
        t2 = new Thread(() -> playGame2(game2));
        t2.start();
        t1.join();
        t2.join();



    }


    private void playGame1(GameModel game) {
        List<FullGameModelView> fullGameModelViewList = new ArrayList<>();
        game.addGameObserver((evt,x)-> fullGameModelViewList.add(game.getGameModelView()));
        FullGameModelView lmv = getLastModel(fullGameModelViewList);
        String currPlayer = lmv.getCurrentPlayerName();
        game.setState(GameState.DRAW);
        List<Position> positions = new ArrayList<>();
        positions.add(new Position(0,0));
        positions.add(new Position(0,1));
        positions.add(new Position(0,2));
        try {
            game.drawTiles(positions,currPlayer);
            fail();
        } catch (InvalidParameterException ignored) {
        }
        positions = new ArrayList<>();
        positions.add(new Position(-1,3));
        positions.add(new Position(-1,4));
        try {
            game.drawTiles(positions,currPlayer);
        } catch (InvalidParameterException ignored) {
            fail();
        }
        game.setState(GameState.ORGANIZE);
        List<ItemTile> tiles = new ArrayList<>();
        tiles.add(lmv.getTile(-1,3));
        tiles.add(lmv.getTile(-1,4));
        Collections.shuffle(tiles);
        try {
            game.orderChosenTiles(tiles,currPlayer);
        } catch (InvalidParameterException e) {
            fail();
        }
        game.setState(GameState.INSERT);
        try {
            game.insertTiles(currPlayer,0);
        } catch (InvalidParameterException | FullColumnException e) {
            fail();
        }
        game.setState(GameState.COUNTING_POINTS);
        lmv = getLastModel(fullGameModelViewList);
        ItemTile[][] updatedBookshelf = lmv.getPlayer(lmv.getCurrentPlayerName()).getBookshelf();
        for(int i = 0 ; i< updatedBookshelf.length; i++){
            for(int j = 0; j< updatedBookshelf[0].length;j++){
                if(i==5 && j==0) assertEquals(tiles.get(0).getType(),updatedBookshelf[i][j].getType());
                else if(i==4 && j==0) assertEquals(tiles.get(1).getType(),updatedBookshelf[i][j].getType());
                else assertEquals(ItemTileType.EMPTY,updatedBookshelf[i][j].getType());
            }
        }
        game.nextTurn();
        game.setState(GameState.DRAW);
        lmv = getLastModel(fullGameModelViewList);
        assertNotEquals(currPlayer, lmv.getCurrentPlayerName(), "Current player is the same of the previuos turn");
        String oldPlayer = currPlayer;
        currPlayer=lmv.getCurrentPlayerName();
        positions = new ArrayList<>();
        positions.add(new Position(0,3));
        
        try {
            game.drawTiles(positions,oldPlayer);
            fail();
        } catch (InvalidParameterException ignored) {
        }
        try{
            game.drawTiles(positions, currPlayer);
        }catch(InvalidParameterException e) {
            fail();
        }
        game.setState(GameState.ORGANIZE);
        tiles = new ArrayList<>();
        tiles.add(lmv.getTile(0,3));
        Collections.shuffle(tiles);
        try {
            game.orderChosenTiles(tiles,currPlayer);
        } catch (InvalidParameterException e) {
            fail();
        }
        game.setState(GameState.INSERT);
        try {
            game.insertTiles(currPlayer,0);
        } catch (InvalidParameterException | FullColumnException e) {
            fail();
        }
        game.setState(GameState.COUNTING_POINTS);
        lmv = getLastModel(fullGameModelViewList);
        updatedBookshelf = lmv.getPlayer(lmv.getCurrentPlayerName()).getBookshelf();
        for(int i = 0 ; i< updatedBookshelf.length; i++){
            for(int j = 0; j< updatedBookshelf[0].length;j++){
                if(i==5 && j==0) assertEquals(tiles.get(0).getType(),updatedBookshelf[i][j].getType());
                else assertEquals(ItemTileType.EMPTY,updatedBookshelf[i][j].getType());
            }
        }
        game.nextTurn();
        game.setState(GameState.DRAW);
    }

    private void playGame2(GameModel game) {
        List<FullGameModelView> fullGameModelViewList = new ArrayList<>();
        game.addGameObserver((evt, x)-> fullGameModelViewList.add(game.getGameModelView()));
        FullGameModelView lmv = getLastModel(fullGameModelViewList);
        String currPlayer = lmv.getCurrentPlayerName();
        fillBookshelf(lmv, game);
        if(!game.isBookshelfFull(currPlayer)){
            fail("Bookshelf non piena");
        }else{
            game.setState(GameState.LAST_DRAW);
            game.nextTurn();
            List<Position> positions = new ArrayList<>();
            positions.add(new Position (-1, 3));
            try {
                game.drawTiles(positions,lmv.getCurrentPlayerName());
            } catch (InvalidParameterException ignored) {}
            game.setState(GameState.LAST_ORGANIZE);
            List<ItemTile> tiles = new ArrayList<>();
            tiles.add(lmv.getTile(-1,3));
            Collections.shuffle(tiles);
            try {
                game.orderChosenTiles(tiles,lmv.getCurrentPlayerName());
            } catch (InvalidParameterException ignored) {}
            game.setState(GameState.LAST_ORGANIZE);
            try {
                game.insertTiles(lmv.getCurrentPlayerName(),0);
            } catch (InvalidParameterException | FullColumnException ignored) {}
            game.setState(GameState.LAST_COUNTING_POINTS);
        }
    }

    private void fillBookshelf(FullGameModelView lmv, GameModel game){
        String p = lmv.getCurrentPlayerName();
        List<Position> positions = new ArrayList<>();
        positions.add(new Position(0,3));
        positions.add(new Position(-1,3));
        try {
            game.drawTiles(positions,p);
        } catch (InvalidParameterException e) {fail();}
        game.setState(GameState.ORGANIZE);
        List<ItemTile> tiles = new ArrayList<>();
        tiles.add(lmv.getTile(0,3));
        tiles.add(lmv.getTile(-1,3));
        Collections.shuffle(tiles);
        try {
            game.orderChosenTiles(tiles,p);
        } catch (InvalidParameterException e) {fail();}
        game.setState(GameState.INSERT);
        try {
            game.insertTiles(p,0);
        } catch (InvalidParameterException | FullColumnException e) {fail();}
        game.setState(GameState.COUNTING_POINTS);
        game.setState(GameState.DRAW);

        positions = new ArrayList<>();
        positions.add(new Position(-1,4));
        try {
            game.drawTiles(positions,p);
        } catch (InvalidParameterException e) {fail();}
        game.setState(GameState.ORGANIZE);
        tiles = new ArrayList<>();
        tiles.add(lmv.getTile(-1,4));
        Collections.shuffle(tiles);
        try {
            game.orderChosenTiles(tiles,p);
        } catch (InvalidParameterException e) {fail();}
        game.setState(GameState.INSERT);
        try {
            game.insertTiles(p,0);
        } catch (InvalidParameterException | FullColumnException e) {fail();}
        game.setState(GameState.COUNTING_POINTS);
        game.setState(GameState.DRAW);


        positions=new ArrayList<>();
        positions.add(new Position(-1,2));
        positions.add(new Position(0,2));
        positions.add(new Position(-2, 2));
        try {
            game.drawTiles(positions,p);
        } catch (InvalidParameterException e) {fail();}
        game.setState(GameState.ORGANIZE);
        tiles = new ArrayList<>();
        tiles.add(lmv.getTile(-1,2));
        tiles.add(lmv.getTile(0,2));
        tiles.add(lmv.getTile(-2,2));
        Collections.shuffle(tiles);
        try {
            game.orderChosenTiles(tiles,p);
        } catch (InvalidParameterException e) {fail();}
        game.setState(GameState.INSERT);
        try {
            game.insertTiles(p,0);
        } catch (InvalidParameterException | FullColumnException e) {fail();}
        game.setState(GameState.COUNTING_POINTS);
        game.setState(GameState.DRAW);

        positions=new ArrayList<>();
        positions.add(new Position(-1,1));
        positions.add(new Position(-2,1));
        positions.add(new Position(0, 1));
        try {
            game.drawTiles(positions,p);
        } catch (InvalidParameterException e) {fail();}
        game.setState(GameState.ORGANIZE);
        tiles = new ArrayList<>();
        tiles.add(lmv.getTile(-1,1));
        tiles.add(lmv.getTile(-2,1));
        tiles.add(lmv.getTile(0,1));
        Collections.shuffle(tiles);
        try {
            game.orderChosenTiles(tiles,p);
        } catch (InvalidParameterException e) {fail();}
        game.setState(GameState.INSERT);
        try {
            game.insertTiles(p,1);
        } catch (InvalidParameterException | FullColumnException e) {fail();}
        game.setState(GameState.COUNTING_POINTS);
        game.setState(GameState.DRAW);

        positions=new ArrayList<>();
        positions.add(new Position(1,2));
        positions.add(new Position(2,2));
        try {
            game.drawTiles(positions,p);
        } catch (InvalidParameterException e) {fail();}
        game.setState(GameState.ORGANIZE);
        tiles = new ArrayList<>();
        tiles.add(lmv.getTile(1,2));
        tiles.add(lmv.getTile(2,2));
        Collections.shuffle(tiles);
        try {
            game.orderChosenTiles(tiles,p);
        } catch (InvalidParameterException e) {fail();}
        game.setState(GameState.INSERT);
        try {
            game.insertTiles(p,1);
        } catch (InvalidParameterException | FullColumnException e) {fail();}
        game.setState(GameState.COUNTING_POINTS);
        game.setState(GameState.DRAW);

        positions=new ArrayList<>();
        positions.add(new Position(4,1));
        try {
            game.drawTiles(positions,p);
        } catch (InvalidParameterException e) {fail();}
        game.setState(GameState.ORGANIZE);
        tiles = new ArrayList<>();
        tiles.add(lmv.getTile(4,1));
        Collections.shuffle(tiles);
        try {
            game.orderChosenTiles(tiles,p);
        } catch (InvalidParameterException e) {fail();}
        game.setState(GameState.INSERT);
        try {
            game.insertTiles(p,1);
        } catch (InvalidParameterException | FullColumnException e) {fail();}
        game.setState(GameState.COUNTING_POINTS);
        game.setState(GameState.DRAW);


        positions=new ArrayList<>();
        positions.add(new Position(3,1));
        positions.add(new Position(2,1));
        positions.add(new Position(1, 1));
        try {
            game.drawTiles(positions,p);
        } catch (InvalidParameterException e) {fail();}
        game.setState(GameState.ORGANIZE);
        tiles = new ArrayList<>();
        tiles.add(lmv.getTile(3,1));
        tiles.add(lmv.getTile(2,1));
        tiles.add(lmv.getTile(1,1));
        Collections.shuffle(tiles);
        try {
            game.orderChosenTiles(tiles,p);
        } catch (InvalidParameterException e) {fail();}
        game.setState(GameState.INSERT);
        try {
            game.insertTiles(p,2);
        } catch (InvalidParameterException | FullColumnException e) {fail();}
        game.setState(GameState.COUNTING_POINTS);
        game.setState(GameState.DRAW);
        positions=new ArrayList<>();
        positions.add(new Position(-2,0));
        positions.add(new Position(-1,0));
        positions.add(new Position(-3,0));
        try {
            game.drawTiles(positions,p);
        } catch (InvalidParameterException e) {fail();}
        game.setState(GameState.ORGANIZE);
        tiles = new ArrayList<>();
        tiles.add(lmv.getTile(-2,0));
        tiles.add(lmv.getTile(-1,0));
        tiles.add(lmv.getTile(-3,0));
        Collections.shuffle(tiles);
        try {
            game.orderChosenTiles(tiles,p);
        } catch (InvalidParameterException e) {fail();}
        game.setState(GameState.INSERT);
        try {
            game.insertTiles(p,2);
        } catch (InvalidParameterException | FullColumnException e) {fail();}
        game.setState(GameState.COUNTING_POINTS);
        game.setState(GameState.DRAW);

        positions=new ArrayList<>();
        positions.add(new Position(0,0));
        positions.add(new Position(1,0));
        positions.add(new Position(2, 0));
        try {
            game.drawTiles(positions,p);
        } catch (InvalidParameterException e) {fail();}
        game.setState(GameState.ORGANIZE);
        tiles = new ArrayList<>();
        tiles.add(lmv.getTile(0,0));
        tiles.add(lmv.getTile(1,0));
        tiles.add(lmv.getTile(2,0));
        Collections.shuffle(tiles);
        try {
            game.orderChosenTiles(tiles,p);
        } catch (InvalidParameterException e) {fail();}
        game.setState(GameState.INSERT);
        try {
            game.insertTiles(p,3);
        } catch (InvalidParameterException | FullColumnException e) {fail();}
        game.setState(GameState.COUNTING_POINTS);
        game.setState(GameState.DRAW);

        positions=new ArrayList<>();
        positions.add(new Position(3,0));
        try {
            game.drawTiles(positions,p);
        } catch (InvalidParameterException e) {fail();}
        game.setState(GameState.ORGANIZE);
        tiles = new ArrayList<>();
        tiles.add(lmv.getTile(3,0));
        Collections.shuffle(tiles);
        try {
            game.orderChosenTiles(tiles,p);
        } catch (InvalidParameterException e) {fail();}
        game.setState(GameState.INSERT);
        try {
            game.insertTiles(p,3);
        } catch (InvalidParameterException | FullColumnException e) {fail();}
        game.setState(GameState.COUNTING_POINTS);
        game.setState(GameState.DRAW);

        positions=new ArrayList<>();
        positions.add(new Position(-4,-1));
        positions.add(new Position(-3,-1));
        try {
            game.drawTiles(positions,p);
        } catch (InvalidParameterException e) {fail();}
        game.setState(GameState.ORGANIZE);
        tiles = new ArrayList<>();
        tiles.add(lmv.getTile(-4,-1));
        tiles.add(lmv.getTile(-3,-1));
        Collections.shuffle(tiles);
        try {
            game.orderChosenTiles(tiles,p);
        } catch (InvalidParameterException e) {fail();}
        game.setState(GameState.INSERT);
        try {
            game.insertTiles(p,3);
        } catch (InvalidParameterException | FullColumnException e) {fail();}
        game.setState(GameState.COUNTING_POINTS);
        game.setState(GameState.DRAW);

        positions=new ArrayList<>();
        positions.add(new Position(-1,-1));
        positions.add(new Position(0,-1));
        positions.add(new Position(-2, -1));
        try {
            game.drawTiles(positions,p);
        } catch (InvalidParameterException e) {fail();}
        game.setState(GameState.ORGANIZE);
        tiles = new ArrayList<>();
        tiles.add(lmv.getTile(-1,-1));
        tiles.add(lmv.getTile(0,-1));
        tiles.add(lmv.getTile(-2,-1));
        Collections.shuffle(tiles);
        try {
            game.orderChosenTiles(tiles,p);
        } catch (InvalidParameterException e) {fail();}
        game.setState(GameState.INSERT);
        try {
            game.insertTiles(p,4);
        } catch (InvalidParameterException | FullColumnException e) {fail();}
        game.setState(GameState.COUNTING_POINTS);
        game.setState(GameState.DRAW);

        positions=new ArrayList<>();
        positions.add(new Position(2,-1));
        positions.add(new Position(1, -1));
        try {
            game.drawTiles(positions,p);
        } catch (InvalidParameterException e) {fail();}
        game.setState(GameState.ORGANIZE);
        tiles = new ArrayList<>();
        tiles.add(lmv.getTile(2,-1));
        tiles.add(lmv.getTile(1,-1));
        Collections.shuffle(tiles);
        try {
            game.orderChosenTiles(tiles,p);
        } catch (InvalidParameterException e) {fail();}
        game.setState(GameState.INSERT);
        try {
            game.insertTiles(p,4);
        } catch (InvalidParameterException | FullColumnException e) {fail();}
        game.setState(GameState.COUNTING_POINTS);
        game.setState(GameState.DRAW);

        positions=new ArrayList<>();
        positions.add(new Position(-2,-2));
        try {
            game.drawTiles(positions,p);
        } catch (InvalidParameterException e) {fail();}
        game.setState(GameState.ORGANIZE);
        tiles = new ArrayList<>();
        tiles.add(lmv.getTile(-2,-2));
        Collections.shuffle(tiles);
        try {
            game.orderChosenTiles(tiles,p);
        } catch (InvalidParameterException e) {fail();}
        game.setState(GameState.INSERT);
        try {
            game.insertTiles(p,4);
        } catch (InvalidParameterException | FullColumnException e) {fail();}
        game.setState(GameState.COUNTING_POINTS);
        game.setState(GameState.DRAW);
    }

    private FullGameModelView getLastModel(List<FullGameModelView> list){
        return list.get(list.size()-1);
    }

    private void playLobby(LobbyModel lobby){
        List<LobbyModelView> lobbyModelViewList = new ArrayList<>();
        lobby.addLobbyObserver((evt, x)-> lobbyModelViewList.add(lobby.getLobbyModelView()));
        try {
            lobby.addPlayer("Giorgio");
        } catch (Exception e) {
            fail();
        }
        LobbyModelView lmv = getLast(lobbyModelViewList);
        assertEquals("Giorgio",lmv.getPlayers().get(0));
        assertEquals(lobby.getName(),lmv.getName());
        assertEquals("Giorgio",lmv.getRoomOwner());
        assertEquals(2,lmv.getGameConfig().getNumPlayers());

        try {
            lobby.addPlayer("Giorgio");
            fail();
        } catch (Exception ignored) {
        }
        lmv = getLast(lobbyModelViewList);
        assertEquals("Giorgio",lmv.getPlayers().get(0));
        assertEquals(lobby.getName(),lmv.getName());
        assertEquals("Giorgio",lmv.getRoomOwner());
        assertEquals(2,lmv.getGameConfig().getNumPlayers());
        try {
            lobby.addPlayer("Giovanni");
        } catch (Exception e) {
            fail();
        }
        lmv = getLast(lobbyModelViewList);
        assertEquals("Giorgio",lmv.getPlayers().get(0));
        assertEquals("Giovanni",lmv.getPlayers().get(1));
        assertEquals(lobby.getName(),lmv.getName());
        assertEquals("Giorgio",lmv.getRoomOwner());
        assertEquals(2,lmv.getGameConfig().getNumPlayers());
        try{
            lobby.addPlayer("Luca");
            fail("Lobby should be full cause default size is 2");
        } catch (Exception ignored){
        }
        lmv = getLast(lobbyModelViewList);
        assertEquals("Giorgio",lmv.getPlayers().get(0));
        assertEquals("Giovanni",lmv.getPlayers().get(1));
        assertEquals(lobby.getName(),lmv.getName());
        assertEquals("Giorgio",lmv.getRoomOwner());
        assertEquals(2,lmv.getGameConfig().getNumPlayers());
        try {
            lobby.setGameConfig(new GameConfig(4));
        } catch (Exception e) {
            fail();
        }
        lmv = getLast(lobbyModelViewList);
        assertEquals("Giorgio",lmv.getPlayers().get(0));
        assertEquals("Giovanni",lmv.getPlayers().get(1));
        assertEquals(lobby.getName(),lmv.getName());
        assertEquals("Giorgio",lmv.getRoomOwner());
        assertEquals(4,lmv.getGameConfig().getNumPlayers());
        try{
            lobby.addPlayer("Luca");
        } catch (Exception e){
            fail();
        }
        lmv = getLast(lobbyModelViewList);
        assertEquals("Giorgio",lmv.getPlayers().get(0));
        assertEquals("Giovanni",lmv.getPlayers().get(1));
        assertEquals("Luca",lmv.getPlayers().get(2));
        assertEquals(lobby.getName(),lmv.getName());
        assertEquals("Giorgio",lmv.getRoomOwner());
        assertEquals(4,lmv.getGameConfig().getNumPlayers());
        try {
            lobby.setGameConfig(new GameConfig(2));
            fail("there are 3 players in the lobby");
        } catch (Exception ignored) {
        }
        lmv = getLast(lobbyModelViewList);
        assertEquals("Giorgio",lmv.getPlayers().get(0));
        assertEquals("Giovanni",lmv.getPlayers().get(1));
        assertEquals("Luca",lmv.getPlayers().get(2));
        assertEquals(lobby.getName(),lmv.getName());
        assertEquals("Giorgio",lmv.getRoomOwner());
        assertEquals(4,lmv.getGameConfig().getNumPlayers());
        try {
            lobby.setGameConfig(new GameConfig(3));
        } catch (Exception e) {
            fail();
        }
        lmv = getLast(lobbyModelViewList);
        assertEquals("Giorgio",lmv.getPlayers().get(0));
        assertEquals("Giovanni",lmv.getPlayers().get(1));
        assertEquals("Luca",lmv.getPlayers().get(2));
        assertEquals(lobby.getName(),lmv.getName());
        assertEquals("Giorgio",lmv.getRoomOwner());
        assertEquals(3,lmv.getGameConfig().getNumPlayers());
        try {
            lobby.removePlayer("InvalidName");
            fail();
        } catch (Exception ignored) {
        }
        lmv = getLast(lobbyModelViewList);
        assertEquals("Giorgio",lmv.getPlayers().get(0));
        assertEquals("Giovanni",lmv.getPlayers().get(1));
        assertEquals("Luca",lmv.getPlayers().get(2));
        assertEquals(lobby.getName(),lmv.getName());
        assertEquals("Giorgio",lmv.getRoomOwner());
        assertEquals(3,lmv.getGameConfig().getNumPlayers());
        try{
            lobby.removePlayer("Giovanni");
        } catch (Exception e) {
            fail();
        }
        lmv = getLast(lobbyModelViewList);
        assertEquals("Giorgio",lmv.getPlayers().get(0));
        assertEquals("Luca",lmv.getPlayers().get(1));
        assertEquals(lobby.getName(),lmv.getName());
        assertEquals("Giorgio",lmv.getRoomOwner());
        assertEquals(3,lmv.getGameConfig().getNumPlayers());
        try{
            lobby.addPlayer("Giovanni");
        } catch (Exception e) {
            fail();
        }
        lmv = getLast(lobbyModelViewList);
        assertEquals("Giorgio",lmv.getPlayers().get(0));
        assertEquals("Luca",lmv.getPlayers().get(1));
        assertEquals("Giovanni",lmv.getPlayers().get(2));
        assertEquals(lobby.getName(),lmv.getName());
        assertEquals("Giorgio",lmv.getRoomOwner());
        assertEquals(3,lmv.getGameConfig().getNumPlayers());
        try{
            lobby.removePlayer("Giorgio");
        } catch (Exception e) {
            fail();
        }
        lmv = getLast(lobbyModelViewList);
        assertEquals("Luca",lmv.getPlayers().get(0));
        assertEquals("Giovanni",lmv.getPlayers().get(1));
        assertEquals(lobby.getName(),lmv.getName());
        assertEquals("Luca",lmv.getRoomOwner());
        assertEquals(3,lmv.getGameConfig().getNumPlayers());
        try{
            lobby.addPlayer("Giorgio");
        } catch (Exception e) {
            fail();
        }
        lmv = getLast(lobbyModelViewList);
        assertEquals("Luca",lmv.getPlayers().get(0));
        assertEquals("Giovanni",lmv.getPlayers().get(1));
        assertEquals("Giorgio",lmv.getPlayers().get(2));
        assertEquals(lobby.getName(),lmv.getName());
        assertEquals("Luca",lmv.getRoomOwner());
        assertEquals(3,lmv.getGameConfig().getNumPlayers());
    }

    public LobbyModelView getLast(List<LobbyModelView> list){
        if(list.size()==0) return null;
        return list.get(list.size()-1);
    }
}
