package it.polimi.myshelfie.model.modelviews;

import it.polimi.myshelfie.model.Game;
import it.polimi.myshelfie.model.GameState;
import it.polimi.myshelfie.model.ItemTile;

import java.io.Serializable;
import java.util.List;

/**
 * Represents the draw move model view.
 */
public class DrawModelView implements Serializable, GameModelView {
    private final String currentPlayer;
    private final GameState currentState;
    private final ItemTile[][] livingRoom;
    private final List<ItemTile> takenTiles;

    /**
     * Constructor.
     *
     * @param game the game where the move was made.
     */
    public DrawModelView(Game game) {
        this.currentPlayer = game.getCurrentPlayer();
        this.currentState = game.getState();
        this.livingRoom = game.getLivingRoom();
        this.takenTiles = game.getPlayer(currentPlayer).getTakenTiles();

    }

    /**
     * @return the player playing.
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
     * @return the living room.
     */
    public ItemTile[][] getLivingRoom() {
        return livingRoom;
    }

    /**
     * @return the taken tiles in the draw move.
     */
    public List<ItemTile> getTakenTiles() {
        return takenTiles;
    }
}
