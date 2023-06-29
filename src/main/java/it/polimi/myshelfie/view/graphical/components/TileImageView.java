package it.polimi.myshelfie.view.graphical.components;

import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.ItemTileType;
import it.polimi.myshelfie.view.graphical.Images;
import javafx.scene.CacheHint;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;


/**
 * Represents the TileImageView.
 */
public class TileImageView extends ImageView {
    private static final HashMap<String, Image> tilesNameToImage = new HashMap<>();
    private ItemTile tile;

    /**
     * Constructor of the TileImageView class
     *
     * @param tile the ItemTile whose TileImageView is to be constructed
     */
    public TileImageView(ItemTile tile) {
        Image image = getTileImage(tile);
        this.tile = tile;
        super.setImage(image);
        super.setCache(true);
        super.setCacheHint(CacheHint.SPEED);
    }


    /**
     * Default Constructor.
     */
    public TileImageView() {
        super();
        this.tile = null;
        super.setCache(true);
        super.setCacheHint(CacheHint.SPEED);
    }

    /**
     * Preloads the Tiles images such that it is no longer required to get them from resources
     */
    public static void initImages() {
        for (ItemTileType itemTileType : ItemTileType.values()) {
            if (itemTileType == ItemTileType.EMPTY) return;
            for (int i = 1; i < 4; i++) {
                tilesNameToImage.put(itemTileType.getName() + i,
                        Images.getImage("game/itemTiles/" + itemTileType.getName() + i + ".png"));
            }
        }
    }

    /**
     * Returns the preloaded tile image associated with the given ItemTile
     *
     * @param itemTile the itemTile whose image is to be returned
     * @return the Image
     */
    private Image getTileImage(ItemTile itemTile) {
        if (itemTile == null || itemTile.getType() == ItemTileType.EMPTY) return null;
        else return tilesNameToImage.get(itemTile.getType().getName() + (itemTile.getId() + 1));
    }

    /**
     * Returns a matrix of TilesImageView such that is mimics a ItemTile matrix
     *
     * @param matrix the ItemTile matrix to be cloned
     * @return the cloned TileImageView matrix
     */
    public static TileImageView[][] convertMatrix(ItemTile[][] matrix) {
        TileImageView[][] result = new TileImageView[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                result[i][j] = new TileImageView(matrix[i][j]);
            }
        }
        return result;
    }


    /**
     * Sets the tile.
     * @param tile the tile to set.
     * @param opacity the opacity to set.
     */
    public void setTile(ItemTile tile, int opacity) {
        super.setImage(getTileImage(tile));
        this.tile = tile;
        this.setOpacity(opacity);
    }


    /**
     * Returns the tile.
     * @return the ItemTile.
     */
    public ItemTile getTile() {
        return tile;
    }
}
