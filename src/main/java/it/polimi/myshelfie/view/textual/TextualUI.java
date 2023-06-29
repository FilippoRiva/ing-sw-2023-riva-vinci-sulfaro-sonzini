package it.polimi.myshelfie.view.textual;

import it.polimi.myshelfie.model.GameState;
import it.polimi.myshelfie.model.modelviews.ClientExceptionsNotifierModelView;
import it.polimi.myshelfie.model.modelviews.FullGameModelView;
import it.polimi.myshelfie.model.modelviews.PlayerModelView;
import it.polimi.myshelfie.view.*;
import it.polimi.myshelfie.view.textual.pages.*;

import java.util.ArrayList;
import java.util.List;


/**
 *Represents the TextualUI.
 */
public class TextualUI implements UI {
    private final ViewManager viewManager;
    private final Terminal t;
    private final List<String> inputParameters;
    private final Thread readerThread;
    private String currentBookshelfOwner;
    private ScreenPage currentPage;
    private InternalViewState internalViewState;


    /**
     * Constructor.
     */
    public TextualUI() {
        this.viewManager = ViewManager.getInstance();
        this.inputParameters = new ArrayList<>();
        this.t = new Terminal();
        this.currentPage = new InitPage(t, inputParameters);
        CLIInputReader reader = new CLIInputReader(this, inputParameters);
        this.readerThread = new Thread(reader::read);
    }

    /**
     * this update is called when the buffer receives a new update from the server
     * It switches over the ViewUpdate event and updates the part of the view referring to that data
     *
     * @param evt the ViewUpdate event passed by the viewManager
     */
    @Override
    public void update(ViewUpdate evt, Object unused) {
        switch (evt) {
            case MODEL_UPDATE -> new Thread(this::initUpdate).start();
            case EXCEPTION_UPDATE -> new Thread(() -> printException(false)).start();
            case CHAT_UPDATE -> new Thread(this::chatUpdate).start();
            case LOBBY_UPDATE -> new Thread(this::lobbyUpdate).start();
            case GAME_UPDATE -> new Thread(this::gameUpdate).start();
            case VIEW_TYPE_UPDATE -> new Thread(this::updateViewType).start();
        }
    }


    /**
     * Renders the currentPage if viewManager.getViewType() == ViewType.GAME
     */
    private synchronized void gameUpdate() {
        if (viewManager.getViewType() == ViewType.GAME && !(internalViewState == InternalViewState.CHAT ||
                internalViewState == InternalViewState.HELP)) {
            if (viewManager.getGameModelView().getCurrentPlayerName().equals(ViewManager.getUsername()) &&
                    viewManager.getGameModelView().getCurrentState() == GameState.ORGANIZE &&
                    viewManager.getGameModelView().getCurrentPlayer().getTakenTiles().size() == 1) {
                viewManager.organizeRequest(viewManager.getGameModelView().getCurrentPlayer().getTakenTiles());
            } else currentPage.render();
        }
    }


    /**
     * Renders the currentPage if viewManager.getViewType() == ViewType.LOBBY
     */
    private synchronized void lobbyUpdate() {
        if (viewManager.getViewType() == ViewType.LOBBY) {
            currentPage.render();
        }
    }


    /**
     * Renders the currentPage if InternalViewState == InternalViewState.CHAT
     */
    private synchronized void chatUpdate() {
        if ((viewManager.getViewType() == ViewType.LOBBY || viewManager.getViewType() == ViewType.GAME)
                && internalViewState == InternalViewState.CHAT) {
            currentPage.render();
        }
    }


    /**
     * Renders the currentPage if viewManager.getViewType() == ViewType.INIT
     */
    private synchronized void initUpdate() {
        if (viewManager.getViewType() == ViewType.INIT && internalViewState != InternalViewState.CREATE) {
            currentPage.render();
        }
    }


