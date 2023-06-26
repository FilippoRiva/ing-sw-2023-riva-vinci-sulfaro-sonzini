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
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class GameController{

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
    @FXML
    public GameOverlayController gameOverlayController;
    @FXML
    public  GamePanelController gamePanelController;
    @FXML
    public EndGameController endGameController;

    private final ViewManager viewManager = ViewManager.getInstance();

    public void initialize() {
        Stage stage = GraphicalUI.stage;
        endGame.setVisible(false);
        endGame.setMouseTransparent(true);
        gameOverlay.setVisible(true);
        //gameOverlay.setMouseTransparent(true);
        gameOverlayController.customInitialize();
        gamePanelController.customInitialize();
        endGameController.customInitialize();
        CustomBindings.bindSize(backgroundImage,stage.widthProperty(),stage.heightProperty());
        CustomBindings.bindSize(mainAnchorPane,stage.widthProperty(),stage.heightProperty());
    }
    
    public void gameStart(){
        //default visible panels
        endGame.setVisible(false);
        endGame.setMouseTransparent(true);
        gamePanel.setVisible(true);
        gamePanel.setMouseTransparent(false);
        //AutoPlayer Activation
        if(ViewManager.getInstance().getLobbyModelView().getName().equals("test") &&
                ViewManager.getUsername().startsWith("bot")) ViewManager.getInstance().startAutoPlayer();
        //completely loading everything
        gamePanelController.setState(viewManager.getGameModelView().getCurrentState());
        gamePanelController.leftBookshelfController.loadLeft();
        gamePanelController.rightBookshelfController.loadRight();
        gamePanelController.livingRoomController.loadGrid();
        gameOverlayController.gameStart();
    }

    public void update() {
        GameState currentState = viewManager.getCurrentStateDiff();

        gamePanelController.setState(currentState); //this reloads game panel elements
        if(currentState == GameState.DRAW || currentState == GameState.LAST_DRAW)
            gameOverlayController.startTurn();
        if(viewManager.getCurrentStateDiff()== GameState.WIN){
            Animations.timer(()->{
                gameOverlay.setMouseTransparent(true);
                gamePanel.setMouseTransparent(true);
                gameOverlay.setVisible(false);
                gamePanel.setVisible(false);
                endGameController.update();
                endGame.setVisible(true);
                endGame.setMouseTransparent(false);
            },2000);
        }
    }

    public void updateChat() {
        gameOverlayController.chatController.addChatMessage();
    }

    public void displayError(String message) {
    }

    public void reset() {
        endGame.setVisible(false);
        endGame.setMouseTransparent(true);
        gamePanelController.reset();
        gameOverlayController.reset();
        endGameController.reset();
    }

    public void processKeyPress(KeyEvent keyEvent) {
        switch (keyEvent.getCode()){
            case ESCAPE -> {
                if(gameOverlayController.chatController.isChatVisible()) gameOverlayController.toggleChatVisible();
            }
            case ENTER -> {
                if(gameOverlayController.chatController.isChatVisible()) gameOverlayController.chatController.sendMessage();
                else if(viewManager.getCurrentStateDiff()==GameState.DRAW) gamePanelController.draw();
                else if(viewManager.getCurrentStateDiff()==GameState.ORGANIZE) gamePanelController.organize();
            }
            case C -> {
                if(!gameOverlayController.chatController.isChatVisible()) gameOverlayController.toggleChatVisible();
            }
        }
    }
}
