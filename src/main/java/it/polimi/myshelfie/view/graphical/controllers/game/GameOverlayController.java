package it.polimi.myshelfie.view.graphical.controllers.game;


import it.polimi.myshelfie.model.Token;
import it.polimi.myshelfie.model.modelviews.CommonGoalModelView;
import it.polimi.myshelfie.model.modelviews.PlayerModelView;
import it.polimi.myshelfie.view.ViewManager;
import it.polimi.myshelfie.view.graphical.Animations;
import it.polimi.myshelfie.view.graphical.CustomBindings;
import it.polimi.myshelfie.view.graphical.GraphicalUI;
import it.polimi.myshelfie.view.graphical.components.TokenImageView;
import it.polimi.myshelfie.view.graphical.controllers.ChatController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

import static it.polimi.myshelfie.view.graphical.Images.getImage;

/**
 * The game overlay controller containing common goals, the personal goal, the chat and other info components.
 */
public class GameOverlayController {
    final ViewManager viewManager = ViewManager.getInstance();
    @FXML
    public ChatController chatController;
    @FXML
    protected Label messageLabel;
    @FXML
    protected Label playingLabel;
    @FXML
    protected VBox playersListVbox;
    @FXML
    protected StackPane fullStackPane;
    @FXML
    protected HBox commonGoalsHBox;
    @FXML
    protected StackPane chat;
    @FXML
    protected ImageView commonGoal1;
    @FXML
    protected ImageView commonGoal2;
    @FXML
    protected StackPane tokens1;
    @FXML
    protected StackPane tokens2;
    @FXML
    protected ImageView personalGoal;
    private Stage stage;

    /**
     * Initializes the controller's component's resize properties and animations.
     * It sets the state variables to their default values.
     */
    public void customInitialize() {
        stage = GraphicalUI.stage;

        CustomBindings.bindSize(fullStackPane, stage.widthProperty(), stage.heightProperty());
        fullStackPane.setPickOnBounds(false);

        messageLabel.setMaxWidth(stage.getWidth() / 3);
        CustomBindings.bindSize(playersListVbox, null, stage.heightProperty().divide(5));

        CustomBindings.bindSize(personalGoal, null, stage.heightProperty().divide(5).multiply(3));

        CustomBindings.bindSize(commonGoalsHBox, stage.widthProperty(), stage.heightProperty().divide(5));
        CustomBindings.bindSize(commonGoal1, null, stage.heightProperty().divide(5));
        CustomBindings.bindSize(commonGoal2, null, stage.heightProperty().divide(5));
        CustomBindings.bindSize(tokens1, null, stage.heightProperty().divide(11));
        CustomBindings.bindSize(tokens2, null, stage.heightProperty().divide(11));

        CustomBindings.bindSize(chat, stage.widthProperty().divide(21).multiply(6), stage.heightProperty().divide(15).multiply(8));
    }

    /**
     * Initializes the view when the game starts.
     */
    public void gameStart() {
        chatController.initGameOverlay(this);

        List<CommonGoalModelView> commonGoals = viewManager.getGameModelView().getCommonGoals();
        int idPersonalGoal = viewManager.getGameModelView().getPlayer(ViewManager.getUsername()).getPersonalGoals().get(0).getId();

        personalGoal.setImage(getImage("game/personalGoals/Personal_Goals" + idPersonalGoal + ".png"));
        commonGoal1.setImage(getImage("game/commonGoals/" + (commonGoals.get(0).getId() + 1) + ".jpg"));
        commonGoal2.setImage(getImage("game/commonGoals/" + (commonGoals.get(1).getId() + 1) + ".jpg"));
        Tooltip.install(commonGoal1, new Tooltip(commonGoals.get(0).getDescription()));
        Tooltip.install(commonGoal2, new Tooltip(commonGoals.get(1).getDescription()));

        tokens1.setTranslateY(-commonGoal1.getFitHeight() / 913 * 37);
        tokens1.setTranslateX(commonGoal1.getFitHeight() / 913 * 321);
        tokens2.setTranslateY(-commonGoal2.getFitHeight() / 913 * 37);
        tokens2.setTranslateX(commonGoal2.getFitHeight() / 913 * 321);
        Platform.runLater(() -> {
            playingLabel.setText(viewManager.getGameModelView().getCurrentPlayerName());
            int i = 1;
            for (PlayerModelView player : viewManager.getGameModelView().getPlayers()) {
                Label label = new Label(i + ") " + player.getName());
                playersListVbox.getChildren().add(label);
                i++;
            }

            for (Token token : commonGoals.get(0).getTokens()) {
                tokens1.getChildren().add(getTokenImageView(token));
            }
            for (Token token : commonGoals.get(1).getTokens()) {
                tokens2.getChildren().add(getTokenImageView(token));
            }
        });

        personalGoal.setTranslateY(stage.getHeight() / 5 * 3 - 70);

        chat.setTranslateX(-20);
        chat.setTranslateY(stage.getHeight() / 15 * 8 - 80);
    }

    private TokenImageView getTokenImageView(Token token) {
        TokenImageView tokenImageView = new TokenImageView(token);
        tokenImageView.setPreserveRatio(true);
        CustomBindings.bindSize(tokenImageView, null, commonGoal1.fitHeightProperty().divide(11).multiply(5));
        return tokenImageView;
    }

    /**
     * Shows the new infos at the beginning of a turn such as the new player who's playing and the new obtained tokens
     */
    public void startTurn() {
        List<Token> tokens = viewManager.getLastPlayerTokenDiff();
        if (!tokens.isEmpty()) {
            for (Token token : tokens) {
                Platform.runLater(() -> {
                    if (token.getRomanNumeral() == 0) {
                        messageLabel.setText("Last round");
                    } else if (token.getRomanNumeral() == 1) {
                        tokens1.getChildren().remove(tokens1.getChildren().size() - 1);
                        messageLabel.setText("Earned a token from the first common goal");
                    } else if (token.getRomanNumeral() == 2) {
                        tokens2.getChildren().remove(tokens2.getChildren().size() - 1);
                        messageLabel.setText("Earned a token from the second common goal");
                    }
                    Animations.fadeIn(messageLabel).play();
                    Animations.timer(() -> Animations.fadeOut(messageLabel).play(), 3000);
                });
            }
        }
        Platform.runLater(() -> playingLabel.setText(viewManager.getCurrentPlayerDiff()));
    }

    /**
     * Translates the personal goal to a fully visible position.
     */
    @FXML
    protected void showPersonalGoal() {
        personalGoal.setTranslateY(0);
    }

    /**
     * Translates the personal goal to a partially visible position.
     */
    @FXML
    protected void hidePersonalGoal() {
        personalGoal.setTranslateY(stage.getHeight() / 5 * 3 - 70);
    }

    /**
     * Toggles the chat visibility and translation.
     */
    public void toggleChatVisible() {
        if (chatController.isChatVisible()) {
            chat.setTranslateY(stage.getHeight() / 15 * 8 - 80);
            chatController.setChatVisible(false);
            return;
        }

        chat.setTranslateY(0);
        chatController.setChatVisible(true);
        //reset notificationCounter
    }

    /**
     * Completely resets this controller to its original state.
     */
    public void reset() {
        Platform.runLater(() -> {
            messageLabel.setText(null);
            tokens1.getChildren().clear();
            tokens2.getChildren().clear();
            playersListVbox.getChildren().clear();
        });
        chatController.reset();
    }
}
