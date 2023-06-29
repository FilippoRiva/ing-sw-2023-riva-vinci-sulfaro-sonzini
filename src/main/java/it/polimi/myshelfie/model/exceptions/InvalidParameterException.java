package it.polimi.myshelfie.model.exceptions;

/**
 * Represents the exception thrown when a string message is not valid.
 */
public class InvalidParameterException extends Exception {
    private final String message;

    public InvalidParameterException(String message) {
        this.message = message;
    }

    /**
     * {@inheritDoc}
     *
     * @return the Message
     */
    @Override
    public String getMessage() {
        return message;
    }
}
