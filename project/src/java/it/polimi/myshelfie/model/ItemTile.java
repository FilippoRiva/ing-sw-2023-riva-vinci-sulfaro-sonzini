package it.polimi.myshelfie.model;

public enum ItemTile {
    CAT("Cat"), //verde
    BOOK("Book"), //bianco
    GAME("Game"),//giallo
    FRAME("Frame"),//blu
    TROPHY("Trophy"),//azzurro
    PLANT("Plant"),//rosa
    EMPTY("Empty");

    private final String name;

    ItemTile(String name){
        this.name = name;
    }
}
