package it.polimi.myshelfie.view.textual;

import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.Token;
import it.polimi.myshelfie.model.modelviews.FullGameModelView;

import java.io.PrintStream;
import java.util.List;


/**
 * Represents the Terminal.
 */
public class Terminal {
    public final boolean ANSI_ON;
    public final int WIDTH = 63;
    public final int SCREEN_HEIGHT = 33;
    public final int INPUT_HEIGHT = 3;
    public final int INPUT_LINE = SCREEN_HEIGHT + 1;
    public final int ACTION_LINE = INPUT_LINE + INPUT_HEIGHT;
    public final int EXCEPTION_LINE = ACTION_LINE + 1;
    public final int INPUT_COLUMN = 15;
    public final int INFO_POS = 43;
    private final PrintStream out;
    private final String titleColor = ANSI.PINK_FG;
    public final String[] title =
            {titleColor + "  __  __          _____ _          _  __ _      \n" + ANSI.RESET,
                    titleColor + " |  \\/  |        / ____| |        | |/ _(_)     \n" + ANSI.RESET,
                    titleColor + " | \\  / |_   _  | (___ | |__   ___| | |_ _  ___ \n" + ANSI.RESET,
                    titleColor + " | |\\/| | | | |  \\___ \\| '_ \\ / _ \\ |  _| |/ _ \\\n" + ANSI.RESET,
                    titleColor + " | |  | | |_| |  ____) | | | |  __/ | | | |  __/\n" + ANSI.RESET,
                    titleColor + " |_|  |_|\\__, | |_____/|_| |_|\\___|_|_| |_|\\___|\n" + ANSI.RESET,
                    titleColor + "          __/ |                                 \n" + ANSI.RESET,
                    titleColor + "         |___/                                  \n" + ANSI.RESET};


    /**
     * Constructor.
     */
    public Terminal() {
        out = System.out;
        String ANSI_ON_str = System.getenv("ANSI_ON");
        if (ANSI_ON_str == null) ANSI_ON = false;
        else ANSI_ON = ANSI_ON_str.equals("true");
        if (ANSI_ON) {
            clearAll();
            clearInput();
        }
    }


    /**
     * Prints the title.
     */
    public void printTitle() {
        int idx = 0;
        for (String s : title) {
            print(s, 1 + idx, 8);
            idx++;
        }
        moveToInput();
    }


