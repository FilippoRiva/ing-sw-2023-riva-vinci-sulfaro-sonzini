package it.polimi.myshelfie.model;

import it.polimi.myshelfie.model.exceptions.LobbyExistanceException;
import it.polimi.myshelfie.model.interfaces.ClientExceptionsNotifier;
import it.polimi.myshelfie.model.interfaces.Model;
import it.polimi.myshelfie.model.interfaces.LobbyModel;

import java.util.HashMap;

public class ModelImpl implements Model {
    //TODO Observer/Listener implementation
    private final HashMap<String, LobbyModel> lobbiesModel = new HashMap<>();

    public LobbyModel addLobby(String lobbyName) throws LobbyExistanceException {
        if(lobbiesModel.containsKey(lobbyName)) throw new LobbyExistanceException(true, lobbyName);

        LobbyModel lobbyModel = new Game();
        lobbiesModel.put(lobbyName, lobbyModel);
        // Notifies Listeners



        return lobbyModel;
    }

    public void removeLobby(String lobbyName) throws LobbyExistanceException {
        if(!lobbiesModel.containsKey(lobbyName)) throw new LobbyExistanceException(false, lobbyName);
        lobbiesModel.remove(lobbyName);
        // Notifies Listeners
    }

    @Override
    public ClientExceptionsNotifier getNewClientExceptionNotifier(String playerName) {
        return null;
    }
}
