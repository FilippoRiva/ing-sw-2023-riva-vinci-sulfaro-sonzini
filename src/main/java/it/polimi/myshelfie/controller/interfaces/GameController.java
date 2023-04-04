package it.polimi.myshelfie.controller.interfaces;

import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.Position;
import it.polimi.myshelfie.model.exceptions.FullColumnException;


import java.util.List;

public interface GameController {
    void drawTiles(List<Position> positions, String playerName);

    void orderChosenTiles(List<ItemTile> orderedTiles, String playerName);

    void insertTiles(String playerName, int column);


    //TODO methods to manage Observer/Listener

}
