package it.polimi.myshelfie.view.graphical.controllers;

import it.polimi.myshelfie.view.ViewBuffer;
import it.polimi.myshelfie.view.graphical.GraphicalUI;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

public class CreateController extends ViewController{
    @FXML
    protected TextField lobbyNameField;
    @FXML
    protected TextField playerNameField;

    ViewBuffer viewBuffer = ViewBuffer.getInstance();

    @Override
    public void setKeyListeners(Scene scene) {
        scene.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()){
                case ESCAPE -> onBackArrowClick();
                case ENTER -> onCreateMouseClicked();
            }
        });
    }

    @FXML
    protected void onCreateMouseClicked() {
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
