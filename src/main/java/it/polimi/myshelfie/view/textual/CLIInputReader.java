package it.polimi.myshelfie.view.textual;

import it.polimi.myshelfie.view.ViewManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class CLIInputReader {
    private boolean on;
    private final BufferedReader in;
    private final CLIInputInterpreter interpreter;
    public CLIInputReader(TextualUI textualUI, ViewManager viewManager, List<String> inputParameters) {
        this.in = new BufferedReader(new InputStreamReader(System.in));
        this.interpreter = new CLIInputInterpreter(textualUI, viewManager,inputParameters);
        this.on = true;
        read();
    }

    private void read(){
        while(on){
            synchronized (this){
                String input;
                try {
                    input = in.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                interpreter.interpret(input);
            }
        }
    }

    public synchronized void stop(){
        this.on = false;
    }
}
