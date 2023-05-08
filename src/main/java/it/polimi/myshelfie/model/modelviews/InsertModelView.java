package it.polimi.myshelfie.model.modelviews;

import it.polimi.myshelfie.model.Game;
import it.polimi.myshelfie.model.GameState;
import it.polimi.myshelfie.model.ItemTile;

import java.io.Serializable;
import java.util.List;

public class InsertModelView implements Serializable {
    private final String currentPlayer;
    private final GameState currentState;
    //todo rimuovere queste tiles perchè sono sempre vuote e farle togliere alla view quando arriva questo messaggio
    final private List<ItemTile> takenTiles;
    private final ItemTile[][] bookshelf;



    public InsertModelView(Game game) {
        this.currentPlayer = game.getCurrentPlayer();
        this.currentState = game.getState();
        this.bookshelf = game.getPlayer(currentPlayer).getBookshelf().getBookshelfItems();
        this.takenTiles = game.getPlayer(currentPlayer).getTakenTiles();
    }

    public String getCurrentPlayer() { return currentPlayer; }

    public GameState getCurrentState() { return currentState; }

    public List<ItemTile> getTakenTiles() { return takenTiles; }

    public ItemTile[][] getBookshelf() { return bookshelf; }
}