    /**
     * Prints an error message.
     * @param message the error message.
     */
    public void printError(String message) {
        if (ANSI_ON) {
            positionCursor(EXCEPTION_LINE, 1);
            out.print(ANSI.RED_FG + message + ANSI.RESET);
        } else out.println("! " + message);
        moveToInput();
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ignored) {
            }
            clearError();
        }).start();
    }


    /**
     * Prints a permanent error message.
     * @param message the error message.
     */
    public void printPermanentError(String message) {
        if (ANSI_ON) {
            positionCursor(EXCEPTION_LINE, 1);
            out.print(ANSI.RED_FG + message + ANSI.RESET);
        } else out.println("! " + message);
        moveToInput();
    }


    /**
     * Prints an action.
     * @param s the string for the action.
     */
    public void printAction(String s) {
        positionCursor(ACTION_LINE, 1);
        eraseLine();
        println(s);
        moveToInput();
    }

    private void print(String message) {
        out.print(message);
    }


    /**
     * Prints a message at a specific line and column
     * @param message the message.
     * @param line the line.
     * @param column the column.
     */
    public void print(String message, int line, int column) {
        positionCursor(line, column);
        print(message);
        moveToInput();
    }

    private void println(String message) {
        out.println(message);
        moveToInput();
    }


    /**
     * Prints a message at a specific line and column.
     * @param message the message to print.
     * @param line the line.
     * @param column the column.
     */
    public void println(String message, int line, int column) {
        positionCursor(line, column);
        println(message);
    }

    /**
     * Prints a string in the middle of the screen
     * @param message the String to print
     * @param line the line at which we want to print the message.
     */
    public void centerPrint(String message, int line) {
        if (message.length() > (WIDTH - 2)) message = message.substring(0, WIDTH - 2);
        positionCursor(line, WIDTH / 2 - message.length() / 2);
        println(message);
    }

    /**
     * Prints a string in the middle of the screen
     * Used for colored messages where the actual length of the message is not the length of the string
     * @param message the String to print
     * @param line the line at which we want to print the message.
     */
    public void centerPrint(String message, int line, int messageLength) {
        if (messageLength > (WIDTH - 2)) message = message.substring(0, WIDTH - 2);
        positionCursor(line, WIDTH / 2 - messageLength / 2);
        println(message);
    }

    /**
     * Prints a Message in the middle of the screen. If the message is wider the screen width
     * it prints just a substring of it.
     * @param message the message to print
     * @param line the line at which we want to print the message.
     * @param messageLength the message length.
     */
    public void printMessage(String message, int line, int messageLength) {
        if (messageLength > (WIDTH - 2)) {
            message = message.substring(0, WIDTH - 2);
            messageLength = WIDTH - 2;
        }
        positionCursor(line, WIDTH / 2 - messageLength / 2);
        println(message);
    }


    /**
     * Prints a divider at a specific line.
     * @param line the line where to print the divider.
     */
    public void printDivider(int line) {
        positionCursor(line, 0);
        if (ANSI_ON) {
            print("█");
            for (int i = 0; i < WIDTH - 1; i++) {
                print("─");
            }
            print("█");
        }
        moveToInput();
    }


    /**
     * Prints a vertical divider at a specific line and column, given the height.
     * @param line the line.
     * @param column then column.
     * @param height the height.
     */
    public void printVerticalDivider(int line, int column, int height) {
        if (ANSI_ON) {
            positionCursor(line, column);
            print("█");
            cursorDown(1);
            cursorLeft(1);
            for (int i = 0; i < height - 2; i++) {
                print("│");
                cursorDown(1);
                cursorLeft(1);
            }
            print("█");
        }
        moveToInput();
    }

    /**
     * Prints a String into the Info Table of the GamePage
     * @param info the info string
     * @param pos the info line
     */
    public void printInInfoTable(String info, int pos) {
        println(info, pos, INFO_POS);
        moveToInput();
    }

    private void printTile(ItemTile tile) {
        if (tile == null) {
            print("│   ");
        } else {
            print("│ ");
            switch (tile.getType()) {
                case CAT -> print(ANSI.GREEN_FG + "■" + ANSI.RESET);
                case BOOK -> print(ANSI.RESET + "■" + ANSI.RESET);
                case GAME -> print(ANSI.YELLOW_FG + "■" + ANSI.RESET);
                case FRAME -> print(ANSI.BLUE_FG + "■" + ANSI.RESET);
                case TROPHY -> print(ANSI.LIGHTBLUE_FG + "■" + ANSI.RESET);
                case PLANT -> print(ANSI.PINK_FG + "■" + ANSI.RESET);
                case EMPTY -> print(" ");
            }
            print(" ");
        }
    }


    /**
     * Prints the List of ItemTiles.
     * @param takenTiles the takenTiles to print.
     * @param line the line at which the takenTiles are to be printed.
     * @param column the column at which the taken Tiles are to be printed.
     * @param printNumbers true if we want to print the numbers to the right of the taken tile representation.
     */
    public void printTakenTiles(List<ItemTile> takenTiles, int line, int column, boolean printNumbers) {
        positionCursor(line, column);
        for (int i = takenTiles.size() - 1; i >= 0; i--) {
            printTile(takenTiles.get(i));
            if (printNumbers) print("| " + i);
            else print("|");
            positionCursor(++line, column);
        }
    }

    /**
     * Prints a bookshelf at a certain line and column.
     * @param bookshelf the bookshelf to print.
     * @param currentPlayer the owner of the bookshelf.
     * @param line the line at which the bookshelf is to be printed.
     * @param column the column at which the bookshelf is to be printed.
     */
    public void printBookshelf(ItemTile[][] bookshelf, String currentPlayer, int line, int column) {
        String formattedCurrentPlayer = currentPlayer.length() > 10 ? currentPlayer.substring(0, 9) : currentPlayer;
        println("Bookshelf : " + formattedCurrentPlayer, line, column);
        int maxY = bookshelf.length;
        int maxX = bookshelf[0].length;
        for (int y = 0; y < maxY; y++) {
            positionCursor(line + 1 + y, column);
            for (int x = 0; x < maxX; x++) {
                printTile(bookshelf[y][x]);
            }
            println("│");
        }
        positionCursor(line + 1 + maxY, column);
        print("└");
        for (int i = 0; i < maxX - 1; i++) print("───┴");
        print("───┘");
        positionCursor(line + 2 + maxY, column);
        for (int i = 0; i < maxX; i++) print("  " + i + " ");
    }

    /**
     * Prints a personalGoal at a certain line and column.
     * @param goal the goal to print.
     * @param line the line at which the goal has to be printed.
     * @param column the column at which the goal has to be printed.
     */
    public void printPersonalGoal(ItemTile[][] goal, int line, int column) {
        positionCursor(line, column);
        println("Personal Goal :");
        int idx = 0;
        for (int y = goal.length - 1; y >= 0; y--) {
            positionCursor(line + 1 + idx, column);
            for (int x = 0; x < goal[0].length; x++) {
                printTile(goal[y][x]);
            }
            println("│");
            idx++;
        }
        positionCursor(line + 1 + goal.length, column);
        print("└");
        for (int i = 0; i < goal[0].length - 1; i++) print("───┴");
        print("───┘");
        moveToInput();
    }

    /**
     * Prints only the first token of a certain list of token in a certain line-column position
     * @param tokens the tokens list
     * @param line the line at which the token is to be printed
     * @param column the column at which the token is to be printed
     */
    public void printTopToken(List<Token> tokens, int line, int column) {
        positionCursor(line, column);
        if (tokens.size() == 0) {
            print("none");
            moveToInput();
            return;
        }
        Token token = tokens.get(tokens.size() - 1);
        print(" │ " + token.getValue() + " │ ");
        print("\n");
        moveToInput();
    }

    /**
     * Prints a list of token in a certain line-column position
     * @param tokens the tokens list
     * @param line the line at which the token is to be printed
     * @param column the column at which the token is to be printed
     */
    public void printAllTokens(List<Token> tokens, int line, int column) {
        positionCursor(line, column);
        if (tokens.size() == 0) {
            cursorLeft(2);
            print("none");
            moveToInput();
            return;
        }
        for (Token tok : tokens) {
            print(" │ " + tok.getValue());
        }
        print(" │ \n");
        moveToInput();
    }

    /**
     * Prints the living room at 1 1 position.
     * @param fullGameModelView the game model view containing the living room.
     */
    public void printLivingRoom(FullGameModelView fullGameModelView) {
        int size = fullGameModelView.getLivingRoom().length * 4;
        positionCursor(1, 1);
        print("  ");
        for (int i = -4; i < 5; i++) {
            if (i < 0) print(" " + i + " ");
            if (i >= 0) print("  " + i + " ");
        }
        println("");
        positionCursor(2, 3);
        print("┌");
        printFirstLine(size);
        int idx = 1;
        for (int y = 4; y >= -4; y--) {
            positionCursor(2 + idx, 1);
            if (y >= 0) print(" " + y);
            else print(String.valueOf(y));
            for (int x = -4; x <= 4; x++) {
                printTile(fullGameModelView.getTile(x, y));
            }
            println("│");
            idx++;
            positionCursor(2 + idx, 1);
            if (y != -4) {
                print("  ├");
                printInternalLine(size);
            } else {
                print("  └");
                printLastLine(size);
            }
            idx++;
        }
        moveToInput();
    }

    private synchronized void printFirstLine(int length) {
        for (int i = 0; i < (length - 2) / 4; i++) out.print("───┬");
        out.print("───┐\n");
    }

    private synchronized void printInternalLine(int length) {
        for (int i = 0; i < (length - 2) / 4; i++) out.print("───┼");
        out.print("───┤\n");
    }

    private synchronized void printLastLine(int length) {
        for (int i = 0; i < (length - 2) / 4; i++) out.print("───┴");
        out.print("───┘\n");
    }

    /**
     * Clears the entire Terminal.
     */
    public void clearAll() {
        out.print(ANSI.CLEAR_ALL);
    }

    /**
     * Clears only the top portion of the screen
     */
    public void clearScreen() {
        eraseLines();
        moveToInput();
    }

    /**
     * Clears the input lines. Excluding the borders.
     */
    public synchronized void clearInput() {
        eraseLine(INPUT_LINE + 1, INPUT_COLUMN, WIDTH - INPUT_COLUMN - 1);
        if (ANSI_ON) print(">", INPUT_LINE + 1, INPUT_COLUMN - 1);
        moveToInput();
    }


    /**
     * Clears the error lines. Excluding the borders.
     */
    public void clearError() {
        positionCursor(EXCEPTION_LINE, 0);
        eraseLine();
        if (!ANSI_ON) return;
        print("│", EXCEPTION_LINE, 0);
        print("│", EXCEPTION_LINE, WIDTH);
        moveToInput();
    }

    private void eraseLines() {
        goOnTop();
        cursorDown(0);
        for (int i = 0; i < 34; i++) {
            eraseLine(i, 0, WIDTH);
        }
        moveToInput();
    }

    private void eraseLine() {
        if (ANSI_ON) out.print(ANSI.ERASE_LINE);
    }

    private void eraseLine(int line, int column, int width) {
        if (ANSI_ON) {
            goOnTop();
            cursorDown(line);
            if (column == 0 && width == WIDTH) {
                eraseLine();
                return;
            }
            cursorRight(column);
            String blank = "";
            for (int i = 0; i < width; i++) blank = blank.concat(" ");
            out.print(blank);
        }
    }

    private void cursorDown(int lines) {
        if (lines == 0) return;
        if (ANSI_ON) {
            for (int i = 0; i < lines; i++) out.print(ANSI.CURSOR_DOWN);
        }
    }

    private void cursorRight(int times) {
        if (times == 0) return;
        if (ANSI_ON) {
            out.print("\033[" + times + "C");
        }
    }

    private void cursorLeft(int times) {
        if (times == 0) return;
        if (ANSI_ON) {
            for (int i = 0; i < times; i++) out.print(ANSI.CURSOR_LEFT);
        }
    }

    private void positionCursor(int line, int column) {
        if (ANSI_ON) {
            goOnTop();
            cursorDown(line);
            cursorRight(column);
        }
    }

    private void goOnTop() {
        if (ANSI_ON) out.print(ANSI.HOME);
    }

    private void moveToInput() {
        if (ANSI_ON) positionCursor(INPUT_LINE + INPUT_HEIGHT / 2, INPUT_COLUMN);
    }
}
