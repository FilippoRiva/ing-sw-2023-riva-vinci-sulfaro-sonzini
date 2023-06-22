package it.polimi.myshelfie.view.graphical.controllers.game;

import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.ItemTileType;
import it.polimi.myshelfie.model.Position;
import it.polimi.myshelfie.view.ViewManager;
import it.polimi.myshelfie.view.graphical.Animations;
import it.polimi.myshelfie.view.graphical.CustomBindings;
import it.polimi.myshelfie.view.graphical.components.TileImageView;
import javafx.animation.Animation;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.binding.Binding;
import javafx.beans.binding.DoubleBinding;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LivingRoomController {
    @FXML
    StackPane mainStack;
    @FXML
    ImageView livingRoomImage;
    @FXML
    GridPane tilesGrid;
    private static final double livingRoomImageSize = 2964;
    private static final double gridTranslateX = 121;
    private static final double gridTranslateY = 133;
    private static final double tileSize = 298.5;
    private static final double gridSpacing = 0;
    private static final double livingRoomSizeToTileSize = livingRoomImageSize/tileSize;
    private static final double gridSpacingToLivingRoomSize = gridSpacing/livingRoomImageSize;
    private static final double gridTraslateXToLivingRoomSize = gridTranslateX/livingRoomImageSize;
    private static final double gridTranslateYToLivingRoomSize = gridTranslateY/livingRoomImageSize;
    ViewManager viewManager = ViewManager.getInstance();
    private DoubleBinding tileSizeBinding;
    protected DoubleBinding tileHeightProperty;
    public List<Position> chosenTiles;
    private List<TileImageView> chosenTilesImages;
    private boolean active;
    private GamePanelController gamePanelController;
    private TileImageView[][] imagesLivingRoom;

    public void initialize(GamePanelController gamePanelController, DoubleBinding tileSizeBinding){

        this.gamePanelController = gamePanelController;
        this.active = false;

        this.chosenTiles = new ArrayList<>();
        this.chosenTilesImages = new ArrayList<>();

        this.tileSizeBinding = tileSizeBinding.multiply(1.5);
        CustomBindings.bindSize(livingRoomImage,this.tileSizeBinding.multiply(livingRoomSizeToTileSize),this.tileSizeBinding.multiply(livingRoomSizeToTileSize));
        CustomBindings.bindSize(mainStack,livingRoomImage.fitWidthProperty(),livingRoomImage.fitHeightProperty());
        tilesGrid.vgapProperty().bind(livingRoomImage.fitHeightProperty().multiply(gridSpacingToLivingRoomSize));
        tilesGrid.hgapProperty().bind(livingRoomImage.fitWidthProperty().multiply(gridSpacingToLivingRoomSize));
        for(ColumnConstraints columnConstraints : tilesGrid.getColumnConstraints()){
            columnConstraints.minWidthProperty().bind(this.tileSizeBinding);
            columnConstraints.maxWidthProperty().bind(columnConstraints.minWidthProperty());
            columnConstraints.maxWidthProperty().bind(columnConstraints.minWidthProperty());
        }
        for(RowConstraints rowConstraints : tilesGrid.getRowConstraints()){
            rowConstraints.minHeightProperty().bind(this.tileSizeBinding);
            rowConstraints.maxHeightProperty().bind(rowConstraints.minHeightProperty());
            rowConstraints.prefHeightProperty().bind(rowConstraints.minHeightProperty());
        }
        Binding<Number> gridSize = this.tileSizeBinding.multiply(9).add(tilesGrid.vgapProperty().multiply(7));
        CustomBindings.bindSize(tilesGrid,gridSize,gridSize);
        tilesGrid.translateXProperty().bind(livingRoomImage.fitWidthProperty().multiply(gridTraslateXToLivingRoomSize));
        tilesGrid.translateYProperty().bind(livingRoomImage.fitHeightProperty().multiply(gridTranslateYToLivingRoomSize));
    }

    public void loadGrid(){
        ItemTile[][] livingRoom = viewManager.getGameModelView().getLivingRoom();
        TileImageView[][] imagesLivingRoom = TileImageView.convertMatrix(livingRoom);
        for (int i = 0; i < imagesLivingRoom.length; i++) {
            for (int j = 0; j < imagesLivingRoom[0].length; j++) {
                TileImageView tile = imagesLivingRoom[i][j];
                tile.setPreserveRatio(true);
                tile.setVisible(false);
                CustomBindings.bindSize(tile, this.tileSizeBinding,this.tileHeightProperty);
                final boolean[] selected = {false};
                final Position[] position = {new Position(i,j).centered(9)};
                tile.setOnMouseClicked((mouseEvent -> {
                    if(!this.active) return;
                    if(chosenTiles.size()==3 && !selected[0]) return; //not allowing selection for more than 3 tiles
                    selected[0] = !selected[0];
                    if(selected[0]){ //selected
                        this.chosenTiles.add(position[0]);
                        this.chosenTilesImages.add(tile);
                        Animations.translateTo(tile,gamePanelController.tileSorterController.getPosition(chosenTiles.size())).play();
                    } else { //de-selection
                        this.chosenTiles.remove(position[0]);
                        this.chosenTilesImages.remove(tile);
                        Animations.eraseTranslation(tile).play();
                        fixChosenTileTranslations();
                    }
                }));
            }
        }
        this.imagesLivingRoom = imagesLivingRoom;
        Platform.runLater(()->{
            Animation[][] animationMatrix = new Animation[imagesLivingRoom.length][imagesLivingRoom[0].length];
            tilesGrid.getChildren().removeAll(tilesGrid.getChildren());
            for(int i = 0; i< imagesLivingRoom.length; i++){
                for(int j = 0; j<imagesLivingRoom[0].length; j++){
                    tilesGrid.add(imagesLivingRoom[i][j],i,j);
                    if(imagesLivingRoom[i][j].getTile()!=null){
                        animationMatrix[i][j] = (Animations.tileRotateIn(imagesLivingRoom[i][j], Duration.millis(100)));
                    } else {
                        animationMatrix[i][j] = new TranslateTransition(Duration.millis(0));
                    }
                }
            }
            List<ParallelTransition> animationLines = new ArrayList<>();
            for(int line = 1; line<=imagesLivingRoom.length*2-1; line++){
                int initColumn = Math.max(0,line-imagesLivingRoom.length);
                int count = Math.min(Math.min(line, imagesLivingRoom.length-initColumn),imagesLivingRoom.length);
                List<Animation> animationLine = new ArrayList<>();
                for(int j = 0; j < count; j++){
                    animationLine.add(animationMatrix[Math.min(animationMatrix.length,line)-j-1][initColumn+j]);
                }
                ParallelTransition pt = new ParallelTransition(animationLine.toArray(Animation[]::new));
                animationLines.add(pt);
            }
            for(int line = 0; line < animationLines.size()-1; line++){
                int finalLine = line;
                animationLines.get(line).setOnFinished((evt)->animationLines.get(finalLine +1).play());
            }
            animationLines.get(0).play();
        });
    }

    public void updateLivingRoom(HashMap<Position,ItemTile> livingRoomDiff){
        if(livingRoomDiff==null) return;
        for(Position pos : livingRoomDiff.keySet()){
            ItemTile changedTile = livingRoomDiff.get(pos);
            TileImageView changedTileImage = imagesLivingRoom[pos.getY()][pos.getX()];
            if(changedTile.getType()==ItemTileType.EMPTY){
                if(!ViewManager.getInstance().isMyTurn()) Animations.tileRotateOut(changedTileImage,Duration.millis(200)).play();
                else {
                    Platform.runLater(()->{
                        tilesGrid.getChildren().remove(changedTileImage);
                        tilesGrid.add(new TileImageView(new ItemTile(ItemTileType.EMPTY,0)),pos.getX(),pos.getY());
                    });
                }
            } else {
                Position correctPosition = new Position(pos.getY(),pos.getX());
                Position centeredPos = correctPosition.centered(9);
                Platform.runLater(()->{
                    tilesGrid.getChildren().remove(changedTileImage);
                    TileImageView newImage = new TileImageView(changedTile);
                    CustomBindings.bindSize(newImage,tileSizeBinding,tileSizeBinding);
                    newImage.setVisible(false);
                    final boolean[] selected = {false};
                    imagesLivingRoom[pos.getY()][pos.getX()] = newImage;
                    tilesGrid.add(newImage,pos.getY(),pos.getX());
                    newImage.setOnMouseClicked(mouseEvent -> {
                        if(!this.active) return;
                        if(chosenTiles.size()==3 && !selected[0]) return; //not allowing selection for more than 3 tiles
                        selected[0] = !selected[0];
                        if(selected[0]){ //selected
                            this.chosenTiles.add(centeredPos);
                            this.chosenTilesImages.add(newImage);
                            Animations.translateTo(newImage,gamePanelController.tileSorterController.getPosition(chosenTiles.size())).play();
                        } else { //de-selection
                            this.chosenTiles.remove(centeredPos);
                            this.chosenTilesImages.remove(newImage);
                            Animations.eraseTranslation(newImage).play();
                            fixChosenTileTranslations();
                        }
                    });
                    Animations.tileRotateIn(newImage,Duration.millis(200)).play();
                });
            }
        }
    }
    public Animation resetChosenTileTranslations(){
        ParallelTransition pt = new ParallelTransition();
        for(TileImageView t : chosenTilesImages){
            pt = new ParallelTransition(pt,Animations.eraseTranslation(t));
        }
        return pt;
    }

    public void setChosenTileTranslations(){
        for(int i = 0; i < chosenTilesImages.size(); i++){
            Animations.translateTo(chosenTilesImages.get(i),gamePanelController.tileSorterController.getPosition(i+1)).play();
        }
    }

    public void fixChosenTileTranslations(){
        for(int i = 0; i < chosenTilesImages.size(); i++){
            Animations.fixTranslateTo(chosenTilesImages.get(i),gamePanelController.tileSorterController.getPosition(i+1)).play();
        }
    }

    public void setActive(boolean active){
        this.active = active;
        if(!active) {
            chosenTiles.clear();
            chosenTilesImages.clear();
        }
    }
}
