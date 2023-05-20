package it.polimi.myshelfie.view.graphical.controllers;

import it.polimi.myshelfie.model.GameState;
import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.ItemTileType;
import it.polimi.myshelfie.model.Position;
import it.polimi.myshelfie.model.modelviews.*;
import it.polimi.myshelfie.view.ViewBuffer;
import it.polimi.myshelfie.view.graphical.BookshelfView;
import it.polimi.myshelfie.view.graphical.GraphicalUI;
import it.polimi.myshelfie.view.graphical.Images;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static it.polimi.myshelfie.view.graphical.Images.getImage;
import static it.polimi.myshelfie.view.graphical.Images.getTileImage;

public class GameController extends ViewController implements Initializable {
    public GridPane livingRoomGrid;
    public Pane bookshelfPane;
    public Label playerBookshelfLabel;
    public ImageView lastTokenImage;
    public ImageView personalGoalImage;
    public ImageView takenToken1Image;
    public ImageView takenToken2Image;
    public ImageView commonGoal1Image;
    public ImageView commonGoal2Image;
    public ImageView token1Image;
    public ImageView token2Image;
    public ImageView takenTile1Image;
    public ImageView takenTile2Image;
    public ImageView takenTile3Image;
    ImageView[] takenTilesImages;
    public Label descriptionLabel;
    public AnchorPane button;
    public Label buttonLabel;

    ViewBuffer viewBuffer = ViewBuffer.getInstance();
    String playerName = GraphicalUI.getPlayerName();
    int numPlayers = viewBuffer.getLobbyModelView().getGameConfig().getNumPlayers();
    CommonGoalModelView[] commonGoals = new CommonGoalModelView[viewBuffer.getLobbyModelView().getGameConfig().getNumCommonGoal()];
    BookshelfView[] bookshelves = new BookshelfView[numPlayers];

    int seeingBookshelf = 0;
    List<Position> selectedTiles;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FullGameModelView fullGameModelView = viewBuffer.getGameModelView();

        int idPersonalGoal = fullGameModelView.getPlayer(playerName).getPersonalGoals().get(0).getId();
        commonGoals[0] = fullGameModelView.getCommonGoals().get(0);
        commonGoals[1] = fullGameModelView.getCommonGoals().get(1);

        personalGoalImage.setImage(getImage("personalGoals/Personal_Goals"+idPersonalGoal+".png"));
        commonGoal1Image.setImage(getImage("commonGoals/"+(commonGoals[0].getId()+1)+".jpg"));
        commonGoal2Image.setImage(getImage("commonGoals/"+(commonGoals[1].getId()+1)+".jpg"));
        token1Image.setImage(getImage("tokens/"+commonGoals[0].getTokens().get(commonGoals[0].getTokens().size()-1).getValue()+".jpg"));
        token2Image.setImage(getImage("tokens/"+commonGoals[1].getTokens().get(commonGoals[1].getTokens().size()-1).getValue()+".jpg"));
        playerBookshelfLabel.setText(playerName);

        for(PlayerModelView pl : viewBuffer.getGameModelView().getPlayers()){
            if(pl.getName().equals(playerName)) break;
            seeingBookshelf++;
        }

