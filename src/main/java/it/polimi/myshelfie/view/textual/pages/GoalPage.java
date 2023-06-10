package it.polimi.myshelfie.view.textual.pages;

import it.polimi.myshelfie.model.modelviews.CommonGoalModelView;
import it.polimi.myshelfie.model.modelviews.FullGameModelView;
import it.polimi.myshelfie.view.textual.ANSI;
import it.polimi.myshelfie.view.textual.Terminal;

import java.util.List;

public class GoalPage extends ScreenPage{
    public GoalPage(Terminal t, List<String> inputParameters) {
        super(t, inputParameters);
    }

    @Override
    public void render() {
        FullGameModelView gmv = viewManager.getGameModelView();
        if(gmv == null) return;
        t.clearScreen();
        List<CommonGoalModelView> goals = gmv.getCommonGoals();
        t.centerPrint(ANSI.PINK_FG+"COMMON GOALS"+ANSI.RESET,1,12);
        t.printDivider(2);
        int line = 3;
        int number = 1;
        for(CommonGoalModelView goal : goals){
            String description = goal.getDescription();
            String[] descriptionLines = description.split("\n");
            t.centerPrint(number++ +"'s description :",line++);
            for(String descriptionLine : descriptionLines){
                t.centerPrint(descriptionLine,line++);
            }
            t.printAllTokens(goal.getTokens(),line++,30-4*goal.getTokens().size()/2);
            t.printDivider(line++);
        }
        t.printAction("help, chat, game");
        printBorder();
    }
}
