package it.polimi.myshelfie.network.observers;

import it.polimi.myshelfie.model.interfaces.GameModel;
import it.polimi.myshelfie.model.modelviews.GameModelView;
import it.polimi.myshelfie.network.Client;
import it.polimi.myshelfie.network.NetworkMessage;
import it.polimi.myshelfie.model.events.GameEvent;
import it.polimi.myshelfie.utils.Observer;

import java.rmi.RemoteException;

public class NetworkGameObserver extends NetworkObserver implements Observer<GameEvent, GameModelView> {
    private GameModel gameModel;
    public NetworkGameObserver(Client client){
        this.client = client;
    }

    @Override
    public void update(GameEvent evt, GameModelView gameModelView) {
        if(evt==null) return;
        new Thread(()->{
            try {
                client.send(new NetworkMessage(evt,gameModelView));
            } catch (RemoteException ignored) {
            }
        }).start();
    }

    public void setGameModel(GameModel gameModel) {
        this.gameModel = gameModel;
    }
}
