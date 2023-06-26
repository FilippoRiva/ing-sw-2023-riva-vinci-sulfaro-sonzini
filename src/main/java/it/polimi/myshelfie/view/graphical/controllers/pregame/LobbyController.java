package it.polimi.myshelfie.view.graphical.controllers.pregame;

import it.polimi.myshelfie.model.modelviews.LobbyModelView;
import it.polimi.myshelfie.view.InternalViewState;
import it.polimi.myshelfie.view.ViewManager;
import it.polimi.myshelfie.view.graphical.*;
import it.polimi.myshelfie.view.graphical.controllers.ChatController;
import javafx.application.Platform;
import javafx.beans.binding.DoubleBinding;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class LobbyController {
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
    protected ImageView clearBackground;
    @FXML
    protected ImageView scrollImage;
    @FXML
    protected StackPane chat;
    @FXML
    protected ChatController chatController;
    private final ViewManager viewManager = ViewManager.getInstance();
    private PreGameController preGameController;

    public void initialize(ImageView homeBanner,PreGameController preGameController){
        Stage stage = GraphicalUI.stage;
        DoubleBinding maxHeight = stage.heightProperty().divide(3).multiply(2);

        CustomBindings.bindSize(scrollImage,stage.widthProperty().divide(3).multiply(2),maxHeight.divide(1.5));
        CustomBindings.bindSize(chat,stage.widthProperty().divide(3),maxHeight.divide(5).multiply(4));
        chat.setTranslateX(-60);

        Effects.applyShadow(startButton);

        CustomBindings.bindLowerPreGamePane(clearBackground,homeBanner);

        Animations.applyHoverScaling(startButton);

        this.preGameController = preGameController;
    }

    public void refresh(){
        Platform.runLater(()-> lobbyNameLabel.setText(viewManager.getLobbyModelView().getName()));
        updateListPlayers();
    }

    @FXML
    protected void onStartMouseClicked() {
        viewManager.startRequest();
    }

    @FXML
    protected void onMouseMinusClicked() {
        if(viewManager.removePlayer()) {
            Animations.nodeError(minusButton,6).play();
        }
    }

    @FXML
    protected void onMousePlusClicked() {
        if(viewManager.addPlayer()) {
            Animations.nodeError(plusButton,6).play();
        }
    }

    public void updateListPlayers(){
        LobbyModelView lobbyModelView = viewManager.getLobbyModelView();
        //generates the nodes to add;
        Task<List<Node>> task = new Task<>() {
            @Override
            protected List<Node> call() {
                List<Node> playersNodes = new ArrayList<>();
                List<String> playersIn = lobbyModelView.getPlayers();

                for(String player : playersIn){
                    Label label = new Label(player);
                    label.setTextFill(Color.WHITE);
                    label.setTextAlignment(TextAlignment.LEFT);

                    if(lobbyModelView.isRoomOwner(player)) {
                        ImageView image = new ImageView(Images.getImage("crown.png"));
                        image.setPreserveRatio(true);
                        image.setFitWidth(30);
                        image.setFitHeight(30);
                        HBox hBox = new HBox(image, label);
                        hBox.setSpacing(10);
                        hBox.setAlignment(Pos.CENTER);
                        HBox.setHgrow(label, Priority.ALWAYS);
                        playersNodes.add(hBox);
                    }
                    else if(player.equals(ViewManager.getUsername())) {
                        label.setTextFill(Color.rgb(218, 106, 45));
                    }

                    if(!lobbyModelView.isRoomOwner(player))
                        playersNodes.add(label);
                }
                return playersNodes;
            }
        };

        task.setOnSucceeded((event)->{
            int currentPlayersIn = lobbyModelView.getPlayers().size();
            int maxNumPlayers = lobbyModelView.getGameConfig().getNumPlayers();
            List<Node> playersNodes;
            try {
                playersNodes = task.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
            //clears the nodes and adds the new ones
            List<Node> finalPlayersNodes = playersNodes;
            Platform.runLater(() -> {
                playersVBox.getChildren().clear();
                playersVBox.getChildren().addAll(finalPlayersNodes);
                if(lobbyModelView.isRoomOwner(ViewManager.getUsername())){
                    minusButton.setDisable(false);
                    plusButton.setDisable(false);
                    startButton.setDisable(currentPlayersIn != maxNumPlayers);
                }else{
                    minusButton.setDisable(true);
                    plusButton.setDisable(true);
                    startButton.setDisable(true);
                }
                playersInLabel.setText(currentPlayersIn + "/" + maxNumPlayers);
            });
        });

        new Thread(task).start();
        chatController.updateChoiceBox();
    }

    public void reset() {
        Platform.runLater(()->{
            lobbyNameLabel.setText("lobby");
            playersVBox.getChildren().clear();
            startButton.setDisable(true);
        });
        chatController.reset();
    }

    public void processKeyEvent(KeyEvent keyEvent) {
        LobbyModelView lobbyModelView = viewManager.getLobbyModelView();
        switch (keyEvent.getCode()){
            case ENTER -> {
                if(chatController.isChatVisible()) chatController.sendMessage();
                else if (lobbyModelView.isRoomOwner(ViewManager.getUsername()) &&
                        lobbyModelView.getPlayers().size() == lobbyModelView.getGameConfig().getNumPlayers())
                    onStartMouseClicked();
            }
            case C -> {
                if(!chatController.isChatVisible()) preGameController.toggleChatVisible();
            }
            case ESCAPE -> {
                if(chatController.isChatVisible()) preGameController.toggleChatVisible();
                else {
                    ViewManager.getInstance().exitRequest();
                    GraphicalUI.mainViewController.changeView(InternalViewState.INIT);
                }
            }
            case PLUS -> {
                if(lobbyModelView.isRoomOwner(ViewManager.getUsername())) onMousePlusClicked();
            }
            case MINUS -> {
                if(lobbyModelView.isRoomOwner(ViewManager.getUsername())) onMouseMinusClicked();
            }
            case S -> {
                if(lobbyModelView.isRoomOwner(ViewManager.getUsername())) viewManager.startRequest();
            }
        }
    }
}
