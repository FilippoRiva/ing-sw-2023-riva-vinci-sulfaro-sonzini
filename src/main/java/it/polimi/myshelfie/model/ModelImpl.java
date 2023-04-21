package it.polimi.myshelfie.model;

import it.polimi.myshelfie.model.exceptions.LobbyExistanceException;
import it.polimi.myshelfie.model.interfaces.ClientExceptionsNotifier;
import it.polimi.myshelfie.model.interfaces.Model;
import it.polimi.myshelfie.model.interfaces.LobbyModel;
import it.polimi.myshelfie.model.modelviews.ModelView;
import it.polimi.myshelfie.network.events.ModelEvent;
import it.polimi.myshelfie.utils.Observable;
import it.polimi.myshelfie.utils.Observer;

import java.util.HashMap;
import java.util.List;

public class ModelImpl extends Observable<ModelEvent> implements Model {
    private final HashMap<String, Game> lobbiesModel = new HashMap<>();

    /**
     * Add a lobby to the list of lobbies.
     * @param lobbyName is the name of the lobby you want to add.
     * @return the lobby model associated to this specific lobby.
     * @throws LobbyExistanceException if the name of the lobby you want to add already exists.
     */
    public LobbyModel addLobby(String lobbyName) throws LobbyExistanceException {
        if(lobbiesModel.containsKey(lobbyName)) throw new LobbyExistanceException(true, lobbyName);

        Game lobbyModel = new Game(lobbyName);
        lobbiesModel.put(lobbyName, lobbyModel);
        setChanged();
        notifyObservers(ModelEvent.LOBBY_ADD);
        return lobbyModel;
    }

    /**
     * Removes a lobby from the list of lobbies.
     * @param lobbyName is the name of the lobby you want to remove.
     * @throws LobbyExistanceException if the name of the lobby you want to remove does not exist.
     */
    public void removeLobby(String lobbyName) throws LobbyExistanceException {
        if(!lobbiesModel.containsKey(lobbyName)) throw new LobbyExistanceException(false, lobbyName);
        lobbiesModel.remove(lobbyName);
        setChanged();
        notifyObservers(ModelEvent.LOBBY_REMOVE);
    }

    @Override
    public void addModelObserver(Observer<ModelEvent> obs) {
        addObserver(obs);
        setChanged();
        notifyObservers();
    }

    @Override
    public ModelView getModelView() {
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
        return new ClientsExceptionsNotifierImpl();
    }
}
