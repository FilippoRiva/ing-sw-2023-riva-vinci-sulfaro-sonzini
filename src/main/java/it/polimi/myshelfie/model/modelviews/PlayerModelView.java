package it.polimi.myshelfie.model.modelviews;

import it.polimi.myshelfie.model.EarnedPoint;
import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.Player;
import it.polimi.myshelfie.model.Token;
import it.polimi.myshelfie.model.goals.PersonalGoal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PlayerModelView implements Serializable {
    private final String name;
    private final List<PersonalGoalModelView> personalGoals;
    private ArrayList<EarnedPoint> earnedPoints;
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
        this.earnedPoints=player.getEarnedPoints();
        this.tokens = player.getObtainedTokens();
        this.bookshelf = player.getBookshelf().getBookshelfItems();
        this.takenTiles = player.getTakenTiles();
    }

    public PlayerModelView(PlayerModelView toCopy){
        this.name = toCopy.name;
        this.personalGoals = new ArrayList<>();
        for(PersonalGoalModelView p : toCopy.personalGoals){
            this.personalGoals.add(new PersonalGoalModelView(p));
        }
        this.earnedPoints=toCopy.earnedPoints;
        this.takenTiles = new ArrayList<>(toCopy.getTakenTiles());
        this.tokens = new ArrayList<>(toCopy.tokens);
        this.bookshelf = new ItemTile[toCopy.bookshelf.length][toCopy.bookshelf[0].length];
        for(int i = 0; i < toCopy.bookshelf.length; i++){
            System.arraycopy(toCopy.bookshelf[i], 0, this.bookshelf[i], 0, toCopy.bookshelf[0].length);
        }
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
        return earnedPoints.stream().mapToInt(EarnedPoint::getValue).sum();
    }

    public ArrayList<EarnedPoint> getEarnedPoints(){
        return earnedPoints;
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
        return new ArrayList<>(takenTiles);
    }


    protected void setTakenTiles(List<ItemTile> takenTiles) {
        this.takenTiles = takenTiles;
    }

    protected void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }

    public void setBookshelf(ItemTile[][] bookshelf) {
        this.bookshelf = bookshelf;
    }

    public void setEarnedPoints(ArrayList<EarnedPoint> e){this.earnedPoints=e;}

    public void diff(PlayerModelView p) {
        if(this.getPoints()==p.getPoints()) this.earnedPoints = new ArrayList<>();
        else this.earnedPoints = p.earnedPoints;
        ArrayList<ItemTile> tempTiles = new ArrayList<>(p.takenTiles);
        tempTiles.removeAll(this.takenTiles);
        this.takenTiles = tempTiles;
        ArrayList<Token> tempTokens = new ArrayList<>(p.tokens);
        tempTokens.removeAll(this.tokens);
        this.tokens = tempTokens;
        if(p.bookshelf.length!=this.bookshelf.length ||
        p.bookshelf[0].length!=this.bookshelf[0].length) this.bookshelf=null;
        else {
            for(int i = 0; i < bookshelf.length; i++){
                for(int j = 0; j < bookshelf[0].length; j++){
                    this.bookshelf[i][j] = this.bookshelf[i][j].getType()==p.bookshelf[i][j].getType() ?
                            null : p.bookshelf[i][j];
                }
            }
        }
    }
}


