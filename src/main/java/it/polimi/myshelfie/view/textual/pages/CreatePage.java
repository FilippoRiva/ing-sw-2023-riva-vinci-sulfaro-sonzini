package it.polimi.myshelfie.view.textual.pages;

import it.polimi.myshelfie.view.textual.Terminal;

import java.util.List;


/**
 * Represents the create page for the textualUI.
 */
public class CreatePage extends ScreenPage {

    /**
     * Initialises a CreatePage.
     * @param t the terminal in which we want to render the page.
     * @param inputParameters the shared input parameters between the TextualUI.
     */
    public CreatePage(Terminal t, List<String> inputParameters) {
        super(t, inputParameters);
    }

    /**
     * Renders the create page inside the Screen portion of the Terminal.
     */
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
