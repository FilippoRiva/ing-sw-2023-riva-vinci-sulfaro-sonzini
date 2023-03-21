package it.polimi.myshelfie.model;

import java.util.Random;

public enum ItemTile {
    CAT("Cat"),         //Green
    BOOK("Book"),       //White
    GAME("Game"),       //Yellow
    FRAME("Frame"),     //Blue
    TROPHY("Trophy"),   //Light Blue
    PLANT("Plant"),     //Pink
    EMPTY("Empty");

    private final String name;

    /**
     *
     * @return a random ItemTile excluding "ItemTile.EMPTY"
     */
    public static ItemTile random(){
        Random rand = new Random();
        int newTilePos = rand.nextInt(ItemTile.values().length-1);
        return ItemTile.values()[newTilePos];
    }

    ItemTile(String name){
        this.name = name;
    }
}