    /**
     * Updates the pages and the InternalViewStates based on the viewType.
     */
    private synchronized void updateViewType() {
        ViewType viewType = viewManager.getViewType();
        switch (viewType) {
            case INIT -> {
                internalViewState = InternalViewState.INIT;
                currentPage = new InitPage(t, inputParameters);
            }
            case LOBBY -> {
                internalViewState = InternalViewState.LOBBY;
                currentPage = new LobbyPage(t, inputParameters);
            }
            case GAME -> {
                internalViewState = InternalViewState.GAME;
                currentBookshelfOwner = ViewManager.getUsername();
                currentPage = new GamePage(t, inputParameters, currentBookshelfOwner);
            }
            case WIN -> {
                internalViewState = InternalViewState.WIN;
                currentPage = new WinPage(t, inputParameters);
            }
            case DISCONNECTION -> {
                printException(true);
                viewManager.setView(ViewType.INIT);
            }
        }
        currentPage.render();
    }


    /**
     * Prints an exception when occurs.
     * @param disconnection a boolean that signals a disconnection.
     */
    public synchronized void printException(boolean disconnection) {
        ClientExceptionsNotifierModelView exceptionsView;
        if (disconnection) exceptionsView = viewManager.getDisconnectedClientExceptionsNotifierView();
        else exceptionsView = viewManager.getClientExceptionsNotifierView();
        if (exceptionsView == null) return;
        t.clearError();
        t.printError(exceptionsView.getLastException().getMessage());
    }


    /**
     * Prints "insert a valid option" when a wrong input is entered by calling {@link #wrongInput(String)}.
     */
    public synchronized void wrongInput() {
        wrongInput("insert a valid option");
    }


    /**
     * Prints an error message.
     * @param message the error message to print.
     */
    public synchronized void wrongInput(String message) {
        t.printError(message);
    }


    /**
     * Show the bookshelf of the next player.
     */
    public synchronized void showNextBookshelf() {
        FullGameModelView gmv = viewManager.getGameModelView();
        List<String> players = gmv.getPlayers().stream().map(PlayerModelView::getName).toList();
        int index = players.indexOf(currentBookshelfOwner);
        if (index == players.size() - 1) currentBookshelfOwner = players.get(0);
        else currentBookshelfOwner = players.get(index + 1);
        currentPage = new GamePage(t, inputParameters, currentBookshelfOwner);
        currentPage.render();
    }


    /**
     * Shows the bookshelf of the previous player.
     */
    public synchronized void showPrevBookshelf() {
        FullGameModelView gmv = viewManager.getGameModelView();
        List<String> players = gmv.getPlayers().stream().map(PlayerModelView::getName).toList();
        int index = players.indexOf(currentBookshelfOwner);
        if (index == 0) currentBookshelfOwner = players.get(players.size() - 1);
        else currentBookshelfOwner = players.get(index - 1);
        currentPage = new GamePage(t, inputParameters, currentBookshelfOwner);
        currentPage.render();
    }


    /**
     * Returns the InternalViewState.
     * @return the InternalViewState.
     */
    public InternalViewState getInternalViewState() {
        return internalViewState;
    }


    /**
     * Sets the Pages based on the specific InternalViewState.
     * @param internalViewState the InternalViewState.
     */
    public synchronized void setInternalViewState(InternalViewState internalViewState) {
        this.internalViewState = internalViewState;
        switch (internalViewState) {
            case INIT -> currentPage = new InitPage(t, inputParameters);
            case JOIN -> currentPage = new JoinPage(t, inputParameters);
            case CREATE -> currentPage = new CreatePage(t, inputParameters);
            case LOBBY -> currentPage = new LobbyPage(t, inputParameters);
            case GAME -> currentPage = new GamePage(t, inputParameters, currentBookshelfOwner);
            case GOALS -> currentPage = new GoalPage(t, inputParameters);
            case CHAT -> currentPage = new ChatPage(t, inputParameters);
            case HELP -> currentPage = new HelpPage(t, inputParameters);
            case WIN -> currentPage = new WinPage(t, inputParameters);
        }
        currentPage.render();
    }

    /**
     * Clears the terminalInput.
     */
    public void clearInput() {
        t.clearInput();
    }


    /**
     * Clears all the terminal.
     */
    public void refresh() {
        t.clearAll();
        currentPage.render();
        t.clearInput();
    }


    /**
     *{@inheritDoc}
     */
    @Override
    public void startUI() {
        readerThread.start();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void stopUI() {
        t.printPermanentError("Unable to reach server, press any button to try to reconnect");
        this.internalViewState = InternalViewState.STOPPED;
        readerThread.interrupt();
        try {
            readerThread.join();
            t.clearAll();
        } catch (InterruptedException ignored) {
        }
    }
}