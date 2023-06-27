package it.polimi.myshelfie.model;


/**
 * Contains all the token of the game.
 */
public enum Token {
    ENDGAME(1, 0),
    TWO_FIRST(2, 1),
    FOUR_FIRST(4, 1),
    SIX_FIRST(6, 1),
    EIGHT_FIRST(8, 1),
    TWO_SECOND(2, 2),
    FOUR_SECOND(4, 2),
    SIX_SECOND(6, 2),
    EIGHT_SECOND(8, 2);

    private final int value;
    private final int romanNumeral;

    /**
     * Constructor.
     *
     * @param value        the value of the token.
     * @param romanNumeral the roman numeral on the token.
     */
    Token(int value, int romanNumeral) {
        this.value = value;
        this.romanNumeral = romanNumeral;
    }

    /**
     * Returns the roman numeral on the back of the token card.
     *
     * @return the roman numeral.
     */
    public int getRomanNumeral() {
        return romanNumeral;
    }

    /**
     * Returns the numeric value (2, 4, 6 or 8) on the token card.
     *
     * @return the token value.
     */
    public int getValue() {
        return value;
    }
}
