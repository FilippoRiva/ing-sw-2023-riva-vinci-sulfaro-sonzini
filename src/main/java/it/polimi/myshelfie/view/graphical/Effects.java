package it.polimi.myshelfie.view.graphical;


import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Region;

public class Effects {
    public static void applyShadow(Region region){
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(30);
        region.setEffect(dropShadow);
    }
}
