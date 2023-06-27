package it.polimi.myshelfie.model.exceptions;

/**
 * Represents the exception thrown when a lobby is full.
 */
public class LobbyFullException extends Exception {

    private final String message;

    public LobbyFullException(boolean exists) {
        if (exists) this.message = "Lobby full";
        else this.message = "Lobby not full";
    }

    public String getMessage() {
        return message;
    }
}
