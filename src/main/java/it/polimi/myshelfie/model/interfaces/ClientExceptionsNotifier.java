package it.polimi.myshelfie.model.interfaces;

import it.polimi.myshelfie.model.exceptions.DisconnectionException;
import it.polimi.myshelfie.model.modelviews.ClientExceptionsNotifierModelView;
import it.polimi.myshelfie.model.events.ExceptionEvent;
import it.polimi.myshelfie.utils.Observer;

/**
 * Represents the client exceptions notifier's interface.
 */
public interface ClientExceptionsNotifier {
    /**
     * Add this exception to the list of exceptions and notifies the observers.
     * @param exception the new exception.
     */
    void notifyException(Exception exception);

    /**
     * Notifies whether a player disconnects.
     * @param exception the disconnection exception.
     */
    void notifyException(DisconnectionException exception);

    /**
     * Adds an exception observer.
     * @param obs is the observer to add.
     */
    void addExceptionsObserver(Observer<ExceptionEvent, ClientExceptionsNotifierModelView> obs);

    /**
     * Removes an exception observer.
     * @param obs the observer to remove
     */
    void removeExceptionsObserver(Observer<ExceptionEvent, ClientExceptionsNotifierModelView> obs);

    /**
     *
     * @return a client exception notifier view.
     */
    ClientExceptionsNotifierModelView getClientExceptionNotifierView();

    /**
     * Removes the exception observer.
     */
    void removeExceptionsObservers();
}
