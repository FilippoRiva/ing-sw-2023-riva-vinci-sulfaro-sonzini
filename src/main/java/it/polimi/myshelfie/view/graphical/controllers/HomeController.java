package it.polimi.myshelfie.view.graphical.controllers;

import it.polimi.myshelfie.view.InternalViewState;
import it.polimi.myshelfie.view.graphical.Animations;
import it.polimi.myshelfie.view.graphical.CustomBindings;
import it.polimi.myshelfie.view.graphical.GraphicalUI;
import javafx.fxml.FXML;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
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

        CustomBindings.bindLowerPreGamePane(clearBackground,homeBanner);
        CustomBindings.bindSize(joinButton, stage.widthProperty().divide(2),stage.heightProperty().divide(5));
        CustomBindings.bindSize(createButton, stage.widthProperty().divide(2),stage.heightProperty().divide(5));

        Animations.applyButtonAnimation(joinButton);
        Animations.applyButtonAnimation(createButton);
    }
    @FXML
    protected void onCreateChoice() {
        GraphicalUI.mainViewController.changeView(InternalViewState.CREATE);
    }
    @FXML
    protected void onJoinChoice() {
        GraphicalUI.mainViewController.changeView(InternalViewState.JOIN);
    }

    public void setKeyListeners() {
        Scene scene = GraphicalUI.stage.getScene();
        scene.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()){
                case C -> onCreateChoice();
                case J -> onJoinChoice();
            }
        });
    }
}
