package it.polimi.myshelfie.view.textual.pages;

import it.polimi.myshelfie.model.EarnedPoint;
import it.polimi.myshelfie.model.PointType;
import it.polimi.myshelfie.model.Token;
import it.polimi.myshelfie.model.modelviews.FullGameModelView;
import it.polimi.myshelfie.model.modelviews.PlayerModelView;
import it.polimi.myshelfie.view.ViewManager;
import it.polimi.myshelfie.view.textual.ANSI;
import it.polimi.myshelfie.view.textual.Terminal;

import java.util.List;

public class WinPage extends ScreenPage{
    public WinPage(Terminal t, ViewManager viewManager, List<String> inputParameters) {
        super(t, viewManager, inputParameters);
    }

    @Override
    public void render() {
        t.clearScreen();
        FullGameModelView gmv = viewManager.getGameModelView();
        List<PlayerModelView> players = gmv.getPlayers();
        players = players.stream().sorted((x,y)->{
            if(x.getPoints()==y.getPoints()) return 0;
            else return x.getPoints()>=y.getPoints() ? -1:1 ;
        }).toList();
        t.centerPrint(ANSI.PINK_FG+"Game Ended"+ANSI.RESET,1,10);
        int line = 4;
        for(PlayerModelView player : players){
            if(line==4) t.centerPrint(ANSI.YELLOW_FG+player.getName()+ANSI.RESET+" : "+player.getPoints()+" pts",line++,
                    (player.getName()+player.getPoints()).length()+7);
            else t.centerPrint(ANSI.GRAY_FG+player.getName()+ANSI.RESET+" "+player.getPoints()+" pts",line++,
                    (player.getName()+player.getPoints()).length()+7);
            for(Token tok : player.getTokens()){
                if(tok.getRomanNumeral()==0) t.centerPrint("End Game Token = " + tok.getValue(),line++);
                else t.centerPrint("Common Goal "+ tok.getRomanNumeral()+" = "+tok.getValue(),line++);
            }
            t.centerPrint("Personal Goals = " + player.getEarnedPoints().stream().filter(x->x.getType()==PointType.PERSONAL).mapToInt(EarnedPoint::getValue).sum(),line++);
            t.centerPrint("Adjacent Tiles = " + player.getEarnedPoints().stream().filter(x->x.getType()== PointType.ADJACENT).mapToInt(EarnedPoint::getValue).sum(),line++);
            line++;
        }
        t.printAction("exit");
        printBorder();
    }
}
