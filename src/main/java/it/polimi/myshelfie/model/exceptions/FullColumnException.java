package it.polimi.myshelfie.model.exceptions;

/**
 * Represents the exception thrown when a column is full.
 */
public class FullColumnException extends Exception{
    private String message = "too many tiles for column ";

    @Override
    public String getMessage() {
        return message;
    }

    public FullColumnException(int column){
        this.message = message.concat(String.valueOf(column));
    }
}
