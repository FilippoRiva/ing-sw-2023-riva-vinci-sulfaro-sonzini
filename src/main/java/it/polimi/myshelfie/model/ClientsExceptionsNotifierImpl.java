package it.polimi.myshelfie.model;

import it.polimi.myshelfie.model.interfaces.ClientExceptionsNotifier;
import it.polimi.myshelfie.model.modelviews.ClientExceptionsNotifierView;
import it.polimi.myshelfie.network.ExceptionEvent;
import it.polimi.myshelfie.utils.Observable;
import it.polimi.myshelfie.utils.Observer;

import java.util.ArrayList;
import java.util.List;

public class ClientsExceptionsNotifierImpl extends Observable<ExceptionEvent> implements ClientExceptionsNotifier {
    List<Exception> exceptionList = new ArrayList<>();
    @Override
    public void notifyException(Exception exception) {
        exceptionList.add(exception);
        setChanged();
        notifyObservers(ExceptionEvent.NEW_EXCEPTION);
    }

    @Override
    public void addExceptionsObserver(Observer<? extends Observable<?>, ExceptionEvent> obs) {
        addObserver(obs);
        setChanged();
        notifyObservers();
    }

    @Override
    public ClientExceptionsNotifierView getClientExceptionNotifierView() {
        return new ClientExceptionsNotifierView(this);
    }

    public Exception getLastException() {
        if(exceptionList.size()==0) return null;
        return exceptionList.get(exceptionList.size()-1);
    }
}
