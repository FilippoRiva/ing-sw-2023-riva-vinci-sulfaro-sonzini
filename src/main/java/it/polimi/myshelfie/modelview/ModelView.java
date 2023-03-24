package it.polimi.myshelfie.modelview;

import it.polimi.myshelfie.model.interfaces.Model;

public class ModelView {
    //TODO Observer/Listener implementation
    Model model;
    public ModelView(Model model){
        this.model = model;
    }
}
