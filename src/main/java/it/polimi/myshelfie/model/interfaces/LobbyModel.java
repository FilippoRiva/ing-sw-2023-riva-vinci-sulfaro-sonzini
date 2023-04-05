package it.polimi.myshelfie.model.interfaces;

import it.polimi.myshelfie.GameConfig;
import it.polimi.myshelfie.model.exceptions.InvalidParameterException;
import it.polimi.myshelfie.model.exceptions.LobbyFullException;

public interface LobbyModel {
    /**
     * sets the game config to be used by the game
     * @param config configurations for the game
     */
    void setGameConfig(GameConfig config);
    /**
     *
     * @param playerName is the name of the new player that has to be added to the game
     */
    void addPlayer(String playerName) throws LobbyFullException, InvalidParameterException;

    /**
     *
     * @param playerName is the name of the player that has to be removed from the game
     */
    void removePlayer(String playerName) throws InvalidParameterException;

    ChatModel getChatModel();

    GameModel getGameModel();

    //TODO methods to manage Observer/Listener

}
