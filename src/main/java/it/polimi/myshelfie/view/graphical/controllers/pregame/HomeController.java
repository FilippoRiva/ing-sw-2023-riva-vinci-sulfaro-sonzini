package it.polimi.myshelfie.view.graphical.controllers.pregame;

import it.polimi.myshelfie.view.InternalViewState;
import it.polimi.myshelfie.view.graphical.Animations;
import it.polimi.myshelfie.view.graphical.CustomBindings;
import it.polimi.myshelfie.view.graphical.Effects;
import it.polimi.myshelfie.view.graphical.GraphicalUI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * The controller of the Home view.
 */
public class HomeController {
    @FXML
    ImageView clearBackground;
    @FXML
    Button joinButton;
    @FXML
    Button createButton;

    /**
     * Initializes the controller's component's resize properties and animations.
     * It sets the state variables to their default values.
     * @param homeBanner the top banner of the pregame view.
     */
    public void initialize(ImageView homeBanner) {
        Stage stage = GraphicalUI.stage;

        CustomBindings.bindLowerPreGamePane(clearBackground, homeBanner);
        CustomBindings.bindSize(joinButton, stage.widthProperty().divide(2), stage.heightProperty().divide(5));
        CustomBindings.bindSize(createButton, stage.widthProperty().divide(2), stage.heightProperty().divide(5));

        Effects.applyShadow(joinButton);
        Effects.applyShadow(createButton);

        Animations.applyHoverScaling(joinButton);
        Animations.applyHoverScaling(createButton);
    }

    /**
     * Goes to the Create internal view state.
     */
    @FXML
    protected void onCreateChoice() {
        GraphicalUI.mainViewController.changeView(InternalViewState.CREATE);
    }

    /**
     * Goes to the Join internal view state.
     */
    @FXML
    protected void onJoinChoice() {
        GraphicalUI.mainViewController.changeView(InternalViewState.JOIN);
    }

    /**
     * Processes how a certain KeyEvent should be interpreted while this Controller is active
     * @param keyEvent the keyEvent to process.
     */
    public void processKeyEvent(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case C -> onCreateChoice();
            case J -> onJoinChoice();
        }
    }
}
