package it.polimi.myshelfie.model;

import it.polimi.myshelfie.model.events.ExceptionEvent;
import it.polimi.myshelfie.model.exceptions.DisconnectionException;
import it.polimi.myshelfie.model.interfaces.ClientExceptionsNotifier;
import it.polimi.myshelfie.model.modelviews.ClientExceptionsNotifierModelView;
import it.polimi.myshelfie.utils.Observable;
import it.polimi.myshelfie.utils.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Is used to notify all the exceptions.
 * It contains a list with all the exceptions and methods to notify them whenever one occurs.
 */
public class ClientExceptionsNotifierImpl extends Observable<ExceptionEvent, ClientExceptionsNotifierModelView> implements ClientExceptionsNotifier {
    final List<Exception> exceptionList = new ArrayList<>();

    /**
     * {@inheritDoc}
     *
     * @param exception the disconnection exception.
     */
    @Override
    public void notifyException(DisconnectionException exception) {
        exceptionList.add(exception);
        setChanged();
        notifyObservers(ExceptionEvent.DISCONNECTION_EXCEPTION, new ClientExceptionsNotifierModelView(this));
    }

    /**
     * {@inheritDoc}
     *
     * @param exception the new exception.
     */
    @Override
    public synchronized void notifyException(Exception exception) {
        exceptionList.add(exception);
        setChanged();
        notifyObservers(ExceptionEvent.NEW_EXCEPTION, new ClientExceptionsNotifierModelView(this));
    }

    /**
     * {@inheritDoc}
     *
     * @param obs is the observer to add.
     */
    @Override
    public synchronized void addExceptionsObserver(Observer<ExceptionEvent, ClientExceptionsNotifierModelView> obs) {
        addObserver(obs);
        setChanged();
        notifyObservers(new ClientExceptionsNotifierModelView(this));
    }

    /**
     * {@inheritDoc}
     *
     * @param obs the observer to remove
     */
    @Override
    public synchronized void removeExceptionsObserver(Observer<ExceptionEvent, ClientExceptionsNotifierModelView> obs) {
        deleteObserver(obs);
    }

    /**
     * {@inheritDoc}
     *
     * @return the {@link ClientExceptionsNotifierModelView} of this ClientExceptionNotifier
     */
    @Override
    public synchronized ClientExceptionsNotifierModelView getClientExceptionNotifierView() {
        return new ClientExceptionsNotifierModelView(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeExceptionsObservers() {
        deleteObservers();
    }

    /**
     * Returns the last Exception notified
     *
     * @return the last exception notified.
     */
    public synchronized Exception getLastException() {
        if (exceptionList.size() == 0) return null;
        return exceptionList.get(exceptionList.size() - 1);
    }
}
