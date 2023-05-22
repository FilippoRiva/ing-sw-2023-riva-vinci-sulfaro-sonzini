package it.polimi.myshelfie.view.graphical.controllers;

import it.polimi.myshelfie.view.ViewBuffer;
import it.polimi.myshelfie.view.graphical.GraphicalUI;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class CreateController extends ViewController{
    @FXML
    protected TextField lobbyNameField;
    @FXML
    protected TextField playerNameField;

    ViewBuffer viewBuffer = ViewBuffer.getInstance();

    @FXML
    protected void onCreateMouseClicked(MouseEvent mouseEvent) {
        String playerName = playerNameField.getText();
        String lobbyName = lobbyNameField.getText();

        if (playerName.isEmpty() || lobbyName.isEmpty()) {
            displayError("compile all the fields!");
        }else {
            viewBuffer.createRequest(lobbyName, playerName);
            GraphicalUI.setMyName(playerName);
            startLoading();
        }
    }
}
