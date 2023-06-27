package it.polimi.myshelfie.model.modelviews;

import it.polimi.myshelfie.model.ModelImpl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the Model view.
 */
public class ModelView implements Serializable {
    final List<String> lobbies;
    final Map<String, Integer> maxSizeMap;
    final Map<String, Integer> currentSizeMap;

    /**
     * Constructor.
     *
     * @param model the model implementation I want the model view.
     */
    public ModelView(ModelImpl model) {
        this.lobbies = model.getLobbies();
        this.maxSizeMap = new HashMap<>();
        this.currentSizeMap = new HashMap<>();
        for (String lobby : lobbies) {
            this.maxSizeMap.put(lobby, model.getLobbyMaxSize(lobby));
            this.currentSizeMap.put(lobby, Math.max(1, model.getLobbyCurrentSize(lobby)));
        }
    }

    /**
     * @return the list with the lobby names.
     */
    public List<String> getLobbies() {
        return lobbies;
    }

    /**
     * @param lobby the name of the lobby.
     * @return the max lobby size.
     */
    public int getMaxSize(String lobby) {
        return maxSizeMap.get(lobby);
    }

    /**
     * @param lobby the name of the lobby.
     * @return the current lobby size.
     */
    public int getCurrentSize(String lobby) {
        return currentSizeMap.get(lobby);
    }

    /**
     * @return the list of lobbies with their max dimension.
     */
    public Map<String, Integer> getMaxSizeMap() {
        return maxSizeMap;
    }

    /**
     * @return the list of lobbies with their current dimension.
     */
    public Map<String, Integer> getCurrentSizeMap() {
        return currentSizeMap;
    }
}
