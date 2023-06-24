package it.polimi.myshelfie.view.graphical.controllers;

import it.polimi.myshelfie.model.chatting.Message;
import it.polimi.myshelfie.model.chatting.Messages;
import it.polimi.myshelfie.view.ViewManager;
import it.polimi.myshelfie.view.graphical.CustomBindings;
import it.polimi.myshelfie.view.graphical.GraphicalUI;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ChatController implements Initializable {
    @FXML
    protected TextField messageText;
    @FXML
    protected ChoiceBox<String> receiverChoice;
    @FXML
    protected ScrollPane chatScroll;
    @FXML
    protected VBox messagesVBox;
    @FXML
    protected ImageView backgroundImage;
    @FXML
    protected ImageView messagesBackgroundImage;
    @FXML
    protected StackPane chat;
    ViewManager viewBuffer = ViewManager.getInstance();
    boolean chatIsOpen = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CustomBindings.bindSize(chatScroll, GraphicalUI.stage.widthProperty().divide(3).subtract(20),null);
        CustomBindings.bindSize(messagesVBox,chatScroll.minWidthProperty().subtract(20),null);
        CustomBindings.bindSize(backgroundImage,chat.minWidthProperty(),chat.minHeightProperty());
        CustomBindings.bindSize(messagesBackgroundImage,chatScroll.minWidthProperty(),chat.minHeightProperty().subtract(180));
        messagesVBox.heightProperty().addListener(observable -> chatScroll.setVvalue(1D));
        receiverChoice.setValue("Everyone");
    }

    @FXML
    public void setChatVisible(boolean value) {
        if(value){
            chatIsOpen = true;
            messageText.requestFocus();
            Platform.runLater(() -> {
                messageText.clear();
                chatScroll.setVvalue(1.0);
            });
        } else {
            chatIsOpen = false;
        }
    }
    @FXML
    public void sendMessage() {
        String receiver;
        if(receiverChoice.getValue().equals("Everyone")) receiver = Messages.toEveryOne;
        else receiver = receiverChoice.getValue();

        Message message = new Message(ViewManager.getUsername(), receiver, messageText.getText());
        Platform.runLater(() -> messageText.clear());
        viewBuffer.sendChatMessage(message);
    }

    public void updateChoiceBox() {
        List<String> players = new ArrayList<>(viewBuffer.getLobbyModelView().getPlayers());
        players.remove(ViewManager.getUsername());
        players.add("Everyone");

        ObservableList<String> obList = FXCollections.observableList(players);
        Platform.runLater(() -> {
            receiverChoice.setItems(obList);
            receiverChoice.setValue("Everyone");
        });
    }

    public void addChatMessage() {
        Message message = viewBuffer.getPlayerChatModelView().getLastMessage();

        //create default messagePane
        HBox messageBox = new HBox();
        messageBox.setCache(true);
        messageBox.setCacheHint(CacheHint.SPEED);
        Region emptySpace = new Region();
        HBox.setHgrow(emptySpace, Priority.ALWAYS);
        VBox singleMessageVBox = new VBox();
        CustomBindings.bindSize(messageBox,chatScroll.minWidthProperty().subtract(20),null);

        if(message.getSender().equals(ViewManager.getUsername())){
            messageBox.getChildren().addAll(emptySpace,singleMessageVBox);
        } else messageBox.getChildren().addAll(singleMessageVBox,emptySpace);

        //create default textLabel
        Label userLabel = new Label(message.getSender());
        userLabel.setStyle("-fx-font-size: 13; -fx-text-fill: DarkGoldenRod; -fx-label-padding: 0 0 0 10");
        Label textLabel = new Label(message.getMessage());
        textLabel.setStyle("-fx-font-size: 20; -fx-label-padding: 0 0 0 10; -fx-wrap-text: true");
        Label timeLabel = new Label(message.getTimeHHmm());
        timeLabel.setStyle("-fx-font-size: 10; -fx-label-padding: 0 0 0 10");

        textLabel.maxWidthProperty().bind(chatScroll.maxWidthProperty().divide(3).multiply(2));
        textLabel.minWidthProperty().bind(chatScroll.maxWidthProperty().divide(3));

        CustomBindings.bindSize(singleMessageVBox,textLabel);

        if(message.getSender().equals(ViewManager.getUsername())){
            singleMessageVBox.setStyle("-fx-background-color: AntiqueWhite; -fx-background-radius: 20 0 20 20");
        } else {
            singleMessageVBox.setStyle("-fx-background-color: AntiqueWhite; -fx-background-radius: 0 20 20 20");
        }

        if(!message.getReceiver().equals(Messages.toEveryOne)) {
            if(message.getSender().equals(ViewManager.getUsername())){
                userLabel.setText("Private to : "+message.getReceiver());
            } else {
                userLabel.setText("Private from : "+message.getSender());
            }
        }

        singleMessageVBox.getChildren().addAll(userLabel,textLabel,timeLabel);
        Platform.runLater(()-> {
            ObservableList<Node> messages = messagesVBox.getChildren();
            if(messages.size() > 100) messages.remove(0);
            messages.add(messageBox);
        });
    }

    public boolean isChatVisible() {
        return chatIsOpen;
    }

    public void reset() {
        Platform.runLater(()->messagesVBox.getChildren().removeAll(messagesVBox.getChildren()));
    }
}
