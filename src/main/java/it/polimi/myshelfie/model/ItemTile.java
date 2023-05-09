package it.polimi.myshelfie.model;

import java.io.Serializable;

public class ItemTile implements Serializable {
    private final ItemTileType type;
    private final int id;
    public ItemTile(ItemTileType type, int num){
        this.type = type;
        this.id = num;
    }

    /**
     * id is used to identify a certain ItemTile of a specific ItemTileType.
     * @return the number of the tile.
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @return the type of the ItemTile.
     */
    public ItemTileType getType() {
        return type;
    }

    @Override
    public boolean equals(Object obj) {
        ItemTile tile;
        try{
            tile = (ItemTile)obj;
        } catch (ClassCastException e){
            return false;
        }
        return this.type.equals(tile.getType());
    }
}
