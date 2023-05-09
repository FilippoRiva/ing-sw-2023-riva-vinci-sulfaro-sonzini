package it.polimi.myshelfie.controller.interfaces;

import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.Position;
import it.polimi.myshelfie.network.observers.NetworkGameObserver;


import java.util.List;

public interface GameController {

    /**
     *
     * @param positions are the positions of the tiles the player wants to select from the board
     * @param playerName is the player playing
     */
    void drawTiles(List<Position> positions, String playerName);

    /**
     *
     * @param orderedTiles are the tiles selected from the board
     * @param playerName is the player playing
     */
    void orderChosenTiles(List<ItemTile> orderedTiles, String playerName);

    /**
     *
     * @param playerName is the player playing
     * @param column is the column where to insert the chosen tiles
     */
    void insertTiles(String playerName, int column);

    void addGameObserver(NetworkGameObserver networkGameObserver);
    void exitGame(String playerName);
}
