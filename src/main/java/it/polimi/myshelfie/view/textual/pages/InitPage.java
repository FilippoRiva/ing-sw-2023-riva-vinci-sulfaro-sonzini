package it.polimi.myshelfie.view.textual.pages;

import it.polimi.myshelfie.model.modelviews.ModelView;
import it.polimi.myshelfie.view.textual.Terminal;

import java.util.List;

/**
 * Represents the init page for the textualUI.
 */
public class InitPage extends ScreenPage {

    /**
     * Initialises a InitPage.
     * @param t the terminal in which we want to render the page.
     * @param inputParameters the shared input parameters between the TextualUI.
     */
    public InitPage(Terminal t, List<String> inputParameters) {
        super(t, inputParameters);
    }

    /**
     * Renders the init page inside the Screen portion of the Terminal.
     */
    @Override
    public void render() {
        ModelView modelView = viewManager.getModelView();
        if (modelView == null) return;
        t.clearScreen();
        t.printTitle();
        if (modelView.getLobbies().size() != 0) {
            t.centerPrint("1. create", 20);
            t.centerPrint("2. join", 21);
            t.printAction("close, 1, 2");
        } else {
            t.centerPrint("1. create", 20);
            t.printAction("close, 1");
        }
        printBorder();
    }
}
