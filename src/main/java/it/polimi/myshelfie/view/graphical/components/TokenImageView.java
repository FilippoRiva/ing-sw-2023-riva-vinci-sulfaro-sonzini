package it.polimi.myshelfie.view.graphical.components;

import it.polimi.myshelfie.model.Token;
import it.polimi.myshelfie.view.graphical.Images;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TokenImageView extends ImageView {
    /**
     * Constructor of the TokenImageView given a certain Token
     *
     * @param token the token whose TokenImageView is to be constructed
     */
    public TokenImageView(Token token) {
        Image image = Images.getImage("game/tokens/" + token.getValue() + ".jpg");
        super.setImage(image);
    }
}
