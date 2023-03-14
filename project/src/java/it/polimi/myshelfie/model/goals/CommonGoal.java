package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.Token;

import java.util.EmptyStackException;
import java.util.Stack;

public class CommonGoal {
    CommonGoalType type;

    private final Stack<Token> tokens;

    public CommonGoal(CommonGoalType commonGoalType){
        this.type = commonGoalType;
        this.tokens = new Stack<>();
    }

    public Token peekToken() throws EmptyStackException {
        return tokens.peek();
    }

    //TODO remove the possibility to push during the game?
    public void pushToken(Token token){
        tokens.push(token);
    }

    public Token popToken() throws EmptyStackException {
        return tokens.pop();
    }

    public CommonGoalType getType(){
        return type;
    }

    public String getDescription(){
        return type.getDescription();
    }
}
