package it.polimi.myshelfie.view.graphical;


import javafx.scene.Node;
import javafx.scene.effect.DropShadow;


/**
 * Represents the Effects.
 */
public class Effects {

    /**
     * Used to apply a shadow.
     * @param node the node where to set the effect.
     */
    public static void applyShadow(Node node) {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(30);
        node.setEffect(dropShadow);
        node.setPickOnBounds(!node.isPickOnBounds());
    }
}
