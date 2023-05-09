package it.polimi.myshelfie.model.interfaces;

import it.polimi.myshelfie.model.modelviews.ClientExceptionsNotifierView;
import it.polimi.myshelfie.model.events.ExceptionEvent;
import it.polimi.myshelfie.utils.Observer;

public interface ClientExceptionsNotifier {
    /**
     * Add this exception to the list of exceptions and notifies the observers.
     * @param exception is the new exception.
     */
    void notifyException(Exception exception);

    /**
     * Add an exception observer.
     * @param obs is the observer to add.
     */
    void addExceptionsObserver(Observer<ExceptionEvent, ClientExceptionsNotifierView> obs);

    /**
     * Removes an exception observer.
     * @param obs the observer to remove
     */
    void removeExceptionsObserver(Observer<ExceptionEvent, ClientExceptionsNotifierView> obs);

    /**
     *
     * @return a client exception notifier view.
     */
    ClientExceptionsNotifierView getClientExceptionNotifierView();
}
