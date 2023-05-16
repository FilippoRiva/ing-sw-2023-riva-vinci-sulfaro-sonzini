package it.polimi.myshelfie.view.graphical;

import it.polimi.myshelfie.view.ViewBuffer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.io.IOException;

public class PreGameController {
    public AnchorPane rootPane;
    public Label createLabel;
    public Label joinLabel;
    public TextField playerName;
    public TextField roomName;

    @FXML
    protected void onCreateMouseEntered() {
        createLabel.setTextFill(Color.color(1, 0, 0));
    }
    @FXML
    protected void onCreateMouseExited() {
        createLabel.setTextFill(Color.color(1, 1, 1));
    }
    @FXML
    protected void onJoinMouseEntered() {
        joinLabel.setTextFill(Color.color(1, 0, 0));
    }
    @FXML
    protected void onJoinMouseExited() {
        joinLabel.setTextFill(Color.color(1, 1, 1));
    }
    @FXML
    protected void onCreateChoice() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/create-view.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    @FXML
    protected void onJoinChoice() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/join-view.fxml"));
        rootPane.getChildren().setAll(pane);
    }


    @FXML
    protected void onCreateMouseClicked() {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/create-view.fxml"));
//        loader.setController(new CreateController(viewBuffer));
//        AnchorPane pane = loader.load();
//        rootPane.getChildren().setAll(pane);
        String plName = playerName.getText();
        String rmName = playerName.getText();

        //TODO create game
    }
}