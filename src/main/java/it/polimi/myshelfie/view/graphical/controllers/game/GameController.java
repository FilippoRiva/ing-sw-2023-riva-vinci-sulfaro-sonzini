package it.polimi.myshelfie.view.graphical.controllers.game;

import it.polimi.myshelfie.model.GameState;
import it.polimi.myshelfie.view.ViewManager;
import it.polimi.myshelfie.view.graphical.Animations;
import it.polimi.myshelfie.view.graphical.CustomBindings;
import it.polimi.myshelfie.view.graphical.GraphicalUI;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class GameController{

    @FXML
    protected ProgressIndicator progress;
    @FXML
    protected AnchorPane endGame;
    @FXML
    protected StackPane gameOverlay;
    @FXML
    protected AnchorPane gamePanel;
    @FXML
    protected ImageView backgroundImage;
    @FXML
    protected AnchorPane mainAnchorPane;
    @FXML
    protected GameOverlayController gameOverlayController;
    @FXML
    protected  GamePanelController gamePanelController;
    @FXML
    protected EndGameController endGameController;

    private final ViewManager viewManager = ViewManager.getInstance();

    public void initialize() {
        Stage stage = GraphicalUI.stage;
        endGame.setVisible(false);
        endGame.setMouseTransparent(true);
        gameOverlay.setVisible(true);
        //gameOverlay.setMouseTransparent(true);
        gameOverlayController.initialize(this);
        //gameOverlayController.setKeyListeners();
        gamePanelController.initialize(this);
        endGameController.initialize(this);
        CustomBindings.bindSize(backgroundImage,stage.widthProperty(),stage.heightProperty());
        CustomBindings.bindSize(mainAnchorPane,stage.widthProperty(),stage.heightProperty());
    }
    
    public void gameStart(){
        //default visible panels
        endGame.setVisible(false);
        endGame.setMouseTransparent(true);
        gamePanel.setVisible(true);
        gamePanel.setMouseTransparent(false);
        //AutoPlayer Activation
        if(ViewManager.getInstance().getLobbyModelView().getName().equals("test") &&
                ViewManager.getUsername().startsWith("bot")) ViewManager.getInstance().startAutoPlayer();
        //completely loading everything
        gamePanelController.setState(viewManager.getGameModelView().getCurrentState());
        gamePanelController.leftBookshelfController.loadLeft();
        gamePanelController.rightBookshelfController.loadRight();
        gamePanelController.livingRoomController.loadGrid();
        gameOverlayController.gameStart();
        setKeyListeners(GraphicalUI.stage);
    }

    private void setKeyListeners(Stage stage){
        stage.getScene().setOnKeyPressed((keyEvent)->{});
        gamePanelController.livingRoomController.loadGrid();
    }

    public void win() {
    }

    public void update() {
        GameState currentState = viewManager.getCurrentStateDiff();

        gamePanelController.setState(currentState); //this reloads game panel elements
        if(currentState == GameState.DRAW || currentState == GameState.LAST_DRAW)
            gameOverlayController.startTurn();
        if(viewManager.getCurrentStateDiff()== GameState.WIN){
            Animations.timer(()->{
                gameOverlay.setMouseTransparent(true);
                gamePanel.setMouseTransparent(true);
                gameOverlay.setVisible(false);
                gamePanel.setVisible(false);
                endGameController.update();
                endGameController.setKeyListeners();
                endGame.setVisible(true);
                endGame.setMouseTransparent(false);
            },2000);
        }
    }

    public void updateChat() {
        gameOverlayController.chatController.addChatMessage();
    }

    public void displayError(String message) {
    }

    public void reset() {
        endGame.setVisible(false);
        endGame.setMouseTransparent(true);
        gamePanelController.reset();
        gameOverlayController.reset();
        endGameController.reset();
    }

    /*
    public void setKeyListeners(Scene scene) {
        scene.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()){
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
            }
        });
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
    */
}
