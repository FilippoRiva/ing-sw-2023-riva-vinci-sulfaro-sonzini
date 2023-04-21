package it.polimi.myshelfie.network.observers;

import it.polimi.myshelfie.model.interfaces.GameModel;
import it.polimi.myshelfie.network.Client;
import it.polimi.myshelfie.network.NetworkMessage;
import it.polimi.myshelfie.network.events.GameEvent;
import it.polimi.myshelfie.utils.Observer;

import java.rmi.RemoteException;

public class NetworkGameObserver extends NetworkObserver implements Observer<GameEvent> {
    private GameModel gameModel;
    public NetworkGameObserver(Client client){
        this.client = client;
    }

    @Override
    public void update(GameEvent evt) {
        if(evt==null) return;
        new Thread(()->{
            try {
                client.send(new NetworkMessage(evt,gameModel.getGameModelView()));
            } catch (RemoteException e) {
                System.err.println("Unable to reach a client");
            }
        }).start();
    }

    public void setGameModel(GameModel gameModel) {
        this.gameModel = gameModel;
    }
}
