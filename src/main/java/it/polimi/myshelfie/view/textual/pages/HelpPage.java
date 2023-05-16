package it.polimi.myshelfie.view.textual.pages;

import it.polimi.myshelfie.view.ViewBuffer;
import it.polimi.myshelfie.view.ViewType;
import it.polimi.myshelfie.view.textual.ANSI;
import it.polimi.myshelfie.view.textual.Terminal;

import java.util.List;

public class HelpPage extends ScreenPage{

    String color = ANSI.PINK_FG;
    public HelpPage(Terminal t, ViewBuffer viewBuffer, List<String> inputParameters) {
        super(t, viewBuffer, inputParameters);
    }

    @Override
    public void render() {
        t.clearScreen();
        int line = 1;
        t.print(color+"Modes"+ANSI.RESET,line++,1);
        t.print("help  : lists all commands", line++, 3);
        t.print("chat  : goes to chat mode",line++,3);
        t.print("lobby : goes to lobby mode",line++, 3);
        t.print("game  : goes to game mode",line++ ,3);
        t.printDivider(line++);
        t.print(color+"Mode : Lobby "+ANSI.RESET,line++,1);
        t.print("+    : increases room size up to 4",line++,3);
        t.print("-    : decreases room size down to 2",line++,3);
        t.print("s    : starts the game if possible",line++,3);
        t.print("exit: leave lobby",line++,3);
        t.printDivider(line++);
        t.print(color+"Mode : Game "+ ANSI.RESET, line++, 1);
        t.print("n        : shows the next player's library",line++,3);
        t.print("p        : shows the previous player's library", line++, 3);
        t.print("d <args> : selects the tiles you want to draw",line++,3);
        t.print("           given x and y coordinates.",line++,3);
        t.print("           Example : \"c -1 3 0 3 1 3\" will draw positions ",line++,3);
        t.print("                      (-1,3) (0,3) (1,3)",line++,3);
        t.print("o <args> : order the drawn tiles",line++,3);
        t.print("           Example : \"o 0 2 1\" will swap the second",line++,3);
        t.print("                     and the third drawn tile  ",line++,3);
        t.print("i <args> : inserts the ordered tiles into the given column",line++,3);
        t.print("           Example : \"i 1\" will insert the tiles into the ",line++,3);
        t.print("                     first column",line++,3);
        t.printDivider(line++);
        t.print(color+"Mode : Chat Commands"+ANSI.RESET, line++, 1);
        t.print("<message> : to send a message to everyone ",line++ ,3 );
        t.print("/<username> <message> : to send a message to a player ",line++,3);
        t.printDivider(line++);
        t.print(color+"Utils"+ANSI.RESET, line++, 1);
        t.print("r : refreshes the UI",line,3);
        if(viewBuffer.getViewType()!= ViewType.LOBBY) t.printAction("game, chat, goals");
        else t.printAction("lobby,chat");
        printBorder();
    }
}
