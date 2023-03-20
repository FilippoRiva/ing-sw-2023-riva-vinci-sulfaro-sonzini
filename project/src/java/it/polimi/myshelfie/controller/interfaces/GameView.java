package it.polimi.myshelfie.controller.interfaces;

import it.polimi.myshelfie.model.ItemTile;

import java.awt.*;
import java.util.List;

public interface GameView {
    /**
     *
     * @param takenTiles are the tiles taken by the player from the living room.
     *                   These tiles are removed from the board.
     */
    void takeTileFromLivingRoom(List<Point> takenTiles);

    /**
     *
      * @param takenTiles are the ordered tiles to be placed in the bookshelf.
     * @param column is the selected column where to put the taken tiles.
     */
    void insertTileInBookshelf(List<ItemTile> takenTiles, int column);
}
