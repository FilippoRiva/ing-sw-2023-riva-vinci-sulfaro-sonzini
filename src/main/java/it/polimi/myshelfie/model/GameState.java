package it.polimi.myshelfie.model;

import java.io.Serializable;

public enum GameState implements Serializable {
    LOBBY,
    STARTGAME,
    DRAW,
    ORGANIZE,
    COUNTINGPOINTS,
    REFILL,
    LASTDRAW,
    LASTORGANIZE,
    LASTCOUNTINGPOINTS,
    WIN
}
