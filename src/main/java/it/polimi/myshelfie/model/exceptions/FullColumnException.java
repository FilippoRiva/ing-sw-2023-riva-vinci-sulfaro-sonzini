package it.polimi.myshelfie.model.exceptions;

public class FullColumnException extends Exception{
    private final String message;

    @Override
    public String getMessage() {
        return message;
    }

    public FullColumnException(String message){
        this.message = message;
    }
}
