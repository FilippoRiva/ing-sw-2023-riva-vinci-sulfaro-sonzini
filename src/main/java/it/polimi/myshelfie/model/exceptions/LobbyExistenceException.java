package it.polimi.myshelfie.model.exceptions;

public class LobbyExistenceException extends Exception{
    private final String message;

    @Override
    public String getMessage() {
        return message;
    }

    public LobbyExistenceException(boolean exists, String lobbyName){
        if(exists) this.message = "this lobby already exists: " + lobbyName;
        else this.message = "this lobby does not exists: " + lobbyName;
    }
}
