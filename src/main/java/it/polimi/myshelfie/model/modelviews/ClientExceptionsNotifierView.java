package it.polimi.myshelfie.model.modelviews;

import it.polimi.myshelfie.model.ClientsExceptionsNotifierImpl;

import java.io.Serializable;

public class ClientExceptionsNotifierView implements Serializable {
    private final Exception lastException;
    public ClientExceptionsNotifierView(ClientsExceptionsNotifierImpl c){
        this.lastException = c.getLastException();
    }

    /**
     *
     * @return the last exception notified.
     */
    public Exception getLastException() {
        return lastException;
    }
}