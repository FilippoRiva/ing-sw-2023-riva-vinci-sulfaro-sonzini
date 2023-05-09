package it.polimi.myshelfie.view;

import it.polimi.myshelfie.model.modelviews.*;
import it.polimi.myshelfie.network.NetworkMessage;
import it.polimi.myshelfie.view.events.ViewEvent;
import it.polimi.myshelfie.utils.Observable;

public abstract class View extends Observable<ViewEvent, NetworkMessage>{

    public abstract void setModelView(ModelView modelView);
    public abstract void setExceptionView(ClientExceptionsNotifierView exceptionView);
    public abstract void setChatView(PlayerChatModelView chatView);
    public abstract void setLobbyModelView(LobbyModelView lobbyModelView);
    public abstract void setGameModelView(FullGameModelView fullGameModelView);
    public abstract void updateDrawView(DrawModelView drawModelView);
    public abstract void updateOrganizeView(OrganizeModelView organizeModelView);
    public abstract void updateInsertView(InsertModelView insertModelView);
    public abstract void updateTurnView(EndTurnModelView endTurnModelView);
    public abstract void setView(ViewType viewType);
}
