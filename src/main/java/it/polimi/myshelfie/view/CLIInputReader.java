package it.polimi.myshelfie.view;

import java.io.BufferedReader;
import java.io.IOException;

public class CLIInputReader {
    //TODO this class is just an EXAMPLE
    boolean on = true;
    BufferedReader in;
    TextualUI UI;
    public CLIInputReader(BufferedReader in, TextualUI textualUI) {
        this.in = in;
        this.UI= textualUI;
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
            UI.input(input);
        }
    }
}
