package it.polimi.myshelfie.controller;

import it.polimi.myshelfie.GameConfig;
import it.polimi.myshelfie.controller.interfaces.GameView;
import it.polimi.myshelfie.controller.interfaces.LobbyView;
import it.polimi.myshelfie.model.Bookshelf;
import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.Position;
import it.polimi.myshelfie.model.goals.PersonalGoal;

import java.awt.*;
import java.util.List;

public class Controller implements GameView, LobbyView {
    @Override
    public void takeTileFromLivingRoom(List<Position> takenTiles, String playerName, String roomName) throws Exception {

    }

    @Override
    public void insertTileInBookshelf(List<ItemTile> takenTiles, int column, String playerName, String roomName) throws Exception {

    }

    @Override
    public void createGame(String namePlayer, String nameRoom) throws Exception {

    }

    @Override
    public List<String> askGameList() {
        return null;
    }

    @Override
    public void joinGame(String namePlayer, String nameRoom) throws Exception {

    }

    @Override
    public void setGameConfig(GameConfig configs) {

    }

    @Override
    public void exitGame(String namePlayer, String roomName) throws Exception {

    }

    GameConfig config;
    public void initializeGame(){
        //TODO implementation
    }
    public void updateBookshelf(List<ItemTile> takenTiles, int column){
        //TODO implementation
    }
    public void updateLivingRoom(List<Position> takenTiles){
        //TODO implementation
    }
    private boolean checkCommonGoal(Bookshelf bookshelf){
        //TODO implementation
        return false;
    }
    private int calculatePoints(Bookshelf bookshelf, PersonalGoal personalGoal){
        //TODO implementation
        return 0;
    }
}
