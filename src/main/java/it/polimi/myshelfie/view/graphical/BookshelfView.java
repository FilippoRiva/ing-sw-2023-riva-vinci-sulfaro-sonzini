package it.polimi.myshelfie.view.graphical;

import javafx.scene.layout.GridPane;

public class BookshelfView {
    private final String playerName;
    private GridPane bookshelfGrid;

    public BookshelfView(String player, GridPane bookshelfGrid) {
        this.playerName = player;
        this.bookshelfGrid = bookshelfGrid;
    }

    public String getPlayerName() {
        return playerName;
    }

    public GridPane getBookshelfGrid() {
        return bookshelfGrid;
    }

    public void setBookshelfGrid(GridPane bookshelfGrid) {
        this.bookshelfGrid = bookshelfGrid;
    }
}
