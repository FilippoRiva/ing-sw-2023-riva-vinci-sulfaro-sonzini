package it.polimi.myshelfie.model.modelviews;

import it.polimi.myshelfie.model.Game;
import it.polimi.myshelfie.model.GameState;
import it.polimi.myshelfie.model.ItemTile;

import java.io.Serializable;

public class InsertModelView implements Serializable, GameModelView {
    private final String currentPlayer;
    private final GameState currentState;
    private final ItemTile[][] bookshelf;



    public InsertModelView(Game game) {
        this.currentPlayer = game.getCurrentPlayer();
        this.currentState = game.getState();
        this.bookshelf = game.getPlayer(currentPlayer).getBookshelf().getBookshelfItems();
    }

    public String getCurrentPlayer() { return currentPlayer; }

    public GameState getCurrentState() { return currentState; }


    public ItemTile[][] getBookshelf() { return bookshelf; }
}
