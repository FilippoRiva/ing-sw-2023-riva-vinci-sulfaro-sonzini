package it.polimi.myshelfie.view.textual.pages;

import it.polimi.myshelfie.model.GameState;
import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.modelviews.CommonGoalModelView;
import it.polimi.myshelfie.model.modelviews.FullGameModelView;
import it.polimi.myshelfie.model.modelviews.PlayerModelView;
import it.polimi.myshelfie.view.ViewBuffer;
import it.polimi.myshelfie.view.textual.ANSI;
import it.polimi.myshelfie.view.textual.Terminal;

import java.util.List;

public class GamePage extends ScreenPage{
    String myUsername;
    String currentLibraryOwner;
    public GamePage(Terminal t, ViewBuffer viewBuffer, List<String> inputParameters, String username, String libraryOwner) {
        super(t, viewBuffer, inputParameters);
        this.myUsername = username;
        this.currentLibraryOwner = libraryOwner;
    }

    @Override
    public void render() {
        FullGameModelView fullGameModelView = viewBuffer.getGameModelView();
        if(fullGameModelView ==null) return;
        t.clearScreen();
        t.printLivingRoom(fullGameModelView);
        t.printDivider(20);
        printCommonGoals(fullGameModelView);
        t.printDivider(26);
        t.printPersonalGoal(fullGameModelView.getPlayer(myUsername).getPersonalGoals().get(0).getGoal(),27,4);
        t.printVerticalDivider(0,40, 20);
        t.printVerticalDivider(27, 30, 7);
        printGameInfo(fullGameModelView);
        t.printBookshelf(fullGameModelView.getPlayer(currentLibraryOwner).getBookshelf(),currentLibraryOwner,27,36);
        if(!fullGameModelView.getCurrentPlayer().equals(myUsername)) {
            t.printAction("help, chat");
        } else {
            switch (fullGameModelView.getCurrentState()){
                case DRAW,LASTDRAW -> t.printAction("help, chat, d <args>");
                case ORGANIZE,LASTORGANIZE -> t.printAction("help, chat, o <args>");
                case INSERT,LASTINSERT -> t.printAction("help, chat, i <column>");
            }
        }
        printBorder();
    }

    private void printGameInfo(FullGameModelView fullGameModelView) {
        int pos = 1;
        t.printInInfoTable("Phase : " + ANSI.BLUE_FG+ fullGameModelView.getCurrentState()+ANSI.RESET, pos);
        pos++;
        t.printInInfoTable("Playing : "+ fullGameModelView.getCurrentPlayer(), pos);
        pos += 4;
        int idx = 1;
        for (PlayerModelView p : fullGameModelView.getPlayers()) {
            if(p.getName().equals(myUsername)){
                t.printInInfoTable("   "+idx+". "+ ANSI.PINK_FG+ANSI.BOLD+p.getName()+ANSI.RESET+" "+ p.getPoints(),pos);
            } else {
                t.printInInfoTable("   "+idx+". "+p.getName() + " " + p.getPoints(), pos);
            }
            idx++;
            pos++;
        }
        if(fullGameModelView.getCurrentPlayer().equals(myUsername)) {
            List<ItemTile> takenTiles = fullGameModelView.getPlayer(myUsername).getTakenTiles();
            if (takenTiles.size() != 0) {
                t.printInInfoTable("Taken Tiles :", 14);
                t.printTakenTiles(takenTiles, 16, 43, fullGameModelView.getCurrentState() == GameState.ORGANIZE);
            }
        } else {
            List<ItemTile> currenPlayerTakenTiles = fullGameModelView.getPlayer(fullGameModelView.getCurrentPlayer()).getTakenTiles();
            if(currenPlayerTakenTiles.size() != 0){
                t.printInInfoTable(fullGameModelView.getCurrentPlayer()+" taken tiles :",14);
                t. printTakenTiles(currenPlayerTakenTiles, 16, 43, false);
            }
        }
        t.printInInfoTable("Remaining tiles : " + fullGameModelView.getRemainingTiles(), 19);
    }

    private void printCommonGoals(FullGameModelView gmv) {
        t.println("Common Goals :",21,1);
        int idx = 1;
        for (CommonGoalModelView c : gmv.getCommonGoals()) {
            t.println(c.getId() + ". :" + c.getDescription().split("\n")[0],21+idx,1);
            t.print("Tokens : ",22+idx,2);
            t.printTokens(c.getTokens(),22+idx,2+10);
            idx+=2;
        }
    }
}
