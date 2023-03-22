package it.polimi.myshelfie.model.interfaces;

import it.polimi.myshelfie.GameConfig;
import it.polimi.myshelfie.model.*;
import it.polimi.myshelfie.model.goals.CommonGoal;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Observer;

public interface GameModel {
    /**
     * Initialize game with the GameConfig that have been previously set.
     * If no custom GameConfig is set the default one is used.
     * Notifies every view on the lobby.
     */
    void init();

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
     * Draws the given positions from the living room
     * @param positions the positions to be removed from the board, where the first in the list is the first one to be
     *                  inserted in the player's bookshelf, and the last one is the last one to be inserted.
     * @param playerName the name of the player drawing the tiles
     * @throws InvalidParameterException if the given positions/column are/is not valid
     */
    void draw(List<Position> positions, String playerName) throws InvalidParameterException;

    void orderChosenTiles(List<ItemTile> orderedTiles) throws InvalidParameterException;

    void insert(String playerName, int column) throws InvalidParameterException;

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

    //TODO Altre cose logiche utili al controller per far proseguire il flusso della partita come:
    // isLastRound, isBookshelfFull, isLastPlayer, isCurrentPlayer

    //TODO metodo per settare il pattern Observer/Listener da parte del controller
}
