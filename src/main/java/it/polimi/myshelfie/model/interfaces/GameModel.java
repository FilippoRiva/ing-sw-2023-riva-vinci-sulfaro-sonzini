package it.polimi.myshelfie.model.interfaces;

import it.polimi.myshelfie.model.*;
import it.polimi.myshelfie.model.exceptions.FullColumnException;


import java.util.List;

public interface GameModel {
    /**
     * Initialize game with the GameConfig that have been previously set.
     * If no custom GameConfig is set the default one is used.
     * Notifies every view on the lobby.
     */
    void initialize();

    /**
     * change the state of the game and notifies every view on the game
     * @param state the new state
     */
    void setState(GameState state);

    /**
     * @return the current state previously set by setState()
     */
    GameState getState();

    /**
     * Draws the given positions from the livingRoom
     * @param positions the positions to be removed from the board, where the first in the list is the first one to be
     *                  inserted in the player's bookshelf, and the last one is the last one to be inserted.
     * @param playerName the name of the player drawing the tiles
     * @throws Exception if the given positions/column are/is not valid
     */
    void drawTiles(List<Position> positions, String playerName) throws Exception;

    void orderChosenTiles(List<ItemTile> orderedTiles, String playerName) throws Exception;

    void insertTiles(String playerName, int column) throws Exception;

    /**
     * check if the player achieved the commonGoal and adds the obtained tokens to the player.
     * to be called at the end of a player's turn
     * @param playerName player playing
     */
    void checkCommonGoal(String playerName);

    /**
     * refill the livingRoom if refillable
     */
    void refillLivingRoom();

    /**
     * change the current player playing and notifies every view
     */
    void nextTurn();

    /**
     * if last round and last player calculates the results
     */
    void calculateWinResults();

    //TODO Methods to manage progress of the game such as:
    // isLastRound, isBookshelfFull, isLastPlayer, isCurrentPlayer

    //TODO methods to manage Observer/Listener
}
