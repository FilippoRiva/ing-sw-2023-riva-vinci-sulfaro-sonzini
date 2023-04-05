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
    public int getId() {
        return id;
    }
    public ItemTile[][] getGoal() {
        return goal;
    }
}
