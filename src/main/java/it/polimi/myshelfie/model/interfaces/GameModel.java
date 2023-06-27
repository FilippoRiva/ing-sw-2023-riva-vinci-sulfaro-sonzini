package it.polimi.myshelfie.model.interfaces;

import it.polimi.myshelfie.model.GameState;
import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.Position;
import it.polimi.myshelfie.model.events.GameEvent;
import it.polimi.myshelfie.model.exceptions.FullColumnException;
import it.polimi.myshelfie.model.exceptions.InvalidParameterException;
import it.polimi.myshelfie.model.modelviews.FullGameModelView;
import it.polimi.myshelfie.model.modelviews.GameModelView;
import it.polimi.myshelfie.utils.Observer;

import java.util.List;

/**
 * Represents the game's interface.
 */
public interface GameModel {

    /**
     * @return the current state previously set by setState().
     */
    GameState getState();

    /**
     * Change the state of the game and notifies every view on the game.
     *
     * @param state the new state.
     */
    void setState(GameState state);

    /**
     * Draws the tiles in the given positions from the livingRoom.
     *
     * @param positions  the positions of the tiles that have to be removed from the board, where the first in the list is the first one to be
     *                   inserted into the player's bookshelf, and the last one is the last one to be inserted.
     * @param playerName the name of the player drawing the tiles.
     * @throws InvalidParameterException if the given positions/column are/is not valid.
     */
    void drawTiles(List<Position> positions, String playerName) throws InvalidParameterException;

    /**
     * Orders the tiles drawn from the living room.
     *
     * @param orderedTiles the tiles taken form the board.
     * @param playerName   the player playing.
     * @throws InvalidParameterException if the player is playing in the wrong turn and if the tiles are different from
     *                                   the ones taken in the draw move.
     */
    void orderChosenTiles(List<ItemTile> orderedTiles, String playerName) throws InvalidParameterException;

    /**
     * Inserts the tiles taken from the board in the chosen column.
     *
     * @param playerName the player playing.
     * @param column     the chose column to insert the tiles in.
     * @throws InvalidParameterException if the player is playing in the wrong turn and if the chosen column does not exist
     *                                   in that bookshelf.
     * @throws FullColumnException       if the selected column is not refillable.
     */
    void insertTiles(String playerName, int column) throws InvalidParameterException, FullColumnException;

    /**
     * Check if the player achieved the commonGoal and add the obtained tokens to the player.
     * Has to be called at the end of a player's turn.
     *
     * @param playerName the player playing
     */
    void checkCommonGoal(String playerName);

    /**
     * Refills the livingRoom if refillable.
     */
    void refillLivingRoom();

    /**
     * Changes the current player playing and notifies every view.
     */
    void nextTurn();

    /**
     * If last round and last player, calculates the results.
     */
    void calculateWinResults();

    /**
     * Adds the end game token to the player who finishes first.
     *
     * @param playerName the name of the player who obtains the token.
     */
    void addEndToken(String playerName);


    /**
     * Checks if the bookshelf is full.
     *
     * @param playerName the player whose bookshelf has to be checked.
     * @return true if the bookshelf is full, false otherwise.
     */
    boolean isBookshelfFull(String playerName);

    /**
     * Cheks whether the player playing is the last one.
     *
     * @param playerName the player to check.
     * @return true if it's the last one, false otherwise.
     */
    boolean isLastPlayer(String playerName);

    /**
     * Adds a game observer.
     *
     * @param obs the observer to add.
     */
    void addGameObserver(Observer<GameEvent, GameModelView> obs);

    /**
     * Removes a game observer.
     *
     * @param obs the game observer to remove.
     */
    void removeGameObserver(Observer<GameEvent, GameModelView> obs);

    /**
     * Return the game model view of that game.
     *
     * @return a game model view.
     */
    FullGameModelView getGameModelView();

    /**
     * @return the game name.
     */
    String getName();

    /**
     * @return the lobby name.
     */
    String getLobbyName();

    /**
     * Removes a game observer.
     */
    void removeGameObservers();

    /**
     * Returns the chat model.
     *
     * @return a chat model.
     */
    ChatModel getChatModel();
}
