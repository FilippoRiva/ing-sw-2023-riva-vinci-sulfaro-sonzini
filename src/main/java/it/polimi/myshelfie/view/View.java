package it.polimi.myshelfie.view;

import it.polimi.myshelfie.model.modelviews.LobbyModelView;
import it.polimi.myshelfie.model.modelviews.ModelView;
import it.polimi.myshelfie.network.ServerConnectionHandler;
import it.polimi.myshelfie.network.events.ViewEvent;
import it.polimi.myshelfie.utils.Observable;


public abstract class View extends Observable<ViewEvent> {

    ServerConnectionHandler server;
    Object[] parameters;
    public abstract Object[] getUpdateParameters();

    public abstract void setModelView(ModelView modelView);

    public abstract void setLobbyModelView(LobbyModelView obj);
    public abstract void setView(ViewType viewType);
}
