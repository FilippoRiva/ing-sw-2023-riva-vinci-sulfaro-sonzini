package it.polimi.myshelfie.view.graphical.controllers;

import it.polimi.myshelfie.view.InternalViewState;
import it.polimi.myshelfie.view.ViewManager;
import it.polimi.myshelfie.view.graphical.Animations;
import it.polimi.myshelfie.view.graphical.CustomBindings;
import it.polimi.myshelfie.view.graphical.GraphicalUI;
import it.polimi.myshelfie.view.graphical.controllers.game.GameController;
import it.polimi.myshelfie.view.graphical.controllers.pregame.PreGameController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {
    @FXML
    protected Button errorButton;
    @FXML
    protected ImageView criticalErrorBackground;
    @FXML
    protected PreGameController preGameController;
    @FXML
    protected GameController gameController;
    @FXML
    private AnchorPane game;
    @FXML
    private StackPane preGame;
    @FXML
    private Pane criticalErrorPane;
    @FXML
    private Label errorLabel;
    private final ViewManager viewManager = ViewManager.getInstance();
    private InternalViewState currentState;

    /**
     * Initializes the view setting its sub controllers to visible/non-visible
     * @param url the URL
     * @param resourceBundle the ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        criticalErrorPane.setVisible(false);
        CustomBindings.bindSize(criticalErrorPane,GraphicalUI.stage.widthProperty(),GraphicalUI.stage.heightProperty());
        CustomBindings.bindSize(criticalErrorBackground,errorLabel.widthProperty().add(40),
                errorLabel.heightProperty().add(200));

        errorLabel.setBackground(Background.fill(Color.BLACK));
        currentState = InternalViewState.INIT;

        preGame.setVisible(true);
        preGame.setMouseTransparent(false);
        game.setVisible(false);
        game.setMouseTransparent(true);

        Animations.applyHoverScaling(errorButton);

        this.setKeyListener();
    }

    /**
     * Given an InternalViewState this function hides the current components and shows the view associated with that
     * internalViewState
     * @param internalViewState the internalViewState to be shown
     */
    public void changeView(InternalViewState internalViewState){
        switch (internalViewState){
            case INIT -> { //transitions from game to init
                preGame.setMouseTransparent(false);
                preGame.setVisible(true);
                game.setMouseTransparent(true);
                game.setVisible(false);
                if(this.currentState == InternalViewState.LOBBY){
                    preGameController.reset();
                } else if(this.currentState == InternalViewState.GAME || this.currentState == InternalViewState.WIN || this.currentState == InternalViewState.DISCONNECTION){
                    preGameController.reset();
                    gameController.reset();
                }
                preGameController.changePage(InternalViewState.INIT);
            }
            case CREATE -> preGameController.changePage(InternalViewState.CREATE);
            case JOIN -> preGameController.changePage(InternalViewState.JOIN);
            case LOBBY -> preGameController.changePage(InternalViewState.LOBBY);
            case GAME -> { //transitions from init to game
                gameController.gameStart();
                preGame.setMouseTransparent(true);
                preGame.setVisible(false);
                preGameController.changePage(InternalViewState.INIT); //presetting INIT PAGE
                game.setMouseTransparent(false);
                game.setVisible(true);
            }
            case DISCONNECTION -> showErrorPane(viewManager.getDisconnectedClientExceptionsNotifierView().getLastException().getMessage());
            case STOPPED -> showErrorPane("You have been disconnected. Try reconnecting later");
        }
        currentState = internalViewState;
    }

    /**
     * Shows an overlay message. Intended for critical error messages such as server disconnection or other major errors
     * @param message the message to be shown
     */
    private void showErrorPane(String message) {
        Platform.runLater(() -> {
            criticalErrorPane.setMouseTransparent(false);
            errorLabel.setText(message);
            criticalErrorPane.setVisible(true);
        });
    }

    /**
     * The action associated with the error overlay confirm button
     */
    @FXML
    public void onErrorButtonMouseClicked(){
        criticalErrorPane.setMouseTransparent(true);
        criticalErrorPane.setVisible(false);
        if(currentState==InternalViewState.DISCONNECTION){
            gameController.reset();
            changeView(InternalViewState.INIT);
        } else if(currentState==InternalViewState.STOPPED){
            GraphicalUI.stage.hide();
            System.exit(0);
        }
    }

    public void joinUpdate() {
        if(currentState == InternalViewState.JOIN)
            preGameController.joinController.updateListLobbies();
    }

    public void lobbyUpdate() {
        if(currentState == InternalViewState.LOBBY && viewManager.getLobbyModelView()!=null)
            preGameController.lobbyController.updateListPlayers();
    }

    public void gameUpdate() {
        if(currentState == InternalViewState.GAME) gameController.update();
    }

    public void exceptionUpdate() {
        if(currentState!=InternalViewState.GAME){
            preGameController.displayError(viewManager.getClientExceptionsNotifierView().getLastException().getMessage());
        } else {
            gameController.displayError(viewManager.getClientExceptionsNotifierView().getLastException().getMessage());
        }
    }

    public void chatUpdate() {
        if(currentState == InternalViewState.GAME) gameController.updateChat();
        else preGameController.notifyNewMessage();
    }

    private void setKeyListener(){
        GraphicalUI.stage.getScene().addEventFilter(KeyEvent.KEY_PRESSED,(KeyEvent keyEvent)->{
            System.out.println("Pressed : "+keyEvent.getText());
            switch (currentState){
                case INIT -> preGameController.homeController.processKeyEvent(keyEvent);
                case JOIN -> preGameController.joinController.processKeyEvent(keyEvent);
                case CREATE -> preGameController.createController.processKeyEvent(keyEvent);
                case LOBBY -> preGameController.lobbyController.processKeyEvent(keyEvent);
                case GAME -> gameController.processKeyPress(keyEvent);
                case WIN ->  gameController.endGameController.processKeyEvent(keyEvent);
            }
        });
    }

}
