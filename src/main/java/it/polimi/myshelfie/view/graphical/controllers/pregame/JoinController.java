package it.polimi.myshelfie.view.graphical.controllers.pregame;

import it.polimi.myshelfie.model.modelviews.ModelView;
import it.polimi.myshelfie.view.InternalViewState;
import it.polimi.myshelfie.view.ViewManager;
import it.polimi.myshelfie.view.graphical.Animations;
import it.polimi.myshelfie.view.graphical.CustomBindings;
import it.polimi.myshelfie.view.graphical.Effects;
import it.polimi.myshelfie.view.graphical.GraphicalUI;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * The controller of the Join view.
 */
public class JoinController {
    final ViewManager viewManager = ViewManager.getInstance();
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
    private String selected = null;
    private PreGameController preGameController;

    /**
     * Initializes the controller's component's resize properties and animations.
     * It sets the state variables to their default values.
     * @param banner the top banner of the pregame view.
     * @param preGameController the preGameController.
     */
    public void initialize(ImageView banner, PreGameController preGameController) {
        CustomBindings.bindSize(listBackgroundImage, GraphicalUI.stage.widthProperty().divide(1.5),
                GraphicalUI.stage.heightProperty().divide(6).multiply(2.5));
        CustomBindings.bindSize(lobbiesVBox, listBackgroundImage.fitWidthProperty(), listBackgroundImage.fitHeightProperty());
        CustomBindings.bindSize(lobbiesScrollPane, listBackgroundImage.fitWidthProperty(), listBackgroundImage.fitHeightProperty());

        Animations.applyHoverScaling(joinButton);
        Effects.applyShadow(joinButton);

        CustomBindings.bindLowerPreGamePane(clearBackground, banner);
        this.preGameController = preGameController;
    }

    /**
     * Makes a join request if the player has selected a lobby and has inserted a username.
     */
    @FXML
    protected void onJoinMouseClicked() {
        String playerName = playerNameField.getText();
        if (selected == null) return;
        if (playerName.isEmpty()) {
            Animations.emptyFieldError(playerNameField).play();
        } else {
            viewManager.joinRequest(selected, playerName);
            ViewManager.setUsername(playerName);
            preGameController.startLoading();
        }
    }

    /**
     * Updates the list of lobbies in the lobbiesVBox.
     */
    public void updateListLobbies() {
        //generating nodes
        Task<List<Node>> task = new Task<>() {
            @Override
            protected List<Node> call() {
                List<Node> result = new ArrayList<>();
                ModelView modelView = viewManager.getModelView();
                List<String> lobbies = modelView.getLobbies();
                for (String lobby : lobbies) {
                    AnchorPane anchorPane = new AnchorPane();
                    if (lobby.equals(selected)) anchorPane.setBackground(Background.fill(Color.rgb(218, 106, 45)));

                    anchorPane.setOnMousePressed(event -> {
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
        task.setOnSucceeded((event) -> {
            List<Node> anchorPanes;
            try {
                anchorPanes = task.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
            Platform.runLater(() -> {
                lobbiesVBox.getChildren().clear();
                lobbiesVBox.getChildren().addAll(anchorPanes);
            });
        });

        new Thread(task).start();
    }

    private Label generateStandardLabel(String text) {
        Label label = new Label(text);
        label.setMaxWidth(837);
        label.setTextFill(Color.WHITE);
        return label;
    }

    /**
     * Resets the controller to its original state.
     */
    public void reset() {
        this.selected = null;
        Platform.runLater(() -> {
            joinButton.setDisable(true);
            playerNameField.clear();
        });
    }

    /**
     * Processes how a certain KeyEvent should be interpreted while this Controller is active
     * @param keyEvent the keyEvent to process.
     */
    public void processKeyEvent(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case ESCAPE -> GraphicalUI.mainViewController.changeView(InternalViewState.INIT);
            case ENTER -> preGameController.joinController.onJoinMouseClicked();
        }
    }
}
