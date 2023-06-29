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


/**
 * The controller of the Create view.
 */
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

    /**
     * Initializes the controller's component's resize properties and animations.
     * It sets the state variables to their default values.
     * @param banner the top banner of the pregame view.
     */
    public void initialize(ImageView banner) {
        CustomBindings.bindLowerPreGamePane(backgroundClear, banner);
        CustomBindings.bindSize(createButton, null, GraphicalUI.stage.heightProperty().divide(13).multiply(2));
        Animations.applyHoverScaling(createButton);
        Effects.applyShadow(createButton);
    }


    /**
     * Makes a createRequest when the create button is clicked.
     * Sets the lobbyName and the playerName.
     */
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


    /**
     * Resets the controller to its original state.
     * Clears the lobbyNameField and the playerNameField.
     */
    public void reset() {
        Platform.runLater(() -> {
            lobbyNameField.clear();
            playerNameField.clear();
        });
    }


    /**
     * Processes how a certain KeyEvent should be interpreted while this Controller is active
     * @param keyEvent the keyEvent to process.
     */
    public void processKeyEvent(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case ESCAPE -> GraphicalUI.mainViewController.changeView(InternalViewState.INIT);
            case ENTER -> this.onCreateMouseClicked();
        }
    }
}
