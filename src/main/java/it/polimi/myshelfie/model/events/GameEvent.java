package it.polimi.myshelfie.model.events;

/**
 * Represents the actions and phases in the game.
 *                                              <ul>
 *                                                  <li>GAME_START</li>
 *                                                  <li>DRAW</li>
 *                                                  <li>ORGANIZE</li>
 *                                                  <li>INSERT</li>
 *                                                  <li>COUNTING_POINTS</li>
 *                                                  <li>WIN</li>
 *                                              </ul>
 */
public enum GameEvent {
    GAME_START,
    DRAW,
    ORGANIZE,
    INSERT,
    COUNTING_POINTS,
    WIN
}
