package it.polimi.myshelfie.model;

import it.polimi.myshelfie.model.exceptions.LobbyExistenceException;
import it.polimi.myshelfie.model.interfaces.ClientExceptionsNotifier;
import it.polimi.myshelfie.model.interfaces.Model;
import it.polimi.myshelfie.model.interfaces.LobbyModel;
import it.polimi.myshelfie.model.modelviews.ModelView;
import it.polimi.myshelfie.model.events.ModelEvent;
import it.polimi.myshelfie.utils.Observable;
import it.polimi.myshelfie.utils.Observer;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Represents the available lobbies that can be joined.
 * It contains a list with all the lobbies created and it can add and remove them from the list.
 */
public class ModelImpl extends Observable<ModelEvent, ModelView> implements Model {
    private final HashMap<String, Game> lobbiesModel = new HashMap<>();

    /**
     * Notifies periodically every client about new lobbies (used to notify config and player changes).
     */
    public ModelImpl(){
        new Thread(()->{
            ScheduledExecutorService ex = Executors.newScheduledThreadPool(1);
            ex.scheduleAtFixedRate(()->{
                setChanged();
                notifyObservers(new ModelView(this));
            },0,10, TimeUnit.SECONDS);
        }).start();
    }

    /**
     * Adds a lobby to the list of lobbies.
     * @param lobbyName the name of the lobby you want to add.
     * @return the lobby model associated to this specific lobby.
     * @throws LobbyExistenceException if the name of the lobby you want to add already exists.
     */
    @Override
    public synchronized LobbyModel addLobby(String lobbyName) throws LobbyExistenceException {
        if(lobbiesModel.containsKey(lobbyName)) throw new LobbyExistenceException(true, lobbyName);

        Game lobbyModel = new Game(lobbyName);
        lobbiesModel.put(lobbyName, lobbyModel);
        setChanged();
        notifyObservers(ModelEvent.LOBBY_ADD, new ModelView(this));
        return lobbyModel;
    }

    /**
     * Removes a lobby from the list of lobbies.
     * @param lobbyName the name of the lobby you want to remove.
     * @throws LobbyExistenceException if the name of the lobby you want to remove does not exist.
     */
    @Override
    public synchronized void removeLobby(String lobbyName) throws LobbyExistenceException {
        if(!lobbiesModel.containsKey(lobbyName)) throw new LobbyExistenceException(false, lobbyName);
        lobbiesModel.remove(lobbyName);
        setChanged();
        notifyObservers(ModelEvent.LOBBY_REMOVE, new ModelView(this));
    }

    @Override
    public synchronized void addModelObserver(Observer<ModelEvent, ModelView> obs) {
        addObserver(obs);
        setChanged();
        notifyObservers(new ModelView(this));
    }

    @Override
    public synchronized void removeModelObserver(Observer<ModelEvent, ModelView> obs) {
        deleteObserver(obs);
    }

    @Override
    public synchronized ModelView getModelView() {
        return new ModelView(this);
    }

    /**
     * @return the list of lobby names.
     */
    public List<String> getLobbies(){
        return lobbiesModel.keySet().stream().toList();
    }

    @Override
    public synchronized ClientExceptionsNotifier getNewClientExceptionNotifier() {
        return new ClientExceptionsNotifierImpl();
    }

    public int getLobbyCurrentSize(String lobby) {
        return lobbiesModel.get(lobby).getCurrentSize();
    }

    public int getLobbyMaxSize(String lobby) {
        return lobbiesModel.get(lobby).getMaxSize();
    }
}
