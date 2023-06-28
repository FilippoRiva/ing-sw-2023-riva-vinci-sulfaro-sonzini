package it.polimi.myshelfie.view.textual.pages;

import it.polimi.myshelfie.view.textual.Terminal;

import java.util.List;

public class CreatePage extends ScreenPage {
    public CreatePage(Terminal t, List<String> inputParameters) {
        super(t, inputParameters);
    }

    @Override
    public void render() {
        t.clearScreen();
        t.printTitle();
        switch (inputParameters.size()) {
            case 0 -> {
                t.centerPrint("Choose a lobby name", 20);
                t.printAction("back, <lobby name>");
            }
            case 1 -> {
                t.centerPrint("Choose an username for joining " + inputParameters.get(0), 20);
                t.printAction("back, <username>");
            }
            case 2 -> {
                t.centerPrint("Creating " + inputParameters.get(0), 20);
                t.printAction("back");
            }
        }
        printBorder();
    }
}
