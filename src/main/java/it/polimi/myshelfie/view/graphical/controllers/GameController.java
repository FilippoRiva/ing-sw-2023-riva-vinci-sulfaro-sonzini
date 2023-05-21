package it.polimi.myshelfie.view.graphical.controllers;

import it.polimi.myshelfie.model.*;
import it.polimi.myshelfie.model.modelviews.CommonGoalModelView;
import it.polimi.myshelfie.model.modelviews.FullGameModelView;
import it.polimi.myshelfie.model.modelviews.PlayerModelView;
import it.polimi.myshelfie.view.ViewBuffer;
import it.polimi.myshelfie.view.graphical.BookshelfView;
import it.polimi.myshelfie.view.graphical.GraphicalUI;
import it.polimi.myshelfie.view.graphical.Images;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

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
    public Label takenTilesLabel;
    public ImageView token1Image;
    public ImageView token2Image;
    public ImageView takenTile1Image;
    public ImageView takenTile2Image;
    public ImageView takenTile3Image;
    ImageView[] takenTilesImages;
    public HBox insertBox;
    public Button insertButton1;
    public Button insertButton2;
    public Button insertButton3;
    public Button insertButton4;
    public Button insertButton5;
    Button[] insertButtons;
    public Label descriptionLabel;
    public Button button;

    ViewBuffer viewBuffer = ViewBuffer.getInstance();
    String playerName = GraphicalUI.getPlayerName();
    int numPlayers = viewBuffer.getLobbyModelView().getGameConfig().getNumPlayers();
    CommonGoalModelView[] commonGoals = new CommonGoalModelView[viewBuffer.getLobbyModelView().getGameConfig().getNumCommonGoal()];
    BookshelfView[] bookshelves = new BookshelfView[numPlayers];
    Image[][] takenTokens = new Image[2][numPlayers];
    int remainingTiles;

    int seeingPlayer = 0;
    List<Position> selectedTiles;
    List<Integer> orderedTiles;
    int chosenColumn;

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
            seeingPlayer++;
        }

        takenTilesImages = new ImageView[]{takenTile1Image, takenTile2Image, takenTile3Image};
        insertButtons = new Button[]{insertButton1, insertButton2, insertButton3, insertButton4, insertButton5};
        remainingTiles = fullGameModelView.getRemainingTiles();
        displayLivingRoom();
        initBookshelves();
    }
    private void initBookshelves(){
        int i = 0;
        for(PlayerModelView pl : viewBuffer.getGameModelView().getPlayers()){
            bookshelves[i] = new BookshelfView(pl.getName(), null);
            for(int j = 0 ; j<2 ; j++){
                takenTokens[j][i] = null;
            }
            i++;
        }
        for(i = 0; i<5; i++){
            int finalI = i;
            insertButtons[i].setOnMouseClicked(mouseEvent -> {
                chosenColumn = finalI;
                for(int k = 0; k<5; k++){
                    if(k == finalI) insertButtons[k].setEffect(new Glow());
                    else insertButtons[k].setEffect(null);
                }
            });
        }
    }

    public void play(){
        FullGameModelView fullGameModelView = viewBuffer.getGameModelView();

        //clean takenTilesImage
        for(ImageView imageView : takenTilesImages){
            imageView.setImage(null);
            imageView.setEffect(null);
        }

        if(!fullGameModelView.getCurrentPlayerName().equals(playerName)) {
            //I'm not playing
            switch (fullGameModelView.getCurrentState()){
                case DRAW, LAST_DRAW -> {
                    refill(fullGameModelView);
                    updateTokens();
                }
                case ORGANIZE, LAST_ORGANIZE -> displayDraw();
                case INSERT, LAST_INSERT -> displayOrganize();
                case COUNTING_POINTS, LAST_COUNTING_POINTS -> updateInsert();
            }
        } else{
            //I'm playing
            switch (fullGameModelView.getCurrentState()){
                case FIRST_DRAW -> {
                    draw();
                }
                case DRAW, LAST_DRAW -> {
                    refill(fullGameModelView);
                    updateTokens();
                    draw();
                }
                case ORGANIZE, LAST_ORGANIZE -> {
                    displayDraw();
                    organize();
                }
                case INSERT, LAST_INSERT -> {
                    displayOrganize();
                    insert();
                }
                case COUNTING_POINTS, LAST_COUNTING_POINTS -> {
                    updateInsert();
                    insertBox.setVisible(false);
                    for(Button button : insertButtons){
                        button.setEffect(null);
                    }
                    button.setVisible(false);
                    takenTilesLabel.setVisible(true);
                }
            }
        }
    }
    private void refill(FullGameModelView fullGameModelView){
        if(fullGameModelView.getRemainingTiles() != remainingTiles){
            displayLivingRoom();
            remainingTiles = fullGameModelView.getRemainingTiles();
        }
    }
    private void draw() {
        Platform.runLater(() -> button.setText("Draw"));
        button.setVisible(true);
        takenTilesLabel.setVisible(false);
        selectedTiles = new ArrayList<>();
    }
    private void organize() {
        orderedTiles = new ArrayList<>();

        //skip organize if you took one tiles
        if(selectedTiles.size() == 1) viewBuffer.organizeRequest(viewBuffer.getGameModelView().getCurrentPlayer().getTakenTiles());
        else Platform.runLater(() -> button.setText("Organize"));
    }
    private void insert() {
        Platform.runLater(() -> button.setText("Insert"));
        insertBox.setVisible(true);
        insertBox.setVisible(playerName.equals(bookshelves[seeingPlayer].getPlayerName()));
        chosenColumn = -1;
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
                        GameState currentState = viewBuffer.getGameModelView().getCurrentState();
                        if((currentState == GameState.FIRST_DRAW || currentState == GameState.DRAW || currentState == GameState.LAST_DRAW)
                                && viewBuffer.getGameModelView().getCurrentPlayerName().equals(playerName)) {
                            Position position = new Position(finalI, finalJ);
                            if (selectedTiles.contains(position)) {
                                image.setEffect(null);
                                deselectTile(position);
                            } else if (selectedTiles.size() < 3) {
                                image.setEffect(new Glow());
                                selectTile(position, itemTile);
                            }
                        }
                    });

                    Platform.runLater(() -> livingRoomGrid.add(image, finalI, finalJ));
                }
            }
        }
    }
    private void deselectTile(Position position) {
        takenTilesImages[selectedTiles.indexOf(position)].setImage(null);
        takenTilesImages[selectedTiles.indexOf(position)].setOnMouseClicked(null);
        selectedTiles.remove(position);

        //scroll to left
        for(int k = 0 ; k<2; k++){
            if(takenTilesImages[k].getImage() == null && takenTilesImages[k+1].getImage() != null){
                takenTilesImages[k].setImage(takenTilesImages[k+1].getImage());
                takenTilesImages[k].setOnMouseClicked(takenTilesImages[k+1].getOnMouseClicked());

                takenTilesImages[k+1].setImage(null);
                takenTilesImages[k+1].setOnMouseClicked(null);
            }
        }
    }
    private void selectTile(Position position, ItemTile itemTile){
        selectedTiles.add(position);

        int index = selectedTiles.size()-1;
        takenTilesImages[index].setImage(getTileImage(itemTile));
        takenTilesImages[index].setOnMouseClicked(mouseEvent -> {
            GameState currentState = viewBuffer.getGameModelView().getCurrentState();
            if((currentState == GameState.ORGANIZE || currentState == GameState.LAST_ORGANIZE) &&
                    viewBuffer.getGameModelView().getCurrentPlayerName().equals(playerName)) {
                if (!orderedTiles.contains(index)) {
                    takenTilesImages[index].setEffect(new Glow());
                    orderedTiles.add(index);
                }else if (orderedTiles.indexOf(index) == orderedTiles.size()-1) {
                    takenTilesImages[index].setEffect(null);
                    orderedTiles.remove((Integer) index);
                }
            }
        });
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

    private void updateInsert() {
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
                            //image.setFitHeight(0);
                            image.setFitWidth(50);
                            image.setEffect(new DropShadow());

                            //col, row
                            newBookshelfGrid.add(image, j, i);
                        }
                    }
                }
                bookshelfView.setBookshelfGrid(newBookshelfGrid);
                if (seeingPlayer == k) displayBookshelf();

                return;
            }
            k++;
        }
    }
    private void displayBookshelf(){
        Platform.runLater(() -> {
            bookshelfPane.getChildren().clear();
            if(bookshelves[seeingPlayer].getBookshelfGrid() != null)
                bookshelfPane.getChildren().add(bookshelves[seeingPlayer].getBookshelfGrid());
            String player = bookshelves[seeingPlayer].getPlayerName();
            playerBookshelfLabel.setText(player);
            personalGoalImage.setVisible(playerName.equals(player));
            insertBox.setVisible(playerName.equals(player));
        });
    }
    private GridPane createBookshelfGrid(){
        GridPane bookshelfGrid = new GridPane();
        bookshelfGrid.setPrefWidth(323);
        bookshelfGrid.setPrefHeight(342);
        bookshelfGrid.setHgap(17);
        bookshelfGrid.setVgap(10);
        bookshelfGrid.setGridLinesVisible(true);

        for(int i = 0 ; i < 5 ; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPrefWidth(100);
            columnConstraints.setMaxWidth(100);
            columnConstraints.setHgrow(Priority.NEVER);
            bookshelfGrid.getColumnConstraints().add(columnConstraints);
        }

        for(int i = 0 ; i < 6 ; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPrefHeight(100);
            rowConstraints.setMaxHeight(100);
            rowConstraints.setVgrow(Priority.NEVER);
            bookshelfGrid.getRowConstraints().add(rowConstraints);
        }

        return bookshelfGrid;
    }

    private void updateTokens(){
        FullGameModelView fullGameModelView = viewBuffer.getGameModelView();
        int previousPlayer = fullGameModelView.getPlayers().indexOf(fullGameModelView.getCurrentPlayer())-1;
        if(previousPlayer == -1) previousPlayer = fullGameModelView.getPlayers().size()-1;

        for(int i = 0 ; i<2; i++){
            List<Token> oldTokens = commonGoals[i].getTokens();
            if(oldTokens.size() > fullGameModelView.getCommonGoals().get(i).getTokens().size()) {
                Image imageNew = null;
                if(oldTokens.size()>1) imageNew = getImage("tokens/"+oldTokens.get(oldTokens.size()-2).getValue()+".jpg");
                Image imageTaken = getImage("tokens/"+oldTokens.get(oldTokens.size()-1).getValue()+".jpg");
                takenTokens[i][previousPlayer] = imageTaken;
                if(i==0) token1Image.setImage(imageNew);
                else token2Image.setImage(imageNew);
            }
            commonGoals[i] = fullGameModelView.getCommonGoals().get(i);
        }

        if(previousPlayer == seeingPlayer) displayTokens();


        //TODO show something good to let all players know
    }
    private void displayTokens(){
        takenToken1Image.setImage(takenTokens[0][seeingPlayer]);
        takenToken2Image.setImage(takenTokens[1][seeingPlayer]);
    }

    @FXML
    protected void onButtonMouseClicked(MouseEvent mouseEvent) {
        switch (viewBuffer.getGameModelView().getCurrentState()){
            case FIRST_DRAW, DRAW, LAST_DRAW ->
                    viewBuffer.drawRequest(selectedTiles.stream().map(x -> x.centered(viewBuffer.getGameModelView().getGameConfig().getLivingRoomSize())).collect(Collectors.toCollection(ArrayList::new)));
            case ORGANIZE, LAST_ORGANIZE ->
                    viewBuffer.organizeRequest(orderedTiles.isEmpty() ? viewBuffer.getGameModelView().getCurrentPlayer().getTakenTiles() :
                            orderedTiles.stream().map(viewBuffer.getGameModelView().getCurrentPlayer().getTakenTiles()::get).toList());
            case INSERT, LAST_INSERT -> {
                if(chosenColumn == -1) displayError("choose a column");
                else viewBuffer.insertRequest(chosenColumn);
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
    protected void prevPlayer(MouseEvent mouseEvent) {
        if(seeingPlayer ==0) seeingPlayer = numPlayers - 1;
        else seeingPlayer--;
        displayBookshelf();
        displayTokens();
    }
    @FXML
    protected void nextPlayer(MouseEvent mouseEvent) {
        if(seeingPlayer ==numPlayers-1) seeingPlayer = 0;
        else seeingPlayer++;
        displayBookshelf();
        displayTokens();
    }
    @Override
    public void onBackArrowClick() {

    }
}
