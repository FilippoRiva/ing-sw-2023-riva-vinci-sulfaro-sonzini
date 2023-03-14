package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.Token;

import java.util.EmptyStackException;
import java.util.Stack;

public abstract class CommonGoal {
    private final Stack<Token> tokens = new Stack<>();

    public abstract boolean isVerified(ItemTile[][] bookshelf);
    public Token peekToken() throws EmptyStackException {
        return tokens.peek();
    }

    public void pushToken(Token token){
        tokens.push(token);
    }

    public Token popToken() throws EmptyStackException {
        return tokens.pop();
    }
}
