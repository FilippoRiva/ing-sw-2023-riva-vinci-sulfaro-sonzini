package it.polimi.myshelfie.model.modelviews;

import it.polimi.myshelfie.model.Game;
import it.polimi.myshelfie.model.GameState;
import it.polimi.myshelfie.model.ItemTile;

import java.io.Serializable;

/**
 * Represents the Insert move model view.
 */
public class InsertModelView implements Serializable, GameModelView {
    private final String currentPlayer;
    private final GameState currentState;
    private final ItemTile[][] bookshelf;


    /**
     * Constructor.
     *
     * @param game the game where the insert move was made.
     */
    public InsertModelView(Game game) {
        this.currentPlayer = game.getCurrentPlayer();
        this.currentState = game.getState();
        this.bookshelf = game.getPlayer(currentPlayer).getBookshelf().getBookshelfItems();
    }

    /**
     * @return the current player.
     */
    public String getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * @return the current state.
     */
    public GameState getCurrentState() {
        return currentState;
    }


    /**
     * @return the bookshelf.
     */
    public ItemTile[][] getBookshelf() {
        return bookshelf;
    }
}
