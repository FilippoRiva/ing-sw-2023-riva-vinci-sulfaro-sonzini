package it.polimi.myshelfie.view.graphical;

import it.polimi.myshelfie.utils.Observer;
import it.polimi.myshelfie.view.ViewBuffer;
import it.polimi.myshelfie.view.ViewUpdate;
import it.polimi.myshelfie.view.graphical.controllers.GameController;
import it.polimi.myshelfie.view.graphical.controllers.JoinController;
import it.polimi.myshelfie.view.graphical.controllers.LobbyController;
import it.polimi.myshelfie.view.graphical.controllers.ViewController;
import it.polimi.myshelfie.view.textual.InternalViewState;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class GraphicalUI extends Application implements Observer<ViewUpdate, Object> {
    private static final double SCALE = (double) 2/3;
    ViewBuffer viewBuffer = ViewBuffer.getInstance();
    static Stage stage;

    static ViewController currentController;
    static InternalViewState currentState;
    static String playerName;

    @Override
    public void update(ViewUpdate evt, Object object) {
        switch (evt) {
            case MODEL_UPDATE -> new Thread(this::joinUpdate).start();
            case EXCEPTION_UPDATE -> new Thread(this::exceptionUpdate).start();
            case CHAT_UPDATE -> {}
            case LOBBY_UPDATE -> new Thread(this::lobbyUpdate).start();
            case GAME_UPDATE -> new Thread(this::gameUpdate).start();
            case VIEW_TYPE_UPDATE -> new Thread(this::viewTypeUpdate).start();
        }
    }

    public void startGUI(){
        Images.initImages();
        Application.launch();
    }
    @Override
    public void start(Stage stage) throws Exception {
        GraphicalUI.stage = stage;
        changeScene("home");

        stage.setTitle("My Shelfie");
        stage.setResizable(true);

        URL iconUrl = getClass().getResource("/images/Icon.png");
        assert iconUrl != null;
        Image icon = new Image(iconUrl.openStream());
        stage.getIcons().add(icon);

        stage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
        stage.show();
    }

    public static void changeScene(String fxmlName) {
        FXMLLoader fxmlLoader = new FXMLLoader(GraphicalUI.class.getResource("/fxml/"+fxmlName+"-view.fxml"));
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load(), 1920 * SCALE, 1080 * SCALE); //(1280, 720)
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        currentController = fxmlLoader.getController();
        Platform.runLater(() -> stage.setScene(scene));


        switch (fxmlName){
            case "home" -> currentState = InternalViewState.INIT;
            case "create" -> currentState = InternalViewState.CREATE;
            case "join" -> currentState = InternalViewState.JOIN;
            case "lobby" -> currentState = InternalViewState.LOBBY;
            case "game" -> currentState = InternalViewState.GAME;
        }
    }

    public static void setPlayerName(String playerName) {
        GraphicalUI.playerName = playerName;
    }
    public static String getPlayerName() {
        return playerName;
    }

    private void joinUpdate() {
        if(currentState == InternalViewState.JOIN) ((JoinController)currentController).refreshListLobbies();
    }
    private void exceptionUpdate() {
        currentController.displayError(viewBuffer.getClientExceptionsNotifierView().getLastException().getMessage());
    }
    private void lobbyUpdate() {
        if(currentState == InternalViewState.LOBBY && viewBuffer.getLobbyModelView()!=null)
            Platform.runLater(() -> ((LobbyController)currentController).refreshListPlayers());
    }
    private void gameUpdate() {
        if(currentState == InternalViewState.GAME)
            ((GameController)currentController).play();
    }
    private void viewTypeUpdate() {
        switch (viewBuffer.getViewType()){
            case INIT -> {
                if(stage != null) changeScene("home");
            }
            case LOBBY -> changeScene("lobby");
            case GAME -> {
                changeScene("game");
                gameUpdate();
            }
            case WIN -> {
                //TODO win page
            }
        }
    }
}
