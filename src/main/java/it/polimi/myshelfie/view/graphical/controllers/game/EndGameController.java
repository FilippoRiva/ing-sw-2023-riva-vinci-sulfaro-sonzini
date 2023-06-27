package it.polimi.myshelfie.view.graphical.controllers.game;

import it.polimi.myshelfie.model.EarnedPoint;
import it.polimi.myshelfie.model.PointType;
import it.polimi.myshelfie.model.Token;
import it.polimi.myshelfie.model.modelviews.LobbyModelView;
import it.polimi.myshelfie.model.modelviews.PlayerModelView;
import it.polimi.myshelfie.view.ViewManager;
import it.polimi.myshelfie.view.graphical.CustomBindings;
import it.polimi.myshelfie.view.graphical.GraphicalUI;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EndGameController{
    @FXML
    protected StackPane thirdPodiumStack;
    @FXML
    protected ImageView secondPodiumImage;
    @FXML
    protected ImageView firstPodiumImage;
    @FXML
    protected ImageView thirdPodiumImage;
    @FXML
    protected Label lobbyNameLabel;
    @FXML
    protected GridPane detailsGrid;
    @FXML
    protected GridPane scoreboardGrid;
    @FXML
    protected StackPane detailsStack;
    @FXML
    protected Label player1;
    @FXML
    protected Label player2;
    @FXML
    protected Label player3;
    @FXML
    protected Label points1;
    @FXML
    protected Label points2;
    @FXML
    protected Label points3;
    @FXML
    protected Button detailsButton;
    @FXML
    protected HBox mainHBox;
    private Label[] pointsPodium;
    private Label[] playersPodium;
    private final ViewManager viewManager = ViewManager.getInstance();
    private final List<Label> scoreboardGridLabels = new ArrayList<>();
    private final List<Label> detailedGridLabels = new ArrayList<>();

    public void customInitialize() {
        CustomBindings.bindSize(mainHBox,GraphicalUI.stage.widthProperty(),GraphicalUI.stage.heightProperty());
        CustomBindings.bindSize(firstPodiumImage,GraphicalUI.stage.widthProperty().divide(5),null);
        CustomBindings.bindSize(secondPodiumImage,GraphicalUI.stage.widthProperty().divide(5),null);
        CustomBindings.bindSize(thirdPodiumImage,GraphicalUI.stage.widthProperty().divide(5),null);
        addColumn(detailsGrid,0,
                new Label(), createLabel("End Token"),
                createLabel("Common Goals"),createLabel("PersonalGoal"),
                createLabel("Adjacent Tiles"), createLabel("Tot"));

        playersPodium = new Label[]{player1, player2, player3};
        pointsPodium = new Label[]{points1, points2, points3};
    }

    private void addRowScoreboard(PlayerModelView player, int position){
        Label nameLabel = new Label((position+1)+". "+player.getName());
        nameLabel.setStyle("-fx-font-size: 25");
        Label pointsLabel = new Label(String.valueOf(player.getPoints()));
        pointsLabel.setStyle("-fx-font-size: 25");
        scoreboardGridLabels.add(nameLabel);
        scoreboardGridLabels.add(pointsLabel);

        Platform.runLater(()-> addRow(scoreboardGrid,position, nameLabel, pointsLabel));
    }

    private void addColumnDetail(PlayerModelView player, int position, int numPlayers){
        List<Token> takenTokens = player.getTokens();
        List<EarnedPoint> earnedPoints= player.getEarnedPoints();
        List<Integer> tokenRomanNumeral = takenTokens.stream().map(Token::getRomanNumeral).toList();

        Label nameLabel = new Label(player.getName());
        nameLabel.setStyle("-fx-font-size: 18");
        Label firstToken = createLabel(tokenRomanNumeral.contains(0) ? 1 : 0);
        int common1 = tokenRomanNumeral.contains(1) ?
                takenTokens.stream().filter(x -> x.getRomanNumeral()==1).toList().get(0).getValue() : 0;
        int common2 = tokenRomanNumeral.contains(2) ?
                takenTokens.stream().filter(x -> x.getRomanNumeral()==2).toList().get(0).getValue() : 0;
        Label common = new Label(common1 + " + " + common2);
        common.setStyle("-fx-font-size: 13");

        Label personal = createLabel(earnedPoints.stream().filter(x->x.getType()==PointType.PERSONAL).mapToInt(EarnedPoint::getValue).sum());
        Label adjacent = createLabel(earnedPoints.stream().filter(x->x.getType()==PointType.ADJACENT).toList().get(0).getValue());

        Label tot = createLabel(player.getPoints());
        //TODO make class and give to all tests
        tot.setStyle("-fx-font-weight: bold; -fx-font-family: 'Baskerville Old Face'; -fx-font-size: 22");

        ColumnConstraints columnConstraints = new ColumnConstraints();
        //columnConstraints.setPercentWidth((double) (724) /numPlayers);
        columnConstraints.setPrefWidth((double) (683 - 200) /numPlayers);
        columnConstraints.setHalignment(HPos.CENTER);
        Label[] labels = {nameLabel,firstToken,common,personal,adjacent,tot};
        detailsGrid.getColumnConstraints().add(columnConstraints);
        detailedGridLabels.addAll(Arrays.asList(labels));
        Platform.runLater(()-> addColumn(detailsGrid,position+1, nameLabel, firstToken, common, personal, adjacent, tot));
        /*
        End token:		1pt
        Common goal 1:	8pt
        Common goal 2:	4pt
        Personal goal:	6pt
        Adjacent tiles:	12pt

        Tot:			31pt
         */
    }

    private Label createLabel(int value){
        Label label = new Label(String.valueOf(value));
        return setLabelStyle(label,15);
    }

    private Label createLabel(String text){
        Label label = new Label(text);
        return setLabelStyle(label,13);
    }

    private Label setLabelStyle(Label label, int fontSize){
        label.setAlignment(Pos.CENTER);
        String style = "-fx-font-size: "+fontSize+"; -fx-text-fill: black";
        label.setStyle(style);
        return label;
    }

    @FXML
    protected void changeViewDetail() {
        Platform.runLater(() -> {
            if (!detailsGrid.isVisible()) {
                detailsGrid.setVisible(true);
                scoreboardGrid.setVisible(false);
                detailsButton.setText("<<Details");
            } else {
                scoreboardGrid.setVisible(true);
                detailsGrid.setVisible(false);
                detailsButton.setText("Details>>");
            }
        });
    }
    @FXML
    protected void onBackArrowClick(){
        viewManager.exitRequest();
    }

    public void update() {
        LobbyModelView lobbyModelView = viewManager.getLobbyModelView();

        //order players for taken points
        List<PlayerModelView> players = viewManager.getPlayersOrderedByScores();

        int numPlayers = players.size();

        if(numPlayers == 2) {
            thirdPodiumStack.setVisible(false);
        }

        for (int i = 0; i < numPlayers; i++) {
            if(i < 3) {
                PlayerModelView player = players.get(i);
                int finalI = i;
                Platform.runLater(()->{
                    playersPodium[finalI].setText(player.getName());
                    pointsPodium[finalI].setText(String.valueOf(player.getPoints()));
                });
            }
            PlayerModelView player = players.get(i);
            addRowScoreboard(player, i);
            addColumnDetail(player, i,numPlayers);
        }

        Platform.runLater(()->lobbyNameLabel.setText(lobbyModelView.getName()));
    }

    public void reset() {
        Platform.runLater(()->{
            detailsGrid.getChildren().removeAll(detailedGridLabels);
            scoreboardGrid.getChildren().removeAll(scoreboardGridLabels);
            detailedGridLabels.clear();
            scoreboardGridLabels.clear();
        });
    }

    public void processKeyEvent(KeyEvent keyEvent) {
        switch (keyEvent.getCode()){
            case ESCAPE -> onBackArrowClick();
            case ENTER -> changeViewDetail();
        }
    }

    private void addColumn(GridPane grid, int column,Label ... labels){
        for(int i = 0; i < labels.length; i++){
            grid.add(labels[i],column,i);
        }
    }

    private void addRow(GridPane grid, int row,Label ... labels){
        for(int i = 0; i < labels.length; i++){
            grid.add(labels[i],i,row);
        }
    }
}
