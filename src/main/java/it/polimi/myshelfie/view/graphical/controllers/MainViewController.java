package it.polimi.myshelfie.view.graphical.controllers;

import it.polimi.myshelfie.view.InternalViewState;
import it.polimi.myshelfie.view.ViewManager;
import it.polimi.myshelfie.view.graphical.GraphicalUI;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {
    @FXML
    private PreGameController preGameController;
    @FXML
    private GameController gameController;
    @FXML
    private AnchorPane game;
    @FXML
    private StackPane preGame;
    @FXML
    private Pane criticalErrorPane;
    @FXML
    private Label errorLabel;
    private ViewManager viewManager;
    private InternalViewState currentState;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.viewManager = ViewManager.getInstance();
        criticalErrorPane.setVisible(false);

        currentState = InternalViewState.INIT;

        game.setVisible(false);
        game.setMouseTransparent(true);
    }

    public void changeView(InternalViewState internalViewState){
        switch (internalViewState){
            case INIT -> {
                preGame.setMouseTransparent(false);
                preGame.setVisible(true);
                game.setMouseTransparent(true);
                game.setVisible(false);
                if(this.currentState == InternalViewState.LOBBY || this.currentState == InternalViewState.GAME)
                    preGameController.reset();
                preGameController.changePage(InternalViewState.INIT);
            }
            case CREATE -> preGameController.changePage(InternalViewState.CREATE);
            case JOIN -> preGameController.changePage(InternalViewState.JOIN);
            case LOBBY -> preGameController.changePage(InternalViewState.LOBBY);
            case GAME -> {
                gameController.gameStart();
                preGame.setMouseTransparent(true);
                preGame.setVisible(false);
                game.setMouseTransparent(false);
                game.setVisible(true);
            }
            case WIN -> {
                gameController.win();
            }
            case DISCONNECTION -> {
                showErrorPane(viewManager.getDisconnectedClientExceptionsNotifierView().getLastException().getMessage());
            }
            case  STOPPED -> {
                showErrorPane("You have been disconnected. Try reconnecting later");
            }
        }
        currentState = internalViewState;
    }

    private void showErrorPane(String message) {
        Platform.runLater(() -> {
            criticalErrorPane.setMouseTransparent(false);
            errorLabel.setText(message);
            errorLabel.setTextFill(Color.RED);
            errorLabel.setBackground(Background.fill(Color.BLACK));
            criticalErrorPane.setVisible(true);
        });
    }

    @FXML
    public void onErrorButtonMouseClicked(){
        criticalErrorPane.setMouseTransparent(true);
        criticalErrorPane.setVisible(false);
        if(currentState==InternalViewState.STOPPED){
            GraphicalUI.stage.hide();
            System.exit(0);
        } else {
            changeView(InternalViewState.INIT);
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
        if(currentState == InternalViewState.GAME) gameController.play();
    }

    public void exceptionUpdate() {
        if(currentState!=InternalViewState.GAME){
            preGameController.displayError(viewManager.getClientExceptionsNotifierView().getLastException().getMessage());
        }
    }

    public void chatUpdate() {
        if(currentState == InternalViewState.GAME) gameController.updateChat();
        preGameController.notifyNewMessage();
        preGameController.lobbyController.chatController.addChatMessage();
    }

}
