package it.polimi.myshelfie.model;

/**
 * Represents the type of the points. They can come from adjacent tiles, common or personal goals and end game.
 * Used by the EarnedPoint class.
 */
public enum PointType {
    ADJACENT,
    COMMON,
    PERSONAL,
    END_GAME
}
