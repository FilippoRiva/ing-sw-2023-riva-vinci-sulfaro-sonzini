package it.polimi.myshelfie.view.graphical;

import javafx.scene.image.Image;

import java.io.IOException;

public class Images {
    public static final Image backPersonalGoal = getImage("game/personalGoals/back.jpg");
    public static final Image emptyToken = getImage("game/tokens/scoring.jpg");

    public static Image getImage(String path){
        try {
            return new Image(Images.class.getResource("/images/"+path).openStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
