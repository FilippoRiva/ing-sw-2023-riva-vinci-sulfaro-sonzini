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

    /**
     *
     * @return the room owner's name.
     */
    public String getRoomOwner() {
        return roomOwner;
    }

    /**
     *
     * @return the list of the player names.
     */
    public List<String> getPlayers() {
        return players;
    }

    /**
     *
     * @return the game config of that lobby.
     */
    public GameConfig getGameConfig(){
        return gameConfig;
    }

    /**
     *
     * @return the lobby name.
     */
    public String getName(){
        return name;
    }
}
