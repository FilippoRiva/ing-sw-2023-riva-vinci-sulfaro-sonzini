package it.polimi.myshelfie.view.textual;

import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.Token;
import it.polimi.myshelfie.model.modelviews.GameModelView;

import java.io.PrintStream;
import java.util.List;

public class Terminal {
    PrintStream out;
    boolean ANSI_ON;
    int WIDTH = 63;
    int SCREEN_HEIGHT = 33;
    int INPUT_HEIGHT = 3;
    int ACTION_HEIGHT = 1;
    int ERROR_HEIGHT = 1;
    int HEIGHT = SCREEN_HEIGHT + INPUT_HEIGHT + ACTION_HEIGHT + ERROR_HEIGHT-1;
    int INPUT_LINE = SCREEN_HEIGHT +1;
    int INPUT_COLUMN = 15;
    int ACTION_LINE = INPUT_LINE+INPUT_HEIGHT;
    int EXCEPTION_LINE= ACTION_LINE+1;
    int INFOPOS = 41;
    public Terminal(){
        out = System.out;
        String ANSI_ON_str = System.getenv("ANSI_ON");
        if(ANSI_ON_str == null) ANSI_ON = false;
        else ANSI_ON = ANSI_ON_str.equals("true");
        if(ANSI_ON) {
            clearAll();
            clearInput();
        }
    }

    public void printTitle(String title){
        positionCursor(0,WIDTH/2-title.length());
        if(ANSI_ON)println(ANSI.BOLD+title.toUpperCase()+ANSI.RESET);
        else println(title.toUpperCase());
        moveToInput();
    }
    public void printError(String message) {
        if(ANSI_ON) {
            positionCursor(EXCEPTION_LINE,0);
            out.print(ANSI.RED_FG +message+ANSI.RESET);
        }
        else out.println("! "+message);
        moveToInput();
    }
    public void printAction(String s) {
        positionCursor(ACTION_LINE,0);
        eraseLine();
        println(s);
        moveToInput();
    }
    private void print(String message){
        out.print(message);
    }
    public void print(String message,int line, int column){
        positionCursor(line,column);
        print(message);
    }
    private void println(String message){
        out.println(message);
        moveToInput();
    }
    public void println(String message,int line, int column){
        positionCursor(line,column);
        println(message);
    }

    public void printDivider(int line) {
        positionCursor(line,0);
        if(ANSI_ON) for(int i = 0; i<WIDTH ; i++) {
            print(ANSI.WHITE_BG+" "+ANSI.RESET);
        }
        moveToInput();
    }

    public void printVerticalDivider(int line, int column, int height){
        if(ANSI_ON) {
            goOnTop();
            positionCursor(line,column);
            for(int i = 0;i<height;i++){
                print(ANSI.WHITE_BG+" "+ANSI.RESET);
                cursorDown(1);
                cursorLeft(1);
            }
        }
        moveToInput();
    }

    public void printInInfoTable(String info,int pos){
        println(info,pos,INFOPOS);
        moveToInput();
    }

    private void printTile(ItemTile tile) {
        if(tile == null){
            print("|   ");
        } else {
            print("| ");
            switch (tile.getType()){
                case CAT -> print(ANSI.GREEN_BG+"C"+ANSI.RESET);
                case BOOK -> print(ANSI.WHITE_BG+"B"+ANSI.RESET);
                case GAME -> print(ANSI.YELLOW_BG+"G"+ANSI.RESET);
                case FRAME -> print(ANSI.BLUE_BG+"F"+ANSI.RESET);
                case TROPHY -> print(ANSI.LIGHTBLUE_BG+"T"+ANSI.RESET);
                case PLANT -> print(ANSI.PINK_BG+"P"+ANSI.RESET);
                case EMPTY -> print(" ");
            }
            print(" ");
        }
    }

    public void printBookshelf(ItemTile[][] bookshelf, String currentPlayer,int line, int column) {
        println("Bookshelf : " + currentPlayer,line,column);
        for (int y = 0; y < 6; y++) {
            positionCursor( line + 1 + y,column);
            for (int x = 0; x < 5; x++) {
                printTile(bookshelf[y][x]);
            }
            println("|");
        }
    }

