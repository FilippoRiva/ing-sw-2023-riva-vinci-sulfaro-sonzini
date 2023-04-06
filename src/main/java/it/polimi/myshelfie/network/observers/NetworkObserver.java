package it.polimi.myshelfie.network.observers;

import it.polimi.myshelfie.network.Client;

public abstract class NetworkObserver {
    Client client;
    public Client getClient(){
        return client;
    }
}
