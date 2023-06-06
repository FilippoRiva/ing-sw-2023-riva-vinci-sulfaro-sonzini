package it.polimi.myshelfie.view.graphical.controllers;

import it.polimi.myshelfie.GameConfig;
import it.polimi.myshelfie.model.*;
import it.polimi.myshelfie.model.modelviews.CommonGoalModelView;
import it.polimi.myshelfie.model.modelviews.FullGameModelView;
import it.polimi.myshelfie.model.modelviews.PlayerModelView;
import it.polimi.myshelfie.view.ViewManager;
import it.polimi.myshelfie.view.ViewType;
import it.polimi.myshelfie.view.graphical.BookshelfView;
import it.polimi.myshelfie.view.graphical.Images;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static it.polimi.myshelfie.view.graphical.Images.getImage;
import static it.polimi.myshelfie.view.graphical.Images.getTileImage;

public class GameController {
    @FXML
    protected GridPane livingRoomGrid;
    @FXML
    protected Pane bookshelfPane;
    @FXML
    protected Label playerBookshelfLabel;
    @FXML
    protected ImageView lastTokenImage;
    @FXML
    protected ImageView personalGoalImage;
    private Image myPersonalGoal;
    @FXML
    protected ImageView takenToken1Image, takenToken2Image;
    @FXML
    protected ImageView commonGoal1Image, commonGoal2Image;
    @FXML
    protected Label playingLabel;
    @FXML
    protected ImageView token1Image, token2Image;
    @FXML
    protected ImageView takenTile1Image, takenTile2Image, takenTile3Image;
    ImageView[] takenTilesImages;
    @FXML
    protected HBox insertBox;
    @FXML
    protected Button insertButton1, insertButton2, insertButton3, insertButton4, insertButton5;
    Button[] insertButtons;
    @FXML
    protected Label descriptionLabel;
    @FXML
    protected Button button;
    @FXML
    protected Button crashButton;
    @FXML
    protected Label crashLabel;
    @FXML
    protected Pane crashPane;
    @FXML
    protected AnchorPane endGame;
    @FXML
    protected EndGameController endGameController;

    ViewManager viewManager = ViewManager.getInstance();
    String myName;
    int numPlayers;
    CommonGoalModelView[] commonGoals;
    BookshelfView[] bookshelves;
    Image[][] takenTokens;
    int remainingTiles;

    int seeingPlayer = 0;
    List<Position> selectedTiles = new ArrayList<>();
    List<Integer> orderedTiles;
    int chosenColumn;

    public void gameStart() {
        endGame.setVisible(false);
        endGame.setMouseTransparent(true);
        FullGameModelView fullGameModelView = viewManager.getGameModelView();
        myName = ViewManager.getUsername();
        //setting auto player
        if(fullGameModelView.getRoomName().equals("test") && myName.startsWith("bot")){
            ViewManager.getInstance().startAutoPlayer();
        }

        //
        GameConfig config = fullGameModelView.getGameConfig();
        commonGoals = new CommonGoalModelView[config.getNumCommonGoal()];
        numPlayers = config.getNumPlayers();
        bookshelves = new BookshelfView[numPlayers];
        takenTokens = new Image[2][numPlayers];

        int idPersonalGoal = fullGameModelView.getPlayer(myName).getPersonalGoals().get(0).getId();
        commonGoals[0] = fullGameModelView.getCommonGoals().get(0);
        commonGoals[1] = fullGameModelView.getCommonGoals().get(1);

        myPersonalGoal = getImage("game/personalGoals/Personal_Goals"+idPersonalGoal+".png");
        personalGoalImage.setImage(myPersonalGoal);
        commonGoal1Image.setImage(getImage("game/commonGoals/"+(commonGoals[0].getId()+1)+".jpg"));
        commonGoal2Image.setImage(getImage("game/commonGoals/"+(commonGoals[1].getId()+1)+".jpg"));
        token1Image.setImage(getImage("game/tokens/"+commonGoals[0].getTokens().get(commonGoals[0].getTokens().size()-1).getValue()+".jpg"));
        token2Image.setImage(getImage("game/tokens/"+commonGoals[1].getTokens().get(commonGoals[1].getTokens().size()-1).getValue()+".jpg"));

        for(PlayerModelView pl : viewManager.getGameModelView().getPlayers()){
            if(pl.getName().equals(myName)) break;
            seeingPlayer++;
        }

        takenTilesImages = new ImageView[]{takenTile1Image, takenTile2Image, takenTile3Image};
        insertButtons = new Button[]{insertButton1, insertButton2, insertButton3, insertButton4, insertButton5};
        remainingTiles = fullGameModelView.getRemainingTiles();

        Platform.runLater(()->{
            playerBookshelfLabel.setText(myName);
            playingLabel.setText("Playing " + fullGameModelView.getCurrentPlayerName());
        });
        displayLivingRoom();
        initPlayers();
        play();
    }

