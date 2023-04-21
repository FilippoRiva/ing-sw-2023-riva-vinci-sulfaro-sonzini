package it.polimi.myshelfie.view.textual.pages;

import it.polimi.myshelfie.view.ViewBuffer;
import it.polimi.myshelfie.view.textual.Terminal;

import java.util.List;

public class WinPage extends ScreenPage{
    public WinPage(Terminal t, ViewBuffer viewBuffer, List<String> inputParameters) {
        super(t, viewBuffer, inputParameters);
    }

    @Override
    public void render() {
        t.clearScreen();
        t.println("someone has won!",1,1);
        printBorder();
    }
}
