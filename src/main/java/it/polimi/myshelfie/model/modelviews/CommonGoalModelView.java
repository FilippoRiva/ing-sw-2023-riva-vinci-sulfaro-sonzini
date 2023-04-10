package it.polimi.myshelfie.model.modelviews;

import it.polimi.myshelfie.model.Token;
import it.polimi.myshelfie.model.goals.CommonGoal;

import java.io.Serializable;
import java.util.List;

public class CommonGoalModelView implements Serializable {
    private final List<Token> tokens;
    private final int id;
    private final String description;
    public CommonGoalModelView(CommonGoal commonGoal){
            this.tokens = commonGoal.getTokens();
            this.id = commonGoal.getId();
            this.description = commonGoal.getDescription();
    }

    public List<Token> getTokens(){
        return tokens;
    }

    public int getId(){
        return id;
    }
    public String getDescription(){
        return description;
    }
}
