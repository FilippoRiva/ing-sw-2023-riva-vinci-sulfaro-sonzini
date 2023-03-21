package it.polimi.myshelfie.model;

public class ItemTile {
    private final ItemTileType type;
    private final int num;
    public ItemTile(ItemTileType type, int num){
        this.type = type;
        this.num = num;
    }

    public int getNum() {
        return num;
    }
    public ItemTileType getType() {
        return type;
    }
}
