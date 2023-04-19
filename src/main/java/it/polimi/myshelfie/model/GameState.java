package it.polimi.myshelfie.model;

import java.io.Serializable;

public enum GameState implements Serializable {
    LOBBY,
    DRAW,
    ORGANIZE,
    INSERT,
    COUNTINGPOINTS,
    REFILL,
    LASTDRAW,
    LASTORGANIZE,
    LASTINSERT,
    LASTCOUNTINGPOINTS,
    WIN
}
