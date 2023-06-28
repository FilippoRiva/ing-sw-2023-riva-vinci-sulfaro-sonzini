package it.polimi.myshelfie.view.graphical;

import javafx.scene.image.Image;

import java.io.IOException;
import java.util.Objects;

public class Images {
    public static Image getImage(String path) {
        try {
            return new Image(Objects.requireNonNull(Images.class.getResource("/images/" + path)).openStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
