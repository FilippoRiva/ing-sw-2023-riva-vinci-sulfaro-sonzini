package it.polimi.myshelfie.view.graphical.controllers;

import it.polimi.myshelfie.view.graphical.GraphicalUI;
import javafx.fxml.FXML;
import javafx.scene.Scene;

public class HomeController extends ViewController {
    @FXML
    protected void onCreateChoice() {
        GraphicalUI.changeScene("create");
        progress.setVisible(true);
    }
    @FXML
    protected void onJoinChoice() {
        GraphicalUI.changeScene("join");
        progress.setVisible(true);
    }

    @Override
    public void setKeyListeners(Scene scene) {
        scene.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()){
                case C -> onCreateChoice();
                case J -> onJoinChoice();
            }
        });
    }

    @Override
    protected void onBackArrowClick() {

    }
}
