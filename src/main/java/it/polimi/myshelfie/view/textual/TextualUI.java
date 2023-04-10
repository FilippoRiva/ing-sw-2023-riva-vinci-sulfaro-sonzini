package it.polimi.myshelfie.view.textual;

import it.polimi.myshelfie.model.chatting.Message;
import it.polimi.myshelfie.model.modelviews.*;
import it.polimi.myshelfie.utils.Observer;
import it.polimi.myshelfie.view.ViewBuffer;
import it.polimi.myshelfie.view.ViewType;
import it.polimi.myshelfie.view.ViewUpdate;

import java.util.List;

public class TextualUI implements Observer<ViewUpdate> {
    ViewBuffer viewBuffer;
    Terminal t;
    String myUsername;
    String currentLibraryOwner;

    public TextualUI(ViewBuffer viewBuffer) {
        this.viewBuffer = viewBuffer;
        this.t = new Terminal();
    }

    /**
     * this update is called when the buffer receives a new update from the server
     * It switches over the ViewUpdate event and updates the part of the view referring to that data
     *
     * @param evt the ViewUpdate event passed by the ViewBuffer
     */
    @Override
    public void update(ViewUpdate evt) {
        switch (evt) {
            case MODEL_UPDATE -> new Thread(this::initPrint).start();
            case EXCEPTION_UPDATE -> new Thread(this::printException).start();
            case CHAT_UPDATE -> new Thread(this::printChatView).start();
            case LOBBY_UPDATE -> new Thread(this::printLobbyView).start();
            case GAME_UPDATE -> new Thread(this::printGameModelView).start();
            case VIEWTYPE_UPDATE -> new Thread(this::updateViewType).start();
        }
    }

    private void updateViewType() {
        ViewType viewType = viewBuffer.getViewType();
        switch (viewType) {
            case INIT -> initPrint();
            case EXCEPTION -> printException();
            case LOBBY -> printLobbyView();
            case GAME -> {
                currentLibraryOwner = myUsername;
                printGameModelView();
            }
            case WIN -> printWin();
        }
    }

    /**
     * if the viewType is set to ViewType.INIT it prints the initial screen with the choice for join or create
     */
    public synchronized void initPrint() {
        if (viewBuffer.getViewType() != ViewType.INIT || viewBuffer.getModelView() == null) return;
        t.clearScreen();
        t.printTitle("My Shelfie");
        if (viewBuffer.getModelView().getLobbies().size() != 0) {
            t.println("1. create",5,3);
            t.println("2. join",6,3);
        } else {
            t.println("1. create",5,3);
        }
    }

    public synchronized void joinPrint(String chosenLobby, String username) {
        t.clearScreen();
        if (chosenLobby != null && username != null) {
            t.println("joining " + chosenLobby + " as " + username,1,1);
            return;
        }
        if (chosenLobby == null) {
            t.println("Choose a lobby number" + " :",1,1);
            printAvailableLobbies();
        } else {
            t.println("Choose an username for " + chosenLobby + " :",1,1);
        }
    }

    public synchronized void createPrint(String lobby, String playerName) {
        t.clearScreen();
        if (lobby != null && playerName != null) {
            t.println("creating " + lobby + " as " + playerName,1,1);
            return;
        }
        if (lobby == null) {
            t.println("Choose a lobby name :",1,1);
        } else {
            t.println("Choose an username for joining " + lobby + " :",1,1);
        }
    }

    public synchronized void printAvailableLobbies() {
        ModelView modelView = viewBuffer.getModelView();
        if (viewBuffer.getViewType() != ViewType.INIT || modelView == null) return;
        t.clearScreen();
        t.println("Lobbies : ",1,1);
        if (modelView.getLobbies().size() == 0) {
            t.println("none",2,2);
            return;
        }
        int idx = 1;
        for (String lobby : modelView.getLobbies()) {
            t.println(idx + ". " + lobby,1+idx,2);
            idx++;
        }
    }

    public synchronized void printException() {
        ClientExceptionsNotifierView exceptionsView = viewBuffer.getClientExceptionsNotifierView();
        if (exceptionsView == null) return;
        t.clearError();
        t.printError(exceptionsView.getLastException().getMessage());
    }

    private synchronized void printChatView() {
        PlayerChatModelView chatModelView = viewBuffer.getPlayerChatModelView();
        if (chatModelView == null) return;
        t.clearScreen();
        printMessage(chatModelView.getLastMessage());
    }

