package it.polimi.myshelfie.view.graphical.controllers.pregame;

import it.polimi.myshelfie.view.InternalViewState;
import it.polimi.myshelfie.view.ViewManager;
import it.polimi.myshelfie.view.graphical.Animations;
import it.polimi.myshelfie.view.graphical.CustomBindings;
import it.polimi.myshelfie.view.graphical.Effects;
import it.polimi.myshelfie.view.graphical.GraphicalUI;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

public class CreateController {
    final ViewManager viewManager = ViewManager.getInstance();
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

    public void initialize(ImageView banner, PreGameController preGameController) {
        CustomBindings.bindLowerPreGamePane(backgroundClear, banner);
        CustomBindings.bindSize(createButton,null,GraphicalUI.stage.heightProperty().divide(13).multiply(2));
        Animations.applyHoverScaling(createButton);
        Effects.applyShadow(createButton);
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

    public void reset() {
        Platform.runLater(() -> {
            lobbyNameField.clear();
            playerNameField.clear();
        });
    }

    public void processKeyEvent(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case ESCAPE -> GraphicalUI.mainViewController.changeView(InternalViewState.INIT);
            case ENTER -> this.onCreateMouseClicked();
        }
    }
}
