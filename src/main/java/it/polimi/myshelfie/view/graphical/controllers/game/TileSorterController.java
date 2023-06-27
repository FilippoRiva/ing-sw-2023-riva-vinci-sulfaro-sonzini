package it.polimi.myshelfie.view.graphical.controllers.game;

import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.ItemTileType;
import it.polimi.myshelfie.view.ViewManager;
import it.polimi.myshelfie.view.graphical.Animations;
import it.polimi.myshelfie.view.graphical.CustomBindings;
import it.polimi.myshelfie.view.graphical.components.TileImageView;
import javafx.animation.Animation;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.*;

public class TileSorterController {
    @FXML
    protected StackPane mainStackPane;
    @FXML
    protected ImageView backgroundImage;
    @FXML
    protected VBox tilesVBox;
    @FXML
    protected HBox tilesHBox;
    @FXML
    protected StackPane topStack;
    @FXML
    protected StackPane midStack;
    @FXML
    protected StackPane bottomStack;
    @FXML
    protected TileImageView top;
    @FXML
    protected TileImageView middle;
    @FXML
    protected TileImageView bottom;
    @FXML
    protected Label topChoiceDot;
    @FXML
    protected Label midChoiceDot;
    @FXML
    protected Label bottomChoiceDot;
    private TileImageView[] tiles;
    private Label[] choiceDots;
    private boolean active;
    private List<TileImageView> orderedTiles;
    private List<Label> activeDots;

    public void initialize(NumberBinding tileSize) {
        this.orderedTiles = new ArrayList<>();
        this.tiles = new TileImageView[]{bottom, middle, top};
        this.choiceDots = new Label[]{bottomChoiceDot, midChoiceDot, topChoiceDot};
        this.activeDots = new ArrayList<>();
        tileSize = tileSize.multiply(1.5);
        tilesVBox.spacingProperty().bind(tileSize.divide(2));
        for (TileImageView tile : tiles) {
            tile.setTile(new ItemTile(ItemTileType.CAT, 0), 0);
            CustomBindings.bindSize(tile, tileSize, tileSize);
        }
        for (Label l : choiceDots) {
            l.translateYProperty().bind(Bindings.negate(tileSize.divide(4)));
            l.setScaleX(0);
            l.setScaleY(0);
        }
        CustomBindings.bindSize(backgroundImage,
                Bindings.add(tileSize, 40),
                Bindings.multiply(tileSize, 3).add(tilesVBox.spacingProperty().multiply(2)).add(40));
        CustomBindings.bindSize(tilesVBox,
                backgroundImage.fitWidthProperty(),
                backgroundImage.fitHeightProperty());
        CustomBindings.bindSize(tilesHBox, tilesVBox);
        active = false;
    }

    public Point2D getPosition(int tileIndex) {
        return switch (tileIndex) {
            case 1 -> getPosition(bottom);
            case 2 -> getPosition(middle);
            case 3 -> getPosition(top);
            default -> throw new IllegalStateException("Unexpected value: " + tileIndex);
        };
    }

    private Point2D getPosition(TileImageView tile) {
        return CustomBindings.getAbsolutePosition(tile);
    }

    public void loadTiles() {
        List<ItemTile> drawnTiles = ViewManager.getInstance().getGameModelView().getPlayer(ViewManager.getUsername()).getTakenTiles();
        for (int i = 0; i < drawnTiles.size(); i++) {
            TileImageView tile = tiles[i];
            tile.setTile(drawnTiles.get(i), 1);
            int finalI = i;
            tile.setOnMousePressed((mouseEvent) -> {
                if (!active) return;
                if (orderedTiles.contains(tile)) {
                    orderedTiles.remove(tile);
                    tile.setEffect(null);
                    Animation hideAnimation = Animations.reverse(Animations.popFadeIn(choiceDots[finalI]));
                    activeDots.remove(choiceDots[finalI]);
                    hideAnimation.setOnFinished((evt) -> Platform.runLater(() -> choiceDots[finalI].setText("-1")));
                    hideAnimation.play();
                    fixChoiceDots(Integer.parseInt(choiceDots[finalI].getText()));
                } else {
                    orderedTiles.add(tile);
                    tile.setEffect(new Glow());
                    Platform.runLater(() -> choiceDots[finalI].setText(String.valueOf(orderedTiles.size())));
                    Animations.popFadeIn(choiceDots[finalI]).play();
                    activeDots.add(choiceDots[finalI]);
                }
            });
        }
    }

