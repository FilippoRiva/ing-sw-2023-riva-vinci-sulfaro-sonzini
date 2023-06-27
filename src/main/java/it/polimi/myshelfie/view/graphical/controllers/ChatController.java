package it.polimi.myshelfie.view.graphical.controllers;

import it.polimi.myshelfie.model.chatting.Message;
import it.polimi.myshelfie.model.chatting.Messages;
import it.polimi.myshelfie.view.ViewManager;
import it.polimi.myshelfie.view.graphical.Animations;
import it.polimi.myshelfie.view.graphical.CustomBindings;
import it.polimi.myshelfie.view.graphical.controllers.game.GameOverlayController;
import javafx.animation.Animation;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
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
    @FXML
    protected Label notificationDot;
    ViewManager viewManager = ViewManager.getInstance();
    boolean chatIsOpen = false;
    private GameOverlayController gameOverlayController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CustomBindings.bindSize(chatScroll, backgroundImage.fitWidthProperty().subtract(20),null);
        CustomBindings.bindSize(messagesVBox,chatScroll.minWidthProperty().subtract(20),null);
        CustomBindings.bindSize(backgroundImage,chat.minWidthProperty(),chat.minHeightProperty());
        CustomBindings.bindSize(messagesBackgroundImage,chatScroll.minWidthProperty(),chat.minHeightProperty().subtract(180));
        messagesVBox.heightProperty().addListener(observable -> chatScroll.setVvalue(1D));
        receiverChoice.setValue("Everyone");
        notificationDot.setVisible(true);
        notificationDot.setOpacity(0);
    }

    @FXML
    public void setChatVisible(boolean visible) {
        if(visible){
            resetNotificationDot();
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
        viewManager.sendChatMessage(message);
    }

    public void updateChoiceBox() {
        List<String> players = new ArrayList<>(viewManager.getLobbyModelView().getPlayers());
        players.remove(ViewManager.getUsername());
        players.add("Everyone");

        ObservableList<String> obList = FXCollections.observableList(players);
        Platform.runLater(() -> {
            receiverChoice.setItems(obList);
            receiverChoice.setValue("Everyone");
        });
    }

    public void addChatMessage() {
        Message message = viewManager.getPlayerChatModelView().getLastMessage();

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
        if(message.getReceiver().equals(Messages.toEveryOne))userLabel.setStyle("-fx-font-size: 13; -fx-label-padding: 0 0 0 10");
        else userLabel.setStyle("-fx-font-size: 13; -fx-text-fill:MidnightBlue; -fx-label-padding: 0 0 0 10");
        Label textLabel = new Label(message.getMessage());
        textLabel.setStyle("-fx-font-size: 20; -fx-label-padding: 0 0 0 10; -fx-wrap-text: true");
        Label timeLabel = new Label(message.getTimeHHmm());
        timeLabel.setStyle("-fx-font-size: 10; -fx-label-padding: 0 0 0 10");

        textLabel.maxWidthProperty().bind(chatScroll.maxWidthProperty().divide(3).multiply(2));
        textLabel.minWidthProperty().bind(chatScroll.maxWidthProperty().divide(3));

        CustomBindings.bindSize(singleMessageVBox,textLabel);

        if(message.getSender().equals(ViewManager.getUsername())){
            if(message.getReceiver().equals(Messages.toEveryOne)) singleMessageVBox.setStyle("-fx-background-color: NavajoWhite ; -fx-background-radius: 20 0 20 20");
            else singleMessageVBox.setStyle("-fx-background-color: NavajoWhite  ; -fx-background-radius: 20 0 20 20");
        } else {
            if(message.getReceiver().equals(Messages.toEveryOne)) singleMessageVBox.setStyle("-fx-background-color: AntiqueWhite; -fx-background-radius: 20 0 20 20");
            else singleMessageVBox.setStyle("-fx-background-color: AntiqueWhite; -fx-background-radius: 0 20 20 20");
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

        updateNotificationDot();
    }

    private void updateNotificationDot(){
        if(!isChatVisible()) {
            int prev = Integer.parseInt(notificationDot.getText());
            if(prev==0) {
                Animations.popFadeIn(notificationDot).play();
            }
            int newValue = prev+1;
            Platform.runLater(()->notificationDot.setText(String.valueOf(newValue)));
        }
    }

    private void resetNotificationDot(){
        if(notificationDot.getText().equals("0")) return;
        Animation a = Animations.reverse(Animations.popFadeIn(notificationDot));
        a.setOnFinished((evt)-> Platform.runLater(()->notificationDot.setText("0")));
        a.play();
    }

    public boolean isChatVisible() {
        return chatIsOpen;
    }

    public void reset() {
        Platform.runLater(()->messagesVBox.getChildren().removeAll(messagesVBox.getChildren()));
    }


    public void toggleChat() {
        if(gameOverlayController != null) gameOverlayController.toggleChatVisible();
    }

    public void initGameOverlay(GameOverlayController gameOverlayController) {
        this.gameOverlayController = gameOverlayController;
        updateChoiceBox();
    }
}
