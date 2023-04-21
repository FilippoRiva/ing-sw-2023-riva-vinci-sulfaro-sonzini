package it.polimi.myshelfie.view.textual.pages;

import it.polimi.myshelfie.view.ViewBuffer;
import it.polimi.myshelfie.view.textual.Terminal;

import java.util.List;

public class HelpPage extends ScreenPage{

    public HelpPage(Terminal t, ViewBuffer viewBuffer, List<String> inputParameters) {
        super(t, viewBuffer, inputParameters);
    }

    @Override
    public void render() {
        t.clearScreen();
        t.print("help : lists all commands", 1, 1);
        t.print("chat : goes to chat mode",2,1);
        t.print("game : goes to game mode",3 ,1);
        t.print("Game Commands :", 4, 1);
        t.print("n        : shows the next player's library",5,3);
        t.print("p        : shows the previous player's library", 6, 3);
        t.print("d <args> : selects the tiles you want to draw",7,3);
        t.print("           given x and y coordinates.",8,3);
        t.print("           Example : \"c -1 3 0 3 1 3\" will draw positions ",9,3);
        t.print("                      (-1,3) (0,3) (1,3)",10,3);
        t.print("o <args> : order the drawn tiles",11,3);
        t.print("           Example : \"o 0 2 1\" will swap the second",12,3);
        t.print("                     and the third drawn tile  ",13,3);
        t.print("i <args> : inserts the ordered tiles into the given column",14,3);
        t.print("           Example : \"i 1\" will insert the tiles into the ",15,3);
        t.print("                     first column",16,3);
        t.print("Chat Commands : ", 20, 1);
        t.print("<message> : to send a message to everyone ",21 ,3 );
        t.print("/<username> <message> : to send a message to a player ",22,3);
        t.print("Utils : ", 27, 1);
        t.print("r : refreshes the UI",28,1);
        t.printAction("game, chat");
        printBorder();
    }
}
