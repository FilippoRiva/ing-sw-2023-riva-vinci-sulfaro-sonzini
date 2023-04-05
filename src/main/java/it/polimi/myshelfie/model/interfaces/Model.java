package it.polimi.myshelfie.model.interfaces;

import it.polimi.myshelfie.model.exceptions.LobbyExistanceException;

public interface Model {
    /**
     * Adds a lobbyName to this list.
     * @param lobbyName the lobby name
     * @throws Exception lobbyName not available
     */
    LobbyModel addLobby(String lobbyName) throws LobbyExistanceException;
    void removeLobby(String lobbyName) throws LobbyExistanceException;

    ClientExceptionsNotifier getNewClientExceptionNotifier(String playerName);
}
