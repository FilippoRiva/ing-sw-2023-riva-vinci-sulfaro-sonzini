package it.polimi.myshelfie.model;

import it.polimi.myshelfie.model.events.ExceptionEvent;
import it.polimi.myshelfie.model.exceptions.DisconnectionException;
import it.polimi.myshelfie.model.interfaces.ClientExceptionsNotifier;
import it.polimi.myshelfie.model.modelviews.ClientExceptionsNotifierModelView;
import it.polimi.myshelfie.utils.Observer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ClientExceptionsNotifierImplTest {
    @Test
    public void modelClientExceptionNotifierTest() {
        final ClientExceptionsNotifierModelView[] view = new ClientExceptionsNotifierModelView[1];
        final ExceptionEvent[] event = new ExceptionEvent[1];
        ClientExceptionsNotifier interfaceCen = new ModelImpl().getNewClientExceptionNotifier();
        ClientExceptionsNotifierImpl cen = (ClientExceptionsNotifierImpl) interfaceCen;
        Observer<ExceptionEvent, ClientExceptionsNotifierModelView> obs = (evt, ClientExceptionNotifierModelView) -> {
            view[0] = ClientExceptionNotifierModelView;
            event[0] = evt;
        };
        Exception e = new DisconnectionException("Gigi");
        cen.addExceptionsObserver(obs);
        cen.notifyException(e);
        assertEquals(view[0].getLastException(), cen.getClientExceptionNotifierView().getLastException());
        assertEquals(event[0], ExceptionEvent.NEW_EXCEPTION);
        assertEquals(cen.getLastException(), e);

        view[0] = null;
        event[0] = null;
        cen.removeExceptionsObserver(obs);
        cen.notifyException(new DisconnectionException("Gigi"));
        assertNull(view[0]);
        assertNull(event[0]);
    }
}