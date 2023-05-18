package it.polimi.myshelfie.view.graphical.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.io.IOException;

public class HomeController {
    public AnchorPane rootPane;
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
    public void onCreateChoice(MouseEvent mouseEvent) throws IOException {
        changeScreen("create-view");
    }
    public void onJoinChoice(MouseEvent mouseEvent) throws IOException {
        changeScreen("join-view");
    }

    private void changeScreen(String fxmlName) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/"+fxmlName+".fxml"));
        AnchorPane pane = loader.load();
        rootPane.getChildren().setAll(pane);
    }
}
