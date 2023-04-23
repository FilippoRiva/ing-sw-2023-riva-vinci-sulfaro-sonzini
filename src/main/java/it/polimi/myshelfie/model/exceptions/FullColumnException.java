package it.polimi.myshelfie.model.exceptions;

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
