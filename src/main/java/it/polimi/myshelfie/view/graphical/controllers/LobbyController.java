package it.polimi.myshelfie.view.graphical.controllers;

import it.polimi.myshelfie.model.modelviews.LobbyModelView;
import it.polimi.myshelfie.view.ViewBuffer;
import it.polimi.myshelfie.view.graphical.GraphicalUI;
import it.polimi.myshelfie.view.graphical.Images;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LobbyController extends ViewController implements Initializable {
    public Label lobbyNameLabel;
    public VBox playersVBox;
    public Label playersInLabel;
    public Button minusButton;
    public Button plusButton;
    public Button startButton;

    ViewBuffer viewBuffer = ViewBuffer.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lobbyNameLabel.setText(viewBuffer.getLobbyModelView().getName());
        createListPlayers();
    }

    @FXML
    protected void onStartMouseClicked(MouseEvent mouseEvent) {
        viewBuffer.startRequest();
    }

    @FXML
    protected void onMouseMinusClicked(MouseEvent mouseEvent) {
        if(!viewBuffer.removePlayer()) displayError("cannot remove player");
    }

    @FXML
    protected void onMousePlusClicked(MouseEvent mouseEvent) {
        if(!viewBuffer.addPlayer()) displayError("cannot add player");
    }

    public void refreshListPlayers(){
        Platform.runLater(() -> playersVBox.getChildren().clear());
        createListPlayers();
    }

    private void createListPlayers(){
        LobbyModelView lobbyModelView = viewBuffer.getLobbyModelView();
        List<String> playersIn = lobbyModelView.getPlayers();

        for(String player : playersIn){
            Label label = new Label(player);
            label.setMaxWidth(837);
            label.setFont(Font.font("Kristen ITC", 40));
            label.setTextFill(Color.WHITE);
            label.setPadding(new Insets(0, 0, 0, 110));

            if(lobbyModelView.isRoomOwner(player)) {
                ImageView image = new ImageView(Images.getImage("omino.jpg"));
                image.setPreserveRatio(true);
                image.setFitWidth(0);
                image.setFitHeight(60);
                label.setPadding(new Insets(0));
                HBox hBox = new HBox(image, label);
                hBox.setSpacing(20);
                Platform.runLater(() -> playersVBox.getChildren().add(hBox));
            }
            else if(player.equals(GraphicalUI.getPlayerName())) {
                label.setTextFill(Color.rgb(218, 106, 45));
                label.setFont(Font.font("Kristen ITC", 50));
            }

            if(!lobbyModelView.isRoomOwner(player))
                Platform.runLater(() -> playersVBox.getChildren().add(label));
        }

        int currentPlayersIn = lobbyModelView.getPlayers().size();
        int maxNumPlayers = lobbyModelView.getGameConfig().getNumPlayers();
        if(lobbyModelView.isRoomOwner(GraphicalUI.getPlayerName())){
            minusButton.setDisable(false);
            plusButton.setDisable(false);
            startButton.setDisable(currentPlayersIn != maxNumPlayers);
        }else{
            minusButton.setDisable(true);
            plusButton.setDisable(true);
            startButton.setDisable(true);
        }
        Platform.runLater(() -> playersInLabel.setText(currentPlayersIn + "/" + maxNumPlayers));
    }

    @Override
    public void onBackArrowClick() {
        super.onBackArrowClick();
        viewBuffer.exitRequest();
    }
}
