package it.polimi.myshelfie.model.interfaces;

import it.polimi.myshelfie.model.events.ModelEvent;
import it.polimi.myshelfie.model.exceptions.LobbyExistenceException;
import it.polimi.myshelfie.model.modelviews.ModelView;
import it.polimi.myshelfie.utils.Observer;

/**
 * Represents the model's interface.
 */
public interface Model {
    /**
     * Adds a lobbyName to this list.
     *
     * @param lobbyName the lobby name
     * @throws LobbyExistenceException if the name of the lobby you want to add already exists.
     */
    LobbyModel addLobby(String lobbyName) throws LobbyExistenceException;

    /**
     * Removes a lobby from the list and notifies the observers.
     *
     * @param lobbyName the name of the lobby.
     * @throws LobbyExistenceException if the lobby that has to be removed does not exist.
     */
    void removeLobby(String lobbyName) throws LobbyExistenceException;

    /**
     * Add a model observer.
     *
     * @param obs is the observer to add.
     */
    void addModelObserver(Observer<ModelEvent, ModelView> obs);

    /**
     * Removes a Model Observer.
     *
     * @param obs the observer to remove.
     */
    void removeModelObserver(Observer<ModelEvent, ModelView> obs);

    /**
     * Returns the model view.
     *
     * @return the model view.
     */
    ModelView getModelView();

    /**
     * To be used by the controller to get new Exception Notifiers.
     * @return the client exception notifier.
     */
    ClientExceptionsNotifier getNewClientExceptionNotifier();
}
