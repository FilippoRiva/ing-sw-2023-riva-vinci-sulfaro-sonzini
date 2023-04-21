package it.polimi.myshelfie.view.textual.pages;

import it.polimi.myshelfie.view.ViewBuffer;
import it.polimi.myshelfie.view.textual.Terminal;

import java.util.List;

public abstract class ScreenPage {
    Terminal t;
    ViewBuffer viewBuffer;
    List<String> inputParameters;
    public ScreenPage(Terminal t, ViewBuffer viewBuffer, List<String> inputParameters){
        this.t = t;
        this.viewBuffer = viewBuffer;
        this.inputParameters = inputParameters;
    }
    public abstract void render();

    public void printBorder(){
        t.printDivider(0);
        t.printDivider(40);
        t.printDivider(t.INPUT_LINE);
        t.printDivider(t.INPUT_LINE+t.INPUT_HEIGHT-1);
        t.printVerticalDivider(0,0,40);
        t.printVerticalDivider(0,63,40);
        t.print("┌",0,0);
        t.print("┐",0,63);
        t.print("└",40,0);
        t.print("┘",40,63);
        t.print("├",t.INPUT_LINE,0);
        t.print("├",t.INPUT_LINE+2,0);
        t.print("┤",t.INPUT_LINE,t.WIDTH);
        t.print("┤",t.INPUT_LINE,t.WIDTH);
    }
}
