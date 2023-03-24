package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.Token;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalFactoryTest {
    @Test
    void createCommonGoals(){
        try{
            CommonGoalDefaultsFactory.getGoals(1,2);
            fail();
        }catch (InvalidParameterException ignored){
        }
        try{
            CommonGoalDefaultsFactory.getGoals(3,0);
            fail();
        }catch (InvalidParameterException ignored){
        }

        List<CommonGoal> commonGoals = CommonGoalDefaultsFactory.getGoals(2, 2);
        assertNotEquals(commonGoals.get(0), commonGoals.get(1));
        assertEquals(commonGoals.get(0).popToken(), Token.EIGHT_FIRST);
        assertEquals(commonGoals.get(0).popToken(), Token.FOUR_FIRST);
        assertEquals(commonGoals.get(1).popToken(), Token.EIGHT_SECOND);
        assertEquals(commonGoals.get(1).popToken(), Token.FOUR_SECOND);

        commonGoals = CommonGoalDefaultsFactory.getGoals(3, 2);
        assertNotEquals(commonGoals.get(0), commonGoals.get(1));
        assertEquals(commonGoals.get(0).popToken(), Token.EIGHT_FIRST);
        assertEquals(commonGoals.get(0).popToken(), Token.SIX_FIRST);
        assertEquals(commonGoals.get(0).popToken(), Token.FOUR_FIRST);
        assertEquals(commonGoals.get(1).popToken(), Token.EIGHT_SECOND);
        assertEquals(commonGoals.get(1).popToken(), Token.SIX_SECOND);
        assertEquals(commonGoals.get(1).popToken(), Token.FOUR_SECOND);

        commonGoals = CommonGoalDefaultsFactory.getGoals(4, 2);
        assertNotEquals(commonGoals.get(0), commonGoals.get(1));
        assertEquals(commonGoals.get(0).popToken(), Token.EIGHT_FIRST);
        assertEquals(commonGoals.get(0).popToken(), Token.SIX_FIRST);
        assertEquals(commonGoals.get(0).popToken(), Token.FOUR_FIRST);
        assertEquals(commonGoals.get(0).popToken(), Token.TWO_FIRST);
        assertEquals(commonGoals.get(1).popToken(), Token.EIGHT_SECOND);
        assertEquals(commonGoals.get(1).popToken(), Token.SIX_SECOND);
        assertEquals(commonGoals.get(1).popToken(), Token.FOUR_SECOND);
        assertEquals(commonGoals.get(1).popToken(), Token.TWO_SECOND);

        commonGoals = CommonGoalDefaultsFactory.getGoals(4, 12);
        for(int i = 0; i<commonGoals.size()-1;i++) assertNotEquals(commonGoals.get(i).getId(), commonGoals.get(i+1).getId());
        for(int i = 0; i<commonGoals.size()-1;i++) assertNotEquals(commonGoals.get(i), commonGoals.get(i+1));
        for(int i = 0; i<commonGoals.size();i++){
            if(i%2==0){
                assertEquals(commonGoals.get(i).popToken(), Token.EIGHT_FIRST);
                assertEquals(commonGoals.get(i).popToken(), Token.SIX_FIRST);
                assertEquals(commonGoals.get(i).popToken(), Token.FOUR_FIRST);
                assertEquals(commonGoals.get(i).popToken(), Token.TWO_FIRST);
            }
            else {
                assertEquals(commonGoals.get(i).popToken(), Token.EIGHT_SECOND);
                assertEquals(commonGoals.get(i).popToken(), Token.SIX_SECOND);
                assertEquals(commonGoals.get(i).popToken(), Token.FOUR_SECOND);
                assertEquals(commonGoals.get(i).popToken(), Token.TWO_SECOND);
            }
        }
    }
}
