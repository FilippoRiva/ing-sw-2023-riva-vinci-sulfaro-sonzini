package it.polimi.myshelfie.model.modelviews;

import it.polimi.myshelfie.GameConfig;
import it.polimi.myshelfie.model.Game;

import java.io.Serializable;
import java.util.List;

/**
 * Represents the lobby model view.
 */
public class LobbyModelView implements Serializable {
    private final String name;
    private final List<String> players;
    private final String roomOwner;
    private final GameConfig gameConfig;

    /**
     * Constructor.
     *
     * @param game the game of this lobby.
     */
    public LobbyModelView(Game game) {
        this.players = game.getPlayersNames();
        this.roomOwner = game.getRoomOwner();
        this.gameConfig = game.getGameConfig();
        this.name = game.getName();
    }

    /**
     * Returns the room owner's name.
     *
     * @return the room owner's name.
     */
    public String getRoomOwner() {
        return roomOwner;
    }

    /**
     * Returns the list of the player names.
     * @return the list of the player names.
     */
    public List<String> getPlayers() {
        return players;
    }

    /**
     * Returns the game config of that lobby.
     * @return the game config of that lobby.
     */
    public GameConfig getGameConfig() {
        return gameConfig;
    }

    /**
     * Returns the lobby name.
     * @return the lobby name.
     */
    public String getName() {
        return name;
    }

    /**
     * Checks whether the player is the room owner.
     *
     * @param username the player to check.
     * @return true if he's the room owner, false otherwise.
     */
    public boolean isRoomOwner(String username) {
        return roomOwner.equals(username);
    }
}
