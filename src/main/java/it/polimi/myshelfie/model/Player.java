package it.polimi.myshelfie.model;

import it.polimi.myshelfie.model.goals.PersonalGoal;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private final String name;
    private int points;
    private final ArrayList<Token> obtainedTokens;
    private PersonalGoal personalGoal;
    private Bookshelf bookshelf;
    private List<ItemTile> tilesTaken;


    public Player(String name){
        this.name = name;
        points = 0;
        obtainedTokens = new ArrayList<>();
    }

    public void setPersonalGoal(PersonalGoal personalGoal) {
        this.personalGoal = personalGoal;
    }

    public void setBookshelf(Bookshelf bookshelf){
        this.bookshelf = bookshelf;
    }

    /**
     *
     * @param token is the token obtained from a completed common goal
     */
    public void addObtainedToken(Token token){
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

    public ArrayList<Token> getObtainedTokens() {
        return new ArrayList<>(obtainedTokens);
    }

    public PersonalGoal getPersonalGoal(){
        return personalGoal;
    }
    public Bookshelf getBookshelf(){return bookshelf;}

    public void setTilesTaken(List<ItemTile> tilesTaken) {
        this.tilesTaken = tilesTaken;
    }

    public List<ItemTile> getTilesTaken() {
        return tilesTaken;
    }

    public void calculatePoints() {
        //TODO implementation of adjacent tile point
        points += personalGoal.achievedPoints(bookshelf.getBookshelfItems()) + bookshelf.achievedAdjacentTiles();
    }
}
