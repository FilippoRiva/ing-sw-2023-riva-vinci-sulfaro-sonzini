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
     * @param game the game where the move was made.
     */
    public OrganizeModelView(Game game) {
        this.currentPlayer = game.getCurrentPlayer();
        this.currentState = game.getState();
        this.orderedTiles = game.getPlayer(currentPlayer).getTakenTiles();
    }

    /**
     *
     * @return the current player
     */
    public String getCurrentPlayer() { return currentPlayer; }

    /**
     *
     * @return the current state
     */
    public GameState getCurrentState() { return currentState;}

    /**
     *
     * @return the list of ordered tiles.
     */
    public List<ItemTile> getOrderedTiles() { return orderedTiles; }
}
