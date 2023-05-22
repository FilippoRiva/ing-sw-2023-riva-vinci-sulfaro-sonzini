package it.polimi.myshelfie.view.graphical.controllers;

import it.polimi.myshelfie.view.graphical.GraphicalUI;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;

import java.util.Timer;
import java.util.TimerTask;

public abstract class ViewController {
    @FXML
    protected ProgressIndicator progress;
    @FXML
    protected Label errorLabel;

    public void displayError(String errorMessage){
        stopLoading();
        if(errorLabel != null){
            Platform.runLater(() -> {
                errorLabel.setText(errorMessage);
                errorLabel.setVisible(true);

            });
            timer(() -> Platform.runLater(() -> errorLabel.setVisible(false)), 3000);
        }

    }
    public void startLoading(){
        Platform.runLater(() -> progress.setVisible(true));
    }
    public void stopLoading(){
        Platform.runLater(() -> progress.setVisible(false));
    }

    @FXML
    protected void onBackArrowClick(){
        GraphicalUI.changeScene("home");
    }

    public void timer(Runnable runnable, long time){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runnable.run();
            }
        }, time);
    }
}
