package it.polimi.myshelfie.view.graphical;

import it.polimi.myshelfie.utils.Observer;
import it.polimi.myshelfie.view.ViewUpdate;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GUITest extends Application implements Observer<ViewUpdate, Object> {
    private static final double SCALE = (double) 2/3;
    static Stage stage;

    @Override
    public void update(ViewUpdate evt, Object object) {
    }

    public void startGUI(){
        Application.launch();
    }
    @Override
    public void start(Stage stage) {
        GUITest.stage = stage;
        changeScene("endGame");

        stage.setTitle("My Shelfie");
        stage.setResizable(true);

        stage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
        stage.show();
    }

    public static void changeScene(String fxmlName) {
        FXMLLoader fxmlLoader = new FXMLLoader(GraphicalUI.class.getResource("/fxml/"+fxmlName+"-view.fxml"));
        fxmlLoader.setController(new EndGameControllerTest());
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load(), 1920 * SCALE, 1080 * SCALE); //(1280, 720)
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Platform.runLater(() -> stage.setScene(scene));
    }
}
