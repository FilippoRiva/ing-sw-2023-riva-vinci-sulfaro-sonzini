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

    /**
     * Assigns the personal goal to the player.
     * @param personalGoals is the list of the player's personal goals.
     */
    public void setPersonalGoals(List<PersonalGoal> personalGoals) {
        this.personalGoals = new ArrayList<>(personalGoals);
    }

    /**
     * Assigns the bookshelf to the player.
     * @param bookshelf is the player's bookshelf.
     */
    public void setBookshelf(Bookshelf bookshelf){
        this.bookshelf = bookshelf;
    }

    /**
     * Adds the token obtained from a common goal to the player who completed it.
     * @param token is the token obtained from a completed common goal.
     */
    public void addObtainedToken(Token token){
        if(token == null) return;
        obtainedTokens.add(token);
    }

    /**
     * @return the player's points.
     */
    public int getPoints() {
        return points;
    }

    /**
     * Adds the obtained points of the player in that turn to the points he already has.
     * @param points are the points obtained in that turn from the player.
     */
    public void addPoints(int points) {
        this.points += points;
    }

    /**
     * @return the player name.
     */
    public String getName(){
        return name;
    }

    /**
     * Return the tokens obtained by the player.
     * @return a list of tokens.
     */
    public List<Token> getObtainedTokens() {
        return new ArrayList<>(obtainedTokens);
    }

    /**
     * Return the list of the player's personal goals.
     * @return the player's list of personal goals.
     */
    public synchronized List<PersonalGoal> getPersonalGoals(){
        return new ArrayList<>(personalGoals);
    }

    /**
     * @return the player's bookshelf.
     */
    public Bookshelf getBookshelf(){return bookshelf;}

    /**
     * Sets the taken tiles from the board to the player.
     * @param takenTiles is the list of the tiles taken from the board.
     */
    public void setTakenTiles(List<ItemTile> takenTiles) {
        this.takenTiles = takenTiles;
    }


    /**
     * @return the tiles taken from the board.
     */
    public List<ItemTile> getTakenTiles() {
        return new ArrayList<>(takenTiles);
    }

    /**
     * Calculate the points obtained by the player at the end of his turn.
     */
    public void calculatePoints() {
        for(PersonalGoal personalGoal : personalGoals){
            points += personalGoal.achievedPoints(bookshelf.getBookshelfItems()) + bookshelf.achievedAdjacentTiles();
        }
    }

    public void clearTakenTiles() {
        this.takenTiles = new ArrayList<>();
    }
}
