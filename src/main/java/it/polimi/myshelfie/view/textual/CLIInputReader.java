package it.polimi.myshelfie.view.textual;

import it.polimi.myshelfie.view.InternalViewState;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class CLIInputReader {
    public final BufferedReader in;
    private final CLIInputInterpreter interpreter;
    private final TextualUI UI;

    public CLIInputReader(TextualUI textualUI, List<String> inputParameters) {
        this.UI = textualUI;
        this.in = new BufferedReader(new InputStreamReader(System.in));
        this.interpreter = new CLIInputInterpreter(textualUI, inputParameters);
    }

    void read() {
        while (!Thread.currentThread().isInterrupted()) {
            synchronized (this) {
                String input;
                try {
                    input = in.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (UI.getInternalViewState() != InternalViewState.STOPPED) interpreter.interpret(input);
                else return;
            }
        }
    }
}
