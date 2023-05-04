package it.polimi.myshelfie.view.textual.pages;

import it.polimi.myshelfie.model.modelviews.LobbyModelView;
import it.polimi.myshelfie.view.ViewBuffer;
import it.polimi.myshelfie.view.textual.ANSI;
import it.polimi.myshelfie.view.textual.Terminal;

import java.util.List;

public class LobbyPage extends ScreenPage{
    String username;
    public LobbyPage(Terminal t, ViewBuffer viewBuffer, List<String> inputParameters, String username) {
        super(t, viewBuffer, inputParameters);
        this.username = username;
    }

    @Override
    public void render() {
        LobbyModelView lobbyModelView = viewBuffer.getLobbyModelView();
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
        if(viewBuffer.getLobbyModelView().isRoomOwner(username))t.printAction("help, chat, exit, +, -, s");
        else t.printAction("help, chat, exit");
        printBorder();
    }
}
