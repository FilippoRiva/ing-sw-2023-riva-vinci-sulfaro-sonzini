package it.polimi.myshelfie.model.modelviews;

import it.polimi.myshelfie.model.ClientExceptionsNotifierImpl;

import java.io.Serializable;

/**
 * Represents the client exceptions notifier model view.
 */
public class ClientExceptionsNotifierModelView implements Serializable {
    private final Exception lastException;

    /**
     * Default constructor.
     *
     * @param c the last exceptions.
     */
    public ClientExceptionsNotifierModelView(ClientExceptionsNotifierImpl c) {
        this.lastException = c.getLastException();
    }

    /**
     * @return the last exception notified.
     */
    public Exception getLastException() {
        return lastException;
    }
}