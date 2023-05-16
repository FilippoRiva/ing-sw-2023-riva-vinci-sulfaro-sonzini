package it.polimi.myshelfie.view.graphical;

import it.polimi.myshelfie.network.NetworkMessage;
import it.polimi.myshelfie.utils.Observer;
import it.polimi.myshelfie.view.ViewUpdate;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.URL;

public class GraphicalUI extends Application implements Observer<ViewUpdate, NetworkMessage> {
    private final double SCALE = (double) 2/3;

    @Override
    public void update(ViewUpdate evt, NetworkMessage networkMessage) {

    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(GraphicalUI.class.getResource("/fxml/home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1920 * SCALE, 1080 * SCALE); //(1280, 720)
        stage.setTitle("My Shelfie");
        stage.setResizable(false);
        stage.setScene(scene);

        URL iconUrl = getClass().getResource("/images/Publisher material/Icon.png");
        assert iconUrl != null;
        Image icon = new Image(iconUrl.openStream());
        stage.getIcons().add(icon);

        stage.show();
    }

    public void startGUI(){
        Application.launch();
    }
}
