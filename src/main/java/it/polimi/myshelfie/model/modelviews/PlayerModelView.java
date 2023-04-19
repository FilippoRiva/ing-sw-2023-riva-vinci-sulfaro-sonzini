package it.polimi.myshelfie.model.modelviews;

import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.Player;
import it.polimi.myshelfie.model.Token;
import it.polimi.myshelfie.model.goals.PersonalGoal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PlayerModelView implements Serializable {
    final private String name;
    final private List<PersonalGoalModelView> personalGoals;
    final private int points;
    final private List<ItemTile> takenTiles;
    final private List<Token> tokens;
    final private ItemTile[][] bookshelf;
    public PlayerModelView(Player player){
        this.name = player.getName();
        List<PersonalGoal> goals = player.getPersonalGoals();
        this.personalGoals = new ArrayList<>();
        for(PersonalGoal p : goals){
            this.personalGoals.add(new PersonalGoalModelView(p));
        }
        this.points = player.getPoints();
        this.tokens = player.getObtainedTokens();
        this.bookshelf = player.getBookshelf().getBookshelfItems();
        this.takenTiles = player.getTakenTiles();
    }

    public String getName() {
        return name;
    }

    public List<PersonalGoalModelView> getPersonalGoals() {
        return personalGoals;
    }

    public int getPoints() {
        return points;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public ItemTile[][] getBookshelf() {
        return bookshelf;
    }

    public List<ItemTile> getTakenTiles() {
        return takenTiles;
    }
}
