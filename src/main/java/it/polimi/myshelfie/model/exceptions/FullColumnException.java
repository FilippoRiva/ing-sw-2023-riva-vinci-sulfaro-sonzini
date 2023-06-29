package it.polimi.myshelfie.model.exceptions;

/**
 * Represents the exception thrown when a column is full.
 */
public class FullColumnException extends Exception {
    private String message = "too many tiles for column ";

    public FullColumnException(int column) {
        this.message = message.concat(String.valueOf(column));
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
