package it.polimi.myshelfie.view.graphical;


import javafx.animation.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.util.Timer;
import java.util.TimerTask;

public class Animations {
    public static void applyButtonAnimation(Button button){
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100));
        scaleTransition.setFromX(1);
        scaleTransition.setFromY(1);
        scaleTransition.setToX(1.05);
        scaleTransition.setToY(1.05);
        scaleTransition.setNode(button);
        ScaleTransition reverseScaleTransition = new ScaleTransition(Duration.millis(200));
        reverseScaleTransition.setFromX(1.05);
        reverseScaleTransition.setFromY(1.05);
        reverseScaleTransition.setToX(1);
        reverseScaleTransition.setToY(1);
        reverseScaleTransition.setNode(button);
        button.setOnMouseEntered((mouseEvent -> scaleTransition.play()));
        button.setOnMouseExited((mouseEvent -> reverseScaleTransition.play()));
    }

    public static void timer(Runnable runnable, long time){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runnable.run();
            }
        }, time);
    }

    public static TranslateTransition getSlideUpTransition(StackPane pane){
        TranslateTransition tt = new TranslateTransition(Duration.millis(200));
        tt.setNode(pane);
        tt.setFromY(pane.getHeight());
        tt.setToY(0);
        return tt;
    }

    public static TranslateTransition getSlideDownTransition(StackPane pane){
        TranslateTransition tt = new TranslateTransition(Duration.millis(200));
        tt.setNode(pane);
        tt.setFromY(0);
        tt.setToY(pane.getHeight());
        return tt;
    }

    public static TranslateTransition getSlideDownTransition(Region region){
        TranslateTransition tt = new TranslateTransition(Duration.millis(200));
        tt.setFromY(-region.getLayoutY()-region.getHeight());
        tt.setToY(0);
        tt.setNode(region);
        return tt;
    }

    public static TranslateTransition getSlideUpTransition(Region region){
        TranslateTransition tt = new TranslateTransition(Duration.millis(200));
        tt.setFromY(0);
        tt.setToY(-region.getLayoutY()-region.getHeight());
        tt.setNode(region);
        return tt;
    }

    public static Animation buttonError(Button button) {
        RotateTransition rt = new RotateTransition(Duration.millis(40));
        RotateTransition rt2 = new RotateTransition(Duration.millis(60));
        RotateTransition rt3 = new RotateTransition(Duration.millis(40));
        SequentialTransition st = new SequentialTransition(rt,rt2,rt3);
        ScaleTransition scaleT = new ScaleTransition(Duration.millis(70));
        ParallelTransition pt = new ParallelTransition(st,scaleT);

        scaleT.setFromX(1);
        scaleT.setToX(1.2);
        scaleT.setFromY(1);
        scaleT.setToY(1.2);
        scaleT.setCycleCount(2);
        scaleT.setAutoReverse(true);
        scaleT.setNode(button);

        rt.setByAngle(10);
        rt.setNode(button);

        rt2.setByAngle(-20);
        rt2.setNode(button);

        rt3.setByAngle(10);
        rt3.setToAngle(0);
        rt3.setNode(button);

        return pt;
    }

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

    public static TranslateTransition slideRegionFromTop(Region region, boolean forceVisible){
        region.setTranslateY(-region.getLayoutY()-region.getHeight());
        if(forceVisible){
            region.setVisible(true);
            region.setMouseTransparent(false);
        }
        TranslateTransition tt = Animations.getSlideDownTransition(region);
        return tt;
    }

    public static TranslateTransition slideRegionToTop(Region region){
        TranslateTransition tt = Animations.getSlideUpTransition(region);
        tt.play();
        tt.setOnFinished((finish)->{
            region.setVisible(false);
            region.setMouseTransparent(true);
        });
        return tt;
    }

    public static Animation fadeIn(Region region) {
        FadeTransition ft = new FadeTransition(Duration.millis(100),region);
        ft.setByValue(1);
        return ft;
    }

    public static Animation pop(Region region){
        ScaleTransition pop = new ScaleTransition(Duration.millis(100),region);
        pop.setFromX(0);
        pop.setFromY(0);
        pop.setByX(1);
        pop.setByY(1);
        return pop;
    }

    public static Animation popFadeIn(Region region){
        Animation fade = fadeIn(region);
        Animation pop = pop(region);
        return new ParallelTransition(fade,pop);
    }

    public static Animation reverse(Animation animation){
        animation.setRate(-1);
        return animation;
    }
}
