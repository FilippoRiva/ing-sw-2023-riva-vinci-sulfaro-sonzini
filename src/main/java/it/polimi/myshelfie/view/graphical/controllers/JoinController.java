package it.polimi.myshelfie.view.graphical.controllers;

import it.polimi.myshelfie.model.modelviews.ModelView;
import it.polimi.myshelfie.view.ViewBuffer;
import it.polimi.myshelfie.view.graphical.GraphicalUI;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class JoinController extends ViewController implements Initializable {
    @FXML
    protected TextField playerNameField;
    @FXML
    protected VBox lobbiesVBox;
    @FXML
    protected Button joinButton;

    ViewBuffer viewBuffer = ViewBuffer.getInstance();
    String selected = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createListLobbies();
    }
    @FXML
    protected void onJoinMouseClicked(MouseEvent mouseEvent) {
        String playerName = playerNameField.getText();

        if (playerName.isEmpty()) {
            displayError("choose a name!");
        }else {
            viewBuffer.joinRequest(selected, playerName);
            GraphicalUI.setMyName(playerName);
            startLoading();
        }
    }

    public void refreshListLobbies(){
        Platform.runLater(() -> lobbiesVBox.getChildren().clear());
        createListLobbies();
    }
    private void createListLobbies(){
        ModelView modelView = viewBuffer.getModelView();
        List<String> lobbies = modelView.getLobbies();
        for(String lobby : lobbies){
            AnchorPane anchorPane = new AnchorPane();
            if(lobby.equals(selected)) anchorPane.setBackground(Background.fill(Color.rgb(218, 106, 45)));

            anchorPane.setOnMouseClicked(event -> {
                selected = lobby;
                Platform.runLater(() -> joinButton.setDisable(false));
                refreshListLobbies();
            });


            Label label = generateStandardLabel(lobby);
            label.setAlignment(Pos.TOP_LEFT);
            anchorPane.getChildren().add(label);
            AnchorPane.setLeftAnchor(label, 20D);

            label = generateStandardLabel(modelView.getCurrentSize(lobby) + "/" + modelView.getMaxSize(lobby));
            label.setAlignment(Pos.TOP_RIGHT);
            anchorPane.getChildren().add(label);
            AnchorPane.setRightAnchor(label, 20D);

            Platform.runLater(() -> lobbiesVBox.getChildren().add(anchorPane));
        }
    }
    private Label generateStandardLabel(String text){
        Label label = new Label(text);
        label.setMaxWidth(837);
        label.setTextFill(Color.WHITE);
        return label;
    }
}
