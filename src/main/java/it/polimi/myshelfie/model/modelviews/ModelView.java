package it.polimi.myshelfie.model.modelviews;

import it.polimi.myshelfie.model.ModelImpl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelView implements Serializable {
    List<String> lobbies;
    Map<String, Integer> maxSizeMap;
    Map<String, Integer> currentSizeMap;
    public ModelView(ModelImpl model){
        this.lobbies = model.getLobbies();
        this.maxSizeMap = new HashMap<>();
        this.currentSizeMap = new HashMap<>();
        for(String lobby : lobbies){
            this.maxSizeMap.put(lobby,model.getLobbyMaxSize(lobby));
            this.currentSizeMap.put(lobby,model.getLobbyCurrentSize(lobby));
        }
    }
    /**
     * @return the list with the lobby names.
     */
    public List<String> getLobbies() {
        return lobbies;
    }
    public int getMaxSize(String lobby){
        return maxSizeMap.get(lobby);
    }

    public int getCurrentSize(String lobby){
        return currentSizeMap.get(lobby);
    }

    public Map<String, Integer> getMaxSizeMap(){
        return maxSizeMap;
    }

    public Map<String, Integer> getCurrentSizeMap(){
        return currentSizeMap;
    }
}
