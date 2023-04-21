package it.polimi.myshelfie.view.textual.pages;

import it.polimi.myshelfie.model.modelviews.ModelView;
import it.polimi.myshelfie.view.ViewBuffer;
import it.polimi.myshelfie.view.textual.Terminal;

import java.util.List;

public class JoinPage extends ScreenPage{
    public JoinPage(Terminal t, ViewBuffer viewBuffer, List<String> inputParameters) {
        super(t, viewBuffer, inputParameters);
    }

    @Override
    public void render() {
        t.clearScreen();
        t.printTitle();
        switch (inputParameters.size()){
            case 0 -> {
                t.centerPrint("Choose a lobby number" + " :",20);
                printAvailableLobbies();
            }
            case 1 -> t.centerPrint("Choose an username for " + inputParameters.get(0) + " :",20);
            case 2 -> t.centerPrint("Joining " + inputParameters.get(0),20);
        }
        printBorder();
    }

    public void printAvailableLobbies() {
        ModelView modelView = viewBuffer.getModelView();
        if (modelView.getLobbies().size() == 0) {
            t.centerPrint("none",21);
            return;
        }
        int idx = 1;
        for (String lobby : modelView.getLobbies()) {
            t.centerPrint(idx + ". " + lobby,21+idx);
            idx++;
            if(idx>13) break;
        }
    }
}
