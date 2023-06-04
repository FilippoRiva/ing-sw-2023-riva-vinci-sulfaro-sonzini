package it.polimi.myshelfie.view.graphical.controllers;

import it.polimi.myshelfie.view.InternalViewState;
import it.polimi.myshelfie.view.ViewManager;
import it.polimi.myshelfie.view.graphical.Animations;
import it.polimi.myshelfie.view.graphical.CustomBindings;
import it.polimi.myshelfie.view.graphical.GraphicalUI;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class CreateController {
    @FXML
    protected TextField lobbyNameField;
    @FXML
    protected TextField playerNameField;
    @FXML
    protected ImageView backgroundClear;
    @FXML
    protected Button createButton;
    @FXML
    protected Label createErrorLabel;
    PreGameController preGameController;
    ViewManager viewManager = ViewManager.getInstance();

    public void initialize(ImageView banner,PreGameController preGameController){
        CustomBindings.bindLowerPreGamePane(backgroundClear,banner);
        Animations.applyButtonAnimation(createButton);
        this.preGameController = preGameController;
    }

    @FXML
    protected void onCreateMouseClicked() {
        String playerName = playerNameField.getText();
        String lobbyName = lobbyNameField.getText();

        if (lobbyName.isEmpty()) {
            Animations.emptyFieldError(lobbyNameField).play();
        } else if (playerName.isEmpty()) {
            Animations.emptyFieldError(playerNameField).play();
        } else {
            viewManager.createRequest(lobbyName, playerName);
            ViewManager.setUsername(playerName);
        }
    }

    public void setKeyListeners() {
        Scene scene = GraphicalUI.stage.getScene();
        scene.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()){
                case ESCAPE -> GraphicalUI.mainViewController.changeView(InternalViewState.INIT);
                case ENTER -> this.onCreateMouseClicked();
            }
        });
    }

    public void reset() {
        Platform.runLater(()->{
            lobbyNameField.clear();
            playerNameField.clear();
        });
    }
}
