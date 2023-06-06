package it.polimi.myshelfie.view.textual;

import it.polimi.myshelfie.view.InternalViewState;
import it.polimi.myshelfie.view.ViewManager;

import java.io.*;
import java.util.List;

public class CLIInputReader {
    public final BufferedReader in;
    private final CLIInputInterpreter interpreter;
    private final TextualUI UI;
    public CLIInputReader(TextualUI textualUI, ViewManager viewManager, List<String> inputParameters) {
        this.UI = textualUI;
        this.in = new BufferedReader(new InputStreamReader(System.in));
        this.interpreter = new CLIInputInterpreter(textualUI, viewManager,inputParameters);
    }

    void read(){
        while(!Thread.currentThread().isInterrupted()){
            synchronized (this){
                String input;
                try {
                    input = in.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if(UI.getInternalViewState() != InternalViewState.STOPPED) interpreter.interpret(input);
                else return;
                }
            }
    }
}
