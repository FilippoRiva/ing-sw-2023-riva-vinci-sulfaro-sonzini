package it.polimi.myshelfie.view.textual.pages;

import it.polimi.myshelfie.model.modelviews.LobbyModelView;
import it.polimi.myshelfie.view.ViewManager;
import it.polimi.myshelfie.view.textual.ANSI;
import it.polimi.myshelfie.view.textual.Terminal;

import java.util.List;

public class LobbyPage extends ScreenPage{
    public LobbyPage(Terminal t, List<String> inputParameters) {
        super(t, inputParameters);
    }

    @Override
    public void render() {
        LobbyModelView lobbyModelView = viewManager.getLobbyModelView();
        if(lobbyModelView == null) return;
        t.clearScreen();
        t.centerPrint(lobbyModelView.getName()+" " +
                lobbyModelView.getPlayers().size()+"/"+lobbyModelView.getGameConfig().getNumPlayers(),2);
        int idx = 1;
        for (String player : lobbyModelView.getPlayers()) {
            if(lobbyModelView.isRoomOwner(player)) t.centerPrint(
                    idx + ". " + ANSI.PINK_FG +player+ANSI.RESET,
                    4+idx,
                    player.length()+3);
            else t.centerPrint(idx + ". " + player,4+idx);
            idx++;
        }
        if(viewManager.getLobbyModelView().isRoomOwner(ViewManager.getUsername()))t.printAction("help, chat, exit, +, -, s");
        else t.printAction("help, chat, exit");
        printBorder();
    }
}
