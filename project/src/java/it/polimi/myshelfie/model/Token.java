package it.polimi.myshelfie.model;

public enum Token {
    ENDGAME(1,0),
    TWOFIRST(2,1),
    FOURFIRST(4,1),
    SIXFIRST(6,1),
    EIGHTFIRST(8,1),
    TWOSECOND(2,2),
    FOURSECOND(4,2),
    SIXSECOND(6,2),
    EIGHTSECOND(8,2);

    private final int value;
    private final int romanNumeral;

    public int getRomanNumeral() {
        return romanNumeral;
    }

    public int getValue() {
        return value;
    }

    Token(int value, int romanNumeral){
        this.value = value;
        this.romanNumeral = romanNumeral;
    }
}
