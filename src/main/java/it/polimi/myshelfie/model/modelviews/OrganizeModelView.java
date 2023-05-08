package it.polimi.myshelfie.model.modelviews;

import it.polimi.myshelfie.model.Game;
import it.polimi.myshelfie.model.GameState;
import it.polimi.myshelfie.model.ItemTile;

import java.io.Serializable;
import java.util.List;

public class OrganizeModelView implements Serializable {
    private final String currentPlayer;
    private final GameState currentState;
    private final List<ItemTile> orderedTiles;


    public OrganizeModelView(Game game) {
        this.currentPlayer = game.getCurrentPlayer();
        this.currentState = game.getState();
        this.orderedTiles = game.getPlayer(currentPlayer).getTakenTiles();
    }

    public String getCurrentPlayer() { return currentPlayer; }

    public GameState getCurrentState() { return currentState;}

    public List<ItemTile> getOrderedTiles() { return orderedTiles; }
}