    private synchronized void fixChoiceDots(int removedPosition) {
        if (removedPosition == activeDots.size() + 1) return; //we removed the top element
        for (Label choiceDot : activeDots) {
            int currentIndex = Integer.parseInt(choiceDot.getText());
            if (currentIndex > removedPosition)
                Platform.runLater(() -> choiceDot.setText(String.valueOf(currentIndex - 1)));
        }
    }

    /**
     * Returns the chosen ordered tiles. If the player confirm without selecting any tile it returns an empty ItemTile List
     *
     * @return the list of the ItemTile chosen by the player
     */
    public synchronized List<ItemTile> getOrderedTiles() {
        Map<Integer, ItemTile> orderedTiles = new HashMap<>();
        for (int i = 0; i < tiles.length; i++) {
            int index = Integer.parseInt(choiceDots[i].getText());
            if (index != -1) {
                orderedTiles.put(index, tiles[i].getTile());
            }
        }
        List<ItemTile> result = new ArrayList<>();
        for (int i = 0; i < activeDots.size(); i++) {
            if (orderedTiles.containsKey(i + 1)) {
                result.add(orderedTiles.get(i + 1));
            }
        }
        return result;
    }


    /**
     * Corrects the ordering of the tiles given the new order in the ViewManager
     */
    public void fixOrdering() {
        List<Animation> orderingAnimations = new ArrayList<>();
        List<ItemTile> orderedTiles = ViewManager.getInstance().getGameModelView().getCurrentPlayer().getTakenTiles();
        ArrayList<TileImageView> tilesCopy = new ArrayList<>(Arrays.stream(tiles).toList());
        for (int i = 0; i < orderedTiles.size(); i++) {
            ItemTile tile = orderedTiles.get(i);
            for (TileImageView tileImage : tilesCopy) {
                if (tileImage.getTile().getType() == tile.getType() && tileImage.getTile().getId() == tile.getId()) {
                    tileImage.setEffect(null);
                    orderingAnimations.add(Animations.fixTranslateTo(tileImage, getPosition(i + 1)));
                    tilesCopy.remove(tileImage);
                    break;
                }
            }
        }
        for (Animation a : orderingAnimations) a.play();
    }

    /**
     * Hides active dots and resets their numbers afterward
     */
    public synchronized void resetDots() {
        for (Label l : activeDots) {
            Animation hideAnimation = Animations.popFadeOut(l);
            hideAnimation.setOnFinished((evt) -> Platform.runLater(() -> l.setText("-1")));
            hideAnimation.play();
        }
    }

    /**
     * Completely reset the component emptying the orderedTiles list, resetting the active Dots,
     * rotating out the TileImageView and erasing translations afterward
     */
    public synchronized void resetAll() {
        orderedTiles.clear();
        activeDots.clear();
        for (TileImageView t : tiles) {
            Animation a = Animations.tileRotateOut(t, Duration.millis(200));
            a.setOnFinished((evt) -> {
                Animation an = Animations.eraseTranslation(t);
                an.setOnFinished((ev) -> Platform.runLater(() -> {
                    t.setTile(new ItemTile(ItemTileType.EMPTY, 0), 0);
                    Animations.tileRotateIn(t, Duration.millis(200)).play();
                    t.setMouseTransparent(false);
                }));
                an.play();
            });
            a.play();
        }
        setActive(false);
    }

    /**
     * Enable the ability to choose tile ordering
     *
     * @param active the active boolean
     */
    public void setActive(boolean active) {
        this.active = active;
    }
}
