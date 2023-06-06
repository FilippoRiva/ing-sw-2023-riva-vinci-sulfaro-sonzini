package it.polimi.myshelfie.view.graphical;

import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.ItemTileType;
import javafx.scene.image.Image;

import java.io.IOException;
import java.util.HashMap;

public class Images {
    private static final HashMap<String, Image> tilesNameToImage = new HashMap<>();
    public static final Image backPersonalGoal = getImage("game/personalGoals/back.jpg");
    public static final Image emptyToken = getImage("game/tokens/scoring.jpg");

    static void initImages(){
        for(ItemTileType itemTileType : ItemTileType.values()){
            if(itemTileType == ItemTileType.EMPTY) return;
            for(int i = 1 ; i < 4 ; i++){
                tilesNameToImage.put(itemTileType.getName()+i,
                        getImage("game/itemTiles/" + itemTileType.getName() + i + ".png"));
            }
        }
    }

    public static Image getImage(String path){
        try {
            return new Image(Images.class.getResource("/images/"+path).openStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Image getTileImage(ItemTile itemTile){
        return tilesNameToImage.get(itemTile.getType().getName()+(itemTile.getId()+1));
    }
}
