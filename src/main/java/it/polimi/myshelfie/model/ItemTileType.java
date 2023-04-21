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
     * Use to generate a random ItemTileType.
     * @return a random ItemTileType excluding the EMPTY tile.
     */
    public static ItemTileType random(){
        Random rand = new Random();
        int tilePos = rand.nextInt(ItemTileType.values().length-1);
        return ItemTileType.values()[tilePos];
    }

    ItemTileType(String name){
        this.name = name;
    }

    /**
     *
     * @return the name of the ItemTileType (cat, game, frame, book, plant, trophy and empty)
     */
    public String getName() {
        return name;
    }
}
