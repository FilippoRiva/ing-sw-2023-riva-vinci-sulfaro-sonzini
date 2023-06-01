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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class LobbyController extends ViewController implements Initializable {
    @FXML
    protected Label notificationDot;
    @FXML
    protected TextArea messageText;
    @FXML
    protected ChoiceBox<String> receiverChoice;
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

    ViewBuffer viewBuffer = ViewBuffer.getInstance();
    boolean chatIsOpen = false;
    int notificationCont = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lobbyNameLabel.setBackground(Background.fill(Color.rgb(229, 146, 93)));
        lobbyNameLabel.setText(viewBuffer.getLobbyModelView().getName());

        messagesVBox.heightProperty().addListener(observable -> scrollPane.setVvalue(1D));
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

        //create default textLabel
        Label userLabel = new Label(message.getSender());
        userLabel.setStyle("-fx-font-size: 13; -fx-text-fill: white; -fx-label-padding: 0 0 0 10");
        Label textLabel = new Label(message.getMessage());
        textLabel.setStyle("-fx-font-size: 20; -fx-label-padding: 0 0 0 10; -fx-wrap-text: true");
        //textLabel.setWrapText(true);
        Label timeLabel = new Label(message.getTimeHHmm());
        timeLabel.setStyle("-fx-font-size: 10; -fx-label-padding: 0 0 0 10");

        VBox singleMessageVBox = new VBox();
        singleMessageVBox.setMaxWidth(415*0.8);
        singleMessageVBox.setMinWidth(415*0.3);

        if(message.getSender().equals(GraphicalUI.getMyName())){
            singleMessageVBox.setStyle("-fx-background-color: red; -fx-background-radius: 20 0 20 20");
            AnchorPane.setRightAnchor(singleMessageVBox,5D);                 //set messagePane to the right
            userLabel.setAlignment(Pos.TOP_LEFT);
            textLabel.setAlignment(Pos.TOP_LEFT);
            timeLabel.setAlignment(Pos.TOP_LEFT);
        } else {
            singleMessageVBox.setStyle("-fx-background-color: red; -fx-background-radius: 0 20 20 20");
            AnchorPane.setLeftAnchor(singleMessageVBox,5D);                //set messagePane to the left
        }

        if(!message.getReceiver().equals(Messages.toEveryOne)) {
            if(message.getSender().equals(GraphicalUI.getMyName())){
                userLabel.setText("Private to : "+message.getReceiver());
            } else {
                userLabel.setText("Private from : "+message.getSender());
            }
        }

        singleMessageVBox.getChildren().addAll(userLabel,textLabel,timeLabel);
        messagePane.getChildren().add(singleMessageVBox);
        Platform.runLater(()-> {
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

        Platform.runLater(() -> scrollPane.setVvalue(1.0));
    }
    @FXML
    protected void setChatInvisible() {
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
        if(receiverChoice.getValue().equals("Everyone")) receiver = Messages.toEveryOne;
        else receiver = receiverChoice.getValue();

        Message message = new Message(GraphicalUI.getMyName(), receiver, messageText.getText());
        Platform.runLater(() -> messageText.clear());
        viewBuffer.sendChatMessage(message);
    }

    private void updateChoiceBox(){
        List<String> players = new ArrayList<>(viewBuffer.getLobbyModelView().getPlayers());
        players.remove(GraphicalUI.getMyName());
        players.add("Everyone");

        ObservableList<String> obList = FXCollections.observableList(players);
        Platform.runLater(() -> {
            receiverChoice.setItems(obList);
            receiverChoice.setValue("Everyone");
        });
    }
}
