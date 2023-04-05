package it.polimi.myshelfie.model.exceptions;

public class InvalidParameterException extends Exception{
    private final String message;

    @Override
    public String getMessage() {
        return message;
    }

    public InvalidParameterException(String message){
        this.message = message;
    }
}
