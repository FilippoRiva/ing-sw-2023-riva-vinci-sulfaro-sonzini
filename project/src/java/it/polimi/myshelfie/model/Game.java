package it.polimi.myshelfie.model;

import it.polimi.myshelfie.model.exceptions.GameFullException;
import it.polimi.myshelfie.model.exceptions.NameTakenException;
import it.polimi.myshelfie.model.chatting.Chat;
import it.polimi.myshelfie.model.goals.CommonGoal;

import java.util.ArrayList;

public class Game {
    String gameName;
    GameState currentState;
    Chat chat;
    CommonGoal[] commonGoals;
    LivingRoom livingRoom;
    ArrayList<Player> players;

    public Game(){
        //TODO initial state
        currentState = GameState.ROOMSETUP;
        chat = new Chat();
        commonGoals = new CommonGoal[2];
        livingRoom = new LivingRoom();
        players = new ArrayList<>();
    }

    public void setGameName(String gameName){
        this.gameName = gameName;
    }

    public void setCurrentState(GameState nextState){
       this.currentState = nextState;
    }

    public void setCommonGoal(CommonGoal[] goals){
       this.commonGoals = goals;
    }

    public void addPlayer(Player player) throws GameFullException, NameTakenException {
        players.add(player);
    }

    public String getGameName(){
        return gameName;
    }

    public GameState getCurrentState(){
        return currentState;
    }

    public Chat getChat() {
        return chat;
    }

    public CommonGoal[] getCommonGoals() {
        return commonGoals;
    }

    public LivingRoom getLivingRoom() {
        return livingRoom;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}
