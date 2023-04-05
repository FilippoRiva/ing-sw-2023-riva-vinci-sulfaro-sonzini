package it.polimi.myshelfie.model;

import it.polimi.myshelfie.GameConfig;
import it.polimi.myshelfie.model.exceptions.FullColumnException;
import it.polimi.myshelfie.model.exceptions.InvalidParameterException;
import it.polimi.myshelfie.model.interfaces.GameModel;
import it.polimi.myshelfie.model.interfaces.LobbyModel;
import it.polimi.myshelfie.model.interfaces.Model;
import it.polimi.myshelfie.model.modelviews.*;
import it.polimi.myshelfie.network.GameEvent;
import it.polimi.myshelfie.network.LobbyEvent;
import it.polimi.myshelfie.network.ModelEvent;
import it.polimi.myshelfie.utils.Observable;
import it.polimi.myshelfie.utils.Observer;
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
            model.addModelObserver((Observer<? extends Observable<ModelEvent>, ModelEvent>)(o, evt)->{
                setModelView1(model.getModelView());
            });
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
            model.addModelObserver((Observer<? extends Observable<ModelEvent>, ModelEvent>)(o, evt)->{
                setModelView2(model.getModelView());
            });
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
            finalLobby1.addPlayer("Pippo");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        finalLobby1.initialize();
        finalLobby2.initialize();
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
        //Luca Giovanni Giorgio Pippo
        List<GameModelView> gameModelViewList = new ArrayList<>();
        game.addGameObserver((Observer<? extends Observable<GameEvent>, GameEvent>)(o,evt)->{
            gameModelViewList.add(game.getGameModelView());
        });
    }
    private void playGame2(GameModel game) {
        List<GameModelView> gameModelViewList = new ArrayList<>();
        game.addGameObserver((Observer<? extends Observable<GameEvent>, GameEvent>)(o, evt)->{
            gameModelViewList.add(game.getGameModelView());
        });
        GameModelView lmv = getLastModel(gameModelViewList);
        String player = lmv.getCurrentPlayer();
        List<Position> positions = new ArrayList<>();
        positions.add(new Position(0,0));
        positions.add(new Position(0,1));
        positions.add(new Position(0,2));
        try {
            game.drawTiles(positions,player);
            fail();
        } catch (InvalidParameterException ignored) {
        }
        positions = new ArrayList<>();
        positions.add(new Position(-1,3));
        positions.add(new Position(-1,4));
        try {
            game.drawTiles(positions,player);
        } catch (InvalidParameterException ignored) {
            fail();
        }
        List<ItemTile> tiles = new ArrayList<>();
        tiles.add(lmv.getTile(-1,3));
        tiles.add(lmv.getTile(-1,4));
        Collections.shuffle(tiles);
        try {
            game.orderChosenTiles(tiles,player);
        } catch (InvalidParameterException e) {
            fail();
        }
        try {
            game.insertTiles(player,0);
        } catch (InvalidParameterException | FullColumnException e) {
            fail();
        }
        lmv = getLastModel(gameModelViewList);
        ItemTile[][] updatedBookshelf = lmv.getPlayer(lmv.getCurrentPlayer()).getBookshelf();
        for(int i = 0 ; i< updatedBookshelf.length; i++){
            for(int j = 0; j< updatedBookshelf[0].length;j++){
                if(i==5 && j==0) assertEquals(tiles.get(0).getType(),updatedBookshelf[i][j].getType());
                else if(i==4 && j==0) assertEquals(tiles.get(1).getType(),updatedBookshelf[i][j].getType());
                else assertEquals(ItemTileType.EMPTY,updatedBookshelf[i][j].getType());
            }
        }
        game.nextTurn();
    }

    private GameModelView getLastModel(List<GameModelView> list){
        return list.get(list.size()-1);
    }

    private void playLobby(LobbyModel lobby){
        List<LobbyModelView> lobbyModelViewList = new ArrayList<>();
        lobby.addLobbyObserver((Observer<? extends Observable<LobbyEvent>, LobbyEvent>)(o,evt)->{
            lobbyModelViewList.add(lobby.getLobbyModelView());
        });
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
        } catch (Exception e){
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
        } catch (Exception e) {
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
