package it.polimi.myshelfie.view.graphical.controllers;

import it.polimi.myshelfie.view.graphical.GraphicalUI;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class HomeController extends ViewController{
    @FXML
    protected void onCreateChoice() {
        GraphicalUI.changeScene("create");
        progress.setVisible(true);
    }
    @FXML
    protected void onJoinChoice() {
        GraphicalUI.changeScene("join");
        progress.setVisible(true);
    }
}
