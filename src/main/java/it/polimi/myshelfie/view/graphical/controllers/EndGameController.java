package it.polimi.myshelfie.view.graphical.controllers;

import it.polimi.myshelfie.model.EarnedPoint;
import it.polimi.myshelfie.model.PointType;
import it.polimi.myshelfie.model.Token;
import it.polimi.myshelfie.model.modelviews.FullGameModelView;
import it.polimi.myshelfie.model.modelviews.LobbyModelView;
import it.polimi.myshelfie.model.modelviews.PlayerModelView;
import it.polimi.myshelfie.view.InternalViewState;
import it.polimi.myshelfie.view.ViewManager;
import it.polimi.myshelfie.view.graphical.GraphicalUI;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EndGameController implements Initializable {
    @FXML
    protected Label lobbyNameLabel;
    @FXML
    protected GridPane detailsGrid;
    @FXML
    protected GridPane scoreboardGrid;
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
    Label[] pointsPodium;
    Label[] playersPodium;
    ViewManager viewManager = ViewManager.getInstance();
    private int numPlayers;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LobbyModelView lobbyModelView = viewManager.getLobbyModelView();
        FullGameModelView fullGameModelView = viewManager.getGameModelView();
    }

    public void setKeyListeners(Scene scene) {
        scene.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()){
                case ESCAPE -> onBackArrowClick();
                case ENTER -> changeViewDetail();
            }
        });
    }

    private void addRowScoreboard(PlayerModelView player, int position){
        Label nameLabel = new Label((position+1)+". "+player.getName());
        nameLabel.setStyle("-fx-font-size: 25");
        Label pointsLabel = new Label(String.valueOf(player.getPoints()));
        pointsLabel.setStyle("-fx-font-size: 25");

        Platform.runLater(()->scoreboardGrid.addRow(position, nameLabel, pointsLabel));
    }

    private void addColumnDetail(PlayerModelView player, int position){
        List<Token> takenTokens = player.getTokens();
        List<EarnedPoint> earnedPoints= player.getEarnedPoints();
        List<Integer> tokenRomanNumeral = takenTokens.stream().map(Token::getRomanNumeral).toList();


        Label nameLabel = new Label(player.getName());
        //TODO style for the nameLabel
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
        detailsGrid.getColumnConstraints().add(columnConstraints);
        Platform.runLater(()->detailsGrid.addColumn(position+1, nameLabel, firstToken, common, personal, adjacent, tot));
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
        label.setAlignment(Pos.CENTER);
        label.setStyle("-fx-font-size: 10");

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
        GraphicalUI.mainViewController.changeView(InternalViewState.INIT);
    }

    public void update() {
        LobbyModelView lobbyModelView = viewManager.getLobbyModelView();
        FullGameModelView fullGameModelView = viewManager.getGameModelView();
        playersPodium = new Label[]{player1, player2, player3};
        pointsPodium = new Label[]{points1, points2, points3};

        //order players for taken points
        List<PlayerModelView> players = fullGameModelView.getPlayers().stream().sorted((x,y)->{
            if(x.getPoints()==y.getPoints()) return 0;
            else return x.getPoints()>=y.getPoints() ? -1:1 ;
        }).toList();

        numPlayers = players.size();

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
            addColumnDetail(player, i);
        }

        Platform.runLater(()->lobbyNameLabel.setText(lobbyModelView.getName()));
    }
}
