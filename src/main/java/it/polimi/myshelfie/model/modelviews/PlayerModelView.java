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
    private int points;
    private List<ItemTile> takenTiles;
    private List<Token> tokens;
    private ItemTile[][] bookshelf;
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

    /**
     *
     * @return the player name.
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return the player's list of personal goals.
     */
    public List<PersonalGoalModelView> getPersonalGoals() {
        return personalGoals;
    }

    /**
     *
     * @return the player's points.
     */
    public int getPoints() {
        return points;
    }

    /**
     *
     * @return the player's list of obtained tokens.
     */
    public List<Token> getTokens() {
        return tokens;
    }

    /**
     *
     * @return a copy of the player's bookshelf.
     */
    public ItemTile[][] getBookshelf() {
        return bookshelf;
    }

    /**
     *
     * @return the list of the tiles taken from the player.
     */
    public List<ItemTile> getTakenTiles() {
        return takenTiles;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setTakenTiles(List<ItemTile> takenTiles) {
        this.takenTiles = takenTiles;
    }

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }

    public void setBookshelf(ItemTile[][] bookshelf) {
        this.bookshelf = bookshelf;
    }
}


