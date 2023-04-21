package it.polimi.myshelfie.view.textual.pages;

import it.polimi.myshelfie.model.modelviews.ModelView;
import it.polimi.myshelfie.view.ViewBuffer;
import it.polimi.myshelfie.view.textual.Terminal;

import java.util.List;

public class InitPage extends ScreenPage{

    public InitPage(Terminal t, ViewBuffer viewBuffer, List<String> inputParameters) {
        super(t, viewBuffer, inputParameters);
    }

    @Override
    public void render() {
        t.clearScreen();
        t.printTitle();
        ModelView modelView = viewBuffer.getModelView();
        if (modelView!=null && modelView.getLobbies().size() != 0) {
            t.centerPrint("1. create",20);
            t.centerPrint("2. join",21);
        } else {
            t.centerPrint("1. create",20);
        }
        printBorder();
    }
}
