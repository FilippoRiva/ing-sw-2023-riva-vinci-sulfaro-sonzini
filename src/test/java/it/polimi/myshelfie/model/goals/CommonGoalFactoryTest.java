package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.Token;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class CommonGoalFactoryTest {
    @Test
    void createCommonGoals(){
        List<CommonGoal> commonGoals = CommonGoalFactory.getGoals(2, 2);
        assertNotEquals(commonGoals.get(0), commonGoals.get(1));
        assertEquals(commonGoals.get(0).popToken(), Token.EIGHT_FIRST);
        assertEquals(commonGoals.get(0).popToken(), Token.FOUR_FIRST);
        assertEquals(commonGoals.get(1).popToken(), Token.EIGHT_SECOND);
        assertEquals(commonGoals.get(1).popToken(), Token.FOUR_SECOND);

        commonGoals = CommonGoalFactory.getGoals(3, 2);
        assertNotEquals(commonGoals.get(0), commonGoals.get(1));
        assertEquals(commonGoals.get(0).popToken(), Token.EIGHT_FIRST);
        assertEquals(commonGoals.get(0).popToken(), Token.SIX_FIRST);
        assertEquals(commonGoals.get(1).popToken(), Token.EIGHT_SECOND);
        assertEquals(commonGoals.get(1).popToken(), Token.SIX_SECOND);

        commonGoals = CommonGoalFactory.getGoals(4, 2);
        assertNotEquals(commonGoals.get(0), commonGoals.get(1));
        assertEquals(commonGoals.get(0).popToken(), Token.EIGHT_FIRST);
        assertEquals(commonGoals.get(0).popToken(), Token.SIX_FIRST);
        assertEquals(commonGoals.get(1).popToken(), Token.EIGHT_SECOND);
        assertEquals(commonGoals.get(1).popToken(), Token.SIX_SECOND);
    }
}
