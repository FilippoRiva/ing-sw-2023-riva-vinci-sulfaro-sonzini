package it.polimi.myshelfie.controller.interfaces;

import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.Position;
import it.polimi.myshelfie.model.events.GameEvent;
import it.polimi.myshelfie.model.modelviews.GameModelView;
import it.polimi.myshelfie.utils.Observer;


import java.util.List;

/**
 * Represents a controller for the game.
 */
public interface GameController {
    /**
     * Allows the player playing to draw from the board the tiles he wants to add to his bookshelf.
     * @param positions the positions of the tiles the player wants to select from the board.
     * @param playerName the player playing.
     */
    void drawTiles(List<Position> positions, String playerName);

    /**
     * Allows the player to order the tiles he drew from the board before insert them in the bookshelf.
     * @param orderedTiles the tiles selected from the board.
     * @param playerName the player playing.
     */
    void orderChosenTiles(List<ItemTile> orderedTiles, String playerName);

    /**
     * Allows the player to insert the ordered tiles in his bookshelf.
     * @param playerName the player playing.
     * @param column the column where to insert the chosen tiles.
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
     * @param playerName the name of the player quitting.
     */
    void disconnectFromGame(String playerName);

    /**
     * Disconnects a player from the end game
     * @param username the name of the player leaving.
     * @param fromDisconnection indicates the disconnection.
     */
    void leaveEndGame(String username, boolean fromDisconnection);
}
