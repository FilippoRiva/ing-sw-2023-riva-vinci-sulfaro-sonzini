package it.polimi.myshelfie.view.graphical.controllers.game;

import it.polimi.myshelfie.model.GameState;
import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.Position;
import it.polimi.myshelfie.view.ViewManager;
import it.polimi.myshelfie.view.graphical.Animations;
import it.polimi.myshelfie.view.graphical.CustomBindings;
import it.polimi.myshelfie.view.graphical.Effects;
import it.polimi.myshelfie.view.graphical.GraphicalUI;
import javafx.animation.Animation;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

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
    Label actionSwitch;
    @FXML
    Button confirmButton;
    @FXML
    Label confirmButtonLabel;
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

    public void reset() {
        leftBookshelfController.reset();
        rightBookshelfController.reset();
        livingRoomController.reset();
        tileSorterController.resetAll();
    }

    private enum PanelState {ACTION, SPECTATE}
    private PanelState state;
    private final ViewManager vm = ViewManager.getInstance();
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
        confirmButtonLabel.translateXProperty().bind(confirmButton.translateXProperty());
        confirmButtonLabel.translateYProperty().bind(confirmButton.translateYProperty().add(20).add(confirmButton.heightProperty()));
        confirmButtonLabel.setFont(new Font(10));
        actionSwitch.translateYProperty().bind(stage.heightProperty().divide(2));
        actionSwitch.setOpacity(0);
        panelHBox.spacingProperty().bind(stage.widthProperty().divide(40));
        tileSorter.setOpacity(0);
        confirmButton.setOpacity(0);
        confirmButton.setMouseTransparent(true);

        Effects.applyShadow(confirmButton);

        Animations.applyHoverScaling(confirmButton);
        Animations.applyHoverScaling(actionSwitch);

        state = PanelState.SPECTATE;
    }

    private void actionView(){
        if(state!=PanelState.SPECTATE) return;


        actionSwitch.setOnMousePressed((event)-> spectatorView());
        actionSwitch.setMouseTransparent(true);
        Animations.fadeIn(actionSwitch).play();
        Animations.rotateZ(actionSwitch,-180, Duration.millis(200)).play();

        switch (vm.getCurrentStateDiff()){
            case DRAW, LAST_DRAW -> livingRoomController.setActive(true);
            case ORGANIZE, LAST_ORGANIZE -> tileSorterController.setActive(true);
            case INSERT, LAST_INSERT, COUNTING_POINTS, LAST_COUNTING_POINTS -> rightBookshelfController.setActive(true);
        }

        //Animation
        Animation translate = Animations.tranlateToLeft(leftBookshelf);
        translate.setOnFinished((event)->{
            leftBookshelf.setVisible(false);
            leftBookshelf.setMouseTransparent(true);
        });
        Animation translateLivingRoom = Animations.translateBy(livingRoom,leftBookshelf.getLayoutX()+livingRoomController.livingRoomImage.getFitWidth()/2-GraphicalUI.stage.getWidth()/2,0);
        Animation scaleRightBookshelf = Animations.scale(rightBookshelf,-1,-1,0.2);
        Animation fadeInTileSorter = Animations.fadeIn(tileSorter);
        Animation fadeInConfirmButton;
        if(vm.getGameModelView().getCurrentState()!=GameState.INSERT){ //preventing fade of confirm button when in Insert state
            fadeInConfirmButton = Animations.fadeIn(confirmButton);
        } else {
            fadeInConfirmButton = new TranslateTransition(); //empty transition
        }

        ParallelTransition toActionTransition = new ParallelTransition(translate,translateLivingRoom,scaleRightBookshelf,fadeInTileSorter,fadeInConfirmButton);
        toActionTransition.setOnFinished((evt)->{
            state = PanelState.ACTION;
            Animation lastTranslation = livingRoomController.setChosenTileTranslations();
            lastTranslation.setOnFinished((evt1)->{
                confirmButton.setMouseTransparent(false);
                actionSwitch.setMouseTransparent(false);
            });
            lastTranslation.play();
        });
        toActionTransition.play();
    }

    private void spectatorView(){
        if(state!=PanelState.ACTION) return;

        actionSwitch.setOnMousePressed((event)-> actionView());
        actionSwitch.setMouseTransparent(true);
        if(!vm.isMyTurn()){
            Animations.fadeOut(actionSwitch).play();
        } else {
            switch (vm.getCurrentStateDiff()){
                case DRAW, LAST_DRAW -> livingRoomController.setActive(false);
                case ORGANIZE, LAST_ORGANIZE -> tileSorterController.setActive(false);
                case INSERT, LAST_INSERT, COUNTING_POINTS, LAST_COUNTING_POINTS -> rightBookshelfController.setActive(false);
            }
        }

        Animations.rotateZ(actionSwitch,180, Duration.millis(200)).play();

        //Animation
        leftBookshelf.setVisible(true);
        leftBookshelf.setMouseTransparent(false);
        Animation translate = Animations.translateFromLeft(leftBookshelf);
        Animation translateLivingRoom = Animations.eraseTranslation(livingRoom);
        Animation scaleRightBookshelf = Animations.scale(rightBookshelf,rightBookshelf.getScaleX(),1,-1);
        Animation fadeOutTileSorter = Animations.fadeOut(tileSorter);
        Animation fadeOutConfirmButton = Animations.fadeOut(confirmButton);
        Animation resetChosenTilesTranslations = livingRoomController.resetChosenTileTranslations();

        ParallelTransition toSpectatorTransition = new ParallelTransition(translate,translateLivingRoom,scaleRightBookshelf,fadeOutTileSorter,fadeOutConfirmButton,
                resetChosenTilesTranslations);
        toSpectatorTransition.setOnFinished((evt)->{
            state = PanelState.SPECTATE;
            confirmButton.setMouseTransparent(false);
            actionSwitch.setMouseTransparent(false);
        });
        toSpectatorTransition.play();
    }

    /**
     * Set the GamePanel State. Responsible for the update logic of sub-Controllers and the confirmButton's logic
     * @param state the new GamePanel State
     */
    public void setState(GameState state) {
        switch (state){
            case DRAW,LAST_DRAW -> {
                if(vm.getLastPlayer().equals(ViewManager.getUsername())){
                    rightBookshelfController.loadTokens();
                } else {
                    leftBookshelfController.loadTokens();
                }
                if(vm.isRemainingTilesDiff()) {
                    livingRoomController.updateLivingRoom(vm.getLivingRoomDiff());
                }
                if(vm.isMyTurn()){
                    Animations.timer(this::actionView,1000);
                    Platform.runLater(()->confirmButton.setText("Confirm Draw"));
                    confirmButton.setOnMousePressed((event)-> {
                        if(livingRoomController.chosenTiles.size()==0){
                            Animations.nodeError(confirmButton,1).play();
                            showConfirmError("Choose some tiles first");
                        } else if(vm.areChosenTilesInvalid(livingRoomController.chosenTiles)){
                            Animations.nodeError(confirmButton,1).play(); //not valid positions
                            showConfirmError("Tiles are not valid");
                        } else if(Position.arePositionsMisaligned(livingRoomController.chosenTiles)){
                            Animations.nodeError(confirmButton,1).play(); //not aligned
                            showConfirmError("Tiles are not aligned");
                        } else {
                            vm.drawRequest(livingRoomController.chosenTiles);

                        }
                    });
                }
            }
            case ORGANIZE,LAST_ORGANIZE -> {
                livingRoomController.updateLivingRoom(vm.getLivingRoomDiff());
                if(vm.isMyTurn()){
                    livingRoomController.resetChoice();
                    tileSorterController.loadTiles();
                    livingRoomController.setActive(false); //disabling clickable tiles and emptying list
                    if(vm.getGameModelView().getCurrentPlayer().getTakenTiles().size()==1){
                        vm.organizeRequest(vm.getGameModelView().getCurrentPlayer().getTakenTiles());
                    } else {
                        Platform.runLater(()->{
                            Animation out = Animations.fadeOut(confirmButton);
                            out.setOnFinished((evt)->{
                                confirmButton.setText("Confirm Organize");
                                Animations.fadeIn(confirmButton).play();
                            });
                            out.play();
                        });
                        tileSorterController.setActive(true);
                        confirmButton.setOnMousePressed((event)->{
                            List<ItemTile> orderedTiles = tileSorterController.getOrderedTiles();
                            if(orderedTiles.size()==0 || orderedTiles.size()==vm.getGameModelView().getCurrentPlayer().getTakenTiles().size()){
                                vm.organizeRequest(tileSorterController.getOrderedTiles());
                            } else {
                                Animations.nodeError(confirmButton,1).play();
                                showConfirmError("Order every tile first");
                            }
                        });
                    }
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
                    rightBookshelfController.loadNewTiles();
                    rightBookshelfController.setActive(false);
                    tileSorterController.resetAll();
                    Animations.timer(this::spectatorView,1000);
                } else {
                    leftBookshelfController.loadNewTiles();
                }
            }
            case WIN -> {
                if(vm.getWinner().getName().equals(ViewManager.getUsername())) rightBookshelfController.loadTokens();
                else leftBookshelfController.loadTokens();
            }
        }
    }

    public void showConfirmError(String message){
        Platform.runLater(()->{
            confirmButtonLabel.setText(message);
            Animations.fadeIn(confirmButtonLabel).play();
            Animations.timer(()-> Animations.fadeOut(confirmButtonLabel).play(),2000);
        });
    }
}