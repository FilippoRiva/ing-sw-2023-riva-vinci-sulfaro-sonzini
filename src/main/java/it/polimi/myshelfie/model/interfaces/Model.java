package it.polimi.myshelfie.model.interfaces;

public interface Model {
    /**
     * Adds a lobbyName to this list.
     * @param lobbyName the lobby name
     * @throws Exception lobbyName not available
     */
    LobbyModel addLobby(String lobbyName) throws Exception;
    void removeLobby(String lobbyName) throws Exception;
    void askGameList();
}
