package it.polimi.myshelfie.view.textual.pages;

import it.polimi.myshelfie.model.modelviews.LobbyModelView;
import it.polimi.myshelfie.view.ViewBuffer;
import it.polimi.myshelfie.view.textual.Terminal;

import java.util.List;

public class LobbyPage extends ScreenPage{
    public LobbyPage(Terminal t, ViewBuffer viewBuffer, List<String> inputParameters) {
        super(t, viewBuffer, inputParameters);
    }

    @Override
    public void render() {
        LobbyModelView lobbyModelView = viewBuffer.getLobbyModelView();
        if(lobbyModelView == null) return;
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
        printBorder();
    }
}
