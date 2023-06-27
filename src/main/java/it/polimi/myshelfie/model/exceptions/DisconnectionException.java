package it.polimi.myshelfie.model.exceptions;

/**
 * Represents the exception thrown when a player disconnects.
 */
public class DisconnectionException extends Exception{
    public DisconnectionException(String playerName){
        super(playerName + " has disconnected. Current game ends");
    }
}
