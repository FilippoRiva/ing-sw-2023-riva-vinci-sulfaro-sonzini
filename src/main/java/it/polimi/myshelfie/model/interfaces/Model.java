package it.polimi.myshelfie.model.interfaces;

import it.polimi.myshelfie.model.modelviews.ModelView;
import it.polimi.myshelfie.network.ModelEvent;
import it.polimi.myshelfie.utils.Observable;
import it.polimi.myshelfie.utils.Observer;

import it.polimi.myshelfie.model.exceptions.LobbyExistanceException;

public interface Model {
    /**
     * Adds a lobbyName to this list.
     * @param lobbyName the lobby name
     * @throws Exception lobbyName not available
     */
    LobbyModel addLobby(String lobbyName) throws LobbyExistanceException;
    void removeLobby(String lobbyName) throws LobbyExistanceException;
    void addModelObserver(Observer<? extends Observable<?>, ModelEvent> obs);
    ModelView getModelView();

    ClientExceptionsNotifier getNewClientExceptionNotifier(String playerName);
}
