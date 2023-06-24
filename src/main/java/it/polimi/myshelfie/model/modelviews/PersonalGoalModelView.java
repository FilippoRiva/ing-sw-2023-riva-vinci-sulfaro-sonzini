package it.polimi.myshelfie.model.modelviews;

import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.goals.PersonalGoal;

import java.io.Serializable;

public class PersonalGoalModelView implements Serializable {
    private final int id;
    private final ItemTile[][] goal;
    public PersonalGoalModelView(PersonalGoal pg){
        this.id = pg.getId();
        this.goal = pg.getGoal();
    }

    public PersonalGoalModelView(PersonalGoalModelView toCopy){
        this.id = toCopy.id;
        this.goal = new ItemTile[toCopy.goal.length][toCopy.goal[0].length];
        for(int i = 0; i < toCopy.goal.length; i++){
            System.arraycopy(toCopy.goal[i], 0, this.goal[i], 0, toCopy.goal[0].length);
        }
    }

    /**
     *
     * @return the id of that personal goal.
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @return a copy of the personal goal.
     */
    public ItemTile[][] getGoal() {
        return goal;
    }
}
