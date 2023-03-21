package it.polimi.myshelfie.model;

import it.polimi.myshelfie.model.goals.PersonalGoal;
import java.util.ArrayList;

public class Player {
    private final String name;
    private int position;
    private int points;
    private final ArrayList<Token> obtainedTokens;
    private PersonalGoal personalGoal;
    private Bookshelf bookshelf;

    public Player(String name){
        this.name = name;
        position = -1;
        points = 0;
        obtainedTokens = new ArrayList<>();
    }

    public void setPosition(int position){
        this.position = position;
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

    public void setPoints(int points) {
        this.points = points;
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
    public int getPosition(){
        return position;
    }

    public ArrayList<Token> getObtainedTokens() {
        return new ArrayList<>(obtainedTokens);
    }

    public PersonalGoal getPersonalGoal(){
        return personalGoal;
    }
    public Bookshelf getBookshelf(){return bookshelf;}
}
