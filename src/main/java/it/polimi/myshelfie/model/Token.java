package it.polimi.myshelfie.model;

public enum Token {
    ENDGAME(1,0),
    TWO_FIRST(2,1),
    FOUR_FIRST(4,1),
    SIX_FIRST(6,1),
    EIGHT_FIRST(8,1),
    TWO_SECOND(2,2),
    FOUR_SECOND(4,2),
    SIX_SECOND(6,2),
    EIGHT_SECOND(8,2);

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
