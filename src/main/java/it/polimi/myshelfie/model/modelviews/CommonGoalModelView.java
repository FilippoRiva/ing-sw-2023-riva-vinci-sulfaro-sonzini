package it.polimi.myshelfie.model.modelviews;

import it.polimi.myshelfie.model.Token;
import it.polimi.myshelfie.model.goals.CommonGoal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the common goal model view.
 */
public class CommonGoalModelView implements Serializable {
    private final List<Token> tokens;
    private final int id;
    private final String description;

    /**
     * Default Constructor.
     *
     * @param commonGoal the common goal I want the model view.
     */
    public CommonGoalModelView(CommonGoal commonGoal) {
        this.tokens = commonGoal.getTokens();
        this.id = commonGoal.getId();
        this.description = commonGoal.getDescription();
    }

    /**
     * Copy Constructor.
     *
     * @param toCopy the common model view to copy.
     */
    public CommonGoalModelView(CommonGoalModelView toCopy) {
        this.tokens = new ArrayList<>(toCopy.tokens);
        this.id = toCopy.id;
        this.description = toCopy.description;
    }

    /**
     * Returns the list of tokens on the common goal.
     *
     * @return the list of tokens on the common goal.
     */
    public List<Token> getTokens() {
        return tokens;
    }

    /**
     * Returns the id of that common goal.
     * @return the id of that common goal.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the description of that common goal.
     * @return the description of that common goal.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Modifies the attribute "tokens". It changes it with the difference between this one and the one to confront.
     *
     * @param theNewOne the common goal to confronts.
     */
    public void diff(CommonGoalModelView theNewOne) {
        this.tokens.removeAll(theNewOne.tokens);
    }
}