    public void printPersonalGoal(ItemTile[][] goal,int line, int column) {
        positionCursor(line,column);
        println("Personal Goal :");
        int idx=0;
        for (int y = goal.length - 1; y >= 0; y--) {
            positionCursor(line+1+idx,column);
            for (int x = 0; x < goal[0].length; x++) {
                printTile(goal[y][x]);
            }
            println("|");
            idx++;
        }
        moveToInput();
    }

    public void printTokens(List<Token> tokens,int line, int column){
        positionCursor(line,column);
        print(" | ");
        for (Token tok : tokens) {
            print(tok.getValue() + " | ");
        }
        print("\n");
        moveToInput();
    }

    public void printLivingRoom(GameModelView gameModelView) {
        //TODO make this print position dependant
        int size = gameModelView.getLivingRoom().length*4;
        goOnTop();
        print("   ");
        for (int i = -4; i < 5; i++) {
            if (i < 0) print(" " + i + " ");
            if (i >= 0) print("  " + i + " ");
        }
        println("");
        positionCursor(1,2);
        printLine(size);
        int idx = 1;
        for (int y = 4; y >= -4; y--) {
            positionCursor(1+idx,0);
            if (y >= 0) print(" " + y);
            else print(String.valueOf(y));
            for (int x = -4; x <= 4; x++) {
                printTile(gameModelView.getTile(x, y));
            }
            println("|");
            idx++;
            positionCursor(1+idx,0);
            print("  -");
            printLine(size);
            idx++;
        }
        moveToInput();
    }

    private synchronized void printLine(int length) {
        for (int i = 0; i < length; i++) out.print("-");
        out.print("\n");
    }


    public void clearAll(){
        out.print(ANSI.CLEAR_ALL);
        clearInput();
    }
    public void clearScreen(){
        eraseLines(0, SCREEN_HEIGHT);
        moveToInput();
    }

    public synchronized void clearInput() {
        eraseLines(INPUT_LINE,INPUT_LINE+INPUT_HEIGHT-1);
        printDivider(INPUT_LINE);
        if(ANSI_ON) print(">",INPUT_LINE+1,INPUT_COLUMN-1);
        printDivider(INPUT_LINE+2);
    }


    public void clearError() {
        positionCursor(EXCEPTION_LINE,0);
        eraseLine();
        moveToInput();
    }

    public synchronized void clearAction(){
        positionCursor(ACTION_LINE,0);
        eraseLine();
        moveToInput();
    }

    private void eraseLines(int begin, int end){
        if(end<begin) return;
        eraseLines(begin,end,0,WIDTH);
    }

    private void eraseLines(int begin, int end, int column, int width){
        goOnTop();
        cursorDown(begin);
        for(int i = 0; i<end-begin; i++){
            eraseLine(begin+i,column,width);
        }
        moveToInput();
    }

    private void eraseLine(){
        if(ANSI_ON) out.print(ANSI.ERASE_LINE);
    }
    private void eraseLine(int line,int column, int width){
        if(ANSI_ON){
            goOnTop();
            cursorDown(line);
            if(column == 0 && width == WIDTH) {
                eraseLine();
                return;
            }
            cursorRight(column);
            String blank = "";
            for(int i =0; i<width; i++) blank = blank.concat(" ");
            out.print(blank);
        }
    }

    private void cursorUp(int lines){
        if(lines==0) return;
        if(ANSI_ON){
            for(int i = 0; i<lines; i++) out.print(ANSI.CURSOR_UP);
        }
    }
    private void cursorDown(int lines){
        if(lines==0) return;
        if(ANSI_ON){
            for(int i = 0; i<lines; i++) out.print(ANSI.CURSOR_DOWN);
        }
    }
    private void cursorRight(int times) {
        if(times==0) return;
        if(ANSI_ON) {
            out.print("\033["+times+"C");
        }
    }

    private void cursorLeft(int times){
        if(times==0) return;
        if(ANSI_ON){
            for(int i =0; i<times;i++) out.print(ANSI.CURSOR_LEFT);
        }
    }

    private void positionCursor(int line, int column){
        if(ANSI_ON){
            goOnTop();
            cursorDown(line);
            cursorRight(column);
        }
    }
    private void goOnTop() {
        if(ANSI_ON) out.print(ANSI.HOME);
    }
    private void goToBottom() {
        positionCursor(HEIGHT,0);
    }

    private void moveToInput(){
        if(ANSI_ON) positionCursor(INPUT_LINE+INPUT_HEIGHT/2,INPUT_COLUMN);
    }
}
