package it.polimi.myshelfie.view.graphical.controllers;

import it.polimi.myshelfie.model.modelviews.ModelView;
import it.polimi.myshelfie.view.InternalViewState;
import it.polimi.myshelfie.view.ViewManager;
import it.polimi.myshelfie.view.graphical.Animations;
import it.polimi.myshelfie.view.graphical.CustomBindings;
import it.polimi.myshelfie.view.graphical.GraphicalUI;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class JoinController {
    @FXML
    protected TextField playerNameField;
    @FXML
    protected VBox lobbiesVBox;
    @FXML
    protected Button joinButton;
    @FXML
    protected ImageView listBackgroundImage;
    @FXML
    protected ImageView clearBackground;
    @FXML
    protected ScrollPane lobbiesScrollPane;
    @FXML
    protected Label joinErrorLabel;
    private PreGameController preGameController;
    ViewManager viewManager = ViewManager.getInstance();
    String selected = null;

    public void initialize(ImageView banner,PreGameController preGameController){
        CustomBindings.bindSize(listBackgroundImage,GraphicalUI.stage.widthProperty().divide(1.5),
                GraphicalUI.stage.heightProperty().divide(6).multiply(2.5));
        CustomBindings.bindSize(lobbiesVBox,listBackgroundImage.fitWidthProperty(),listBackgroundImage.fitHeightProperty());
        CustomBindings.bindSize(lobbiesScrollPane,listBackgroundImage.fitWidthProperty(),listBackgroundImage.fitHeightProperty());

        Animations.applyButtonAnimation(joinButton);
        CustomBindings.bindLowerPreGamePane(clearBackground,banner);
        this.preGameController = preGameController;
    }

    public void setKeyListeners() {
        Scene scene = GraphicalUI.stage.getScene();
        scene.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()){
                case ESCAPE -> GraphicalUI.mainViewController.changeView(InternalViewState.INIT);
                case ENTER -> onJoinMouseClicked();
            }
        });
    }

    @FXML
    protected void onJoinMouseClicked() {
        String playerName = playerNameField.getText();
        if (playerName.isEmpty()) {
            Animations.emptyFieldError(playerNameField).play();
        }else {
            viewManager.joinRequest(selected, playerName);
            ViewManager.setUsername(playerName);
            preGameController.startLoading();
        }
    }

    public void updateListLobbies(){
        //generating nodes
        Task<List<Node>> task = new Task<>() {
            @Override
            protected List<Node> call() {
                List<Node> result = new ArrayList<>();
                ModelView modelView = viewManager.getModelView();
                List<String> lobbies = modelView.getLobbies();
                for(String lobby : lobbies){
                    AnchorPane anchorPane = new AnchorPane();
                    if(lobby.equals(selected)) anchorPane.setBackground(Background.fill(Color.rgb(218, 106, 45)));

                    anchorPane.setOnMouseClicked(event -> {
                        selected = lobby;
                        Platform.runLater(() -> joinButton.setDisable(false));
                        updateListLobbies();
                    });

                    Label label = generateStandardLabel(lobby);
                    label.setAlignment(Pos.TOP_LEFT);
                    anchorPane.getChildren().add(label);
                    AnchorPane.setLeftAnchor(label, 20D);

                    label = generateStandardLabel(modelView.getCurrentSize(lobby) + "/" + modelView.getMaxSize(lobby));
                    label.setAlignment(Pos.TOP_RIGHT);
                    anchorPane.getChildren().add(label);
                    AnchorPane.setRightAnchor(label, 20D);
                    result.add(anchorPane);
                }
                return result;
            }
        };

        //updating view
        task.setOnSucceeded((event)->{
            List<Node> anchorPanes;
            try {
                anchorPanes = task.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
            Platform.runLater(()->{
                lobbiesVBox.getChildren().clear();
                lobbiesVBox.getChildren().addAll(anchorPanes);
            });
        });

        new Thread(task).start();
    }
    private Label generateStandardLabel(String text){
        Label label = new Label(text);
        label.setMaxWidth(837);
        label.setTextFill(Color.WHITE);
        return label;
    }

    public void reset() {
        this.selected = null;
        Platform.runLater(()->playerNameField.clear());
    }
}
