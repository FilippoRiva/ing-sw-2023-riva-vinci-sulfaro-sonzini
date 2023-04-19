package it.polimi.myshelfie.model;

import it.polimi.myshelfie.model.goals.PersonalGoal;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private final String name;
    private int points;
    private final ArrayList<Token> obtainedTokens;
    private List<PersonalGoal> personalGoals;
    private Bookshelf bookshelf;
    private List<ItemTile> takenTiles;


    public Player(String name){
        this.name = name;
        this.points = 0;
        this.obtainedTokens = new ArrayList<>();
        this. takenTiles = new ArrayList<>();
    }

    public void setPersonalGoals(List<PersonalGoal> personalGoals) {
        this.personalGoals = new ArrayList<>(personalGoals);
    }

    public void setBookshelf(Bookshelf bookshelf){
        this.bookshelf = bookshelf;
    }

    /**
     *
     * @param token is the token obtained from a completed common goal
     */
    public void addObtainedToken(Token token){
        if(token == null) return;
        obtainedTokens.add(token);
    }

    public int getPoints() {
        return points;
    }

    /**
     *
     * @param points are the points obtained in that turn from the player
     */
    public void addPoints(int points) {
        this.points += points;
    }

    public String getName(){
        return name;
    }

    public List<Token> getObtainedTokens() {
        return new ArrayList<>(obtainedTokens);
    }

    public synchronized List<PersonalGoal> getPersonalGoals(){
        return new ArrayList<>(personalGoals);
    }
    public Bookshelf getBookshelf(){return bookshelf;}

    public void setTakenTiles(List<ItemTile> takenTiles) {
        this.takenTiles = takenTiles;
    }

    public List<ItemTile> getTakenTiles() {
        return new ArrayList<>(takenTiles);
    }

    public void calculatePoints() {
        for(PersonalGoal personalGoal : personalGoals){
            points += personalGoal.achievedPoints(bookshelf.getBookshelfItems()) + bookshelf.achievedAdjacentTiles();
        }
    }

    public void clearTakenTiles() {
        this.takenTiles = new ArrayList<>();
    }
}
