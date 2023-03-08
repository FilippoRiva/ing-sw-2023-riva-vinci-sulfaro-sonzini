package com.myshelfie.model;

public enum ItemTile {
    CAT("Cat"),BOOK("Book"),GAME("Game"),FRAME("Frame"),TROPHY("Trophy"),PLANT("Plant"),EMPTY("Empty");

    private final String name;

    ItemTile(String name){
        this.name = name;
    }
}
