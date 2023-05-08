package it.polimi.myshelfie.model.modelviews;

import it.polimi.myshelfie.model.Token;
import it.polimi.myshelfie.model.goals.CommonGoal;

import java.io.Serializable;
import java.util.List;

public class CommonGoalModelView implements Serializable {
    private List<Token> tokens;
    private final int id;
    private final String description;
    public CommonGoalModelView(CommonGoal commonGoal){
            this.tokens = commonGoal.getTokens();
            this.id = commonGoal.getId();
            this.description = commonGoal.getDescription();
    }

    /**
     *
     * @return the list of tokens on the common goal.
     */
    public List<Token> getTokens(){
        return tokens;
    }

    /**
     *
     * @return the id of that common goal.
     */
    public int getId(){
        return id;
    }

    /**
     *
     * @return the description of that common goal.
     */
    public String getDescription(){
        return description;
    }

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }
}
