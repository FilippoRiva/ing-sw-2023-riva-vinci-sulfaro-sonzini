package it.polimi.myshelfie.view;

import it.polimi.myshelfie.model.modelviews.*;
import it.polimi.myshelfie.network.NetworkMessage;
import it.polimi.myshelfie.network.events.ViewEvent;
import it.polimi.myshelfie.utils.Observable;


public abstract class View extends Observable<ViewEvent>{

    public abstract void setModelView(ModelView modelView);
    public abstract void setExceptionView(ClientExceptionsNotifierView exceptionView);
    public abstract void setChatView(PlayerChatModelView chatView);
    public abstract void setLobbyModelView(LobbyModelView obj);
    public abstract void setGameModelView(GameModelView obj);
    public abstract void setView(ViewType viewType);
    public abstract NetworkMessage getMessage();
}
