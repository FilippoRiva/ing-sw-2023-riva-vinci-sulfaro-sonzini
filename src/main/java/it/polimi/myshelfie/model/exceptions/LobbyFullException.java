package it.polimi.myshelfie.model.exceptions;

public class LobbyFullException extends Exception{

    private final String message;

    public String getMessage(){ return message; }
    public LobbyFullException(boolean exists){
        if(exists) this.message = "Lobby full";
        else this.message = "Lobby not full";
    }
}
