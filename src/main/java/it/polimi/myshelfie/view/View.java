package it.polimi.myshelfie.view;

import it.polimi.myshelfie.model.events.ExceptionEvent;
import it.polimi.myshelfie.model.modelviews.*;
import it.polimi.myshelfie.network.NetworkMessage;
import it.polimi.myshelfie.utils.Observable;
import it.polimi.myshelfie.view.events.ViewEvent;


/**
 * Represents the generic View.
 */
public abstract class View extends Observable<ViewEvent, NetworkMessage> {

    /**
     * Associates a model view.
     *
     * @param modelView the model view to set.
     */
    public abstract void setModelView(ModelView modelView);

    /**
     * Associates an Exception view.
     *
     * @param exceptionView the exception view to check.
     * @param evt           the exception event that has occurred.
     */
    public abstract void setExceptionView(ClientExceptionsNotifierModelView exceptionView, ExceptionEvent evt);


    /**
     * Associates a Chat view.
     *
     * @param chatView the chat view to set.
     */
    public abstract void setChatView(PlayerChatModelView chatView);


    /**
     * Associates a Lobby model view.
     *
     * @param lobbyModelView the lobby model view to set.
     */
    public abstract void setLobbyModelView(LobbyModelView lobbyModelView);


    /**
     * Associates a Game model view.
     *
     * @param fullGameModelView the game model view to set.
     */
    public abstract void setGameModelView(FullGameModelView fullGameModelView);


    /**
     * Updates the DrawModelView when a draw move occurs.
     *
     * @param drawModelView the model view to update.
     */
    public abstract void updateDrawView(DrawModelView drawModelView);


    /**
     * Updates the OrganizeModelVIew when an organize move occurs.
     *
     * @param organizeModelView the model view to update.
     */
    public abstract void updateOrganizeView(OrganizeModelView organizeModelView);


    /**
     * Updates the InsertModelView when an insert move occurs.
     *
     * @param insertModelView the model view to update.
     */
    public abstract void updateInsertView(InsertModelView insertModelView);


    /**
     * Updates the EndTurnModelView.
     *
     * @param endTurnModelView the model view to update.
     */
    public abstract void updateTurnView(EndTurnModelView endTurnModelView);


    /**
     * Sets a specific view based on the view type.
     *
     * @param viewType the view type.
     */
    public abstract void setView(ViewType viewType);


    /**
     * Stops the UI.
     */
    public abstract void stopUI();
}