    private synchronized void printMessage(Message lastMessage) {
        //TODO
    }

    public synchronized void printLobbyView() {
        LobbyModelView lobbyModelView = viewBuffer.getLobbyModelView();
        if (viewBuffer.getViewType() != ViewType.LOBBY || lobbyModelView == null) return;
        t.clearScreen();
        t.println("Name : " + lobbyModelView.getName(),1,1);
        t.println("Owner : " + lobbyModelView.getRoomOwner(),2,1);
        int idx = 1;
        for (String player : lobbyModelView.getPlayers()) {
            t.println(idx + ". " + player,3+idx,4);
            idx++;
        }
        t.println("Max players : " + lobbyModelView.getGameConfig().getNumPlayers(),3+idx+1,1);
        t.printAction("+ : add player, - : remove player, s : start game");
    }

    private synchronized void printGameModelView() {
        GameModelView gameModelView = viewBuffer.getGameModelView();
        if (viewBuffer.getViewType() != ViewType.GAME || gameModelView == null) return;
        t.clearScreen();
        t.printLivingRoom(gameModelView);
        t.printDivider(20);
        printCommonGoals(gameModelView);
        t.printDivider(26);
        t.printPersonalGoal(gameModelView.getPlayer(myUsername).getPersonalGoals().get(0).getGoal(),27,4);
        t.printVerticalDivider(0,40, 20);
        t.printVerticalDivider(27, 30, 7);
        printGameInfo(gameModelView);
        t.printBookshelf(gameModelView.getPlayer(currentLibraryOwner).getBookshelf(),currentLibraryOwner,27,36);
        t.printAction("n/p : next/previous bookshelf, c <args> : choice and insert");
    }

    private synchronized void printGameInfo(GameModelView gameModelView) {
        int pos = 0;
        t.printInInfoTable("Current state : " + gameModelView.getCurrentState(), pos);
        pos++;
        t.printInInfoTable("Current player : " + gameModelView.getCurrentPlayer(), pos);
        pos += 3;
        t.printInInfoTable("Players :", pos);
        pos++;
        int idx = 1;
        for (PlayerModelView p : gameModelView.getPlayers()) {
            if(p.getName().equals(myUsername)){
                t.printInInfoTable("   "+idx+". "+ANSI.BOLD+p.getName()+ANSI.RESET+" "+ p.getPoints(),pos);
            } else {
                t.printInInfoTable("   "+idx+". "+p.getName() + " " + p.getPoints(), pos);
            }
            idx++;
            pos++;
        }
        t.printInInfoTable("Remaining tiles : " + gameModelView.getRemainingTiles(), 19);
    }

    private synchronized void printCommonGoals(GameModelView gmv) {
        t.println("Common Goals :",21,0);
        int idx = 1;
        for (CommonGoalModelView c : gmv.getCommonGoals()) {
            t.println(c.getId() + ". :" + c.getDescription().split("\n")[0],21+idx,0);
            t.print("Tokens : ",22+idx,1);
            idx+=2;
            t.printTokens(c.getTokens());
        }
    }

    private synchronized void printWin() {
    }

    public synchronized void wrongInput() {
        t.printError("insert a valid option");
    }

    public synchronized void clearInput() {
        t.clearInput();
    }

    public synchronized void clearError() {
        t.clearError();
    }

    public synchronized void setMyUsername(String myUsername) {
        this.myUsername = myUsername;
    }

    public synchronized void showNextLibrary() {
        GameModelView gmv = viewBuffer.getGameModelView();
        if(gmv==null) return;
        List<String> players = gmv.getPlayers().stream().map(PlayerModelView::getName).toList();
        int index = players.indexOf(currentLibraryOwner);
        if(index == players.size()-1) currentLibraryOwner = players.get(0);
        else currentLibraryOwner = players.get(index+1);
        printGameModelView();
    }

    public synchronized void showPrevLibrary() {
        GameModelView gmv = viewBuffer.getGameModelView();
        if(gmv==null) return;
        List<String> players = gmv.getPlayers().stream().map(PlayerModelView::getName).toList();
        int index = players.indexOf(currentLibraryOwner);
        if(index == 0) currentLibraryOwner = players.get(players.size()-1);
        else currentLibraryOwner = players.get(index-1);
        printGameModelView();
    }
}