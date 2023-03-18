package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.Token;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalTest {

    @Test
    void commonGoalStackTest() {
        CommonGoal cg = new CommonGoalCorner();
        cg.pushToken(Token.EIGHTFIRST);
        cg.pushToken(Token.FOURFIRST);
        assertEquals(cg.popToken(),Token.FOURFIRST);
        assertEquals(cg.peekToken(),Token.EIGHTFIRST);
    }
}