package it.polimi.myshelfie.view.textual;

import it.polimi.myshelfie.model.modelviews.*;
import it.polimi.myshelfie.network.NetworkMessage;
import it.polimi.myshelfie.utils.Observer;
import it.polimi.myshelfie.view.ViewBuffer;
import it.polimi.myshelfie.view.ViewType;
import it.polimi.myshelfie.view.ViewUpdate;
import it.polimi.myshelfie.view.textual.pages.*;

import java.util.List;

public class TextualUI implements Observer<ViewUpdate, NetworkMessage> {
    ViewBuffer viewBuffer;
    Terminal t;
    String myUsername;
    String currentLibraryOwner;
    ScreenPage currentPage;
    List<String> inputParameters;

    InternalViewState internalViewState;

    public TextualUI(ViewBuffer viewBuffer, List<String> inputParameters) {
        this.viewBuffer = viewBuffer;
        this.inputParameters = inputParameters;
        this.t = new Terminal();
        this.currentPage = new InitPage(t,viewBuffer,inputParameters);
    }

    /**
     * this update is called when the buffer receives a new update from the server
     * It switches over the ViewUpdate event and updates the part of the view referring to that data
     *
     * @param evt the ViewUpdate event passed by the ViewBuffer
     */
    @Override
    public void update(ViewUpdate evt, NetworkMessage unused) {
        switch (evt) {
            case MODEL_UPDATE -> new Thread(this::initUpdate).start();
            case EXCEPTION_UPDATE -> new Thread(this::printException).start();
            case CHAT_UPDATE -> new Thread(this::chatUpdate).start();
            case LOBBY_UPDATE -> new Thread(this::lobbyUpdate).start();
            case GAME_UPDATE -> new Thread(this::gameUpdate).start();
            case VIEWTYPE_UPDATE -> new Thread(this::updateViewType).start();
        }
    }

    private synchronized void gameUpdate() {
        if(viewBuffer.getViewType()==ViewType.GAME && !(internalViewState == InternalViewState.CHAT ||
                internalViewState == InternalViewState.HELP)){
            currentPage.render();
        }
    }

    private synchronized void lobbyUpdate() {
        if(viewBuffer.getViewType()==ViewType.LOBBY){
            currentPage.render();
        }
    }

    private synchronized void chatUpdate() {
        if((viewBuffer.getViewType()==ViewType.LOBBY || viewBuffer.getViewType()==ViewType.GAME)
                && internalViewState == InternalViewState.CHAT){
            currentPage.render();
        }
    }

    private synchronized void initUpdate() {
        if(viewBuffer.getViewType()==ViewType.INIT && internalViewState != InternalViewState.CREATE){
            currentPage.render();
        }
    }

    private synchronized void updateViewType() {
        ViewType viewType = viewBuffer.getViewType();
        switch (viewType) {
            case INIT -> {
                internalViewState = InternalViewState.INIT;
                currentPage = new InitPage(t, viewBuffer,inputParameters);
            }
            case LOBBY -> {
                internalViewState = InternalViewState.LOBBY;
                currentPage = new LobbyPage(t, viewBuffer,inputParameters,myUsername);
            }
            case GAME -> {
                internalViewState = InternalViewState.GAME;
                currentLibraryOwner = myUsername;
                currentPage = new GamePage(t,viewBuffer,inputParameters,myUsername,currentLibraryOwner);
            }
            case WIN -> {
                internalViewState = InternalViewState.WIN;
                currentPage = new WinPage(t,viewBuffer,inputParameters);
            }
        }
        currentPage.render();
    }

    public synchronized void printException() {
        ClientExceptionsNotifierView exceptionsView = viewBuffer.getClientExceptionsNotifierView();
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

    public synchronized void setMyUsername(String myUsername) {
        this.myUsername = myUsername;
    }

    public synchronized void showNextLibrary() {
        FullGameModelView gmv = viewBuffer.getGameModelView();
        List<String> players = gmv.getPlayers().stream().map(PlayerModelView::getName).toList();
        int index = players.indexOf(currentLibraryOwner);
        if(index == players.size()-1) currentLibraryOwner = players.get(0);
        else currentLibraryOwner = players.get(index+1);
        currentPage = new GamePage(t,viewBuffer,inputParameters,myUsername,currentLibraryOwner);
        currentPage.render();
    }

    public synchronized void showPrevLibrary() {
        FullGameModelView gmv = viewBuffer.getGameModelView();
        List<String> players = gmv.getPlayers().stream().map(PlayerModelView::getName).toList();
        int index = players.indexOf(currentLibraryOwner);
        if(index == 0) currentLibraryOwner = players.get(players.size()-1);
        else currentLibraryOwner = players.get(index-1);
        currentPage = new GamePage(t,viewBuffer,inputParameters,myUsername,currentLibraryOwner);
        currentPage.render();
    }

    public InternalViewState getInternalViewState() {
        return internalViewState;
    }

    public synchronized void setInternalViewState(InternalViewState internalViewState) {
        this.internalViewState = internalViewState;
        switch (internalViewState){
            case INIT -> currentPage = new InitPage(t,viewBuffer,inputParameters);
            case JOIN -> currentPage = new JoinPage(t,viewBuffer,inputParameters);
            case CREATE -> currentPage = new CreatePage(t,viewBuffer,inputParameters);
            case LOBBY -> currentPage = new LobbyPage(t,viewBuffer,inputParameters,myUsername);
            case GAME -> currentPage = new GamePage(t,viewBuffer,inputParameters,myUsername,currentLibraryOwner);
            case CHAT -> currentPage = new ChatPage(t,viewBuffer,inputParameters,myUsername);
            case HELP -> currentPage = new HelpPage(t,viewBuffer,inputParameters);
            case WIN -> currentPage = new WinPage(t,viewBuffer,inputParameters);
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