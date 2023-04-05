package it.polimi.myshelfie.model.modelviews;

import it.polimi.myshelfie.GameConfig;
import it.polimi.myshelfie.model.Game;

import java.io.Serializable;
import java.util.List;

public class LobbyModelView implements Serializable {
    private final String name;
    private final List<String> players;
    private final String roomOwner;
    private final GameConfig gameConfig;
    public LobbyModelView(Game game){
        this.players = game.getPlayersNames();
        this.roomOwner = game.getRoomOwner();
        this.gameConfig = game.getGameConfig();
        this.name = game.getName();
    }
    public String getRoomOwner() {
        return roomOwner;
    }
    public List<String> getPlayers() {
        return players;
    }
    public GameConfig getGameConfig(){
        return gameConfig;
    }
    public String getName(){
        return name;
    }
}
