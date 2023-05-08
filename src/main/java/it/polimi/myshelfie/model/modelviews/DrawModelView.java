package it.polimi.myshelfie.model.modelviews;

import it.polimi.myshelfie.model.Game;
import it.polimi.myshelfie.model.GameState;
import it.polimi.myshelfie.model.ItemTile;

import java.io.Serializable;
import java.util.List;

public class DrawModelView  implements Serializable, GameModelView {
    private final String currentPlayer;
    private final GameState currentState;
    private final ItemTile[][] livingRoom;
    private final List<ItemTile> takenTiles;

    public DrawModelView(Game game) {
        this.currentPlayer = game.getCurrentPlayer();
        this.currentState = game.getState();
        this.livingRoom = game.getLivingRoom();
        this.takenTiles = game.getPlayer(currentPlayer).getTakenTiles();

    }

    public String getCurrentPlayer() { return currentPlayer; }

    public GameState getCurrentState() { return currentState; }

    public ItemTile[][] getLivingRoom() { return livingRoom; }

    public List<ItemTile> getTakenTiles() { return takenTiles; }
}
