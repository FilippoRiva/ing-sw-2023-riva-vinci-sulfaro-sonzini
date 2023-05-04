package it.polimi.myshelfie.view.graphical;

import it.polimi.myshelfie.model.modelviews.*;
import it.polimi.myshelfie.network.NetworkMessage;
import it.polimi.myshelfie.utils.Observer;
import it.polimi.myshelfie.view.View;
import it.polimi.myshelfie.view.ViewType;
import it.polimi.myshelfie.view.ViewUpdate;

public class GraphicalUI extends View implements Observer<ViewUpdate> {
    @Override
    public void setModelView(ModelView modelView) {

    }

    @Override
    public void setExceptionView(ClientExceptionsNotifierView exceptionView) {

    }

    @Override
    public void setChatView(PlayerChatModelView chatView) {

    }

    @Override
    public void setLobbyModelView(LobbyModelView obj) {

    }

    @Override
    public void setGameModelView(GameModelView obj) {

    }

    @Override
    public void setView(ViewType viewType) {

    }

    @Override
    public NetworkMessage getMessage() {
        return null;
    }

    @Override
    public void update(ViewUpdate evt) {

    }
}
