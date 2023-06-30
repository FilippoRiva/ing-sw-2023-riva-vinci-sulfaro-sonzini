package it.polimi.myshelfie.view.graphical.controllers.game;

import it.polimi.myshelfie.model.GameState;
import it.polimi.myshelfie.view.ViewManager;
import it.polimi.myshelfie.view.graphical.Animations;
import it.polimi.myshelfie.view.graphical.CustomBindings;
import it.polimi.myshelfie.view.graphical.GraphicalUI;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * The game controller contains the panel, overlay and end game controllers.
 * It is used to propagate updates to the controllers.
 */
public class GameController {

    private final ViewManager viewManager = ViewManager.getInstance();
    @FXML
    public GameOverlayController gameOverlayController;
    @FXML
    public GamePanelController gamePanelController;
    @FXML
    public EndGameController endGameController;
    @FXML
    protected ProgressIndicator progress;
    @FXML
    protected AnchorPane endGame;
    @FXML
    protected StackPane gameOverlay;
    @FXML
    protected AnchorPane gamePanel;
    @FXML
    protected ImageView backgroundImage;
    @FXML
    protected AnchorPane mainAnchorPane;


    /**
     * Initializes the controller's component's resize properties and animations.
     * It sets the state variables to their default values.
     */
    public void initialize() {
        Stage stage = GraphicalUI.stage;
        endGame.setVisible(false);
        endGame.setMouseTransparent(true);
        gameOverlay.setVisible(true);
        //gameOverlay.setMouseTransparent(true);
        gameOverlayController.customInitialize();
        gamePanelController.customInitialize();
        endGameController.customInitialize();
        CustomBindings.bindSize(backgroundImage, stage.widthProperty(), stage.heightProperty());
        CustomBindings.bindSize(mainAnchorPane, stage.widthProperty(), stage.heightProperty());
    }

    /**
     * Sets to visible the components that needs to be visible at the beginning of the game.
     */
    public void gameStart() {
        //default visible panels
        endGame.setVisible(false);
        endGame.setMouseTransparent(true);
        gamePanel.setVisible(true);
        gamePanel.setMouseTransparent(false);
        gameOverlay.setMouseTransparent(false);
        gameOverlay.setVisible(true);
        //AutoPlayer Activation
        if (ViewManager.getInstance().getLobbyModelView().getName().equals("test") &&
                ViewManager.getUsername().startsWith("bot")) ViewManager.getInstance().startAutoPlayer();
        //completely loading everything
        gamePanelController.setState(viewManager.getGameModelView().getCurrentState());
        gamePanelController.leftBookshelfController.loadLeft();
        gamePanelController.rightBookshelfController.loadRight();
        gamePanelController.livingRoomController.loadGrid();
        gameOverlayController.gameStart();
    }

    /**
     * Used to trigger a game update over this controller and its sub controllers.
     */
    public void update() {
        GameState currentState = viewManager.getGameModelView().getCurrentState();

        gamePanelController.setState(currentState); //this reloads game panel elements
        if (currentState == GameState.DRAW || currentState == GameState.LAST_DRAW)
            gameOverlayController.startTurn();
        if (viewManager.getCurrentStateDiff() == GameState.WIN) {
            gameOverlayController.startTurn();
            gamePanelController.livingRoomController.checkEndToken();
            Animations.timer(() -> {
                gameOverlay.setMouseTransparent(true);
                gameOverlay.setVisible(false);
                gamePanel.setMouseTransparent(true);
                gamePanel.setVisible(false);
                endGameController.update();
                endGame.setVisible(true);
                endGame.setMouseTransparent(false);
            }, 2000);
        }
    }


    /**
     * Updates the chat by adding a chat message.
     */
    public void updateChat() {
        gameOverlayController.chatController.addChatMessage();
    }


    /**
     * Displays an error.
     * @param message the error message to display.
     */
    public void displayError(String message) {
    }


    /**
     * Resets the controller to its original state.
     */
    public void reset() {
        endGame.setVisible(false);
        endGame.setMouseTransparent(true);
        gamePanelController.reset();
        gameOverlayController.reset();
        endGameController.reset();
    }


    /**
     * Processes how a certain KeyEvent should be interpreted while this Controller is active.
     * @param keyEvent the keyEvent to process.
     */
    public void processKeyEvent(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case ESCAPE -> {
                if (gameOverlayController.chatController.isChatVisible()) gameOverlayController.toggleChatVisible();
            }
            case ENTER -> {
                if (gameOverlayController.chatController.isChatVisible())
                    gameOverlayController.chatController.sendMessage();
                else if (viewManager.getCurrentStateDiff() == GameState.DRAW) gamePanelController.draw();
                else if (viewManager.getCurrentStateDiff() == GameState.ORGANIZE) gamePanelController.organize();
            }
            case C -> {
                if (!gameOverlayController.chatController.isChatVisible()) gameOverlayController.toggleChatVisible();
            }
        }
    }
}
