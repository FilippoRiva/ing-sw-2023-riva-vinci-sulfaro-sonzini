package it.polimi.myshelfie.model;

public class ItemTile {
    private final ItemTileType type;
    private final int id;
    public ItemTile(ItemTileType type, int num){
        this.type = type;
        this.id = num;
    }

    public int getId() {
        return id;
    }
    public ItemTileType getType() {
        return type;
    }
}
