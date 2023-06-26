package it.polimi.myshelfie.view.graphical;


import javafx.scene.Node;
import javafx.scene.effect.DropShadow;

public class Effects {
    public static void applyShadow(Node node){
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(30);
        node.setEffect(dropShadow);
        node.setPickOnBounds(!node.isPickOnBounds());
    }
}
