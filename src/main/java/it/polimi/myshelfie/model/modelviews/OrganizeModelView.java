package it.polimi.myshelfie.model.modelviews;

import it.polimi.myshelfie.model.Game;
import it.polimi.myshelfie.model.GameState;
import it.polimi.myshelfie.model.ItemTile;

import java.io.Serializable;
import java.util.List;

/**
 * Represents the Organize move model view.
 */
public class OrganizeModelView implements Serializable, GameModelView {
    private final String currentPlayer;
    private final GameState currentState;
    private final List<ItemTile> orderedTiles;


    /**
     * Default constructor.
     *
     * @param game the game where the move was made.
     */
    public OrganizeModelView(Game game) {
        this.currentPlayer = game.getCurrentPlayer();
        this.currentState = game.getState();
        this.orderedTiles = game.getPlayer(currentPlayer).getTakenTiles();
    }

    /**
     * Returns the current player
     *
     * @return the current player
     */
    public String getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Returns the current state
     * @return the current state
     */
    public GameState getCurrentState() {
        return currentState;
    }

    /**
     * Returns the list of ordered tiles.
     * @return the list of ordered tiles.
     */
    public List<ItemTile> getOrderedTiles() {
        return orderedTiles;
    }
}
