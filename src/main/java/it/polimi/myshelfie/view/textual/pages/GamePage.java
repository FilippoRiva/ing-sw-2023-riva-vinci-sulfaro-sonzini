package it.polimi.myshelfie.view.textual.pages;

import it.polimi.myshelfie.model.GameState;
import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.modelviews.CommonGoalModelView;
import it.polimi.myshelfie.model.modelviews.GameModelView;
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
        GameModelView gameModelView = viewBuffer.getGameModelView();
        if(gameModelView==null) return;
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
        if(!gameModelView.getCurrentPlayer().equals(myUsername)) {
            t.printAction("help, chat");
        } else {
            switch (gameModelView.getCurrentState()){
                case DRAW,LASTDRAW -> {
                    t.printAction("help, chat, d <args>");
                }
                case ORGANIZE,LASTORGANIZE -> {
                    t.printAction("help, chat, o <args>");
                }
                case INSERT,LASTINSERT -> {
                    t.printAction("help, chat, i <column>");
                }
            }
        }
        printBorder();
    }

    private void printGameInfo(GameModelView gameModelView) {
        int pos = 1;
        t.printInInfoTable("Phase : " + ANSI.BLUE_FG+gameModelView.getCurrentState()+ANSI.RESET, pos);
        pos++;
        t.printInInfoTable("Playing : "+gameModelView.getCurrentPlayer(), pos);
        pos += 4;
        int idx = 1;
        for (PlayerModelView p : gameModelView.getPlayers()) {
            if(p.getName().equals(myUsername)){
                t.printInInfoTable("   "+idx+". "+ ANSI.PINK_FG+ANSI.BOLD+p.getName()+ANSI.RESET+" "+ p.getPoints(),pos);
            } else {
                t.printInInfoTable("   "+idx+". "+p.getName() + " " + p.getPoints(), pos);
            }
            idx++;
            pos++;
        }
        if(gameModelView.getCurrentPlayer().equals(myUsername)) {
            List<ItemTile> takenTiles = gameModelView.getPlayer(myUsername).getTakenTiles();
            if (takenTiles.size() != 0) {
                t.printInInfoTable("Taken Tiles :", 14);
                t.printTakenTiles(takenTiles, 16, 43, gameModelView.getCurrentState() == GameState.ORGANIZE);
            }
        } else {
            List<ItemTile> currenPlayerTakenTiles = gameModelView.getPlayer(gameModelView.getCurrentPlayer()).getTakenTiles();
            if(currenPlayerTakenTiles.size() != 0){
                t.printInInfoTable(gameModelView.getCurrentPlayer()+" taken tiles :",14);
                t. printTakenTiles(currenPlayerTakenTiles, 16, 43, false);
            }
        }
        t.printInInfoTable("Remaining tiles : " + gameModelView.getRemainingTiles(), 19);
    }

    private void printCommonGoals(GameModelView gmv) {
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
