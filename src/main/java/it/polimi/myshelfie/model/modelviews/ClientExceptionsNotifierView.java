package it.polimi.myshelfie.model.modelviews;

import it.polimi.myshelfie.model.ClientsExceptionsNotifierImpl;

public class ClientExceptionsNotifierView {
    private final Exception lastException;
    public ClientExceptionsNotifierView(ClientsExceptionsNotifierImpl c){
        this.lastException = c.getLastException();
    }

    public Exception getLastException() {
        return lastException;
    }
}