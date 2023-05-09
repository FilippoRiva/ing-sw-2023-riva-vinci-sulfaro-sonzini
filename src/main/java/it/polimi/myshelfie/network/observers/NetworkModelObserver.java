package it.polimi.myshelfie.network.observers;

import it.polimi.myshelfie.model.interfaces.Model;
import it.polimi.myshelfie.model.modelviews.ModelView;
import it.polimi.myshelfie.network.Client;
import it.polimi.myshelfie.network.NetworkMessage;
import it.polimi.myshelfie.model.events.ModelEvent;
import it.polimi.myshelfie.utils.Observer;

import java.rmi.RemoteException;

public class NetworkModelObserver extends NetworkObserver implements Observer<ModelEvent, ModelView> {
    Model model;
    public NetworkModelObserver(Client client){
        this.client = client;
    }
    @Override
    public void update(ModelEvent evt, ModelView modelView) {
        new Thread(()->{
            try {
                client.send(new NetworkMessage(evt,model.getModelView()));
            } catch (RemoteException ignored) {
            }
        }).start();
    }

    public void setModel(Model model) {
        this.model = model;
    }
}
