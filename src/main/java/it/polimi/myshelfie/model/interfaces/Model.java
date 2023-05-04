package it.polimi.myshelfie.model.interfaces;

import it.polimi.myshelfie.model.modelviews.ModelView;
import it.polimi.myshelfie.model.events.ModelEvent;
import it.polimi.myshelfie.utils.Observer;

import it.polimi.myshelfie.model.exceptions.LobbyExistanceException;

public interface Model {
    /**
     * Adds a lobbyName to this list.
     * @param lobbyName the lobby name.
     * @throws Exception if lobbyName is not available.
     */
    LobbyModel addLobby(String lobbyName) throws LobbyExistanceException;

    /**
     * Removes a lobby from the list and notifies the observers.
     * @param lobbyName is the name of the lobby.
     * @throws LobbyExistanceException if the lobby that has to be removed does not exist.
     */
    void removeLobby(String lobbyName) throws LobbyExistanceException;

    /**
     * Add a model observer.
     * @param obs is the observer to add.
     */
    void addModelObserver(Observer<ModelEvent> obs);

    /**
     * Removes a Model Observer
      * @param obs the observer to remove
     */
    void removeModelObserver(Observer<ModelEvent> obs);

    /**
     *
     * @return the model view
     */
    ModelView getModelView();

    /**
     *
     * @return the client exception notifier.
     */
    ClientExceptionsNotifier getNewClientExceptionNotifier();
}
