package it.polimi.myshelfie.model.exceptions;

public class LobbyFullException extends Exception{
    public LobbyFullException(String message){
        super(message);
    }
    public LobbyFullException(){
        super();
    }
}
