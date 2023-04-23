package it.polimi.myshelfie.view.textual.pages;

import it.polimi.myshelfie.model.modelviews.GameModelView;
import it.polimi.myshelfie.model.modelviews.PlayerModelView;
import it.polimi.myshelfie.view.ViewBuffer;
import it.polimi.myshelfie.view.textual.ANSI;
import it.polimi.myshelfie.view.textual.Terminal;

import java.util.List;

public class WinPage extends ScreenPage{
    public WinPage(Terminal t, ViewBuffer viewBuffer, List<String> inputParameters) {
        super(t, viewBuffer, inputParameters);
    }

    @Override
    public void render() {
        t.clearScreen();
        GameModelView gmv = viewBuffer.getGameModelView();
        List<PlayerModelView> players = gmv.getPlayers();
        players = players.stream().sorted((x,y)->{
            if(x.getPoints()==y.getPoints()) return 0;
            else return x.getPoints()>=y.getPoints() ? -1:1 ;
        }).toList();
        t.centerPrint(ANSI.PINK_FG+"Game Ended"+ANSI.RESET,1,10);
        int idx = 1;
        for(PlayerModelView player : players){
            t.centerPrint(idx+". "+player.getName()+" "+player.getPoints(),3+idx);
            idx++;
        }
        t.printAction("exit");
        printBorder();
    }
}
