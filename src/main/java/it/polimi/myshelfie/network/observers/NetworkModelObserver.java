package it.polimi.myshelfie.network.observers;

import it.polimi.myshelfie.model.interfaces.Model;
import it.polimi.myshelfie.network.Client;
import it.polimi.myshelfie.network.events.ModelEvent;
import it.polimi.myshelfie.utils.Observer;

import java.rmi.RemoteException;

public class NetworkModelObserver extends NetworkObserver implements Observer<ModelEvent> {
    boolean firstNotify;
    Model model;
    public NetworkModelObserver(Client client){
        this.client = client;
        firstNotify = false;
    }
    @Override
    public void update(ModelEvent evt) {
        if(evt==null){
            if(!firstNotify) {
                send(null);
            }
        } else {
            send(evt);
        }
    }
    private void send(ModelEvent evt){
        try {
            client.send(model.getModelView(),evt);
            firstNotify = true;
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void setModel(Model model) {
        this.model = model;
    }
}
