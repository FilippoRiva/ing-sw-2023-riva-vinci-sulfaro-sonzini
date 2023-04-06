package it.polimi.myshelfie.network.observers;

import it.polimi.myshelfie.model.interfaces.ClientExceptionsNotifier;
import it.polimi.myshelfie.network.Client;
import it.polimi.myshelfie.network.events.ExceptionEvent;
import it.polimi.myshelfie.utils.Observer;

import java.rmi.RemoteException;

public class NetworkExceptionsObserver extends NetworkObserver implements Observer<ExceptionEvent> {
    ClientExceptionsNotifier exceptionsNotifier;
    public NetworkExceptionsObserver(Client client){
        this.client = client;
    }
    public void setExceptionsNotifier(ClientExceptionsNotifier exceptionsNotifier) {
        this.exceptionsNotifier = exceptionsNotifier;
    }
    public Client getClient(){
        return client;
    }
    @Override
    public void update(ExceptionEvent evt) {
        try {
            if(evt!=null) client.send(exceptionsNotifier.getClientExceptionNotifierView(),evt);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
