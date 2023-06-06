package it.polimi.myshelfie.view.graphical;


import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;

public class CustomBindings {
    public static void bindSize(Region pane, ObservableValue<Number> width, ObservableValue<Number> height){
        if(width!=null){
            pane.minWidthProperty().bind(width);
            pane.maxWidthProperty().bind(width);
        }
        if(height!=null){
            pane.minHeightProperty().bind(height);
            pane.maxHeightProperty().bind(height);
        }
    }

    public static void bindSize(ImageView image, ObservableValue<Number> width, ObservableValue<Number> height){
        image.fitWidthProperty().bind(width);
        image.fitHeightProperty().bind(height);
    }

    public static void bindSize(Region region, Region regionToCopySizeOf){
        region.minWidthProperty().bind(regionToCopySizeOf.minWidthProperty());
        region.maxWidthProperty().bind(regionToCopySizeOf.maxWidthProperty());
        region.prefWidthProperty().bind(regionToCopySizeOf.prefWidthProperty());
        region.minHeightProperty().bind(regionToCopySizeOf.minHeightProperty());
        region.maxHeightProperty().bind(regionToCopySizeOf.maxHeightProperty());
        region.prefHeightProperty().bind(regionToCopySizeOf.prefHeightProperty());
    }

    public static void bindLowerPreGamePane(ImageView lowerBackground,ImageView banner){
        CustomBindings.bindSize(lowerBackground,GraphicalUI.stage.widthProperty(),
                Bindings.max(
                        GraphicalUI.stage.heightProperty().subtract(banner.fitWidthProperty().multiply(banner.getImage().getHeight()/banner.getImage().getWidth()))
                        ,GraphicalUI.stage.heightProperty().divide(3).multiply(2))
        );
    }
}
