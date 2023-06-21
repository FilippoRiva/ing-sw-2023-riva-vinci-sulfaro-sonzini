package it.polimi.myshelfie.view.graphical.controllers.game;

import it.polimi.myshelfie.model.*;
import it.polimi.myshelfie.model.modelviews.FullGameModelView;
import it.polimi.myshelfie.model.modelviews.PlayerModelView;
import it.polimi.myshelfie.view.ViewManager;
import it.polimi.myshelfie.view.graphical.Animations;
import it.polimi.myshelfie.view.graphical.CustomBindings;
import it.polimi.myshelfie.view.graphical.GraphicalUI;
import it.polimi.myshelfie.view.graphical.components.TileImageView;
import it.polimi.myshelfie.view.graphical.components.TokenImageView;
import javafx.application.Platform;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.Property;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class BookshelfController {
    @FXML
    protected BorderPane mainBorderPane;
    @FXML
    protected ImageView bookshelfImage;
    @FXML
    protected HBox topHBox;
    @FXML
    protected HBox bottomHBox;
    @FXML
    protected ImageView chair;
    @FXML
    protected StackPane commonGoalTokensStack;
    @FXML
    protected Label playerName;
    @FXML
    protected GridPane bookshelfGrid;
    @FXML
    protected Button prevButton;
    @FXML
    protected Button nextButton;
    @FXML
    protected StackPane gridStack;
    @FXML
    protected StackPane rightStack;
    @FXML
    protected StackPane leftStack;
    private int currentPlayerPos;
    private final ViewManager viewManager = ViewManager.getInstance();
    private final float bookshelfImageWidth = 1413;
    private final float bookshelfImageHeight = 1410;
    private final float tileImageSize = 163;
    private final float imageVGap = 30;
    private final float imageHGap = 62;
    private final float gridWidth = tileImageSize*5+imageHGap*4;
    private final float gridHeigth = tileImageSize*6+imageVGap*5;
    private final float bookshelfTranslationY = -40;
    private final float bookshelfImageRatio = bookshelfImageWidth/bookshelfImageHeight;
    private final float tileHeightToBookshelfHeight = tileImageSize/bookshelfImageHeight;
    private final float tileWidthToBookshelfWidth = tileImageSize/bookshelfImageWidth;
    private final float hGapToBookshelfHeight = imageHGap/bookshelfImageHeight;
    private final float vGapToBookshelfWidth = imageVGap/bookshelfImageWidth;
    private final float gridYTranslateToBookshelfHeight = bookshelfTranslationY/bookshelfImageHeight;
    private final float gridWidthToBookshelfWidth = gridWidth/bookshelfImageWidth;
    private final float gridHeigthToBookshelfHeigth = gridHeigth/bookshelfImageHeight;

    protected Stage stage = GraphicalUI.stage;

    public void initialize(boolean left){
        CustomBindings.bindSize(mainBorderPane,bookshelfImage.fitWidthProperty().add(80),
                bookshelfImage.fitHeightProperty().add(topHBox.heightProperty()).add(bottomHBox.heightProperty()));
        CustomBindings.bindSize(bookshelfImage,
                Bindings.min(stage.heightProperty().divide(3.5).multiply(bookshelfImageRatio),stage.widthProperty().divide(4)),
                Bindings.min(stage.heightProperty().divide(3.5),stage.widthProperty().divide(4).divide(bookshelfImageRatio)));
        CustomBindings.bindSize(gridStack,bookshelfImage.fitWidthProperty(),bookshelfImage.fitHeightProperty());
        CustomBindings.bindSize(bottomHBox,null,chair.fitHeightProperty());
        CustomBindings.bindSize(topHBox,null,chair.fitHeightProperty());
        CustomBindings.bindSize(leftStack,null,bookshelfImage.fitHeightProperty());
        CustomBindings.bindSize(rightStack,null,bookshelfImage.fitHeightProperty());

        //left and right differences
        if(!left) {
            leftStack.getChildren().removeAll(prevButton);
            rightStack.getChildren().removeAll(nextButton);
            topHBox.spacingProperty().bind(bookshelfImage.fitHeightProperty().multiply(hGapToBookshelfHeight));
            for(Node node : topHBox.getChildren()){
                CustomBindings.bindSize((Region)node,bookshelfImage.fitWidthProperty().multiply(tileWidthToBookshelfWidth),
                        bookshelfImage.fitHeightProperty().multiply(tileHeightToBookshelfHeight));
            }
        } else {
            prevButton.setOnMouseClicked((event)->prevBookshelf());
            nextButton.setOnMouseClicked((event)->nextBookshelf());
            topHBox.getChildren().removeAll(topHBox.getChildren());
            currentPlayerPos = 0;
        }

        //grid settings
        bookshelfGrid.vgapProperty().bind(bookshelfImage.fitWidthProperty().multiply(vGapToBookshelfWidth));
        bookshelfGrid.hgapProperty().bind(bookshelfImage.fitHeightProperty().multiply(hGapToBookshelfHeight));
        for(ColumnConstraints columnConstraints : bookshelfGrid.getColumnConstraints()){
            columnConstraints.minWidthProperty().bind(bookshelfImage.fitWidthProperty().multiply(tileWidthToBookshelfWidth));
            columnConstraints.maxWidthProperty().bind(columnConstraints.minWidthProperty());
            columnConstraints.maxWidthProperty().bind(columnConstraints.minWidthProperty());
        }
        for(RowConstraints rowConstraints : bookshelfGrid.getRowConstraints()){
            rowConstraints.minHeightProperty().bind(bookshelfImage.fitHeightProperty().multiply(tileHeightToBookshelfHeight));
            rowConstraints.maxHeightProperty().bind(rowConstraints.minHeightProperty());
            rowConstraints.prefHeightProperty().bind(rowConstraints.minHeightProperty());
        }
        bookshelfGrid.translateYProperty().bind(bookshelfImage.fitHeightProperty().multiply(gridYTranslateToBookshelfHeight));
        CustomBindings.bindSize(bookshelfGrid,bookshelfImage.fitWidthProperty().multiply(gridWidthToBookshelfWidth),
                bookshelfImage.fitHeightProperty().multiply(gridHeigthToBookshelfHeigth));

        CustomBindings.bindSize(chair,stage.widthProperty().divide(25),stage.heightProperty().divide(15));

        Animations.applyHoverScaling(chair);
    }

    private void nextBookshelf() {
        PlayerModelView player;
        List<PlayerModelView> players = viewManager.getGameModelView().getPlayers();
        players.remove(viewManager.getGameModelView().getPlayer(ViewManager.getUsername()));
        currentPlayerPos++;
        if(currentPlayerPos<players.size()) {
            player = players.get(currentPlayerPos);
        } else {
            currentPlayerPos = 0;
            player = players.get(0);
        }
        loadBookshelf(player);
    }

    private void prevBookshelf() {
        PlayerModelView player;
        List<PlayerModelView> players = viewManager.getGameModelView().getPlayers();
        players.remove(viewManager.getGameModelView().getPlayer(ViewManager.getUsername()));
        currentPlayerPos--;
        if(currentPlayerPos < 0) {
            currentPlayerPos = players.size()-1;
            player = players.get(players.size()-1);
        } else {
            player = players.get(currentPlayerPos);
        }
        loadBookshelf(player);
    }

    protected void loadBookshelf(PlayerModelView player) {
        TileImageView[][] bookshelf = TileImageView.convertMatrix(player.getBookshelf());
        boolean firstPlayer = ViewManager.getInstance().getGameModelView().getFirstPlayer().equals(player);
        Platform.runLater(()->{
            clearTiles();
            clearTokens();
            if(player.getTokens().size()==0){
                addToken(Token.ENDGAME,false);
            }
            for(Token t : player.getTokens()){
                addToken(t,true);
            }
            for(int i = 0; i < bookshelf.length; i++){
                for(int j = 0; j < bookshelf[0].length; j++){
                    TileImageView tileImage = bookshelf[i][j];
                    tileImage.setPreserveRatio(true);
                    CustomBindings.bindSize(tileImage,bookshelfImage.fitWidthProperty().multiply(tileWidthToBookshelfWidth),
                            bookshelfImage.fitHeightProperty().multiply(tileHeightToBookshelfHeight));
                    bookshelfGrid.add(tileImage,j,i);
                }
            }
            chair.setVisible(firstPlayer);
            playerName.setText(player.getName());
        });
    }

    private PlayerModelView getCurrentPlayer(){
        return viewManager.getGameModelView().getPlayer(currentPlayerPos);
    }

    public void reloadBookshelf(){
        loadBookshelf(getCurrentPlayer());
    }

    public void addToken(Token token,boolean visible){
        int stackSize = commonGoalTokensStack.getChildren().size();
        TokenImageView tokenImage = new TokenImageView(token);
        if(!visible) tokenImage.setVisible(false);
        tokenImage.setPreserveRatio(true);
        CustomBindings.bindSize(tokenImage,stage.widthProperty().divide(25),stage.heightProperty().divide(15));
        CustomBindings.bindTranslate(tokenImage,
                tokenImage.fitWidthProperty().divide(2).multiply(stackSize),
                tokenImage.fitWidthProperty().divide(2).multiply(stackSize));
        Platform.runLater(()->{
            commonGoalTokensStack.getChildren().add(tokenImage);
        });
    }

    public void clearTokens(){
        commonGoalTokensStack.getChildren().removeAll(commonGoalTokensStack.getChildren());
    }

    public void clearTiles(){
        bookshelfGrid.getChildren().removeAll(bookshelfGrid.getChildren());
    }

    public void addTiles(List<ItemTile> tiles, int column){
        List<TileImageView> tilesImages = new ArrayList<>();
        for(ItemTile tile : tiles){
            TileImageView tileImage = new TileImageView(tile);
            tileImage.setPreserveRatio(true);
            CustomBindings.bindSize(tileImage,bookshelfImage.fitWidthProperty().multiply(tileWidthToBookshelfWidth),
                bookshelfImage.fitHeightProperty().multiply(tileHeightToBookshelfHeight));
            tilesImages.add(tileImage);
        }
        Platform.runLater(()->{
            int i = 0;
            for(TileImageView tile : tilesImages){
                bookshelfGrid.add(tile,column,5-i++);
            }
        });
    }

    public void loadLeft() {
        currentPlayerPos = -1;
        this.nextBookshelf();
    }

    public void loadRight() {
        FullGameModelView gmv = ViewManager.getInstance().getGameModelView();
        loadBookshelf(gmv.getPlayer(ViewManager.getUsername()));
    }

    public DoubleBinding tileHeightProperty() {
        return bookshelfImage.fitHeightProperty().multiply(tileHeightToBookshelfHeight);
    }

    public DoubleBinding tileWidthProperty() {
        return bookshelfImage.fitWidthProperty().multiply(tileWidthToBookshelfWidth);
    }
}
