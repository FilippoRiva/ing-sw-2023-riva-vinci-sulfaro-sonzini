package it.polimi.myshelfie.view.graphical.controllers;

import it.polimi.myshelfie.model.chatting.Message;
import it.polimi.myshelfie.model.chatting.Messages;
import it.polimi.myshelfie.model.modelviews.LobbyModelView;
import it.polimi.myshelfie.view.ViewBuffer;
import it.polimi.myshelfie.view.graphical.GraphicalUI;
import it.polimi.myshelfie.view.graphical.Images;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.net.URL;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class LobbyController extends ViewController implements Initializable {
    @FXML
    protected Label notificationDot;
    @FXML
    protected TextArea messageText;
    @FXML
    protected ChoiceBox receiverChoice;
    @FXML
    protected AnchorPane chatPane;
    @FXML
    protected ScrollPane scrollPane;
    @FXML
    protected VBox messagesVBox;
    @FXML
    protected Label lobbyNameLabel;
    @FXML
    protected VBox playersVBox;
    @FXML
    protected Label playersInLabel;
    @FXML
    protected Button minusButton;
    @FXML
    protected Button plusButton;
    @FXML
    protected Button startButton;

    @FXML
    protected ImageView chatIcon;

    ViewBuffer viewBuffer = ViewBuffer.getInstance();
    boolean chatIsOpen = false;
    int notificationCont = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lobbyNameLabel.setBackground(Background.fill(Color.rgb(229, 146, 93)));
        lobbyNameLabel.setText(viewBuffer.getLobbyModelView().getName());

        updateChoiceBox();
        createListPlayers();
    }
    @FXML
    protected void onStartMouseClicked(MouseEvent mouseEvent) {
        viewBuffer.startRequest();
    }

    @FXML
    protected void onMouseMinusClicked(MouseEvent mouseEvent) {
        if(!viewBuffer.removePlayer())
            displayError("cannot remove player!");
    }

    @FXML
    protected void onMousePlusClicked(MouseEvent mouseEvent) {
        if(!viewBuffer.addPlayer())
            displayError("cannot add player!");
    }

    public void refreshListPlayers(){
        Platform.runLater(() -> playersVBox.getChildren().clear());
        updateChoiceBox();
        createListPlayers();
    }

    private void createListPlayers(){
        LobbyModelView lobbyModelView = viewBuffer.getLobbyModelView();
        List<String> playersIn = lobbyModelView.getPlayers();

        for(String player : playersIn){
            Label label = new Label(player);
            label.setMaxWidth(837);
            label.setTextFill(Color.WHITE);
            label.setPadding(new Insets(0, 0, 0, 80));

            if(lobbyModelView.isRoomOwner(player)) {
                ImageView image = new ImageView(Images.getImage("crown.png"));
                image.setPreserveRatio(true);
                image.setFitWidth(0);
                image.setFitHeight(60);
                label.setPadding(new Insets(0));
                HBox hBox = new HBox(image, label);
                hBox.setSpacing(20);
                Platform.runLater(() -> playersVBox.getChildren().add(hBox));
            }
            else if(player.equals(GraphicalUI.getMyName())) {
                label.setTextFill(Color.rgb(218, 106, 45));
            }

            if(!lobbyModelView.isRoomOwner(player))
                Platform.runLater(() -> playersVBox.getChildren().add(label));
        }

        int currentPlayersIn = lobbyModelView.getPlayers().size();
        int maxNumPlayers = lobbyModelView.getGameConfig().getNumPlayers();
        if(lobbyModelView.isRoomOwner(GraphicalUI.getMyName())){
            Platform.runLater(() -> {
                minusButton.setDisable(false);
                plusButton.setDisable(false);
                startButton.setDisable(currentPlayersIn != maxNumPlayers);
            });
        }else{
            Platform.runLater(() -> {
                minusButton.setDisable(true);
                plusButton.setDisable(true);
                startButton.setDisable(true);
            });
        }
        Platform.runLater(() -> playersInLabel.setText(currentPlayersIn + "/" + maxNumPlayers));
    }

    public void addChatMessage() {
        if(!chatIsOpen){
            if(notificationCont == 0) notificationDot.setVisible(true);
            notificationCont++;
            Platform.runLater(() -> notificationDot.setText(String.valueOf(notificationCont)));
        }
        Message message = viewBuffer.getPlayerChatModelView().getLastMessage();

        //create default messagePane
        AnchorPane messagePane = new AnchorPane();
        messagePane.setMaxWidth(415);
        messagePane.setMinWidth(415);
        messagePane.setMinHeight(72);

        //create default textLabel
        Label userLabel = new Label(message.getSender());
        userLabel.setStyle("-fx-font-size: 13;");
        Label textLabel = new Label(message.getMessage());
        textLabel.setStyle("-fx-font-size: 20;");
        Label timeLabel = new Label("è ora");
        timeLabel.setStyle("-fx-font-size: 10");

        VBox singleMessageVBox = new VBox();
        singleMessageVBox.setMaxWidth(415*0.8);
        singleMessageVBox.setMinWidth(415*0.3);
        singleMessageVBox.setStyle("-fx-background-color: red");
        textLabel.setPadding(new Insets(10));
        textLabel.setWrapText(true);

        if(message.getSender().equals(GraphicalUI.getMyName())){
            AnchorPane.setRightAnchor(singleMessageVBox,0D);                 //set messagePane to the right
            userLabel.setAlignment(Pos.TOP_LEFT);
            textLabel.setAlignment(Pos.TOP_LEFT);
            timeLabel.setAlignment(Pos.TOP_LEFT);
        } else {
            AnchorPane.setLeftAnchor(singleMessageVBox,0D);                //set messagePane to the left
        }

        if(!message.getReceiver().equals(Messages.toEveryOne)) {
            if(message.getSender().equals(GraphicalUI.getMyName())){
                userLabel.setText("Private to : "+message.getReceiver());
            } else {
                userLabel.setText("Private from : "+message.getSender());
            }
        }

        Platform.runLater(()->{
            singleMessageVBox.getChildren().addAll(userLabel,textLabel,timeLabel);
            messagePane.getChildren().add(singleMessageVBox);
            messagesVBox.getChildren().add(messagePane);
        });
    }

    @Override
    public void onBackArrowClick() {
        viewBuffer.exitRequest();
        super.onBackArrowClick();
    }

    @FXML
    protected void setChatVisible(){
        chatIsOpen = true;
        notificationDot.setVisible(false);
        notificationCont = 0;

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), chatPane);
        translateTransition.setByX(+415);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), chatPane);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);

        fadeTransition.play();
        translateTransition.play();
    }
    @FXML
    protected void SetChatInvisible() {
        chatIsOpen = false;
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), chatPane);
        translateTransition.setByX(-415);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), chatPane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);

        fadeTransition.play();
        translateTransition.play();
    }
    @FXML
    protected void sendMessage() {
        String receiver;
        if(receiverChoice.getValue().toString().equals("Everyone")) receiver = Messages.toEveryOne;
        else receiver = receiverChoice.getValue().toString();

        Message message = new Message(GraphicalUI.getMyName(), receiver, messageText.getText());
        Platform.runLater(() -> messageText.clear());
        viewBuffer.sendChatMessage(message);
    }

    private void updateChoiceBox(){
        List<String> players = new ArrayList<>(viewBuffer.getLobbyModelView().getPlayers());
        players.remove(GraphicalUI.getMyName());
        players.add("Everyone");

        ObservableList obList = FXCollections.observableList(players);
        Platform.runLater(() -> {
            receiverChoice.setItems(obList);
            receiverChoice.setValue("Everyone");
        });
    }
}
