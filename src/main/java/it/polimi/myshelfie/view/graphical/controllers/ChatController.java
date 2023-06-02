package it.polimi.myshelfie.view.graphical.controllers;

import it.polimi.myshelfie.model.chatting.Message;
import it.polimi.myshelfie.model.chatting.Messages;
import it.polimi.myshelfie.view.ViewBuffer;
import it.polimi.myshelfie.view.graphical.GraphicalUI;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public abstract class ChatController extends ViewController implements Initializable {
    @FXML
    protected Label notificationDot;
    @FXML
    protected TextField messageText;
    @FXML
    protected ChoiceBox<String> receiverChoice;
    @FXML
    protected AnchorPane chatPane;
    @FXML
    protected ScrollPane chatScroll;
    @FXML
    protected VBox messagesVBox;

    ViewBuffer viewBuffer = ViewBuffer.getInstance();
    boolean chatIsOpen = false;
    int notificationCont = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        messagesVBox.heightProperty().addListener(observable -> chatScroll.setVvalue(1D));
        updateChoiceBox();
    }

    public abstract void setKeyListeners(Scene scene);

    @FXML
    protected void setChatVisible() {
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

        messageText.requestFocus();

        Platform.runLater(() -> {
            messageText.clear();
            chatScroll.setVvalue(1.0);
        });
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

    protected void updateChoiceBox() {
        List<String> players = new ArrayList<>(viewBuffer.getLobbyModelView().getPlayers());
        players.remove(GraphicalUI.getMyName());
        players.add("Everyone");

        ObservableList<String> obList = FXCollections.observableList(players);
        Platform.runLater(() -> {
            receiverChoice.setItems(obList);
            receiverChoice.setValue("Everyone");
        });
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
}
