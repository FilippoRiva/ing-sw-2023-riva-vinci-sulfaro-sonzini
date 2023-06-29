package it.polimi.myshelfie.model;

import java.io.Serializable;

/**
 * Represents the earned points during a turn.
 */
public class EarnedPoint implements Serializable {

    private final PointType type;
    private final int value;

    /**
     * Constructor.
     *
     * @param type  the type of the point (where it comes from)
     * @param value the value of the points.
     */
    public EarnedPoint(PointType type, int value) {
        this.type = type;
        this.value = value;
    }

    /**
     * Returns the point type.
     *
     * @return the point type.
     */
    public PointType getType() {
        return type;
    }

    /**
     * Returns the point value.
     * @return the point value.
     */
    public int getValue() {
        return value;
    }


}
