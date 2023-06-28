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

public class HomeController {
    @FXML
    ImageView clearBackground;
    @FXML
    Button joinButton;
    @FXML
    Button createButton;

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

    @FXML
    protected void onCreateChoice() {
        GraphicalUI.mainViewController.changeView(InternalViewState.CREATE);
    }

    @FXML
    protected void onJoinChoice() {
        GraphicalUI.mainViewController.changeView(InternalViewState.JOIN);
    }

    public void processKeyEvent(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case C -> onCreateChoice();
            case J -> onJoinChoice();
        }
    }
}
