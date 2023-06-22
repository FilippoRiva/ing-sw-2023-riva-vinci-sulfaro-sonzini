package it.polimi.myshelfie.view.graphical.controllers.game;

import it.polimi.myshelfie.model.GameState;
import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.view.ViewManager;
import it.polimi.myshelfie.view.graphical.Animations;
import it.polimi.myshelfie.view.graphical.CustomBindings;
import it.polimi.myshelfie.view.graphical.GraphicalUI;
import javafx.animation.Animation;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.List;

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
    StackPane tileSorter;
    @FXML
    BookshelfController leftBookshelfController;
    @FXML
    BookshelfController rightBookshelfController;
    @FXML
    LivingRoomController livingRoomController;
    @FXML
    TileSorterController tileSorterController;
    private enum PanelState {ACTION, SPECTATE}
    private PanelState state;
    public void initialize(GameController gameController){
        Stage stage = GraphicalUI.stage;
        CustomBindings.bindSize(panelHBox,stage.widthProperty(),stage.heightProperty());
        leftBookshelfController.initialize(true);
        rightBookshelfController.initialize(false);
        livingRoomController.initialize(this,leftBookshelfController.tileWidthProperty());
        tileSorterController.initialize(leftBookshelfController.tileWidthProperty());
        tileSorter.translateXProperty().bind(livingRoomController.livingRoomImage.fitWidthProperty().divide(10.5));
        confirmButton.translateXProperty().bind(tileSorter.translateXProperty());
        confirmButton.translateYProperty().bind(livingRoomController.livingRoomImage.fitHeightProperty().divide(3));
        actionSwitch.translateYProperty().bind(stage.heightProperty().divide(2));
        panelHBox.spacingProperty().bind(stage.widthProperty().divide(40));
        tileSorter.setOpacity(0);
        confirmButton.setOpacity(0);
        confirmButton.setMouseTransparent(true);
        Animations.applyHoverScaling(confirmButton);
        state = PanelState.SPECTATE;
    }

    private void actionView(){
        if(state!=PanelState.SPECTATE) return;
        Animation translate = Animations.tranlateToLeft(leftBookshelf);
        translate.setOnFinished((event)->{
            leftBookshelf.setVisible(false);
            leftBookshelf.setMouseTransparent(true);
        });
        Animation translateLivingRoom = Animations.translateBy(livingRoom,leftBookshelf.getLayoutX()+livingRoomController.livingRoomImage.getFitWidth()/2-GraphicalUI.stage.getWidth()/2,0);
        Animation scaleRightBookshelf = Animations.scale(rightBookshelf,-1,-1,0.2);
        Animation fadeInTileSorter = Animations.fadeIn(tileSorter);
        Animation fadeInConfirmButton;
        if(ViewManager.getInstance().getGameModelView().getCurrentState()!=GameState.INSERT){ //preventing fade of confirm button when in Insert state
            fadeInConfirmButton = Animations.fadeIn(confirmButton);
        } else {
            fadeInConfirmButton = new TranslateTransition(); //empty transition
        }

        actionSwitch.setOnMouseClicked((event)-> spectatorView(true));
        actionSwitch.setVisible(true);
        actionSwitch.setMouseTransparent(false);

        ParallelTransition toActionTransition = new ParallelTransition(translate,translateLivingRoom,scaleRightBookshelf,fadeInTileSorter,fadeInConfirmButton);
        toActionTransition.setOnFinished((evt)->{
            state = PanelState.ACTION;
            confirmButton.setMouseTransparent(false);
            livingRoomController.setChosenTileTranslations();
        });
        toActionTransition.play();
    }

    private void spectatorView(boolean myTurn){
        if(state!=PanelState.ACTION) return;
        leftBookshelf.setVisible(true);
        leftBookshelf.setMouseTransparent(false);
        Animation translate = Animations.translateFromLeft(leftBookshelf);
        Animation translateLivingRoom = Animations.eraseTranslation(livingRoom);
        Animation scaleRightBookshelf = Animations.scale(rightBookshelf,rightBookshelf.getScaleX(),1,-1);
        Animation fadeOutTileSorter = Animations.fadeOut(tileSorter);
        Animation fadeOutConfirmButton = Animations.fadeOut(confirmButton);
        Animation resetChosenTilesTranslations = livingRoomController.resetChosenTileTranslations();

        actionSwitch.setOnMouseClicked((event)-> actionView());
        if(!myTurn){
            actionSwitch.setVisible(false);
            actionSwitch.setMouseTransparent(true);
        }
        ParallelTransition toSpectatorTransition = new ParallelTransition(translate,translateLivingRoom,scaleRightBookshelf,fadeOutTileSorter,fadeOutConfirmButton,
                resetChosenTilesTranslations);
        toSpectatorTransition.setOnFinished((evt)->{
            state = PanelState.SPECTATE;
            confirmButton.setMouseTransparent(true);
        });
        toSpectatorTransition.play();
    }

    public void setState(GameState currentStateDiff) {
        ViewManager vm = ViewManager.getInstance();
        switch (currentStateDiff){
            case DRAW,LAST_DRAW -> {
                if(vm.isRemainingTilesDiff()) {
                    livingRoomController.updateLivingRoom(ViewManager.getInstance().getLivingRoomDiff());
                }
                if(vm.isMyTurn()){
                    this.setActive(true);
                    Platform.runLater(()->confirmButton.setText("Confirm Draw"));
                    confirmButton.setOnMouseClicked((event)-> vm.drawRequest(livingRoomController.chosenTiles));
                }
            }
            case ORGANIZE,LAST_ORGANIZE -> {
                livingRoomController.updateLivingRoom(ViewManager.getInstance().getLivingRoomDiff());
                if(vm.isMyTurn()){
                    tileSorterController.loadTiles();
                    Platform.runLater(()->confirmButton.setText("Confirm Organize"));
                    livingRoomController.setActive(false); //disabling clickable tiles and emptying list
                    tileSorterController.setActive(true);
                    confirmButton.setOnMouseClicked((event)->{
                        List<ItemTile> orderedTiles = tileSorterController.getOrderedTiles();
                        if(orderedTiles.size()==0 || orderedTiles.size()==vm.getGameModelView().getCurrentPlayer().getTakenTiles().size()){
                            vm.organizeRequest(tileSorterController.getOrderedTiles());
                        } else {
                            Animations.buttonError(confirmButton).play();
                        }
                    });
                }
            }
            case INSERT,LAST_INSERT -> {
                if(vm.isMyTurn()){
                    Animations.fadeOut(confirmButton).play();
                    tileSorterController.setActive(false);
                    tileSorterController.resetDots();
                    tileSorterController.fixOrdering();
                    rightBookshelfController.setActive(true);
                }
            }
            case COUNTING_POINTS,LAST_COUNTING_POINTS -> {
                if(vm.isMyTurn()){
                    rightBookshelfController.setActive(false);
                    tileSorterController.resetAll();
                    setActive(false);
                }
            }
        }

        //completely reloading elements
        leftBookshelfController.loadLeft();
        rightBookshelfController.loadRight();
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