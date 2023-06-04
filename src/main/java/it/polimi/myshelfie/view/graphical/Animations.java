package it.polimi.myshelfie.view.graphical;


import javafx.animation.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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

    public static TranslateTransition getSlideDownTransition(Button button){
        TranslateTransition tt = new TranslateTransition(Duration.millis(200));
        tt.setFromY(-button.getLayoutY()-button.getHeight());
        tt.setToY(0);
        tt.setNode(button);
        return tt;
    }

    public static TranslateTransition getSlideUpTransition(Button button){
        TranslateTransition tt = new TranslateTransition(Duration.millis(200));
        tt.setFromY(0);
        tt.setToY(-button.getLayoutY()-button.getHeight());
        tt.setNode(button);
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
}
