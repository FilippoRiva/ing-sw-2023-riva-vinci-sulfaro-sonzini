package it.polimi.myshelfie.controller.interfaces;



public interface Controller {
    /**
     *
     * @param playerName is the name of the first player that creates the current game.
     * @param lobbyName is the name of the lobby.
     */
    LobbyController createLobby(String playerName, String lobbyName);
    /**
     *
     * @param playerName is the name of the player.
     * @param lobbyName  is the name of the lobby entered by the player
     */
    LobbyController joinLobby(String playerName, String lobbyName);


    //TODO methods to manage Observer/Listener between model and view

}
