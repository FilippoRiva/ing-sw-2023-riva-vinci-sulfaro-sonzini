package it.polimi.myshelfie.model.exceptions;

public class LobbyExistanceException extends Exception{
    private final String message;

    @Override
    public String getMessage() {
        return message;
    }

    public LobbyExistanceException(boolean exists, String lobbyName){
        if(exists) this.message = "this lobby already exists: " + lobbyName;
        else this.message = "this lobby does not exists: " + lobbyName;
    }
}
