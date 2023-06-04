package it.polimi.myshelfie.view.graphical;

import it.polimi.myshelfie.model.*;
import it.polimi.myshelfie.model.modelviews.PlayerModelView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EndGameControllerTest implements Initializable {
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
    Label[] playersPodium;
    @FXML
    protected Label points1;
    @FXML
    protected Label points2;
    @FXML
    protected Label points3;
    Label[] pointsPodium;
    @FXML
    protected Button detailsButton;

    private int numPlayers;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playersPodium = new Label[]{player1, player2, player3};
        pointsPodium = new Label[]{points1, points2, points3};

        lobbyNameLabel.setText("Giocatori belli");

        List<PlayerModelView> players = new ArrayList<>();

        Player pippo = new Player("Antonioo");
        pippo.addObtainedToken(Token.EIGHT_SECOND);
        pippo.addObtainedToken(Token.SIX_FIRST);
        pippo.addObtainedToken(Token.ENDGAME);
        pippo.setPersonalGoals(new ArrayList<>());
        pippo.setBookshelf(new Bookshelf(new Dimension()));
        pippo.setTakenTiles(new ArrayList<>());
        PlayerModelView p = new PlayerModelView(pippo);
        ArrayList<EarnedPoint> earnedPoints = new ArrayList<>();
        earnedPoints.add(new EarnedPoint(PointType.ADJACENT, 12));
        earnedPoints.add(new EarnedPoint(PointType.PERSONAL, 6));
        earnedPoints.add(new EarnedPoint(PointType.COMMON, 8));
        earnedPoints.add(new EarnedPoint(PointType.COMMON, 6));
        earnedPoints.add(new EarnedPoint(PointType.COMMON, 1));
        p.setEarnedPoints(earnedPoints);
        players.add(p);

        Player pluto = new Player("Plutoplutopluto");
//        pluto.addObtainedToken(Token.TWO_SECOND);
//        pluto.addObtainedToken(Token.FOUR_FIRST);
        pluto.setPersonalGoals(new ArrayList<>());
        pluto.setBookshelf(new Bookshelf(new Dimension()));
        pluto.setTakenTiles(new ArrayList<>());
        p = new PlayerModelView(pluto);
        earnedPoints = new ArrayList<>();
        earnedPoints.add(new EarnedPoint(PointType.ADJACENT, 12));
        earnedPoints.add(new EarnedPoint(PointType.PERSONAL, 6));
        p.setEarnedPoints(earnedPoints);
        players.add(p);

        Player minnie = new Player("MinnieMinnieMinnie");
//        minnie.addObtainedToken(Token.TWO_SECOND);
//        minnie.addObtainedToken(Token.FOUR_FIRST);
        minnie.setPersonalGoals(new ArrayList<>());
        minnie.setBookshelf(new Bookshelf(new Dimension()));
        minnie.setTakenTiles(new ArrayList<>());
        p = new PlayerModelView(minnie);
        earnedPoints = new ArrayList<>();
        earnedPoints.add(new EarnedPoint(PointType.ADJACENT, 0));
        earnedPoints.add(new EarnedPoint(PointType.PERSONAL, 14));
        p.setEarnedPoints(earnedPoints);
        players.add(p);

        Player paperino = new Player("P");
        paperino.addObtainedToken(Token.TWO_SECOND);
//        paperino.addObtainedToken(Token.FOUR_FIRST);
        paperino.setPersonalGoals(new ArrayList<>());
        paperino.setBookshelf(new Bookshelf(new Dimension()));
        paperino.setTakenTiles(new ArrayList<>());
        p = new PlayerModelView(paperino);
        earnedPoints = new ArrayList<>();
        earnedPoints.add(new EarnedPoint(PointType.ADJACENT, 23));
        earnedPoints.add(new EarnedPoint(PointType.PERSONAL, 6));
        earnedPoints.add(new EarnedPoint(PointType.COMMON, 2));
        p.setEarnedPoints(earnedPoints);
        players.add(p);

        players = players.stream().sorted((x,y)->{
            if(x.getPoints()==y.getPoints()) return 0;
            else return x.getPoints()>=y.getPoints() ? -1:1 ;
        }).toList();

        numPlayers = players.size();
        for (int i = 0; i < numPlayers; i++) {
            if(i < 3) {
                PlayerModelView player = players.get(i);
                playersPodium[i].setText(player.getName());
                pointsPodium[i].setText(String.valueOf(player.getPoints()));
            }
            PlayerModelView player = players.get(i);
            addRowScoreboard(player, i);
            addColumnDetail(player, i);
        }
    }

    private void addRowScoreboard(PlayerModelView player, int position){
        Label nameLabel = new Label((position+1)+". "+player.getName());
        nameLabel.setStyle("-fx-font-size: 25");
        Label pointsLabel = new Label(String.valueOf(player.getPoints()));
        pointsLabel.setStyle("-fx-font-size: 25");

        scoreboardGrid.addRow(position, nameLabel, pointsLabel);
    }

    private void addColumnDetail(PlayerModelView player, int position){
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

        //TODO takes points for personal goal and adjacent tiles
        Label personal = createLabel(earnedPoints.stream().filter(x->x.getType()==PointType.PERSONAL).mapToInt(EarnedPoint::getValue).sum());
        Label adjacent = createLabel(earnedPoints.stream().filter(x->x.getType()==PointType.ADJACENT).toList().get(0).getValue());

        Label tot = createLabel(player.getPoints());
        tot.setStyle("-fx-font-weight: bold; -fx-font-family: 'Baskerville Old Face'; -fx-font-size: 22");

        ColumnConstraints columnConstraints = new ColumnConstraints();
        //columnConstraints.setPercentWidth((double) (683-200) /numPlayers);
        columnConstraints.setPrefWidth((double) (683 - 200) /numPlayers);
        columnConstraints.setHalignment(HPos.CENTER);
        //columnConstraints.setHgrow(Priority.NEVER);
        detailsGrid.getColumnConstraints().add(columnConstraints);
        detailsGrid.addColumn(position+1, nameLabel, firstToken, common, personal, adjacent, tot);
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
    protected void changeViewDetail(MouseEvent mouseEvent) {
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
}
