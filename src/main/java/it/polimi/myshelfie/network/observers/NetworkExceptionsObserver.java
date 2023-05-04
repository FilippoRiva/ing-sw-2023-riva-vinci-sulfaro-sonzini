package it.polimi.myshelfie.network.observers;

import it.polimi.myshelfie.model.interfaces.ClientExceptionsNotifier;
import it.polimi.myshelfie.network.Client;
import it.polimi.myshelfie.network.NetworkMessage;
import it.polimi.myshelfie.model.events.ExceptionEvent;
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
        if(evt==null) return;
        new Thread(()->{
            try {
                client.send(new NetworkMessage(evt,exceptionsNotifier.getClientExceptionNotifierView()));
            } catch (RemoteException ignored) {
            }
        }).start();
    }
}
