package it.polimi.myshelfie.view.graphical;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.io.IOException;

public class HomeController {
    public AnchorPane rootPane;
    public AnchorPane create;
    public Label createLabel;
    public AnchorPane join;
    public Label joinLabel;

    @FXML
    protected void onCreateMouseEntered() {
        createLabel.setTextFill(Color.color(1, 0, 0));
    }
    @FXML
    protected void onCreateMouseExited() {
        createLabel.setTextFill(Color.color(1, 1, 1));
    }
    @FXML
    protected void onJoinMouseEntered() {
        joinLabel.setTextFill(Color.color(1, 0, 0));
    }
    @FXML
    protected void onJoinMouseExited() {
        joinLabel.setTextFill(Color.color(1, 1, 1));
    }

    public void onCreateMouseClicked() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/create-view.fxml"));
        rootPane.getChildren().setAll(pane);
    }
}