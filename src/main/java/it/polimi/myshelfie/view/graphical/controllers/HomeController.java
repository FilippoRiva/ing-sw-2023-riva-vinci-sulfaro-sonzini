package it.polimi.myshelfie.view.graphical.controllers;

import it.polimi.myshelfie.view.graphical.GraphicalUI;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class HomeController extends ViewController{
    public Label createLabel;
    public Label joinLabel;

    @FXML
    protected void onCreateMouseEntered(MouseEvent mouseEvent) {createLabel.setTextFill(Color.rgb(229,146,93));
    }
    @FXML
    protected void onCreateMouseExited(MouseEvent mouseEvent) {createLabel.setTextFill(Color.rgb(255, 255, 255));
    }
    @FXML
    protected void onJoinMouseEntered(MouseEvent mouseEvent) {joinLabel.setTextFill(Color.rgb(229, 146, 93));
    }
    @FXML
    protected void onJoinMouseExited(MouseEvent mouseEvent) {joinLabel.setTextFill(Color.rgb(255, 255, 255));
    }
    @FXML
    protected void onCreateChoice(MouseEvent mouseEvent) {
        GraphicalUI.changeScene("create");
        progress.setVisible(true);
    }
    @FXML
    protected void onJoinChoice(MouseEvent mouseEvent) {
        GraphicalUI.changeScene("join");
        progress.setVisible(true);
    }
}
