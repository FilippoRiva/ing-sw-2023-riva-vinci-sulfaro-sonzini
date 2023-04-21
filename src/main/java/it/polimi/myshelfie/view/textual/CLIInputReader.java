package it.polimi.myshelfie.view.textual;

import it.polimi.myshelfie.view.ViewBuffer;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class CLIInputReader {
    boolean on;
    BufferedReader in;
    CLIInputInterpreter interpreter;
    public CLIInputReader(BufferedReader in, TextualUI textualUI, ViewBuffer viewBuffer, List<String> inputParameters) {
        this.in = in;
        this.interpreter = new CLIInputInterpreter(textualUI,viewBuffer,inputParameters);
        this.on = true;
        read();
    }

    private void read(){
        while(on){
            String input="";
            try {
                input = in.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            interpreter.interpret(input);
        }
    }
}
