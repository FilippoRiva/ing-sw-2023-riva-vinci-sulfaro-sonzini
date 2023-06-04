package it.polimi.myshelfie.view.textual;

import it.polimi.myshelfie.model.modelviews.*;
import it.polimi.myshelfie.utils.Observer;
import it.polimi.myshelfie.view.InternalViewState;
import it.polimi.myshelfie.view.ViewManager;
import it.polimi.myshelfie.view.ViewType;
import it.polimi.myshelfie.view.ViewUpdate;
import it.polimi.myshelfie.view.textual.pages.*;

import java.util.List;

public class TextualUI implements Observer<ViewUpdate, Object> {
    private final ViewManager viewManager;
    private final Terminal t;
    private String currentLibraryOwner;
    private ScreenPage currentPage;
    private final List<String> inputParameters;
    private InternalViewState internalViewState;

    public TextualUI(ViewManager viewManager, List<String> inputParameters) {
        this.viewManager = viewManager;
        this.inputParameters = inputParameters;
        this.t = new Terminal();
        this.currentPage = new InitPage(t, viewManager,inputParameters);
    }

    /**
     * this update is called when the buffer receives a new update from the server
     * It switches over the ViewUpdate event and updates the part of the view referring to that data
     *
     * @param evt the ViewUpdate event passed by the ViewBuffer
     */
    @Override
    public void update(ViewUpdate evt, Object unused) {
        switch (evt) {
            case MODEL_UPDATE -> new Thread(this::initUpdate).start();
            case EXCEPTION_UPDATE -> new Thread(()->printException(false)).start();
            case CHAT_UPDATE -> new Thread(this::chatUpdate).start();
            case LOBBY_UPDATE -> new Thread(this::lobbyUpdate).start();
            case GAME_UPDATE -> new Thread(this::gameUpdate).start();
            case VIEW_TYPE_UPDATE -> new Thread(this::updateViewType).start();
        }
    }

    private synchronized void gameUpdate() {
        if(viewManager.getViewType()==ViewType.GAME && !(internalViewState == InternalViewState.CHAT ||
                internalViewState == InternalViewState.HELP)){
            currentPage.render();
        }
    }

    private synchronized void lobbyUpdate() {
        if(viewManager.getViewType()==ViewType.LOBBY){
            currentPage.render();
        }
    }

    private synchronized void chatUpdate() {
        if((viewManager.getViewType()==ViewType.LOBBY || viewManager.getViewType()==ViewType.GAME)
                && internalViewState == InternalViewState.CHAT){
            currentPage.render();
        }
    }

    private synchronized void initUpdate() {
        if(viewManager.getViewType()==ViewType.INIT && internalViewState != InternalViewState.CREATE){
            currentPage.render();
        }
    }

    private synchronized void updateViewType() {
        ViewType viewType = viewManager.getViewType();
        switch (viewType) {
            case INIT -> {
                internalViewState = InternalViewState.INIT;
                currentPage = new InitPage(t, viewManager,inputParameters);
            }
            case LOBBY -> {
                internalViewState = InternalViewState.LOBBY;
                currentPage = new LobbyPage(t, viewManager,inputParameters, ViewManager.getUsername());
            }
            case GAME -> {
                internalViewState = InternalViewState.GAME;
                currentLibraryOwner = ViewManager.getUsername();
                currentPage = new GamePage(t, viewManager,inputParameters, ViewManager.getUsername(),currentLibraryOwner);
            }
            case WIN -> {
                internalViewState = InternalViewState.WIN;
                currentPage = new WinPage(t, viewManager,inputParameters);
            }
            case DISCONNECTION -> {
                printException(true);
                viewManager.setView(ViewType.INIT);
            }
        }
        currentPage.render();
    }

    public synchronized void printException(boolean disconnection) {
        ClientExceptionsNotifierView exceptionsView;
        if(disconnection) exceptionsView = viewManager.getDisconnectedClientExceptionsNotifierView();
        else exceptionsView = viewManager.getClientExceptionsNotifierView();
        if (exceptionsView == null) return;
        t.clearError();
        t.printError(exceptionsView.getLastException().getMessage());
    }

    public synchronized void wrongInput() {
        wrongInput("insert a valid option");
    }

    public synchronized void wrongInput(String message){
        t.printError(message);
    }

    public synchronized void showNextLibrary() {
        FullGameModelView gmv = viewManager.getGameModelView();
        List<String> players = gmv.getPlayers().stream().map(PlayerModelView::getName).toList();
        int index = players.indexOf(currentLibraryOwner);
        if(index == players.size()-1) currentLibraryOwner = players.get(0);
        else currentLibraryOwner = players.get(index+1);
        currentPage = new GamePage(t, viewManager,inputParameters, ViewManager.getUsername(),currentLibraryOwner);
        currentPage.render();
    }

    public synchronized void showPrevLibrary() {
        FullGameModelView gmv = viewManager.getGameModelView();
        List<String> players = gmv.getPlayers().stream().map(PlayerModelView::getName).toList();
        int index = players.indexOf(currentLibraryOwner);
        if(index == 0) currentLibraryOwner = players.get(players.size()-1);
        else currentLibraryOwner = players.get(index-1);
        currentPage = new GamePage(t, viewManager,inputParameters, ViewManager.getUsername(),currentLibraryOwner);
        currentPage.render();
    }

    public InternalViewState getInternalViewState() {
        return internalViewState;
    }

    public synchronized void setInternalViewState(InternalViewState internalViewState) {
        this.internalViewState = internalViewState;
        switch (internalViewState){
            case INIT -> currentPage = new InitPage(t, viewManager,inputParameters);
            case JOIN -> currentPage = new JoinPage(t, viewManager,inputParameters);
            case CREATE -> currentPage = new CreatePage(t, viewManager,inputParameters);
            case LOBBY -> currentPage = new LobbyPage(t, viewManager,inputParameters, ViewManager.username);
            case GAME -> currentPage = new GamePage(t, viewManager,inputParameters, ViewManager.username,currentLibraryOwner);
            case GOALS -> currentPage = new GoalPage(t, viewManager,inputParameters);
            case CHAT -> currentPage = new ChatPage(t, viewManager,inputParameters, ViewManager.username);
            case HELP -> currentPage = new HelpPage(t, viewManager,inputParameters);
            case WIN -> currentPage = new WinPage(t, viewManager,inputParameters);
        }
        currentPage.render();
    }

    public void clearInput() {
        t.clearInput();
    }

    public void refresh() {
        t.clearAll();
        currentPage.render();
        t.clearInput();
    }
}