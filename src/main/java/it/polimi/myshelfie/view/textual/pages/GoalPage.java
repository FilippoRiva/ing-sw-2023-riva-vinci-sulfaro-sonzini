package it.polimi.myshelfie.view.textual.pages;

import it.polimi.myshelfie.model.modelviews.CommonGoalModelView;
import it.polimi.myshelfie.model.modelviews.FullGameModelView;
import it.polimi.myshelfie.view.ViewBuffer;
import it.polimi.myshelfie.view.textual.Terminal;

import java.util.List;

public class GoalPage extends ScreenPage{
    public GoalPage(Terminal t, ViewBuffer viewBuffer, List<String> inputParameters) {
        super(t, viewBuffer, inputParameters);
    }

    @Override
    public void render() {
        t.clearScreen();
        FullGameModelView gmv = viewBuffer.getGameModelView();
        List<CommonGoalModelView> goals = gmv.getCommonGoals();
        int line = 3;
        for(CommonGoalModelView goal : goals){
            t.centerPrint(goal.getDescription(),line++);
            t.printTokens(goal.getTokens(),line++,3);
        }
        t.printAction("help, chat, game");
        printBorder();
    }
}
