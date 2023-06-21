package it.polimi.myshelfie.view.graphical.controllers.game;

import it.polimi.myshelfie.model.GameState;
import it.polimi.myshelfie.view.ViewManager;
import it.polimi.myshelfie.view.graphical.Animations;
import it.polimi.myshelfie.view.graphical.CustomBindings;
import it.polimi.myshelfie.view.graphical.GraphicalUI;
import javafx.animation.Animation;
import javafx.animation.ParallelTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GamePanelController {
    @FXML
    HBox panelHBox;
    @FXML
    BorderPane leftBookshelf;
    @FXML
    BorderPane rightBookshelf;
    @FXML
    StackPane livingRoom;
    @FXML
    Button actionSwitch;
    @FXML
    Button confirmButton;
    @FXML
    BookshelfController leftBookshelfController;
    @FXML
    BookshelfController rightBookshelfController;
    @FXML
    LivingRoomController livingRoomController;
    public void initialize(GameController gameController){
        Stage stage = GraphicalUI.stage;
        CustomBindings.bindSize(panelHBox,stage.widthProperty(),stage.heightProperty());
        leftBookshelfController.initialize(true);
        rightBookshelfController.initialize(false);
        livingRoomController.initialize(leftBookshelfController.tileWidthProperty());
        actionSwitch.translateYProperty().bind(stage.heightProperty().divide(2));
        panelHBox.spacingProperty().bind(stage.widthProperty().divide(40));
    }

    private void actionView(){
        if(leftBookshelf.getTranslateX()!=0) return;
        Animation translate = Animations.tranlateToLeft(leftBookshelf);
        translate.setOnFinished((event)->{
            leftBookshelf.setVisible(false);
            leftBookshelf.setMouseTransparent(true);
        });
        Animation translateLivingRoom = Animations.translateBy(livingRoom,leftBookshelf.getLayoutX()+livingRoomController.livingRoomImage.getFitWidth()-GraphicalUI.stage.getWidth()/2,0);
        translateLivingRoom.setOnFinished((event)-> livingRoom.translateXProperty().bind(leftBookshelf.layoutXProperty()
                .add(livingRoomController.livingRoomImage.layoutXProperty()).subtract(GraphicalUI.stage.widthProperty().divide(2))));
        Animation scaleRightBookshelf = Animations.scale(rightBookshelf,-1,-1,0.2);


        actionSwitch.setOnMouseClicked((event)-> spectatorView(true));
        actionSwitch.setVisible(true);
        actionSwitch.setMouseTransparent(false);

        new ParallelTransition(translate,translateLivingRoom,scaleRightBookshelf).play();
    }

    private void spectatorView(boolean myTurn){
        if(leftBookshelf.getTranslateX()==0) return;
        leftBookshelf.setVisible(true);
        leftBookshelf.setMouseTransparent(false);
        livingRoom.translateXProperty().unbind();
        Animation translate = Animations.translateFromLeft(leftBookshelf);
        Animation translateLivingRoom = Animations.eraseTranslation(livingRoom);
        Animation scaleRightBookshelf = Animations.scale(rightBookshelf,rightBookshelf.getScaleX(),1,-1);

        livingRoomController.setActive(false);

        actionSwitch.setOnMouseClicked((event)-> actionView());
        if(!myTurn){
            actionSwitch.setVisible(false);
            actionSwitch.setMouseTransparent(true);
        }
        new ParallelTransition(translate,translateLivingRoom,scaleRightBookshelf).play();
    }

    public void setState(GameState currentStateDiff) {
        ViewManager vm = ViewManager.getInstance();
        switch (currentStateDiff){
            case DRAW,LAST_DRAW -> {
                //TODO update tokens
                confirmButton.setOnMouseClicked((event)->{
                    vm.drawRequest(livingRoomController.chosenTiles);
                });
            }
            case ORGANIZE,LAST_ORGANIZE -> {
                //TODO update drawn tiles
                confirmButton.setOnMouseClicked((event)->{
                    vm.organizeRequest(null); //TODO
                });
            }
            case INSERT,LAST_INSERT -> {
                //TODO update organized tiles
                confirmButton.setOnMouseClicked((event)->{
                    vm.insertRequest(-1); //TODO
                });
            }
            case COUNTING_POINTS,LAST_COUNTING_POINTS -> {

            }
        }
    }

    public void setActive(boolean active){
        livingRoomController.setActive(active);
        if(active) {
            actionView();
        } else {
            spectatorView(false);
        }
    }
}