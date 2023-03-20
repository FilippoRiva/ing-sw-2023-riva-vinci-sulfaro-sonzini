package it.polimi.myshelfie.model.exceptions;

import org.junit.jupiter.api.Test;

class ExceptionsTest {
    @Test
    public void exceptionsTest() {
        GameFullException gfe = new GameFullException();
        NameTakenException nte = new NameTakenException();
        FullColumnException fce = new FullColumnException("column full" +
                "");
    }
}