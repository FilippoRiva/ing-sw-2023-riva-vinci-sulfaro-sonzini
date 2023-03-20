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
    GameConfig config;
    @Override
    public void createGame(String namePlayer, String nameRoom, int numPlayers){
        //TODO implementation
    }
    @Override
    public List<String> askGameList() {
        //TODO implementation
        return null;
    }
    @Override
    public void joinGame(String namePlayer, String nameRoom) {
        //TODO implementation
    }
    @Override
    public void setGameConfig(GameConfig configs) {
        this.config=configs;
    }
    @Override
    public void exitGame() {
        //TODO implementation
    }
    @Override
    public void takeTileFromLivingRoom(List<Point> takenTiles) {
        //TODO implementation
    }

    @Override
    public void insertTileInBookshelf(List<ItemTile> takenTiles, int column) {
        //TODO implementation
    }
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
