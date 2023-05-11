package it.polimi.myshelfie.model.exceptions;

public class DisconnectionException extends Exception{
    public DisconnectionException(String playerName){
        super(playerName + " has disconnected. Current game ends");
    }
}
