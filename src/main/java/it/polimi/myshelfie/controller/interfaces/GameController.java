package it.polimi.myshelfie.controller.interfaces;

import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.Position;
import it.polimi.myshelfie.model.events.GameEvent;
import it.polimi.myshelfie.model.modelviews.GameModelView;
import it.polimi.myshelfie.utils.Observer;


import java.util.List;

public interface GameController {

    /**
     * Allows the player playing to draw from the board the tiles he wants to add to his bookshelf.
     * @param positions are the positions of the tiles the player wants to select from the board.
     * @param playerName is the player playing.
     */
    void drawTiles(List<Position> positions, String playerName);

    /**
     * Allows the player to order the tiles he drew from the board before insert them in the bookshelf.
     * @param orderedTiles are the tiles selected from the board.
     * @param playerName is the player playing.
     */
    void orderChosenTiles(List<ItemTile> orderedTiles, String playerName);

    /**
     * Allows the player to insert the ordered tiles in his bookshelf.
     * @param playerName is the player playing.
     * @param column is the column where to insert the chosen tiles.
     */
    void insertTiles(String playerName, int column);

    /**
     * Adds a game observer.
     * @param playerName the name of the player.
     * @param networkGameObserver the observer to add.
     */
    void addGameObserver(String playerName, Observer<GameEvent, GameModelView> networkGameObserver);

    /**
     * Disconnects a player from the game.
     * @param playerName is the name of the player quitting.
     */
    void exitGame(String playerName);
}
