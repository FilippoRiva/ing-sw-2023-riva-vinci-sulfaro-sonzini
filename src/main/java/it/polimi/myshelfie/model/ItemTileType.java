package it.polimi.myshelfie.model;

import java.util.Random;

public enum ItemTileType {
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
     * @return a random ItemTile excluding ItemTile.EMPTY
     */
    public static ItemTileType random(){
        Random rand = new Random();
        int newTilePos = rand.nextInt(ItemTileType.values().length-1);
        return ItemTileType.values()[newTilePos];
    }

    ItemTileType(String name){
        this.name = name;
    }
}
