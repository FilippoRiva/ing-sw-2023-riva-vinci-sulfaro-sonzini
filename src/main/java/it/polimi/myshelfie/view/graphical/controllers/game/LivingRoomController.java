package it.polimi.myshelfie.view.graphical.controllers.game;

import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.Position;
import it.polimi.myshelfie.view.ViewManager;
import it.polimi.myshelfie.view.graphical.CustomBindings;
import it.polimi.myshelfie.view.graphical.components.TileImageView;
import javafx.application.Platform;
import javafx.beans.binding.Binding;
import javafx.beans.binding.DoubleBinding;
import javafx.fxml.FXML;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;

public class LivingRoomController {
    @FXML
    StackPane mainStack;
    @FXML
    ImageView livingRoomImage;
    @FXML
    GridPane tilesGrid;
    private static final double livingRoomImageSize = 2964;
    private static final double gridTranslateX = 127;
    private static final double gridTranslateY = 140;
    private static final double tileSize = 289;
    private static final double gridSpacing = 12;
    private static final double livingRoomSizeToTileSize = livingRoomImageSize/tileSize;
    private static final double gridSpacingToLivingRoomSize = gridSpacing/livingRoomImageSize;
    private static final double gridTraslateXToLivingRoomSize = gridTranslateX/livingRoomImageSize;
    private static final double gridTranslateYToLivingRoomSize = gridTranslateY/livingRoomImageSize;
    ViewManager viewManager = ViewManager.getInstance();
    private DoubleBinding tileSizeBinding;
    private DoubleBinding tileHeightProperty;
    public List<Position> chosenTiles;
    private boolean active;

    public void initialize(DoubleBinding tileSizeBinding){

        this.active = false;

        this.chosenTiles = new ArrayList<>();

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

    public void reloadGrid(){
        ItemTile[][] livingRoom = viewManager.getGameModelView().getLivingRoom();
        TileImageView[][] imagesLivingRoom = TileImageView.convertMatrix(livingRoom);
        for (int i = 0; i < imagesLivingRoom.length; i++) {
            for (int j = 0; j < imagesLivingRoom[0].length; j++) {
                TileImageView tile = imagesLivingRoom[i][j];
                tile.setPreserveRatio(true);
                CustomBindings.bindSize(tile, this.tileSizeBinding,this.tileHeightProperty);
                final boolean[] selected = {false};
                final Position[] position = {new Position(i,j).centered(9)};
                tile.setOnMouseClicked((mouseEvent -> {
                    if(!this.active) return;
                    if(chosenTiles.size()==3 && !selected[0]) return; //not allowing selection for more than 3 tiles
                    selected[0] = !selected[0];
                    if(selected[0]){ //selected
                        this.chosenTiles.add(position[0]);
                        tile.setEffect(new Glow());
                    } else { //de-selection
                        this.chosenTiles.remove(position[0]);
                        tile.setEffect(null);
                    }
                }));
            }
        }
        Platform.runLater(()->{
            tilesGrid.getChildren().removeAll(tilesGrid.getChildren());
            for(int i = 0; i< imagesLivingRoom.length; i++){
                for(int j = 0; j<imagesLivingRoom[0].length; j++){
                    tilesGrid.add(imagesLivingRoom[i][j],i,j);
                }
            }
        });
    }

    public void setActive(boolean active){
        this.active = active;
    }
}
