package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.Token;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

/**
 * Represents a generic common goal. It has a description, a stack of token and methods to handle them.
 */
public abstract class CommonGoal {
    private final Stack<Token> tokens = new Stack<>();

    /**
     * Used to verify if a specific common goal has been achieved.
     * @param bookshelf is the player's bookshelf that has to be checked.
     * @return true or false.
     */
    public abstract boolean isVerified(ItemTile[][] bookshelf);

    /**
     *
     * @return the id of the common goal.
     */
    public abstract int getId();

    /**
     * Checks which token is on the top of the stack.
     * @return the token on the top of the stack on a common goal.
     * @throws EmptyStackException if there are no tokens.
     */
    public Token peekToken() throws EmptyStackException {
        return tokens.peek();
    }

    /**
     * Adds a token on the stack.
     * @param token the token to insert on the top of the stack.
     */
    public void pushToken(Token token){
        tokens.push(token);
    }

    /**
     * Removes a token from the stack of a common goal.
     * @return the token removed.
     */
    public Token popToken(){
        return tokens.pop();
    }

    /**
     *
     * @return the list of the tokens on the common goal.
     */
    public List<Token> getTokens() {
        return new ArrayList<>(tokens.stream().toList());
    }

    /**
     *
     * @return the description string of the common goal.
     */
    public abstract String getDescription();
}
