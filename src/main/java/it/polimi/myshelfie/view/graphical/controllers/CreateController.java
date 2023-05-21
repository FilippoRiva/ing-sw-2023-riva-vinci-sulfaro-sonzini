package it.polimi.myshelfie.view.graphical.controllers;

import it.polimi.myshelfie.view.ViewBuffer;
import it.polimi.myshelfie.view.graphical.GraphicalUI;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Timer;
import java.util.TimerTask;

public class CreateController extends ViewController{
    public TextField lobbyNameField;
    public TextField playerNameField;
    public Label createLabel;

    ViewBuffer viewBuffer = ViewBuffer.getInstance();


    @FXML
    protected void onCreateMouseEntered(MouseEvent mouseEvent) {
        createLabel.setTextFill(Color.rgb(229, 146, 93));
    }
    @FXML
    protected void onCreateMouseExited(MouseEvent mouseEvent) {
        createLabel.setTextFill(Color.rgb(229, 146, 93));
    }
    @FXML
    protected void onCreateMouseClicked(MouseEvent mouseEvent) {
        String playerName = playerNameField.getText();
        String lobbyName = lobbyNameField.getText();

        if (playerName.isEmpty() || lobbyName.isEmpty()) {
            displayError("compile all the fields");
            errorLabel.setFont(Font.font("Kristen ITC", 20));
        }else {
            viewBuffer.createRequest(lobbyName, playerName);
            GraphicalUI.setPlayerName(playerName);
            startLoading();
        }
    }
}
