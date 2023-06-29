package it.polimi.myshelfie.view.textual.pages;

import it.polimi.myshelfie.model.modelviews.ModelView;
import it.polimi.myshelfie.view.textual.Terminal;

import java.util.List;

/**
 * Represents the join page for the textualUI.
 */
public class JoinPage extends ScreenPage {

    /**
     * Initialises a JoinPage.
     * @param t the terminal in which we want to render the page.
     * @param inputParameters the shared input parameters between the TextualUI.
     */
    public JoinPage(Terminal t, List<String> inputParameters) {
        super(t, inputParameters);
    }


    /**
     * Renders the join page inside the Screen portion of the Terminal.
     */
    @Override
    public void render() {
        t.clearScreen();
        t.printTitle();
        switch (inputParameters.size()) {
            case 0 -> {
                t.centerPrint("Choose a lobby number" + " :", 20);
                printAvailableLobbies();
            }
            case 1 -> {
                t.centerPrint("Choose an username for " + inputParameters.get(0) + " :", 20);
                t.printAction("back, <username>");
            }
            case 2 -> {
                t.centerPrint("Joining " + inputParameters.get(0), 20);
                t.printAction("back");
            }
        }
        printBorder();
    }

    private void printAvailableLobbies() {
        ModelView modelView = viewManager.getModelView();
        if (modelView == null) {
            t.centerPrint("none", 21);
            return;
        }
        List<String> lobbies = modelView.getLobbies();
        if (lobbies == null || lobbies.size() == 0) {
            t.centerPrint("none", 21);
            return;
        }
        int idx = 1;
        for (String lobby : lobbies) {
            t.centerPrint(
                    idx + ". " + lobby + " " + modelView.getCurrentSize(lobby) + "/" + modelView.getMaxSize(lobby),
                    21 + idx);
            idx++;
            if (idx > 13) break;
        }
        t.printAction("back, [1, ..., " + lobbies.size() + "]");
    }
}