        takenTilesImages = new ImageView[]{takenTile1Image, takenTile2Image, takenTile3Image};
        displayLivingRoom();
        initBookshelves();
    }
    private void initBookshelves(){
        int i = 0;
        for(PlayerModelView pl : viewBuffer.getGameModelView().getPlayers()){
            bookshelves[i] = new BookshelfView(pl.getName(), null);
            i++;
        }
    }

    public void play(){
        FullGameModelView fullGameModelView = viewBuffer.getGameModelView();

        //clean takenTilesImage
        for(ImageView imageView : takenTilesImages){
            imageView.setImage(null);
        }

        if(!fullGameModelView.getCurrentPlayerName().equals(playerName)) {
            //I'm not playing
            switch (fullGameModelView.getCurrentState()){
                case FIRST_DRAW, DRAW, LAST_DRAW -> button.setVisible(false);
                case ORGANIZE, LAST_ORGANIZE -> displayDraw();
                case INSERT, LAST_INSERT -> displayOrganize();
                case COUNTING_POINTS -> displayInsert();
            }
        } else{
            //I'm playing
            switch (fullGameModelView.getCurrentState()){
                case FIRST_DRAW, DRAW, LAST_DRAW ->
                        draw();
                case ORGANIZE, LAST_ORGANIZE -> {
                    displayDraw();
                    organize();
                }
                case INSERT, LAST_INSERT -> {
                    displayOrganize();
                    insert();
                }
                case COUNTING_POINTS ->
                        displayInsert();
            }
        }
    }
    private void draw() {
        Platform.runLater(() -> buttonLabel.setText("Draw"));
        button.setVisible(true);
        selectedTiles = new ArrayList<>();
    }
    private void organize() {
        Platform.runLater(() -> buttonLabel.setText("Organize"));
    }
    private void insert() {
        Platform.runLater(() -> buttonLabel.setText("Insert"));
    }

    private void displayDraw() {
        displayLivingRoom();
        displayTakenTiles();
    }
    private void displayLivingRoom() {
        Platform.runLater(() -> livingRoomGrid.getChildren().clear());

        FullGameModelView fullGameModelView = viewBuffer.getGameModelView();
        ItemTile[][] livingRoom = fullGameModelView.getLivingRoom();
        for(int i = 0 ; i < livingRoom.length ; i++){
            for(int j = 0 ; j < livingRoom.length ; j++){
                ItemTile itemTile = livingRoom[i][j];
                if(itemTile != null && itemTile.getType() != ItemTileType.EMPTY){
                    ImageView image = new ImageView();
                    image.setImage(Images.getTileImage(itemTile));
                    image.setPreserveRatio(true);
                    image.setFitHeight(0);
                    image.setFitWidth(56);
                    image.setEffect(new DropShadow());

                    int finalI = i;
                    int finalJ = j;
                    image.setOnMouseClicked(mouseEvent -> {
                        if(viewBuffer.getGameModelView().getCurrentState() == GameState.DRAW) {
                            Position position = new Position(finalI, finalJ);
                            if (selectedTiles.contains(position)) {
                                takenTilesImages[selectedTiles.indexOf(position)].setImage(null);
                                selectedTiles.remove(position);
                                image.setEffect(null);
                                for(int k = 0 ; k<2; k++){
                                    if(takenTilesImages[k].getImage() == null){
                                        takenTilesImages[k].setImage(takenTilesImages[k+1].getImage());
                                        takenTilesImages[k+1].setImage(null);
                                    }
                                }
                            } else if (selectedTiles.size() < 3) {
                                selectedTiles.add(position);
                                takenTilesImages[selectedTiles.size()-1].setImage(getTileImage(itemTile));
                                image.setEffect(new Glow());
                            }
                        }
                    });

                    Platform.runLater(() -> livingRoomGrid.add(image, finalI, finalJ));
                }
            }
        }
    }

    private void displayOrganize() {
        displayTakenTiles();
    }
    private void displayTakenTiles() {
        List<ItemTile> takenTiles = viewBuffer.getGameModelView().getCurrentPlayer().getTakenTiles();
        for(int i = 0 ; i < takenTiles.size(); i++){
            takenTilesImages[i].setImage(getTileImage(takenTiles.get(i)));
        }
    }

    private void displayInsert() {
        FullGameModelView fullGameModelView = viewBuffer.getGameModelView();
        String currentPlayerName = fullGameModelView.getCurrentPlayerName();

        int k = 0;
        for (BookshelfView bookshelfView : bookshelves) {
            if (bookshelfView.getPlayerName().equals(currentPlayerName)) {
                GridPane newBookshelfGrid = createBookshelfGrid();

                ItemTile[][] bookshelf = fullGameModelView.getCurrentPlayer().getBookshelf();
                for (int i = 0; i < bookshelf.length; i++) {
                    for (int j = 0; j < bookshelf[0].length; j++) {
                        ItemTile itemTile = bookshelf[i][j];
                        if (itemTile != null && itemTile.getType() != ItemTileType.EMPTY) {
                            ImageView image = new ImageView();
                            image.setImage(Images.getTileImage(itemTile));
                            image.setPreserveRatio(true);
                            image.setFitHeight(0);
                            image.setFitWidth(60);
                            image.setEffect(new DropShadow());

                            //col, row
                            newBookshelfGrid.add(image, j, i);
                        }
                    }
                }
                bookshelfView.setBookshelfGrid(newBookshelfGrid);
                if (seeingBookshelf == k) displayBookshelf();

                return;
            }
            k++;
        }
    }
    private void displayBookshelf(){
        Platform.runLater(() -> {
            bookshelfPane.getChildren().clear();
            if(bookshelves[seeingBookshelf].getBookshelfGrid() != null)
                bookshelfPane.getChildren().add(bookshelves[seeingBookshelf].getBookshelfGrid());
            playerBookshelfLabel.setText(bookshelves[seeingBookshelf].getPlayerName());
        });
    }
    private GridPane createBookshelfGrid(){
        GridPane bookshelfGrid = new GridPane();
        bookshelfGrid.setPrefWidth(335);
        bookshelfGrid.setPrefHeight(355);

        for(int i = 0 ; i < 5 ; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPrefWidth(100);
            bookshelfGrid.getColumnConstraints().add(columnConstraints);

            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPrefHeight(100);
            bookshelfGrid.getRowConstraints().add(rowConstraints);
        }
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setPrefHeight(100);
        bookshelfGrid.getRowConstraints().add(rowConstraints);

        bookshelfGrid.setGridLinesVisible(true);

        return bookshelfGrid;
    }

    @FXML
    protected void onButtonMouseClicked(MouseEvent mouseEvent) {
        switch (viewBuffer.getGameModelView().getCurrentState()){
            case FIRST_DRAW, DRAW, LAST_DRAW ->
                    viewBuffer.drawRequest(selectedTiles.stream().map(x -> x.centered(viewBuffer.getGameModelView().getGameConfig().getLivingRoomSize())).collect(Collectors.toCollection(ArrayList::new)));
            case ORGANIZE, LAST_ORGANIZE -> {
                List<Integer> integers = new ArrayList<>();
                integers.add(0);
                viewBuffer.organizeRequest(integers.stream().map(viewBuffer.getGameModelView().getCurrentPlayer().getTakenTiles()::get).toList());
            }
            case INSERT, LAST_INSERT -> {
                viewBuffer.insertRequest(3);
            }
        }
    }
    @FXML
    protected void showDescription1(MouseEvent mouseEvent) {
        Platform.runLater(() -> descriptionLabel.setText(commonGoals[0].getDescription()));
        descriptionLabel.setVisible(true);
    }
    @FXML
    protected void showDescription2(MouseEvent mouseEvent) {
        Platform.runLater(() -> descriptionLabel.setText(commonGoals[1].getDescription()));
        descriptionLabel.setVisible(true);
    }
    @FXML
    protected void hideDescription(MouseEvent mouseEvent) {
        descriptionLabel.setVisible(false);
    }
    @FXML
    protected void onButtonMouseEntered(MouseEvent mouseEvent) {
        buttonLabel.setTextFill(Color.color(1, 0, 0));
    }
    @FXML
    protected void onButtonMouseExited(MouseEvent mouseEvent) {
        buttonLabel.setTextFill(Color.color(1, 1, 1));
    }
    @FXML
    protected void prevBookshelf(MouseEvent mouseEvent) {
        if(seeingBookshelf==0) seeingBookshelf = numPlayers - 1;
        else seeingBookshelf--;
        displayBookshelf();
    }
    @FXML
    protected void nextBookshelf(MouseEvent mouseEvent) {
        if(seeingBookshelf==numPlayers-1) seeingBookshelf = 0;
        else seeingBookshelf++;
        displayBookshelf();
    }
    @Override
    public void onBackArrowClick() {
        super.onBackArrowClick();
        viewBuffer.exitRequest();
    }
}
