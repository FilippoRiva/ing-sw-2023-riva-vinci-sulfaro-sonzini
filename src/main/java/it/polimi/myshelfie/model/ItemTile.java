package it.polimi.myshelfie.model;

import java.io.Serializable;

public class ItemTile implements Serializable {
    private final ItemTileType type;
    private final int id;
    public ItemTile(ItemTileType type, int id){
        this.type = type;
        this.id = id;
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
        if(obj==null || obj.getClass()!=getClass()) return false;
        ItemTile tile;
        tile = (ItemTile)obj;
        return this.type.equals(tile.getType());
    }

    @Override
    public String toString(){
        return type.toString()+" : "+id;
    }
}
