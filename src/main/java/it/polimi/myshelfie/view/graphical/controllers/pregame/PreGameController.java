package it.polimi.myshelfie.view.graphical.controllers.pregame;

import it.polimi.myshelfie.view.InternalViewState;
import it.polimi.myshelfie.view.ViewManager;
import it.polimi.myshelfie.view.graphical.Animations;
import it.polimi.myshelfie.view.graphical.CustomBindings;
import it.polimi.myshelfie.view.graphical.GraphicalUI;
import javafx.animation.Animation;
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
    Button exitButton;
    @FXML
    protected ProgressIndicator progress;
    @FXML
    protected Button chatButton;
    @FXML
    protected Label notificationDot;
    @FXML
    public HomeController homeController;
    @FXML
    public JoinController joinController;
    @FXML
    public CreateController createController;
    @FXML
    public LobbyController lobbyController;

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

        Animations.applyHoverScaling(backButton);
        Animations.applyHoverScaling(chatButton);

        changePage(InternalViewState.INIT);
    }

    public void startLoading(){
        Platform.runLater(() -> progress.setVisible(true));
    }
    public void stopLoading(){
        Platform.runLater(() -> progress.setVisible(false));
    }
    public void changePage(InternalViewState page){
        if(page==this.page) return; //avoiding forced animations
        stopLoading();
        switch (page){
            case INIT -> {
                if(this.page == InternalViewState.CREATE){
                    hidePane(create);
                    hideButton(backButton);
                } else if (this.page ==InternalViewState.JOIN) {
                    hidePane(join);
                    hideButton(backButton);
                } else if (this.page ==InternalViewState.LOBBY) {
                    dualHidePane(lobby,lastSlideUp);
                    hideButton(backButton);
                    hideButton(chatButton);
                    hideLabel(notificationDot);
                } else {
                    create.setVisible(false);
                    create.setMouseTransparent(true);
                    join.setVisible(false);
                    join.setMouseTransparent(true);
                    lobby.setVisible(false);
                    lobby.setMouseTransparent(true);
                    lobbyController.chat.setVisible(false);
                    lobbyController.chat.setMouseTransparent(true);
                    backButton.setVisible(false);
                    backButton.setMouseTransparent(true);
                    chatButton.setVisible(false);
                    chatButton.setMouseTransparent(true);
                    notificationDot.setVisible(true);
                    notificationDot.setOpacity(0);
                }
                showButton(exitButton);
                homeController.setKeyListeners();
            }
            case JOIN -> {
                joinController.updateListLobbies();
                showPane(join);
                lastSlideUp = join;
                showButton(backButton);
                hideButton(exitButton);
                joinController.setKeyListeners();
            }
            case CREATE -> {
                showPane(create);
                lastSlideUp = create;
                showButton(backButton);
                hideButton(exitButton);
                createController.setKeyListeners();
            }
            case LOBBY -> {
                lobbyController.refresh();
                showPane(lobby);
                showButton(chatButton);
                moveLabel(notificationDot);
                backButton.setVisible(true);
                lobbyController.setKeyListeners();
            }
        }
        this.page = page;
    }

    private void dualHidePane(StackPane pane, StackPane secondPane){
        TranslateTransition tt = Animations.getSlideToBottom(pane);
        TranslateTransition tt2 = Animations.getSlideToBottom(secondPane);
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
    private void hidePane(StackPane pane){
        pane.setTranslateY(pane.getHeight());
        TranslateTransition tt = Animations.getSlideToBottom(pane);
        tt.setOnFinished((finish)->{
            pane.setVisible(false);
            pane.setMouseTransparent(true);
        });
        tt.play();
    }
    private void showPane(StackPane pane){
        pane.setTranslateY(pane.getHeight());
        pane.setVisible(true);
        pane.setMouseTransparent(false);
        TranslateTransition tt = Animations.getSlideFromBottom(pane);
        tt.play();
    }

    private void showButton(Button button){
        button.setMouseTransparent(false);
        Animations.slideRegionFromTop(button,true).play();
    }
    private void hideButton(Button button){
        button.setMouseTransparent(true);
        Animations.slideRegionToTop(button).play();
    }
    private void moveLabel(Label label){Animations.slideRegionFromTop(label,false).play();}
    private void hideLabel(Label label){Animations.slideRegionToTop(label).play();}

    @FXML
    public void onBackArrowClicked() {
        if(page == InternalViewState.LOBBY){
            ViewManager.getInstance().exitRequest();
        }
        GraphicalUI.mainViewController.changeView(InternalViewState.INIT);
    }

    @FXML
    public void onExitButtonClicked() {
        System.exit(0);
    }

    public void displayError(String errorMessage){
        Label errorLabel;
        switch (page){
            case JOIN -> errorLabel = joinController.joinErrorLabel;
            case CREATE -> errorLabel = createController.createErrorLabel;
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
        Platform.runLater(()->{
            notificationDot.setOpacity(0);
            notificationDot.setText("0");
        });
    }

    public void toggleChatVisible() {
        if(lobbyController.chatController.isChatVisible()){
            lobbyController.chat.setMouseTransparent(true);
            hidePane(lobbyController.chat);
            lobbyController.chatController.setChatVisible(false);
            return;
        }
        lobbyController.chat.setMouseTransparent(false);
        showPane(lobbyController.chat);
        lobbyController.chatController.setChatVisible(true);
        //reset notificationCounter
        if(notificationDot.getText().equals("0")) return;
        Animation a = Animations.reverse(Animations.popFadeIn(notificationDot));
        a.setOnFinished((evt)->Platform.runLater(()->notificationDot.setText("0")));
        a.play();
    }

    public void notifyNewMessage() {
        lobbyController.chatController.addChatMessage();
        if(lobbyController.chatController.isChatVisible()) return;
        int prev = Integer.parseInt(notificationDot.getText());
        if(prev==0) {
            Animations.popFadeIn(notificationDot).play();
        }
        int newValue = prev+1;
        Platform.runLater(()->notificationDot.setText(String.valueOf(newValue)));
    }
}
