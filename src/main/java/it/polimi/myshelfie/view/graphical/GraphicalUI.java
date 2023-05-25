package it.polimi.myshelfie.view.graphical;

import it.polimi.myshelfie.model.GameState;
import it.polimi.myshelfie.model.modelviews.PlayerModelView;
import it.polimi.myshelfie.utils.Observer;
import it.polimi.myshelfie.view.AutoPlayer;
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
    private static final double SCALE = (double) 2/3; //HD 720p
    ViewBuffer viewBuffer = ViewBuffer.getInstance();
    static Stage stage;

    static ViewController currentController;
    static InternalViewState currentState;
    static String myName;

    @Override
    public synchronized void update(ViewUpdate evt, Object object) {
        switch (evt) {
            case MODEL_UPDATE -> joinUpdate();
            case EXCEPTION_UPDATE -> exceptionUpdate();
            case CHAT_UPDATE -> {}
            case LOBBY_UPDATE -> lobbyUpdate();
            case GAME_UPDATE -> gameUpdate();
            case VIEW_TYPE_UPDATE -> viewTypeUpdate();
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
        stage.setResizable(false);

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


        currentState = switch (fxmlName){
            case "home" -> InternalViewState.INIT;
            case "create" -> InternalViewState.CREATE;
            case "join" -> InternalViewState.JOIN;
            case "lobby" -> InternalViewState.LOBBY;
            case "game" -> InternalViewState.GAME;
            case "endGame" -> InternalViewState.WIN;
            default -> throw new IllegalStateException("Unexpected value: " + fxmlName);
        };
    }

    public static void setMyName(String myName) {
        GraphicalUI.myName = myName;
    }
    public static String getMyName() {
        return myName;
    }

    private synchronized void joinUpdate() {
        if(currentState == InternalViewState.JOIN)
            ((JoinController)currentController).refreshListLobbies();
    }
    private synchronized void exceptionUpdate() {
        currentController.displayError(viewBuffer.getClientExceptionsNotifierView().getLastException().getMessage());
    }
    private synchronized void lobbyUpdate() {
        if(currentState == InternalViewState.LOBBY && viewBuffer.getLobbyModelView()!=null)
            ((LobbyController)currentController).refreshListPlayers();
    }
    private synchronized void gameUpdate() {
        if(currentState == InternalViewState.GAME)
            ((GameController)currentController).play();
    }
    private synchronized void viewTypeUpdate() {
        switch (viewBuffer.getViewType()){
            case INIT -> {
                if(currentState == InternalViewState.GAME && viewBuffer.getGameModelView().getCurrentState() != GameState.WIN){
                    System.out.println("somebody crashed");
                    changeScene("home"); //TODO display crash error
                }else if(stage != null) changeScene("home");
            }
            case LOBBY -> changeScene("lobby");
            case GAME -> {
                changeScene("game");
                gameUpdate();
                if(viewBuffer.getLobbyModelView().getName().equals("test") && myName.startsWith("test")){
                    AutoPlayer a = new AutoPlayer(viewBuffer,null);
                    a.postGameStart(myName);
                }
            }
                case WIN -> changeScene("endGame");
            }
        }
}

