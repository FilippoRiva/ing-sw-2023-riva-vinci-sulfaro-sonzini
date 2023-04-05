package it.polimi.myshelfie.model;

import java.io.Serializable;
import java.util.Random;

public enum ItemTileType implements Serializable {
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
     * @return a random ItemTile excluding the EMPTY tile
     */
    public static ItemTileType random(){
        Random rand = new Random();
        int tilePos = rand.nextInt(ItemTileType.values().length-1);
        return ItemTileType.values()[tilePos];
    }

    ItemTileType(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
