package it.polimi.myshelfie.view.graphical;


import it.polimi.myshelfie.view.graphical.components.TileImageView;
import javafx.animation.*;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Animations {

    /**
     * Applies two animations. When the node is being hovered the node's scale property goes to 1.05.
     * When the node was hovered and is no longer being hovered the node's scale property returns to 1.
     * @param node the node to be scaled
     */
    public static void applyHoverScaling(Node node){
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100));
        scaleTransition.setFromX(1);
        scaleTransition.setFromY(1);
        scaleTransition.setToX(1.05);
        scaleTransition.setToY(1.05);
        scaleTransition.setNode(node);
        ScaleTransition reverseScaleTransition = new ScaleTransition(Duration.millis(200));
        reverseScaleTransition.setFromX(1.05);
        reverseScaleTransition.setFromY(1.05);
        reverseScaleTransition.setToX(1);
        reverseScaleTransition.setToY(1);
        reverseScaleTransition.setNode(node);
        node.setOnMouseEntered((mouseEvent -> scaleTransition.play()));
        node.setOnMouseExited((mouseEvent -> reverseScaleTransition.play()));
    }

    /**
     * Util function that runs a certain task after a certain time of milliseconds
     * @param runnable the task
     * @param time the time in milliseconds
     */
    public static void timer(Runnable runnable, long time){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runnable.run();
            }
        }, time);
    }

    /**
     * Returns an animation that animates a StackPane from the bottom of the view to the intended position of the stackPane
     * in 200 milliseconds.
     * @param pane the StackPane;
     * @return the animation;
     */
    public static TranslateTransition getSlideFromBottom(StackPane pane){
        TranslateTransition tt = new TranslateTransition(Duration.millis(200));
        tt.setNode(pane);
        tt.setFromY(pane.getHeight());
        tt.setToY(0);
        return tt;
    }

    /**
     * Returns an animation that animates a StackPane from the intended position of the stackPane to the bottom of the view
     * in 200 milliseconds.
     * @param pane the StackPane;
     * @return the animation;
     */
    public static TranslateTransition getSlideToBottom(StackPane pane){
        TranslateTransition tt = new TranslateTransition(Duration.millis(200));
        tt.setNode(pane);
        tt.setFromY(0);
        tt.setToY(pane.getHeight());
        return tt;
    }


    /**
     * Returns an animation that animates a Region from the top of the view to the intended position of the Region
     *  in 200 milliseconds. It forces the visibility of the node if the forceVisible parameter is true.
     * @param region the region to be slid
     * @param forceVisible the force visibility flag
     * @return the animation
     */
    public static TranslateTransition slideRegionFromTop(Region region, boolean forceVisible){
        region.setTranslateY(-region.getLayoutY()-region.getHeight());
        if(forceVisible){
            region.setVisible(true);
            region.setMouseTransparent(false);
        }
        return Animations.getSlideFromTop(region);
    }

    /**
     * Returns an animation that animates a Region from the top of the view to the intended position of the Region
     *  in 200 milliseconds.
     * @param region the region;
     * @return the animation;
     */
    private static TranslateTransition getSlideFromTop(Region region){
        TranslateTransition tt = new TranslateTransition(Duration.millis(200));
        tt.setFromY(-region.getLayoutY()-region.getHeight());
        tt.setToY(0);
        tt.setNode(region);
        return tt;
    }

    /**
     * Returns an animation that animates a Region from it's intended position to the top of the view
     *  in 200 milliseconds. It Automatically hides the node when the animation finishes
     * @param region the region to be slid.
     * @return the animation.
     */
    public static TranslateTransition slideRegionToTop(Region region){
        TranslateTransition tt = Animations.getSlideToTop(region);
        tt.play();
        tt.setOnFinished((finish)->{
            region.setVisible(false);
            region.setMouseTransparent(true);
        });
        return tt;
    }

    /**
     * Returns an animation that animates a Region from it's intended position to the top of the view
     *  in 200 milliseconds.
     * @param region the region;
     * @return the animation;
     */
    private static TranslateTransition getSlideToTop(Region region){
        TranslateTransition tt = new TranslateTransition(Duration.millis(200));
        tt.setFromY(0);
        tt.setToY(-region.getLayoutY()-region.getHeight());
        tt.setNode(region);
        return tt;
    }
    /**
     * Returns an animation that shakes a node and scales it. To be used for visual error notification.
     * @param node the node
     * @return the animation
     */
    public static Animation nodeError(Node node, double rotation) {
        RotateTransition rt = new RotateTransition(Duration.millis(40));
        RotateTransition rt2 = new RotateTransition(Duration.millis(60));
        RotateTransition rt3 = new RotateTransition(Duration.millis(40));
        SequentialTransition st = new SequentialTransition(rt,rt2,rt3);
        ScaleTransition scaleT = new ScaleTransition(Duration.millis(70));
        ParallelTransition pt = new ParallelTransition(st,scaleT);

        scaleT.setFromX(1);
        scaleT.setToX(1.1);
        scaleT.setFromY(1);
        scaleT.setToY(1.1);
        scaleT.setCycleCount(2);
        scaleT.setAutoReverse(true);
        scaleT.setNode(node);

        rt.setByAngle(rotation);
        rt.setNode(node);

        rt2.setByAngle(-2*rotation);
        rt2.setNode(node);

        rt3.setByAngle(rotation);
        rt3.setToAngle(0);
        rt3.setNode(node);

        return pt;
    }

    /**
     * Returns an animation that scales a text field to 0.9 and auto-reverses itself. To be used for visual error notification.
     * @param field the textField to the scaled
     * @return the field
     */
    public static Animation emptyFieldError(TextField field) {
        ScaleTransition st = new ScaleTransition(Duration.millis(150));
        st.setNode(field);
        st.setFromX(1);
        st.setToX(0.9);
        st.setFromY(1);
        st.setToY(0.9);
        st.setCycleCount(2);
        st.setAutoReverse(true);
        return st;
    }

    /**
     * Returns an animation that fades in a node if the node opacity is set to lower than 1.
     * @param node the region to be faded in
     * @return the fading animation
     */
    public static Animation fadeIn(Node node) {
        FadeTransition ft = new FadeTransition(Duration.millis(100),node);
        ft.setByValue(1);
        return ft;
    }

    /**
     * Returns an animation that fades out a node if the node opacity is set to lower than 1.
     * @param node the region to be faded in
     * @return the fading animation
     */
    public static Animation fadeOut(Node node) {
        FadeTransition ft = new FadeTransition(Duration.millis(100),node);
        ft.setByValue(-1);
        return ft;
    }

    /**
     * Returns an animation that scales in 100 millis a node from 0% scaling to 100% scaling.
     * @param node the node to be popped
     * @return the animation
     */
    public static Animation pop(Node node){
        ScaleTransition pop = new ScaleTransition(Duration.millis(100),node);
        pop.setFromX(0);
        pop.setFromY(0);
        pop.setByX(1);
        pop.setByY(1);
        return pop;
    }

    public static Animation reversePop(Node node){
        ScaleTransition pop = new ScaleTransition(Duration.millis(100),node);
        pop.setFromX(1);
        pop.setFromY(1);
        pop.setToX(0);
        pop.setToY(0);
        return pop;
    }

    /**
     * Returns the parallel combination of the pop and fadeIn animations
     * @param node the node to be animated
     * @return the animation
     */
    public static Animation popFadeIn(Node node){
        Animation fade = fadeIn(node);
        Animation pop = pop(node);
        return new ParallelTransition(fade,pop);
    }

    public static Animation popFadeOut(Node node){
        Animation fade = Animations.fadeOut(node);
        Animation revPop = reversePop(node);
        return new ParallelTransition(fade,revPop);
    }

    /**
     * Reverses any kind of animation.
     * It modifies the existing animation.
     * @param animation the animation to be reversed.
     * @return the reverse animation
     */
    public static Animation reverse(Animation animation){
        animation.setRate(-1);
        return animation;
    }

    /**
     * Translate a region to the left of the view
     * @param pane the pane to be slid
     * @return the animation
     */
    public static Animation tranlateToLeft(Region pane) {
        TranslateTransition animation = new TranslateTransition(Duration.millis(200));
        animation.setNode(pane);
        animation.setByX(-(pane.getWidth()+pane.getLayoutX()));
        return animation;
    }

    /**
     * Translate a region back to its original position from the left of the view
     * @param pane the pane to be slid
     * @return the animation
     */
    public static Animation translateFromLeft(Region pane){
        TranslateTransition animation = new TranslateTransition(Duration.millis(200));
        animation.setNode(pane);
        animation.setFromX(-(pane.getWidth()+pane.getLayoutX()));
        animation.setToX(0);
        return animation;
    }

    /**
     * Rotates the nodes of a certain amount of degrees in parallel
     * @param degrees the degrees
     * @param nodes the varargs nodes
     * @return the animation
     */
    public static Animation rotateY(double degrees,Duration duration,Node ... nodes){
        List<RotateTransition> rotations = new ArrayList<>();
        for(Node node : nodes){
            RotateTransition rt = new RotateTransition(duration);
            rt.setAxis(Rotate.Y_AXIS);
            rt.setNode(node);
            rt.setByAngle(degrees);
            rotations.add(rt);
        }
        return new ParallelTransition(rotations.toArray(new RotateTransition[0]));
    }

    /**
     * Util method to get an X-Y paired scaling animation.
     * @param node the node to be scaled
     * @param fromXY the fromX and fromY parameters
     * @param toXY the toX and toY parameters
     * @param byXY the byX and byY parameters
     * @return the scaling animation
     */
    public static Animation scale(Node node, double fromXY, double toXY, double byXY){
        ScaleTransition st = new ScaleTransition(Duration.millis(200));
        if(fromXY>=0){
            st.setFromX(fromXY);
            st.setFromY(fromXY);
        }
        if(toXY>=0){
            st.setToX(toXY);
            st.setToY(toXY);
        }
        if(byXY>=0){
            st.setByX(byXY);
            st.setByY(byXY);
        }
        st.setNode(node);
        return st;
    }

    public static Animation translateBy(Node node, double byX, double byY) {
        TranslateTransition tt = new TranslateTransition(Duration.millis(200));
        tt.setNode(node);
        tt.setByX(byX);
        tt.setByY(byY);
        return tt;
    }

    public static Animation translateTo(Node node, Point2D coordinate){
        TranslateTransition tt = new TranslateTransition(Duration.millis(400));
        node.setMouseTransparent(true);
        tt.setOnFinished((evt)->node.setMouseTransparent(false));
        tt.setNode(node);
        tt.setToX(coordinate.getX()-CustomBindings.getAbsolutePosition(node).getX());
        tt.setToY(coordinate.getY()-CustomBindings.getAbsolutePosition(node).getY());
        return tt;
    }

    public static Animation fixTranslateTo(Node node, Point2D coordinate){
        TranslateTransition tt = new TranslateTransition(Duration.millis(400));
        tt.setNode(node);
        node.setMouseTransparent(true);
        tt.setOnFinished((evt)->node.setMouseTransparent(false));
        double yPos = CustomBindings.getAbsolutePosition(node).getY();
        if(coordinate.getY()-yPos==0) {
            node.setMouseTransparent(false);
            return new TranslateTransition(Duration.millis(0));
        }
        tt.setByY(coordinate.getY()-yPos);
        return tt;
    }

    public static Animation eraseTranslation(Node node){
        TranslateTransition tt = new TranslateTransition(Duration.millis(200));
        node.setMouseTransparent(true);
        tt.setOnFinished((evt)->node.setMouseTransparent(false));
        tt.setNode(node);
        tt.setByX(-node.getTranslateX());
        tt.setByY(-node.getTranslateY());
        return tt;
    }

    public static Animation tileRotateIn(TileImageView tile,Duration duration){
        tile.setRotationAxis(Rotate.Y_AXIS);
        tile.setRotate(-90);
        tile.setVisible(true);
        return rotateY(90,duration,tile);
    }

    public static Animation tileRotateOut(TileImageView tile, Duration duration){
        tile.setRotationAxis(Rotate.Y_AXIS);
        tile.setRotate(0);
        tile.setVisible(true);
        Animation rotation = rotateY(-90,duration,tile);
        rotation.setOnFinished((evt)->tile.setVisible(false));
        return rotation;
    }
}
