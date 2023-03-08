package com.myshelfie.model;

import com.myshelfie.model.goals.PersonalGoal;

import java.util.ArrayList;

public class Player {
    private final String name;
    private int position;
    private final ArrayList<Token> obtainedTokens;
    private PersonalGoal personalGoal;
    private Bookshelf bookshelf;

    public Player(String name){
        this.name = name;
        position = -1;
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

    public void addObtainedToken(Token token){
        obtainedTokens.add(token);
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

    public Bookshelf getBookshelf(){
        //TODO da copiare
        return bookshelf;
    }
}
