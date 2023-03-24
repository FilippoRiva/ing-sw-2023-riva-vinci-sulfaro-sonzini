package it.polimi.myshelfie.modelview;

import it.polimi.myshelfie.model.interfaces.GameModel;

public class GameModelView {
    //TODO Observer/Listener implementation
    GameModel gm;
    public GameModelView(GameModel gm){
        this.gm = gm;
    }
}
