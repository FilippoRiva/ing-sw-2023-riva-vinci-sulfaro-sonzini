package it.polimi.myshelfie.view.graphical;

import it.polimi.myshelfie.view.ViewBuffer;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.io.IOException;

public class CreateController {
    public AnchorPane rootPane;
    public TextField lobbyNameField;
    public TextField playerNameField;
    public Label createLabel;

    public void onCreateMouseEntered(MouseEvent mouseEvent) {
        createLabel.setTextFill(Color.color(1, 0, 0));
    }
    public void onCreateMouseExited(MouseEvent mouseEvent) {
        createLabel.setTextFill(Color.color(1, 1, 1));
    }
    public void onCreateMouseClicked(MouseEvent mouseEvent) throws IOException {
        String playerName = playerNameField.getText();
        String lobbyName = lobbyNameField.getText();

        if (playerName.isEmpty() || lobbyName.isEmpty()) {
            //TODO error message: compile all the fields
        }else {
            ViewBuffer.getInstance().createRequest(lobbyName, playerName);
            //TODO check if the lobby already exists, or the player name is already taken
            changeScreen("lobby-view");
            //lobbyNameLabel.setText(lobbyName);
        }
    }

    private void changeScreen(String fxmlName) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/"+fxmlName+".fxml"));
        AnchorPane pane = loader.load();
        rootPane.getChildren().setAll(pane);
    }
}
