package it.polimi.myshelfie.model;

import java.util.ArrayList;

public class LivingRoom {
    ArrayList<ItemTile> bag;
    ItemTile[][] table;

    public LivingRoom(int numTilesPerType){
        ItemTile[] itemTiles = ItemTile.values();
        for (ItemTile itemTile : itemTiles) {
            for (int j = 0; j < numTilesPerType; j++) {
                bag.add(itemTile);
            }
        }

        table = new ItemTile[9][9];
        //TODO no-go cells
    }

    public ArrayList<ItemTile> getBag() {
        return bag;
    }

    public ItemTile[][] getTable() {
        return table;
    }

    public void setTable(ItemTile[][] table) {
        this.table = table;
    }

    public void setBag(ArrayList<ItemTile> bag) {
        this.bag = bag;
    }


    //TODO methods for interaction with the table
    public void takeItem(int x, int y){

    }
}
