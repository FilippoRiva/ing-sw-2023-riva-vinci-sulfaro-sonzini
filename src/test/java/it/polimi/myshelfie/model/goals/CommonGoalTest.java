package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.Token;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalTest {

    @Test
    void commonGoalStackTest() {
        CommonGoal cg = new CommonGoalCorner();
        cg.pushToken(Token.EIGHT_FIRST);
        cg.pushToken(Token.FOUR_FIRST);
        assertEquals(cg.popToken(),Token.FOUR_FIRST);
        assertEquals(cg.peekToken(),Token.EIGHT_FIRST);
    }
}