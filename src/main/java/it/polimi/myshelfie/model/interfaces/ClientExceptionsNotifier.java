package it.polimi.myshelfie.model.interfaces;

import it.polimi.myshelfie.model.modelviews.ClientExceptionsNotifierView;
import it.polimi.myshelfie.network.ExceptionEvent;
import it.polimi.myshelfie.utils.Observable;
import it.polimi.myshelfie.utils.Observer;

public interface ClientExceptionsNotifier {
    void notifyException(Exception exception);
    void addExceptionsObserver(Observer<? extends Observable<?>, ExceptionEvent> obs);
    ClientExceptionsNotifierView getClientExceptionNotifierView();
}
