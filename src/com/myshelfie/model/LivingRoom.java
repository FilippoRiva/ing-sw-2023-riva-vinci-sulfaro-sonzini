package com.myshelfie.model;

import java.util.ArrayList;

public class LivingRoom {
    ArrayList<ItemTile> bag;
    ItemTile[][] items;

    public LivingRoom(){
        ItemTile[] itemTiles = ItemTile.values();
        for(int i = 0; i < itemTiles.length; i++){
            for(int j = 0; j < 22; j++){
                bag.add(itemTiles[i]);
            }
        }

        items = new ItemTile[9][9];
        //TODO no-go cells
    }

    public ArrayList<ItemTile> getBag() {
        return bag;
    }

    public ItemTile[][] getItems() {
        return items;
    }

    public void setItems(ItemTile[][] items) {
        this.items = items;
    }

    public void setBag(ArrayList<ItemTile> bag) {
        this.bag = bag;
    }


    //TODO take to change only that or only set to change all?
    public void takeItem(int x, int y){

    }
}
