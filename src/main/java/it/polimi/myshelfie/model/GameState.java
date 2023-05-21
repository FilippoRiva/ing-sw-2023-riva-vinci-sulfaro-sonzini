package it.polimi.myshelfie.model;

import java.io.Serializable;

public enum GameState implements Serializable {
    LOBBY,
    FIRST_DRAW,
    DRAW,
    ORGANIZE,
    INSERT,
    COUNTING_POINTS,
    LAST_DRAW,
    LAST_ORGANIZE,
    LAST_INSERT,
    LAST_COUNTING_POINTS,
    WIN
}
