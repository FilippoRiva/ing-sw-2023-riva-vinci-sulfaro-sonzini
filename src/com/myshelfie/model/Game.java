package com.myshelfie.model;

import com.myshelfie.model.exceptions.GameFullException;
import com.myshelfie.model.exceptions.NameTakenException;
import com.myshelfie.model.chatting.Chat;
import com.myshelfie.model.goals.CommonGoal;

import java.util.ArrayList;

public class Game {
    String gameName;
    GameState currentState;
    Chat chat;
    CommonGoal[] goals;

    LivingRoom livingRoom;
    ArrayList<Player> players;
    public Game(){
        currentState = GameState.ROOMSETUP;
        chat = new Chat();
        goals = new CommonGoal[2];
        livingRoom = new LivingRoom();
        players = new ArrayList<>();
    }

    public void setGameName(String gameName){
        this.gameName = gameName;
    }

    public void setCurrentState(GameState nextState){
       this.currentState = nextState;
    }

    public void addCommonGoal(CommonGoal goal){
       if(goals[0] == null) goals[0] = goal ;
       else goals[1] = goal;
    }

    public void addPlayer(Player player) throws GameFullException, NameTakenException {
        //TODO eccezioni da implementare
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

    public CommonGoal[] getGoals() {
        return goals;
    }

    public LivingRoom getLivingRoom() {
        return livingRoom;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}
