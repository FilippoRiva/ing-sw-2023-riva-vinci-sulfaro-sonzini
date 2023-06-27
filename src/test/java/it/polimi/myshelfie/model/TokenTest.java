package it.polimi.myshelfie.model;

import org.junit.jupiter.api.Test;

class TokenTest {
    @Test
    public void TokensTest() {
        for (Token token : Token.values()) {
            token.getValue();
            token.getRomanNumeral();
            token.name();
        }
    }
}