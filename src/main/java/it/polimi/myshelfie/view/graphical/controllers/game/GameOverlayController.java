package it.polimi.myshelfie.view.graphical.controllers.game;


import it.polimi.myshelfie.model.modelviews.CommonGoalModelView;
import it.polimi.myshelfie.view.ViewManager;
import it.polimi.myshelfie.view.graphical.CustomBindings;
import it.polimi.myshelfie.view.graphical.GraphicalUI;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

import static it.polimi.myshelfie.view.graphical.Images.getImage;

public class GameOverlayController {
    @FXML
    protected Label playingLabel;
    @FXML
    protected VBox playersListVbox;
    @FXML
    protected StackPane fullStackPane;
    @FXML
    protected HBox commonGoalsHBox;
    @FXML
    protected StackPane chatPane;
    @FXML
    protected ImageView commonGoal1;
    @FXML
    protected ImageView commonGoal2;
    @FXML
    protected ImageView personalGoal;

    ViewManager viewBuffer = ViewManager.getInstance();
    Stage stage;

    public void initialize(GameController gameController){
        stage = GraphicalUI.stage;

        CustomBindings.bindSize(fullStackPane, stage.widthProperty(), stage.heightProperty());
        fullStackPane.setPickOnBounds(false);

        CustomBindings.bindSize(playersListVbox, null, stage.heightProperty().divide(5));

        CustomBindings.bindSize(personalGoal, null, stage.heightProperty().divide(5).multiply(3));

        CustomBindings.bindSize(commonGoalsHBox, stage.widthProperty(), stage.heightProperty().divide(5));
        CustomBindings.bindSize(commonGoal1, null, stage.heightProperty().divide(5));
        CustomBindings.bindSize(commonGoal2, null, stage.heightProperty().divide(5));

        CustomBindings.bindSize(chatPane, stage.widthProperty().divide(21).multiply(6), stage.heightProperty().divide(15).multiply(8));
    }

    public void gameStart() {
        List<CommonGoalModelView> commonGoals = viewBuffer.getGameModelView().getCommonGoals();
        int idPersonalGoal = viewBuffer.getGameModelView().getPlayer(ViewManager.getUsername()).getPersonalGoals().get(0).getId();

        personalGoal.setImage(getImage("game/personalGoals/Personal_Goals"+idPersonalGoal+".png"));
        commonGoal1.setImage(getImage("game/commonGoals/"+(commonGoals.get(0).getId()+1)+".jpg"));
        commonGoal2.setImage(getImage("game/commonGoals/"+(commonGoals.get(1).getId()+1)+".jpg"));

        personalGoal.setTranslateY(stage.getHeight()/5*3 - 70);

        chatPane.setTranslateX(-20);
        chatPane.setTranslateY(stage.getHeight()/15*8 - 100);

        System.out.println("Stage: " + stage.getWidth() + ", " + stage.getHeight());
        System.out.println("Personal: " + personalGoal.getLayoutX() + ", " + personalGoal.getLayoutY());
        System.out.println("Common 1: " + commonGoal1.getLayoutX() + ", " + commonGoal1.getLayoutY());
        System.out.println("Common 2: " + commonGoal2.getLayoutX() + ", " + commonGoal2.getLayoutY());
        System.out.println("Chat: " + chatPane.getLayoutX() + ", " + chatPane.getLayoutY());
    }

    public void showPersonalGoal() {
        personalGoal.setTranslateY(-stage.getHeight()/5*3 + 70);
    }

    public void hidePersonalGoal() {
        personalGoal.setTranslateY(stage.getHeight()/5*3 - 70);
    }
}
