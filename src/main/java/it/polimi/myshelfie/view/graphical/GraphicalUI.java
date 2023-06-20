package it.polimi.myshelfie.view.graphical;

import it.polimi.myshelfie.view.UI;
import it.polimi.myshelfie.view.ViewManager;
import it.polimi.myshelfie.view.ViewUpdate;
import it.polimi.myshelfie.view.graphical.controllers.*;
import it.polimi.myshelfie.view.InternalViewState;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class GraphicalUI extends Application implements UI {
    ViewManager viewManager = ViewManager.getInstance();
    public static Stage stage;
    public static MainViewController mainViewController;

    @Override
    public synchronized void update(ViewUpdate evt, Object object) {
        if(mainViewController==null) return;
        switch (evt) {
            case MODEL_UPDATE -> mainViewController.joinUpdate();
            case EXCEPTION_UPDATE -> mainViewController.exceptionUpdate();
            case CHAT_UPDATE -> mainViewController.chatUpdate();
            case LOBBY_UPDATE -> mainViewController.lobbyUpdate();
            case GAME_UPDATE -> mainViewController.gameUpdate();
            case VIEW_TYPE_UPDATE -> viewTypeUpdate();
        }
    }

    public void startGUI(){
        Application.launch();
    }
    @Override
    public void start(Stage stage) throws Exception {
        GraphicalUI.stage = stage;
        //stage config
        stage.setTitle("My Shelfie");
        stage.setResizable(true);
        stage.setX(0);
        stage.setY(0);
        stage.setMaximized(true);
        stage.setFullScreenExitHint("");
        stage.setMinHeight((double) 1080 /2);
        stage.setMinWidth((double) 1920 /2);
        //loading icon
        URL iconUrl = getClass().getResource("/images/Icon.png");
        assert iconUrl != null;
        Image icon = new Image(iconUrl.openStream());
        stage.getIcons().add(icon);
        //close action
        stage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
        //setting loading page
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/lightWood.jpg")));
        ImageView loadingImage = new ImageView(image);
        ProgressBar progressBar = new ProgressBar(0);
        StackPane loadingPane = new StackPane(loadingImage,progressBar);
        loadingPane.setAlignment(Pos.CENTER);
        StackPane root = new StackPane();
        root.getChildren().add(loadingPane);
        Scene scene = new Scene(root);
        loadingImage.setPreserveRatio(false);
        loadingImage.fitHeightProperty().bind(scene.heightProperty());
        loadingImage.fitWidthProperty().bind(scene.widthProperty());
        stage.setScene(scene);

        //showing stage
        stage.show();

        //loading fxml
        Task<AnchorPane> task = new Task<>(){
            @Override
            protected AnchorPane call() {
                AnchorPane pane;
                FXMLLoader fxmlLoader = new FXMLLoader(GraphicalUI.class.getResource("/fxml/main-view.fxml"));
                updateProgress(2,10);
                try {
                    updateProgress(3,10);
                    pane = fxmlLoader.load();
                    updateProgress(6,10);
                    Images.initImages();
                    updateProgress(9,10);
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
                mainViewController = fxmlLoader.getController();
                updateProgress(10,10);
                return pane;
            }
        };
        task.setOnSucceeded((event)->{
            AnchorPane pane;
            try {
                pane = task.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
            Platform.runLater(()->{
                root.getChildren().addAll(pane.getChildren());
                root.getChildren().remove(loadingPane);
            });
        });
        progressBar.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
    }

    private synchronized void viewTypeUpdate() {
        switch (viewManager.getViewType()) {
            case INIT -> mainViewController.changeView(InternalViewState.INIT);
            case LOBBY -> mainViewController.changeView(InternalViewState.LOBBY);
            case GAME -> mainViewController.changeView(InternalViewState.GAME);
            case WIN -> mainViewController.changeView(InternalViewState.WIN);
            case DISCONNECTION -> mainViewController.changeView(InternalViewState.DISCONNECTION);
        }
    }

    @Override
    public void startUI() {
        startGUI();
    }

    @Override
    public void stopUI() {
        mainViewController.changeView(InternalViewState.STOPPED);
    }
}