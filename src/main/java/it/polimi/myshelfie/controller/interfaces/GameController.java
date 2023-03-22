package it.polimi.myshelfie.controller.interfaces;

import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.Position;
import it.polimi.myshelfie.model.exceptions.FullColumnException;

import java.security.InvalidParameterException;
import java.util.List;

public interface GameController {
    void drawTiles(List<Position> positions, String playerName) throws InvalidParameterException;

    void orderChosenTiles(List<ItemTile> orderedTiles, String playerName) throws InvalidParameterException;

    void insertTiles(String playerName, int column) throws InvalidParameterException, FullColumnException;


    //TODO methods to manage Observer/Listener

}
