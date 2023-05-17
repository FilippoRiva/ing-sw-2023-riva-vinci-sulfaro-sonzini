package it.polimi.myshelfie.view.graphical;

import it.polimi.myshelfie.view.ViewBuffer;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class LobbyController implements Initializable {
    public Label lobbyNameLabel;
    public Label startLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lobbyNameLabel.setText(ViewBuffer.getInstance().getLobbyModelView().getName());
    }

    public void onStartMouseEntered(MouseEvent mouseEvent) {
        startLabel.setTextFill(Color.color(1, 0, 0));
    }
    public void onStartMouseExited(MouseEvent mouseEvent) {
        startLabel.setTextFill(Color.color(1, 1, 1));
    }
    public void onStartMouseClicked(MouseEvent mouseEvent) {
    }
}
