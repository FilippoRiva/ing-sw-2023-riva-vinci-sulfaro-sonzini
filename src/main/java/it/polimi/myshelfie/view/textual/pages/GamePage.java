package it.polimi.myshelfie.view.textual.pages;

import it.polimi.myshelfie.model.GameState;
import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.modelviews.CommonGoalModelView;
import it.polimi.myshelfie.model.modelviews.FullGameModelView;
import it.polimi.myshelfie.model.modelviews.PlayerModelView;
import it.polimi.myshelfie.view.ViewManager;
import it.polimi.myshelfie.view.textual.ANSI;
import it.polimi.myshelfie.view.textual.Terminal;

import java.util.List;

public class GamePage extends ScreenPage{
    private final String username;
    private final String currentLibraryOwner;
    public GamePage(Terminal t, ViewManager viewManager, List<String> inputParameters, String username, String libraryOwner) {
        super(t, viewManager, inputParameters);
        this.username = username;
        this.currentLibraryOwner = libraryOwner;
    }

    @Override
    public void render() {
        FullGameModelView fullGameModelView = viewManager.getGameModelView();
        if(fullGameModelView ==null) return;
        t.clearScreen();
        t.printLivingRoom(fullGameModelView);
        t.printPersonalGoal(fullGameModelView.getPlayer(username).getPersonalGoals().get(0).getGoal(),25,4);
        printGameInfo(fullGameModelView);
        t.printBookshelf(fullGameModelView.getPlayer(currentLibraryOwner).getBookshelf(),currentLibraryOwner,25,36);
        if(!fullGameModelView.getCurrentPlayerName().equals(username)) {
            t.printAction("help, chat, goals");
        } else {
            switch (fullGameModelView.getCurrentState()){
                case DRAW, LAST_DRAW -> t.printAction("help, chat, goals, d <args>");
                case ORGANIZE, LAST_ORGANIZE -> t.printAction("help, chat, goals, o <args>");
                case INSERT, LAST_INSERT -> t.printAction("help, chat, goals, i <column>");
            }
        }
        printBorder();
        t.printDivider(24);
        t.printVerticalDivider(0,42, 25);
        t.printVerticalDivider(24, 30, 11);
    }

    private void printGameInfo(FullGameModelView fullGameModelView) {
        int pos = 1;
        t.printInInfoTable("Phase : " + ANSI.BLUE_FG+ fullGameModelView.getCurrentState()+ANSI.RESET, pos);
        pos++;
        String currentPlayer = fullGameModelView.getCurrentPlayer().getName();
        String formattedCurrentPlayer = String.format(currentPlayer.length()>10 ? currentPlayer.substring(0,9) : currentPlayer , "%-10s");
        t.printInInfoTable("Playing : "+ formattedCurrentPlayer, pos);
        pos += 1;
        t.printInInfoTable("Players :",pos);
        pos++;
        int idx = 1;
        for (PlayerModelView p : fullGameModelView.getPlayers()) {
            String username = p.getName();
            String trimmedUsername = String.format(username.length()>10 ? username.substring(0,9) : username , "%-10s");
            if(username.equals(this.username)){
                t.printInInfoTable("   "+idx+". "+ ANSI.PINK_FG+ANSI.BOLD+trimmedUsername+ANSI.RESET+" "+ p.getPoints(),pos);
            } else {
                t.printInInfoTable("   "+idx+". "+trimmedUsername + " " + p.getPoints(), pos);
            }
            idx++;
            pos++;
        }
        t.printInInfoTable("Common Goal Tokens:",10);
        idx = 1;
        for (CommonGoalModelView c : fullGameModelView.getCommonGoals()) {
            t.printInInfoTable("   "+idx + " :",10+idx);
            t.printTopToken(c.getTokens(),10+idx,48);
            idx++;
        }
        int numberOfCommonGoals = fullGameModelView.getCommonGoals().size();
        if(fullGameModelView.getCurrentPlayerName().equals(username)) {
            List<ItemTile> takenTiles = fullGameModelView.getPlayer(username).getTakenTiles();
            if (takenTiles.size() != 0) {
                t.printInInfoTable("Taken Tiles :", 10+numberOfCommonGoals+3);
                t.printTakenTiles(takenTiles, 10+numberOfCommonGoals+4, 43, fullGameModelView.getCurrentState() == GameState.ORGANIZE);
            }
        } else {
            List<ItemTile> currenPlayerTakenTiles = fullGameModelView.getPlayer(fullGameModelView.getCurrentPlayerName()).getTakenTiles();
            if(currenPlayerTakenTiles.size() != 0){
                t.printInInfoTable(fullGameModelView.getCurrentPlayerName()+"'s taken tiles :",10+numberOfCommonGoals+3);
                t.printTakenTiles(currenPlayerTakenTiles, 10+numberOfCommonGoals+4, 43, false);
            }
        }
        t.printInInfoTable("Remaining tiles :", 22);
        t.printInInfoTable("   "+fullGameModelView.getRemainingTiles(),23);
    }
}
