package it.polimi.myshelfie.model;

import it.polimi.myshelfie.model.goals.PersonalGoal;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private final String name;
    private ArrayList<EarnedPoint> earnedPoints;
    private final ArrayList<Token> obtainedTokens;
    private List<PersonalGoal> personalGoals;
    private Bookshelf bookshelf;
    private List<ItemTile> takenTiles;


    public Player(String name){
        this.name = name;
        this.earnedPoints = new ArrayList<>();
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
        if(token.getValue()==1) addEarnedPoints(new EarnedPoint(PointType.END_GAME, token.getValue()));
        else addEarnedPoints(new EarnedPoint(PointType.COMMON, token.getValue()));
    }

    public void addEarnedPoints(EarnedPoint p){
        earnedPoints.add(p);
    }

    public ArrayList<EarnedPoint> getEarnedPoints() {
        return earnedPoints;
    }

    public int getPoints(){
        return earnedPoints.stream().mapToInt(EarnedPoint::getValue).sum();
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

    public void calculatePoints(){
        for(PersonalGoal p: personalGoals)
            earnedPoints.add(new EarnedPoint(PointType.PERSONAL, p.achievedPoints(bookshelf.getBookshelfItems())));
        earnedPoints.add(new EarnedPoint(PointType.ADJACENT, bookshelf.achievedAdjacentTiles()));
    }

    /**
     * @return the tiles taken from the board.
     */
    public List<ItemTile> getTakenTiles() {
        return new ArrayList<>(takenTiles);
    }


    public void clearTakenTiles() {
        this.takenTiles = new ArrayList<>();
    }
}
