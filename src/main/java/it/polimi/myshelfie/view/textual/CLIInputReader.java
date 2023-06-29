package it.polimi.myshelfie.view.textual;

import it.polimi.myshelfie.view.InternalViewState;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * The CLIInputReader is a class that constantly reads from System.in and sends the read lines to a CLIInputInterpreter
 * in order to process them.
 */
public class CLIInputReader {

    public final BufferedReader in;
    private final CLIInputInterpreter interpreter;
    private final TextualUI UI;

    /**
     * Constructor
     * @param textualUI the textualUI to be associated with this CLIInputReader
     * @param inputParameters the shared List of read parameters between the TextualUI and the InputReader
     */
    public CLIInputReader(TextualUI textualUI, List<String> inputParameters) {
        this.UI = textualUI;
        this.in = new BufferedReader(new InputStreamReader(System.in));
        this.interpreter = new CLIInputInterpreter(textualUI, inputParameters);
    }

    /**
     * This method reads from System.in and sends the read line to the interpreter.
     * If the Thread has been interrupted it returns.
     * If the UI has been STOPPED it returns.
     */
    public void read() {
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
