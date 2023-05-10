package it.polimi.myshelfie.view.graphical;

import it.polimi.myshelfie.network.NetworkMessage;
import it.polimi.myshelfie.utils.Observer;
import it.polimi.myshelfie.view.ViewUpdate;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GraphicalUI extends Application implements Observer<ViewUpdate, NetworkMessage> {

    @Override
    public void update(ViewUpdate evt, NetworkMessage networkMessage) {

    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(GraphicalUI.class.getResource("/fxml/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public void startGUI(){
        Application.launch();
    }
}
