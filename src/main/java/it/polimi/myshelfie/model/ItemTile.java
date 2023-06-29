package it.polimi.myshelfie.model;

import java.io.Serializable;

/**
 * Immutable Class representing a specific tile.
 * It has a type and also an id, used to associate each tile with his image.
 */
public class ItemTile implements Serializable {
    private final ItemTileType type;
    private final int id;

    /**
     * Default Constructor
     *
     * @param type the ItemTileType of the ItemTile
     * @param id   the id of the tile
     */
    public ItemTile(ItemTileType type, int id) {
        this.type = type;
        this.id = id;
    }

    /**
     * Used to get the id of the tile.
     *
     * @return the number of the tile.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the ItemTileType set inside the Constructor.
     *
     * @return the type of the ItemTile.
     */
    public ItemTileType getType() {
        return type;
    }

    /**
     * Checks if an Object is equal to this ItemTile
     *
     * @param obj the object we want to compare
     * @return true if the object is an ItemTile his ItemTileType is the same of this tile
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass()) return false;
        ItemTile tile;
        tile = (ItemTile) obj;
        return this.type.equals(tile.getType());
    }

    /**
     * Converts this ItemTile into a String format
     *
     * @return a String representing the ItemTile. ex: "CAT : 1" or "GAME, 3".
     */
    @Override
    public String toString() {
        return type.toString() + " : " + id;
    }
}
