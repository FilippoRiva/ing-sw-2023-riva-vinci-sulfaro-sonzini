package it.polimi.myshelfie.controller.interfaces;

import it.polimi.myshelfie.model.ItemTile;

import java.awt.*;
import java.util.List;

public interface GameView {
    void takeTileFromLivingRoom(List<Point> takenTiles);
    void insertTileInBookshelf(List<ItemTile> takenTiles, int column);
}
