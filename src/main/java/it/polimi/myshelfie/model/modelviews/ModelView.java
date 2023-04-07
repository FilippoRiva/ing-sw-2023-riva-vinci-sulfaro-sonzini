package it.polimi.myshelfie.model.modelviews;

import it.polimi.myshelfie.model.ModelImpl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ModelView implements Serializable {
    List<String> lobbies;
    public ModelView(ModelImpl model){
        lobbies = model.getLobbies();
    }
    public ModelView(ModelView modelView){
        lobbies = new ArrayList<>(modelView.lobbies);
    }
    public List<String> getLobbies() {
        return lobbies;
    }
}
