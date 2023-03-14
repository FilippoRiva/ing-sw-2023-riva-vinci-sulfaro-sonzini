package it.polimi.myshelfie.model;

import java.util.ArrayList;

public class LivingRoom {
    ArrayList<ItemTile> bag;

    //TODO Matrix?
    ItemTile[][] table;

    public LivingRoom(){
        //TODO there or in Controller?
        ItemTile[] itemTiles = ItemTile.values();
        for(int i = 0; i < itemTiles.length; i++){
            for(int j = 0; j < 22; j++){
                bag.add(itemTiles[i]);
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
