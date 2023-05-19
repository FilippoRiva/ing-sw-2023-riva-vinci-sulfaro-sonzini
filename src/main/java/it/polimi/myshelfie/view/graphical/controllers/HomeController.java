package it.polimi.myshelfie.view.graphical.controllers;

import it.polimi.myshelfie.view.graphical.GraphicalUI;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class HomeController extends ViewController{
    public Label createLabel;
    public Label joinLabel;

    public void onCreateMouseEntered(MouseEvent mouseEvent) {
        createLabel.setTextFill(Color.color(1, 0, 0));
    }
    public void onCreateMouseExited(MouseEvent mouseEvent) {
        createLabel.setTextFill(Color.color(1, 1, 1));
    }
    public void onJoinMouseEntered(MouseEvent mouseEvent) {
        joinLabel.setTextFill(Color.color(1, 0, 0));
    }
    public void onJoinMouseExited(MouseEvent mouseEvent) {
        joinLabel.setTextFill(Color.color(1, 1, 1));
    }
    public void onCreateChoice(MouseEvent mouseEvent) {
        GraphicalUI.changeScene("create");
        progress.setVisible(true);
    }
    public void onJoinChoice(MouseEvent mouseEvent) {
        GraphicalUI.changeScene("join");
        progress.setVisible(true);
    }
}