    public void setKeyListeners(Scene scene) {
        scene.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()){
                /*
                case ESCAPE -> {
                    if(chatIsOpen) setChatInvisible();
                }
                case ENTER -> {
                    if(chatIsOpen) sendMessage();
                }
                case C -> {
                    if(chatIsOpen) setChatInvisible();
                    else setChatVisible();
                }
                 */
            }
        });
    }

    @FXML
    public void setChatVisible(){

    }

    @FXML
    public void setChatInvisible(){

    }

    @FXML
    public void sendMessage(){

    }

    private void initPlayers(){
        int i = 0;
        for(PlayerModelView pl : viewManager.getGameModelView().getPlayers()){
            bookshelves[i] = new BookshelfView(pl.getName(), null);
            for(int j = 0 ; j<2 ; j++){
                takenTokens[j][i] = Images.emptyToken;
            }
            i++;
        }
        for(i = 0; i<5; i++){
            int finalI = i;
            insertButtons[i].setOnMouseClicked(mouseEvent -> {
                chosenColumn = finalI;
                for(int k = 0; k<5; k++){
                    int finalK = k;
                    Platform.runLater(() -> {
                        if (finalK == finalI) insertButtons[finalK].setEffect(new Glow());
                        else insertButtons[finalK].setEffect(null);
                    });
                }
            });
        }
    }

    public synchronized void play(){
        FullGameModelView fullGameModelView = viewManager.getGameModelView();

        //clean takenTilesImage
        Platform.runLater(() -> {
            for (ImageView imageView : takenTilesImages) {
                imageView.setImage(null);
                imageView.setEffect(null);
            }
        });

        boolean playing = fullGameModelView.getCurrentPlayerName().equals(myName);

        //TODO when last round starts display something
        switch (fullGameModelView.getCurrentState()){
            case DRAW, LAST_DRAW -> {
                checkRefill(fullGameModelView);
                updateTokens();
                if(playing) {
                    draw();
                } else Platform.runLater(() -> playingLabel.setText("Playing " + fullGameModelView.getCurrentPlayerName()));
            }
            case ORGANIZE, LAST_ORGANIZE -> {
                displayDraw();
                if(playing) organize();
            }
            case INSERT, LAST_INSERT -> {
                displayOrganize();
                if(playing) insert();
            }
            case COUNTING_POINTS, LAST_COUNTING_POINTS -> {
                updateInsert();
                if(playing) {
                    Platform.runLater(() -> {
                        insertBox.setVisible(false);
                        for (Button button : insertButtons) {
                            button.setEffect(null);
                        }
                        button.setVisible(false);
                        playingLabel.setVisible(true);
                    });
                }
            }
        }
    }
    private void checkRefill(FullGameModelView fullGameModelView){
        if(fullGameModelView.getRemainingTiles() != remainingTiles){
            displayLivingRoom();
            remainingTiles = fullGameModelView.getRemainingTiles();
        }
    }
    private void draw() {
        Platform.runLater(() -> {
            button.setText("Draw");
            button.setVisible(true);
            playingLabel.setVisible(false);
        });

        selectedTiles = new ArrayList<>();
    }
    private void organize() {
        orderedTiles = new ArrayList<>();

        //skip organize if you took one tiles
        if(selectedTiles.size() == 1) viewManager.organizeRequest(viewManager.getGameModelView().getCurrentPlayer().getTakenTiles());
        else Platform.runLater(() -> button.setText("Organize"));
    }
    private void insert() {
        Platform.runLater(() -> {
            button.setText("Insert");
            insertBox.setVisible(myName.equals(bookshelves[seeingPlayer].getPlayerName()));
        });

        chosenColumn = -1;
    }

    private void displayDraw() {
        displayLivingRoom();
        displayTakenTiles();
    }
    private void displayLivingRoom() {
        Platform.runLater(() -> livingRoomGrid.getChildren().clear());

        FullGameModelView fullGameModelView = viewManager.getGameModelView();
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
                        GameState currentState = viewManager.getGameModelView().getCurrentState();
                        if((currentState == GameState.DRAW || currentState == GameState.LAST_DRAW)
                                && viewManager.getGameModelView().getCurrentPlayerName().equals(myName)) {
                            Position position = new Position(finalI, finalJ);
                            if (selectedTiles.contains(position)) {
                                Platform.runLater(() -> image.setEffect(new DropShadow()));
                                deselectTile(position);
                            } else if (selectedTiles.size() < 3) {
                                Platform.runLater(() -> image.setEffect(new Glow()));
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
                int finalK = k;
                Platform.runLater(() -> takenTilesImages[finalK].setImage(takenTilesImages[finalK +1].getImage()));
                takenTilesImages[k].setOnMouseClicked(takenTilesImages[k+1].getOnMouseClicked());

                Platform.runLater(() -> takenTilesImages[finalK+1].setImage(null));
                takenTilesImages[k+1].setOnMouseClicked(null);
            }
        }
    }
    private void selectTile(Position position, ItemTile itemTile){
        selectedTiles.add(position);

        int index = selectedTiles.size()-1;
        Platform.runLater(() -> takenTilesImages[index].setImage(getTileImage(itemTile)));
        takenTilesImages[index].setOnMouseClicked(mouseEvent -> {
            GameState currentState = viewManager.getGameModelView().getCurrentState();
            if((currentState == GameState.ORGANIZE || currentState == GameState.LAST_ORGANIZE) &&
                    viewManager.getGameModelView().getCurrentPlayerName().equals(myName)) {
                if (!orderedTiles.contains(index)) {
                    Platform.runLater(() -> takenTilesImages[index].setEffect(new Glow()));
                    orderedTiles.add(index);
                }else if (orderedTiles.indexOf(index) == orderedTiles.size()-1) {
                    Platform.runLater(() -> takenTilesImages[index].setEffect(null));
                    orderedTiles.remove((Integer) index);
                }
            }
        });
    }

    private void displayOrganize() {
        displayTakenTiles();
    }
    private void displayTakenTiles() {
        List<ItemTile> takenTiles = viewManager.getGameModelView().getCurrentPlayer().getTakenTiles();
        for(int i = 0 ; i < takenTiles.size(); i++){
            int finalI = i;
            Platform.runLater(() -> takenTilesImages[finalI].setImage(getTileImage(takenTiles.get(finalI))));
        }
    }

    private void updateInsert() {
        FullGameModelView fullGameModelView = viewManager.getGameModelView();
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
                            //image.setPreserveRatio(true);
                            image.setFitHeight(48.6);
                            image.setFitWidth(51);
                            image.setEffect(new DropShadow());

                            //col, row
                            newBookshelfGrid.add(image, j, i);
                        }
                    }
                }
                bookshelfView.setBookshelfGrid(newBookshelfGrid);
                if (seeingPlayer == k) displayPlayerInfo();

                return;
            }
            k++;
        }
    }
    private void displayPlayerInfo(){
        Platform.runLater(() -> {
            bookshelfPane.getChildren().clear();
            if(bookshelves[seeingPlayer].getBookshelfGrid() != null)
                bookshelfPane.getChildren().add(bookshelves[seeingPlayer].getBookshelfGrid());
            String seeingPlayerName = bookshelves[seeingPlayer].getPlayerName();
            playerBookshelfLabel.setText(seeingPlayerName);
            if(myName.equals(seeingPlayerName)) personalGoalImage.setImage(myPersonalGoal);
            else personalGoalImage.setImage(Images.backPersonalGoal);
            insertBox.setVisible(myName.equals(seeingPlayerName) &&
                    viewManager.getGameModelView().getCurrentPlayerName().equals(myName) &&
                    (viewManager.getGameModelView().getCurrentState() == GameState.INSERT ||
                            viewManager.getGameModelView().getCurrentState() == GameState.LAST_INSERT));
        });
    }
    private GridPane createBookshelfGrid(){
        GridPane bookshelfGrid = new GridPane();
        bookshelfGrid.setPrefWidth(323);
        bookshelfGrid.setPrefHeight(342);
        bookshelfGrid.setMaxWidth(323);
        bookshelfGrid.setMaxHeight(342);
        bookshelfGrid.setMinWidth(323);
        bookshelfGrid.setMinHeight(342);
        bookshelfGrid.setHgap(17);
        bookshelfGrid.setVgap(10);
        bookshelfGrid.setLayoutX(0);
        bookshelfGrid.setLayoutY(0);
        //bookshelfGrid.setGridLinesVisible(true);

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
        FullGameModelView fullGameModelView = viewManager.getGameModelView();
        int previousPlayer = fullGameModelView.getPlayers().indexOf(fullGameModelView.getCurrentPlayer())-1;
        if(previousPlayer == -1) previousPlayer = fullGameModelView.getPlayers().size()-1;

        for(int i = 0 ; i<2; i++){
            List<Token> oldTokens = commonGoals[i].getTokens();
            if(oldTokens.size() > fullGameModelView.getCommonGoals().get(i).getTokens().size()) {
                Image imageNew;
                if(oldTokens.size()>1) imageNew = getImage("game/tokens/"+oldTokens.get(oldTokens.size()-2).getValue()+".jpg");
                else imageNew = null;

                Image imageTaken = getImage("game/tokens/"+oldTokens.get(oldTokens.size()-1).getValue()+".jpg");
                takenTokens[i][previousPlayer] = imageTaken;
                int finalI = i;
                Image finalImageNew = imageNew;
                Platform.runLater(() -> {
                    if (finalI == 0) token1Image.setImage(finalImageNew);
                    else token2Image.setImage(finalImageNew);
                });
            }
            commonGoals[i] = fullGameModelView.getCommonGoals().get(i);
        }

        if(previousPlayer == seeingPlayer) displayTokens();


        //TODO show something good to let all players know
    }
    private void displayTokens(){
        Platform.runLater(() -> {
            takenToken1Image.setImage(takenTokens[0][seeingPlayer]);
            takenToken2Image.setImage(takenTokens[1][seeingPlayer]);
        });
    }

    @FXML
    protected void onCrashButtonMouseClicked(){
        viewManager.setView(ViewType.INIT);
    }

    @FXML
    protected void onButtonMouseClicked() {
        switch (viewManager.getGameModelView().getCurrentState()){
            case DRAW, LAST_DRAW -> {
                if(selectedTiles.size() == 0) {}//displayError("select at least one tile");
                else viewManager.drawRequest(selectedTiles.stream().map(x -> x.centered(viewManager.getGameModelView().getGameConfig().getLivingRoomSize())).collect(Collectors.toCollection(ArrayList::new)));
            }
            case ORGANIZE, LAST_ORGANIZE ->
                    viewManager.organizeRequest(orderedTiles.isEmpty() ? viewManager.getGameModelView().getCurrentPlayer().getTakenTiles() :
                            orderedTiles.stream().map(viewManager.getGameModelView().getCurrentPlayer().getTakenTiles()::get).toList());
            case INSERT, LAST_INSERT -> {
                if(chosenColumn == -1) {} //displayError("choose a column");
                else viewManager.insertRequest(chosenColumn);
            }

        }
    }
    @FXML
    protected void showDescription1() {
        Platform.runLater(() -> {
            descriptionLabel.setText(commonGoals[0].getDescription());
            descriptionLabel.setFont(Font.font("Kristen ITC", 20));
            descriptionLabel.setVisible(true);
        });

    }
    @FXML
    protected void showDescription2() {
        Platform.runLater(() -> {
            descriptionLabel.setText(commonGoals[1].getDescription());
            descriptionLabel.setFont(Font.font("Kristen ITC", 20));
            descriptionLabel.setVisible(true);
        });
    }
    @FXML
    protected void hideDescription() {
        Platform.runLater(() -> descriptionLabel.setVisible(false));
    }
    @FXML
    protected void prevPlayer() {
        if(seeingPlayer ==0) seeingPlayer = numPlayers - 1;
        else seeingPlayer--;
        displayPlayerInfo();
        displayTokens();
    }
    @FXML
    protected void nextPlayer() {
        if(seeingPlayer ==numPlayers-1) seeingPlayer = 0;
        else seeingPlayer++;
        displayPlayerInfo();
        displayTokens();
    }

    public void updateChat() {
        //TODO
    }

    public void win() {
        endGameController.update();
        endGame.setVisible(true);
        endGame.setMouseTransparent(false);
    }
}
