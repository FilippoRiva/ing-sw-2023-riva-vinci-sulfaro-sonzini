package it.polimi.myshelfie.view.graphical;

import javafx.scene.image.Image;

import java.io.IOException;
import java.util.Objects;

/**
 * Represents the Images used.
 */
public class Images {

    /**
     * Returns an Image given a path.
     * @param path the path.
     * @return an Image.
     */
    public static Image getImage(String path) {
        try {
            return new Image(Objects.requireNonNull(Images.class.getResource("/images/" + path)).openStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
