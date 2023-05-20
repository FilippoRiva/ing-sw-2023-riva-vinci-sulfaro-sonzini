package it.polimi.myshelfie.view.graphical;

import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.ItemTileType;
import javafx.scene.image.Image;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;

public class Images {
    private static final HashMap<String, Image> nameToImage = new HashMap<>();

    static void initImages(){
        for(ItemTileType itemTileType : ItemTileType.values()){
            if(itemTileType == ItemTileType.EMPTY) return;
            for(int i = 1 ; i < 4 ; i++){
                nameToImage.put(itemTileType.getName()+i,
                        getImage("itemTiles/" + itemTileType.getName() + i + ".png"));
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

    public static Image getTile(ItemTile itemTile){
        return nameToImage.get(itemTile.getType().getName()+(itemTile.getId()+1));
    }
}
