package it.polimi.myshelfie.model;

import java.io.Serializable;

public class EarnedPoint implements Serializable {

    private PointType type;
    private int value;

    public EarnedPoint(PointType type, int value) {
        this.type = type;
        this.value = value;
    }

    public PointType getType() {
        return type;
    }

    public int getValue() {
        return value;
    }


}
