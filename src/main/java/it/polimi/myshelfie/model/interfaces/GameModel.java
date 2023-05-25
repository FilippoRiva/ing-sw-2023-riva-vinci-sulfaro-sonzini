package it.polimi.myshelfie.model.interfaces;

import it.polimi.myshelfie.model.*;
import it.polimi.myshelfie.model.exceptions.FullColumnException;
import it.polimi.myshelfie.model.modelviews.FullGameModelView;
import it.polimi.myshelfie.model.events.GameEvent;
import it.polimi.myshelfie.model.modelviews.GameModelView;
import it.polimi.myshelfie.utils.Observer;
import it.polimi.myshelfie.model.exceptions.InvalidParameterException;


import java.util.List;

public interface GameModel {

    /**
     * Change the state of the game and notifies every view on the game.
     * @param state is the new state.
     */
    void setState(GameState state);

    /**
     * @return the current state previously set by setState().
     */
    GameState getState();

    /**
     * Draws the tiles in the given positions from the livingRoom.
     * @param positions are the positions of the tiles that have to be removed from the board, where the first in the list is the first one to be
     *                  inserted into the player's bookshelf, and the last one is the last one to be inserted.
     * @param playerName the name of the player drawing the tiles.
     * @throws InvalidParameterException if the given positions/column are/is not valid.
     */
    void drawTiles(List<Position> positions, String playerName) throws InvalidParameterException;

    /**
     *
     * @param orderedTiles are the tiles taken form the board.
     * @param playerName is the player playing.
     * @throws InvalidParameterException if the player is playing in the wrong turn and if the tiles are different from
     * the ones taken in the draw move.
     */
    void orderChosenTiles(List<ItemTile> orderedTiles, String playerName) throws InvalidParameterException;

    /**
     * Inserts the tiles taken from the board in the chosen column.
     * @param playerName the player playing.
     * @param column the chose column to insert the tiles in.
     * @throws InvalidParameterException if the player is playing in the wrong turn and if the chosen column does not exist
     * in that bookshelf.
     * @throws FullColumnException if the selected column is not refillable.
     */
    void insertTiles(String playerName, int column) throws InvalidParameterException, FullColumnException;

    /**
     * Check if the player achieved the commonGoal and add the obtained tokens to the player.
     * Has to be called at the end of a player's turn.
     * @param playerName player playing
     */
    void checkCommonGoal(String playerName);

    /**
     * refill the livingRoom if refillable.
     */
    void refillLivingRoom();

    /**
     * change the current player playing and notifies every view.
     */
    void nextTurn();

    /**
     * if last round and last player, calculates the results.
     */
    void calculateWinResults();

    void addEndToken(String playerName);

    boolean isBookshelfFull(String playerName);

    boolean isLastPlayer(String playerName);

    /**
     * Add a game observer.
     * @param obs is the observer to add.
     */
    void addGameObserver(Observer<GameEvent, GameModelView> obs);

    /**
     * Removes a game observer.
     * @param obs the game observer to remove.
     */
    void removeGameObserver(Observer<GameEvent, GameModelView> obs);

    /**
     * Return the game model view of that game.
     * @return a game model view.
     */
    FullGameModelView getGameModelView();

    /**
     *
     * @return the game name.
     */
    String getName();

    /**
     *
     * @return the lobby name.
     */
    String getLobbyName();
}
