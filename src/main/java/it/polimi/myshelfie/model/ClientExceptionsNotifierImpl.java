package it.polimi.myshelfie.model;

import it.polimi.myshelfie.model.interfaces.ClientExceptionsNotifier;
import it.polimi.myshelfie.model.modelviews.ClientExceptionsNotifierView;
import it.polimi.myshelfie.model.events.ExceptionEvent;
import it.polimi.myshelfie.utils.Observable;
import it.polimi.myshelfie.utils.Observer;

import java.util.ArrayList;
import java.util.List;

public class ClientExceptionsNotifierImpl extends Observable<ExceptionEvent, ClientExceptionsNotifierView> implements ClientExceptionsNotifier {
    List<Exception> exceptionList = new ArrayList<>();
    @Override
    public synchronized void notifyException(Exception exception) {
        exceptionList.add(exception);
        setChanged();
        notifyObservers(ExceptionEvent.NEW_EXCEPTION, new ClientExceptionsNotifierView(this));
    }

    @Override
    public synchronized void addExceptionsObserver(Observer<ExceptionEvent, ClientExceptionsNotifierView> obs) {
        addObserver(obs);
        setChanged();
        notifyObservers(new ClientExceptionsNotifierView(this));
    }

    @Override
    public synchronized void removeExceptionsObserver(Observer<ExceptionEvent, ClientExceptionsNotifierView> obs) {
        deleteObserver(obs);
    }

    @Override
    public synchronized ClientExceptionsNotifierView getClientExceptionNotifierView() {
        return new ClientExceptionsNotifierView(this);
    }

    /**
     *
     * @return the last exception notified.
     */
    public synchronized Exception getLastException() {
        if(exceptionList.size()==0) return null;
        return exceptionList.get(exceptionList.size()-1);
    }
}
