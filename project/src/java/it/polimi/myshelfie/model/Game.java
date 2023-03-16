package it.polimi.myshelfie.model;

import it.polimi.myshelfie.GameConfig;
import it.polimi.myshelfie.model.exceptions.GameFullException;
import it.polimi.myshelfie.model.exceptions.NameTakenException;
import it.polimi.myshelfie.model.chatting.Chat;
import it.polimi.myshelfie.model.goals.CommonGoal;

import java.util.ArrayList;

public class Game {
    GameConfig gameConfig;
    String gameName;
    GameState currentState;
    Chat chat;
    CommonGoal[] commonGoals;
    LivingRoom livingRoom;
    ArrayList<Player> players;

    public Game(String gameName, GameConfig gameConfig){
        this.gameConfig = gameConfig;
        this.gameName = gameName;
        currentState = GameState.LOBBY;
        chat = new Chat();
        commonGoals = new CommonGoal[gameConfig.getNumCommonGoal()];
        livingRoom = new LivingRoom(gameConfig.getNumTilesPerType());
        players = new ArrayList<>();
    }

    public void addPlayer(Player player) throws GameFullException, NameTakenException {
        players.add(player);
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
