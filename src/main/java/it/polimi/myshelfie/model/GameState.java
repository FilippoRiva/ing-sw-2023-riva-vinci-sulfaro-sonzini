package it.polimi.myshelfie.model;

import java.io.Serializable;

/**
 * Represents the possible states of the game.
 *                                          <ul>
 *                                              <li>LOBBY</li>
 *                                              <li>FIRST_DRAW</li>
 *                                              <li>DRAW</li>
 *                                              <li>ORGANIZE</li>
 *                                              <li>INSERT</li>
 *                                              <li>COUNTING_POINTS</li>
 *                                              <li>LAST_DRAW</li>
 *                                              <li>LAST_ORGANIZE</li>
 *                                              <li>LAST_INSERT</li>
 *                                              <li>LAST_COUNTING_POINTS</li>
 *                                              <li>WIN</li>
 *                                          </ul>
 */
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
