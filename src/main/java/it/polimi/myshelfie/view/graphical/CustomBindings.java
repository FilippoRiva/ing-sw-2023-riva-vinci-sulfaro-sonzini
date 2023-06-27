package it.polimi.myshelfie.view.graphical;


import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;

public class CustomBindings {

    /**
     * Binds min and max width properties of a certain region to the given ObservableValues;
     *
     * @param region is the region whose properties are to be bound
     * @param width  is the width property
     * @param height is the height property
     */
    public static void bindSize(Region region, ObservableValue<Number> width, ObservableValue<Number> height) {
        if (width != null) {
            region.minWidthProperty().bind(width);
            region.maxWidthProperty().bind(width);
        }
        if (height != null) {
            region.minHeightProperty().bind(height);
            region.maxHeightProperty().bind(height);
        }
    }

    /**
     * Binds fitWidth and fitHeight properties of a certain image to the given ObservableValues;
     *
     * @param image  is the image whose properties are to be bound
     * @param width  is the width property
     * @param height is the height property
     */
    public static void bindSize(ImageView image, ObservableValue<Number> width, ObservableValue<Number> height) {
        if (width != null) image.fitWidthProperty().bind(width);
        if (height != null) image.fitHeightProperty().bind(height);
    }

    /**
     * Binds the size of a region to the size of another region
     *
     * @param region             is the region whose properties are to be bound
     * @param regionToCopySizeOf is the region whose properties are to be copied
     */
    public static void bindSize(Region region, Region regionToCopySizeOf) {
        region.minWidthProperty().bind(regionToCopySizeOf.minWidthProperty());
        region.maxWidthProperty().bind(regionToCopySizeOf.maxWidthProperty());
        region.prefWidthProperty().bind(regionToCopySizeOf.prefWidthProperty());
        region.minHeightProperty().bind(regionToCopySizeOf.minHeightProperty());
        region.maxHeightProperty().bind(regionToCopySizeOf.maxHeightProperty());
        region.prefHeightProperty().bind(regionToCopySizeOf.prefHeightProperty());
    }

    /**
     * Binds the translateX and translateY properties of a node to the given ObservableValues
     *
     * @param node is the node whose translations are to be bound
     * @param x    the x translation property
     * @param y    the y translation property
     */
    public static void bindTranslate(Node node, ObservableValue<Number> x, ObservableValue<Number> y) {
        if (x != null) node.translateXProperty().bind(x);
        if (y != null) node.translateYProperty().bind(y);
    }

    /**
     * Binds an image view such that it resizes dynamically with a certain banner behind it such that:
     * - it never shows empty background space
     * - the top banner preserve its ratio
     * - the bottom background resizes and stretches such that is covers 2/3 of the background's height or the difference between the banner height and the total height of the stage
     *
     * @param lowerBackground the lower background
     * @param banner          the top background
     */
    public static void bindLowerPreGamePane(ImageView lowerBackground, ImageView banner) {
        CustomBindings.bindSize(lowerBackground, GraphicalUI.stage.widthProperty(),
                Bindings.max(
                        GraphicalUI.stage.heightProperty().subtract(banner.fitWidthProperty().multiply(banner.getImage().getHeight() / banner.getImage().getWidth()))
                        , GraphicalUI.stage.heightProperty().divide(3).multiply(2))
        );
    }

    /**
     * Binds the width of a column constraint to the given ObservableValue
     *
     * @param columnConstraints the column constraint
     * @param width             the intended with of the column constraint
     */
    public static void bindSize(ColumnConstraints columnConstraints, ObservableValue<Number> width) {
        if (width != null) {
            columnConstraints.minWidthProperty().bind(width);
            columnConstraints.maxWidthProperty().bind(width);
            columnConstraints.prefWidthProperty().bind(width);
        }
    }

    /**
     * Binds the height of a row constraint to the given ObservableValue
     *
     * @param rowConstraints the row constraint
     * @param height         the intended height of the row constraint
     */
    public static void bindSize(RowConstraints rowConstraints, ObservableValue<Number> height) {
        if (height != null) {
            rowConstraints.minHeightProperty().bind(height);
            rowConstraints.maxHeightProperty().bind(height);
            rowConstraints.prefHeightProperty().bind(height);
        }
    }

    public static Point2D getAbsolutePosition(Node node) {
        Bounds regionBounds = node.localToScene(node.getBoundsInLocal());
        return new Point2D(regionBounds.getMinX(), regionBounds.getMinY());
    }
}
