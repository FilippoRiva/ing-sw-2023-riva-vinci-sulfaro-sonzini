package it.polimi.myshelfie.network.observers;

import it.polimi.myshelfie.model.interfaces.GameModel;
import it.polimi.myshelfie.network.Client;
import it.polimi.myshelfie.network.NetworkMessage;
import it.polimi.myshelfie.network.events.GameEvent;
import it.polimi.myshelfie.utils.Observer;

import java.rmi.RemoteException;

public class NetworkGameObserver extends NetworkObserver implements Observer<GameEvent> {
    private GameModel gameModel;
    private boolean firstNotify;
    public NetworkGameObserver(Client client){
        this.client = client;
        this.firstNotify = false;
    }

    @Override
    public void update(GameEvent evt) {
        if(evt==null && firstNotify) return;
        new Thread(()->{
            try {
                client.send(new NetworkMessage(evt,gameModel.getGameModelView()));
                firstNotify = true;
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public void setGameModel(GameModel gameModel) {
        this.gameModel = gameModel;
    }
}
