package it.polimi.myshelfie.view.graphical.controllers;

import it.polimi.myshelfie.view.InternalViewState;
import it.polimi.myshelfie.view.ViewManager;
import it.polimi.myshelfie.view.graphical.Animations;
import it.polimi.myshelfie.view.graphical.CustomBindings;
import it.polimi.myshelfie.view.graphical.GraphicalUI;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class PreGameController implements Initializable {
    @FXML
    StackPane home;
    @FXML
    StackPane create = new StackPane();
    @FXML
    StackPane join;
    @FXML
    StackPane lobby;
    @FXML
    Button backButton;
    @FXML
    ImageView banner;
    @FXML
    ImageView logo;
    @FXML
    protected ProgressIndicator progress;
    @FXML
    HomeController homeController;
    @FXML
    JoinController joinController;
    @FXML
    CreateController createController;
    @FXML
    LobbyController lobbyController;

    InternalViewState page;
    StackPane lastSlideUp;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Stage stage = GraphicalUI.stage;

        banner.fitWidthProperty().bind(stage.widthProperty());
        CustomBindings.bindSize(logo,stage.widthProperty().divide(2),stage.heightProperty().divide(3));

        homeController.initialize(banner);
        joinController.initialize(banner,this);
        createController.initialize(banner,this);
        lobbyController.initialize(banner,this);

        Animations.applyButtonAnimation(backButton);

        changePage(InternalViewState.INIT);
    }

    public void startLoading(){
        Platform.runLater(() -> progress.setVisible(true));
    }
    public void stopLoading(){
        Platform.runLater(() -> progress.setVisible(false));
    }
    protected void changePage(InternalViewState page){
        if(page==this.page) return; //avoiding forced animations
        stopLoading();
        switch (page){
            case INIT -> {
                if(this.page == InternalViewState.CREATE){
                    slideDown(create);
                    slideUp(backButton);
                } else if (this.page ==InternalViewState.JOIN) {
                    slideDown(join);
                    slideUp(backButton);
                } else if (this.page ==InternalViewState.LOBBY) {
                    slideDown(lobby,lastSlideUp);
                    slideUp(backButton);
                } else {
                    create.setVisible(false);
                    create.setMouseTransparent(true);
                    join.setVisible(false);
                    join.setMouseTransparent(true);
                    lobby.setVisible(false);
                    lobby.setMouseTransparent(true);
                    backButton.setVisible(false);
                }
                homeController.setKeyListeners();
            }
            case JOIN -> {
                joinController.updateListLobbies();
                slideUp(join);
                lastSlideUp = join;
                slideDown(backButton);
                joinController.setKeyListeners();
            }
            case CREATE -> {
                slideUp(create);
                lastSlideUp = create;
                slideDown(backButton);
                createController.setKeyListeners();
            }
            case LOBBY -> {
                lobbyController.refresh();
                slideUp(lobby);
                backButton.setVisible(true);
                lobbyController.setKeyListeners();
            }
        }
        this.page = page;
    }

    private void slideDown(StackPane pane,StackPane secondPane){
        TranslateTransition tt = Animations.getSlideDownTransition(pane);
        TranslateTransition tt2 = Animations.getSlideDownTransition(secondPane);
        tt.setOnFinished((finish)->{
            pane.setVisible(false);
            pane.setMouseTransparent(true);
            tt2.play();
        });
        tt2.setOnFinished((finish)->{
            secondPane.setVisible(false);
            secondPane.setMouseTransparent(true);
        });
        tt.play();
    }
    private void slideDown(StackPane pane){
        pane.setTranslateY(pane.getHeight());
        TranslateTransition tt = Animations.getSlideDownTransition(pane);
        tt.setOnFinished((finish)->{
            pane.setVisible(false);
            pane.setMouseTransparent(true);
        });
        tt.play();
    }
    private void slideUp(StackPane pane){
        pane.setTranslateY(pane.getHeight());
        pane.setVisible(true);
        pane.setMouseTransparent(false);
        TranslateTransition tt = Animations.getSlideUpTransition(pane);
        tt.play();
    }

    private void slideDown(Button button){
        button.setTranslateY(-button.getLayoutY()-button.getHeight());
        button.setVisible(true);
        button.setMouseTransparent(false);
        TranslateTransition tt = Animations.getSlideDownTransition(button);
        tt.play();
    }

    private void slideUp(Button button){
        TranslateTransition tt = Animations.getSlideUpTransition(button);
        tt.play();
        tt.setOnFinished((finish)->{
            button.setVisible(false);
            button.setMouseTransparent(true);
        });
    }

    @FXML
    public void onBackArrowClicked() {
        if(page == InternalViewState.LOBBY){
            ViewManager.getInstance().exitRequest();
        }
        GraphicalUI.mainViewController.changeView(InternalViewState.INIT);
    }

    public void displayError(String errorMessage){
        Label errorLabel;
        switch (page){
            case JOIN -> errorLabel = joinController.joinErrorLabel;
            case CREATE -> errorLabel = createController.createErrorLabel;
            case LOBBY -> errorLabel = lobbyController.errorLabel;
            default -> {
                return;
            }
        }
        stopLoading();
        Platform.runLater(() -> {
            errorLabel.setText(errorMessage);
            errorLabel.setVisible(true);
        });
        Animations.timer(() -> Platform.runLater(() -> errorLabel.setVisible(false)), 3000);
    }

    public void reset(){
        joinController.reset();
        createController.reset();
        lobbyController.reset();
    }
}
